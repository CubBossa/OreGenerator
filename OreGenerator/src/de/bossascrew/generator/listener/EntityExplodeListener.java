package de.bossascrew.generator.listener;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import de.bossascrew.generator.data.DataManager;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onBreak(EntityExplodeEvent event) {
        for (Block block : new ArrayList<Block>(event.blockList())) {
	        if(block.getType() == Material.BLAST_FURNACE){
	        	System.out.println(block.getLocation());
	        	if(DataManager.getInstance().getGenerator(block.getLocation()) != null) event.blockList().remove(block);
	        }
        }
	}
}
