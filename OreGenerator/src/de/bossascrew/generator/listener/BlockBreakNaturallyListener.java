package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;

import de.bossascrew.generator.data.DataManager;

public class BlockBreakNaturallyListener implements Listener {

	@EventHandler
	public void onBreak(BlockDestroyEvent event) {
        if(event.getBlock().getType() == Material.BLAST_FURNACE){
        	if(DataManager.getInstance().getGenerator(event.getBlock().getLocation()) != null) event.setCancelled(true);
        }
	}
}
