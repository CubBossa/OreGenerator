package de.bossascrew.generator.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.Inventories.ConfirmGUI;
import de.bossascrew.generator.data.DataManager;
import de.tr7zw.nbtapi.NBTItem;

public class InventoryInteractListener implements Listener {

	
	@EventHandler
	public void invClick(InventoryClickEvent e) {

		if(e.getCurrentItem() == null) return;
		if(!(e.getView().getPlayer() instanceof Player)) return;
		
		NBTItem item = new NBTItem(e.getCurrentItem());
		int generatorId = item.getInteger(Generator.NBT_GENERATORID_KEY);
		GeneratorObject g = DataManager.getInstance().getGenerator(generatorId);
		
		Player p = (Player) e.getView().getPlayer();
		
		if(e.getView().getTitle() != null && e.getView().getTitle().equals(Generator.GUI_TITLE)) {
			e.setCancelled(true);

			String action = item.getString(Generator.NBT_ACTION_KEY);
			int clickedLevel = item.getInteger(Generator.NBT_LEVEL_KEY);
			switch (action) {
			case Generator.NBT_ACTION_VALUE_LEVELINFO:
				if(clickedLevel > g.getLevel()) {
					if(clickedLevel == g.getLevel()+1) {
						p.openInventory(new ConfirmGUI(g, clickedLevel).getInv());
					} else {
						deny(p);
					}
				}
				break;
			case Generator.NBT_ACTION_VALUE_DROP:
				g.drop();
				break;
			}
		} else if(e.getView().getTitle() != null && !e.getView().getTitle().equals(Generator.GUI_CONFIRM_TITLE)) {
			e.setCancelled(true);
			switch(item.getString(Generator.NBT_ACTION_KEY)) {
			case Generator.NBT_ACTION_VALUE_CONFIRM:
				if(g.tryUpgrade(item.getInteger(Generator.NBT_LEVEL_KEY))) {
					g.refreshGUI();
				} else {
					deny(p);
				}
				break;
			case Generator.NBT_ACTION_VALUE_DENY:
				//TODO play sound hewwwww
				break;
			}				
			p.closeInventory();
			g.open(p);
		}
		
	}
	
	public void deny(Player p) {
		//TODO Villager sound
		p.sendMessage(Generator.CANT_AFFORD_LEVEL);
	}
}
