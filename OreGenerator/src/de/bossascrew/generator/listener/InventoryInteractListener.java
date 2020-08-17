package de.bossascrew.generator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.tr7zw.nbtapi.NBTItem;

public class InventoryInteractListener implements Listener {

	
	@EventHandler
	public void invClick(InventoryClickEvent e) {

		//TODO prüfen ob NBT tag plugin:oregenerator
		//TODO prüfen, in welchem GUI man sich gerade befindet
		//TODO nbt guiitem
		if(e.getCurrentItem() == null) return;
		NBTItem item = new NBTItem(e.getCurrentItem());
		int generatorId = item.getInteger("generatorid");
		GeneratorObject g = DataManager.getInstance().getGenerator(generatorId);
		
		String action = item.getString("action");
		int clickedLevel = item.getInteger("level");
		switch (action) {
		case "level":
			if(clickedLevel+1 == g.getLevel()) {
				g.tryUpgrade(clickedLevel);
			}
			break;
		case "drop":
			g.drop();
			break;
		}
	}
}
