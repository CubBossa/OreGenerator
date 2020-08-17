package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.bossascrew.generator.Generator;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if(e.getBlock() != null && e.getBlock().getType() == Material.BLAST_FURNACE) {
			if(((BlastFurnace) e.getBlock().getState()).getCustomName().equals(Generator.NBT_GENERATOR_CODENAME)) {
				e.setCancelled(true);
			}
		}
	}
}
