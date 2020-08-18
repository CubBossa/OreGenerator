package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.bossascrew.generator.data.DataManager;

public class BlockBreakNaturallyListener implements Listener{

	@EventHandler
	public void onBreak(EntityExplodeEvent event) {
        for (Block block : event.blockList().toArray(new Block[event.blockList().size()])){
            if(block.getType() == Material.BLAST_FURNACE){
            	if(DataManager.getInstance().getGenerator(block.getLocation()) != null) event.setCancelled(true);
            }
        }
	}
}
