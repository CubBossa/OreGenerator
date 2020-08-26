package de.bossascrew.generator.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.util.Vector;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.events.OreGenerationEvent;
import de.bossascrew.generator.utils.Dimension;
import de.bossascrew.generator.utils.RandomDistribution;
import de.bossascrew.generator.utils.Level;
import de.bossascrew.generator.utils.Ore;

public class BlockFromToListener implements Listener {

	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onMelt2(BlockFromToEvent event) {
		
		if(event.isCancelled()) return;
		if(!event.getBlock().isLiquid()) return;
		
		boolean lava = event.getBlock().getType() == Material.LAVA;
		
		Block to = event.getToBlock();
        if (generates(event.getBlock(), to)) {
            if(setRandomOres(to.getLocation())) {
            	//eingefügt
            	if(lava) event.setCancelled(true);
            }
            return;
        } else {
            BlockFace[] nesw = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            for (BlockFace face : nesw) {
                if (generates(event.getBlock(), to.getRelative(face))) {
                    if(setRandomOres(!lava ? to.getRelative(face).getLocation() : to.getLocation())) {
                    	if(lava) event.setCancelled(true);
                    }
                    return;
                }
            }
            if(generates(event.getBlock(), to.getRelative(BlockFace.DOWN))) {
            	if(to.getRelative(BlockFace.DOWN).isLiquid() && ((Levelled) to.getRelative(BlockFace.DOWN).getBlockData()).getLevel() != 0) {
            		if(setRandomOres(to.getRelative(BlockFace.DOWN).getLocation())) {
            		}
            	}
            }
        }
	}
	
	public boolean generates(Block from, Block to) {
        if (to.isLiquid()) {
        	if(!Dimension.OVERWORLD.isDimension(from.getWorld().getName())) return false;
            return generates(from.getType(), to.getType());
        } else {
        	if(!Dimension.NETHER.isDimension(from.getWorld().getName())) return false;
        	return generatesNether(from.getType(), to.getType());
        }
	}

    private boolean generates(Material from, Material to) {
        return from != to;
    }

    private boolean generatesNether(Material from, Material to) {
        return from == Material.LAVA && (to == Material.BLUE_ICE || to == Material.PACKED_ICE);
    }
    
	public boolean setRandomOres(Location loc) {
		
		Dimension d = Dimension.OVERWORLD;
		for(Dimension dd : Dimension.values()) {
			if(dd.getWorlds().contains(loc.getWorld().getName())) d = dd;
		}
		//Liste ausgeben
		GeneratorObject g = seemsGeneratorNear(loc);
		Material m = Material.STONE;
		if(g == null) {
			m = calcOres(0, d);
		} else {
			m = calcOres(g.getLevel(), d);
		}
        OreGenerationEvent generateEvent = new OreGenerationEvent(g == null ? null : g.getFurnace().getLocation(), m, g);
        Generator.getInstance().getServer().getPluginManager().callEvent(generateEvent);
        
        if(!generateEvent.isCancelled()) {
    		loc.getWorld().getBlockAt(loc).setType(m);
    		particlesAndSounds(loc, g == null ? null : g.getFurnace().getLocation(), m);
    		return true;
        }
        return false;
	}
	
	private void particlesAndSounds(Location loc, Location genLoc, Material m) {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(loc, Sound.BLOCK_LAVA_EXTINGUISH, 1.0F, 1.0F);
			for(int i = 0; i < 3; i++) {
				p.spawnParticle(Particle.SMOKE_LARGE, loc.clone().add(new Vector(Math.random(), 1, Math.random())), 0, 0.0, 0.01, 0.0);
			}
			if(genLoc != null) 
				for(int i = 0; i < 3; i++) {
					p.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, genLoc.clone().add(new Vector(Math.random(), 1, Math.random())), 0, 0.0, 0.01, 0.0);
				}
			if(genLoc != null && m != Material.STONE) {
				p.playSound(genLoc, Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
				p.playSound(genLoc, Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 1.0f);
			}
		}if(genLoc == null) return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Generator.getInstance(), new Runnable() {
			@Override
			public void run() {
				if(m != Material.STONE) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(genLoc, Sound.BLOCK_PISTON_CONTRACT, 1.0f, 1.0f);
					}
				}
			}
		}, 10L);
	}
	
	private Material calcOres(int level, Dimension d) {
		Level levelValue = null;
		for(Level l : Level.values()) {
			if(l.getLevel() == level)
				levelValue = l;
		}

		RandomDistribution randGen = new RandomDistribution();
		for(Ore o : levelValue.getOres(d)) {
			if(o.prob != 0)
				randGen.addNumber(o.mat, o.prob);
		}
		return randGen.getDistributedRandomNumber();
	}
	
	public GeneratorObject seemsGeneratorNear(Location loc) {
		int generatorRange = Generator.getInstance().getCfg().getGeneratorrange();
		for(int x = -generatorRange; x <= generatorRange; x++) {
			for(int y = -generatorRange; y <= generatorRange; y++) {
				for(int z = -generatorRange; z <= generatorRange; z++) {
					if(loc.clone().add(x, y, z).getBlock().getType() == Material.BLAST_FURNACE) {
						GeneratorObject g = isGenerator(loc.clone().add(x,y,z));
	                    return g;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param loc
	 * @return null if is no generator, else the generator at given location
	 */
	public GeneratorObject isGenerator(Location loc) {
		BlastFurnace b = (BlastFurnace) loc.getBlock().getState();
		if(b == null) return null;
		return DataManager.getInstance().getGenerator(loc);
	}
}
