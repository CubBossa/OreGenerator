package de.bossascrew.generator.listener;

import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;

import de.bossascrew.generator.data.DataManager;

public class InventoryMoveListener implements Listener {

	@EventHandler
	public void onItemMove(InventoryMoveItemEvent e) {
		
		if(e.getDestination().getType() == InventoryType.BLAST_FURNACE) {
			BlockState b = ((BlockState)e.getDestination().getHolder());
			if(DataManager.getInstance().getGenerator(b.getLocation()) != null) {
				e.setCancelled(true);
			}
		}
	}
}
