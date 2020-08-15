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
        	event.setCancelled(true);
        	setRandomOres(to.getLocation());
            return;
        } else {
            BlockFace[] nesw = {BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            for (BlockFace face : nesw) {
                if (generates(event.getBlock(), to.getRelative(face))) {
                    event.setCancelled(true);
                    setRandomOres(to.getLocation());
                    return;
                }
            }
        }
		
	}
	
	public void setRandomOres(Location loc) {
		Dimension d = Dimension.OVERWORLD;
		for(Dimension dd : Dimension.values()) {
			if(dd.getWorlds().contains(loc.getWorld().getName())) d = dd;
		}
		loc.getWorld().getBlockAt(loc).setType(calcOres(seemsGeneratorNear(loc), d));
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
	
	public int seemsGeneratorNear(Location loc) {
		for(int x = -1; x < 2; x++) {
			for(int y = -1; y < 2; y++) {
				for(int z = -1; z < 2; z++) {
					if(loc.clone().add(x, y, z).getBlock().getType() == Material.BLAST_FURNACE) {
						return isGeneratorNear(loc.clone().add(x,y,z));
					}
				}
			}
		}
		return 0;
	}
	
	public int isGeneratorNear(Location loc) {
		BlastFurnace b = (BlastFurnace) loc.getBlock();
		if(b == null) return 0;
		if(b.getCustomName().equalsIgnoreCase(Generator.GENERATOR_NAME)) {
			return 1;
			//TODO Database Check and return level
		}
		return 0;
	}
	
	
	public boolean generates(Block from, Block to) {
        if (!to.isLiquid()) return false;
        return generates(from.getType(), to.getType());
	}

    private boolean generates(Material from, Material to) {
        return from != to;
    }
}
