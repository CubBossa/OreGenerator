package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.Inventories.ConfirmGUI;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.Message;
import de.bossascrew.generator.data.Permission;
import de.tr7zw.nbtapi.NBTItem;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void invClick(InventoryClickEvent event) {

		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
		if(!(event.getView().getPlayer() instanceof Player)) return;
		
		NBTItem item = new NBTItem(event.getCurrentItem());
		int id = item.getInteger(Generator.NBT_GENERATORID_KEY);
		GeneratorObject g = DataManager.getInstance().getGenerator(id);
		
		Player p = (Player) event.getView().getPlayer();
		
		if(event.getView().getTitle() != null && event.getView().getTitle().equals(Message.GUI_TITLE)) {
			event.setCancelled(true);

			String action = item.getString(Generator.NBT_ACTION_KEY);
			int clickedLevel = item.getInteger(Generator.NBT_LEVEL_KEY);
			switch (action) {
			case Generator.NBT_ACTION_VALUE_LEVELINFO:
				if(clickedLevel > g.getLevel()) {
					if(!p.hasPermission(Permission.LEVEL + clickedLevel)) {
						p.sendMessage(Message.NO_PERMISSION);
					} else {
						if(clickedLevel == g.getLevel()+1) {
							p.openInventory(new ConfirmGUI(g, clickedLevel).getInv());
						} else {
							deny(p);
						}
					}
				}
				break;
			case Generator.NBT_ACTION_VALUE_DROP:
				if(!p.hasPermission(Permission.DROP_GENERATOR)) {
					p.sendMessage(Message.NO_PERMISSION);
				} else {
					g.drop();
				}
				break;
			}
		} else if(event.getView().getTitle() != null && event.getView().getTitle().equals(Message.GUI_CONFIRM_TITLE)) {
			event.setCancelled(true);
			p.closeInventory();
			g.open(p);
			switch(item.getString(Generator.NBT_ACTION_KEY)) {
			case Generator.NBT_ACTION_VALUE_CONFIRM:
				if(g.tryUpgrade(item.getInteger(Generator.NBT_LEVEL_KEY))) {
					g.refreshGUI();
				} else {
					deny(p);
				}
				break;
			case Generator.NBT_ACTION_VALUE_DENY:
				p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
				break;
			}				
		}
	}
	
	public void deny(Player p) {
		p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
		p.sendMessage(Message.CANT_AFFORD_LEVEL);
	}
}
