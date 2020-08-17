package de.bossascrew.generator.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.events.OreGenerationEvent;
import de.bossascrew.generator.utils.Dimension;
import de.bossascrew.generator.utils.RandomDistribution;
import de.bossascrew.generator.utils.Level;
import de.bossascrew.generator.utils.Ore;

public class BlockFormListener implements Listener {

	
	@EventHandler
	public void onMelt2(BlockFromToEvent event) {
		
		if(event.isCancelled()) return;
		if(!event.getBlock().isLiquid()) return;
		
		Block to = event.getToBlock();
        if (generates(event.getBlock(), to)) {
            if(setRandomOres(to.getLocation())) {
            	event.setCancelled(true);
            }
            return;
        } else {
            BlockFace[] nesw = {BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            for (BlockFace face : nesw) {
                if (generates(event.getBlock(), to.getRelative(face))) {
                    if(setRandomOres(to.getLocation())) {
                    	event.setCancelled(true);
                    }
                    return;
                }
            }
        }
	}
	
	public boolean setRandomOres(Location loc) {
		Dimension d = Dimension.OVERWORLD;
		for(Dimension dd : Dimension.values()) {
			if(dd.getWorlds().contains(loc.getWorld().getName())) d = dd;
		}
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
    		return true;
        }
        return false;
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
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for(int z = -1; z < 2; z++) {
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
		if(b.getCustomName() != null && b.getCustomName().equalsIgnoreCase(Generator.NBT_GENERATOR_CODENAME)) {
			return DataManager.getInstance().getGenerator(loc);
		}
		return null;
	}
	
	public boolean generates(Block from, Block to) {
        if (!to.isLiquid()) return false;
        return generates(from.getType(), to.getType());
	}

    private boolean generates(Material from, Material to) {
        return from != to;
    }
}
