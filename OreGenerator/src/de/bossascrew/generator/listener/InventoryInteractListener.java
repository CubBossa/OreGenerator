package de.bossascrew.generator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryInteractListener implements Listener {

	
	@EventHandler
	public void invClick(InventoryClickEvent e) {

		//TODO pr�fen ob NBT tag plugin:oregenerator
		//TODO pr�fen, in welchem GUI man sich gerade befindet
		//TODO nbt guiitem		
		String buttonNbt = "";
		switch (buttonNbt) {
		case "level":
			//TODO gucken ob dieses level gekauft wurde oder kauff�hig ist
			break;
		case "drop":
			//TODO diesen sepzifischen generator droppen
			break;
		}
	}
}
