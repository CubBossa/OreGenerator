package de.bossascrew.generator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.tr7zw.nbtapi.NBTItem;

public class InventoryInteractListener implements Listener {

	
	@EventHandler
	public void invClick(InventoryClickEvent e) {

		if(e.getCurrentItem() == null) return;
		if(e.getView().getTitle() != null && !e.getView().getTitle().equals(Generator.GUI_TITLE)) return;
		
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
