package de.bossascrew.generator.listener;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

import de.bossascrew.generator.data.DataManager;

public class InventoryMoveItemListener implements Listener {

	@EventHandler
	public void onItemMove(InventoryMoveItemEvent event) {
		
		if(event.getDestination().getType() == InventoryType.BLAST_FURNACE) {
			BlockState b = ((BlockState)event.getDestination().getHolder());
			if(DataManager.getInstance().getGenerator(b.getLocation()) != null) {
				event.setCancelled(true);
			}
		}
	}
}
