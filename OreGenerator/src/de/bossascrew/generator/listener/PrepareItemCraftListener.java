package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import de.bossascrew.generator.crafting.Crafting;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.Message;
import de.bossascrew.generator.data.Permission;

public class PrepareItemCraftListener implements Listener {

	@EventHandler
	public void prepareCraft(CraftItemEvent event) {
		
		Recipe r = event.getRecipe();
		if(r == null) return;
		if(r.getResult() == null) return;
		if(r.getResult().getType() == Material.BLAST_FURNACE) {
			if(event.getInventory().contains(new ItemStack(Material.DIRT))) {
				if(event.getView().getPlayer() instanceof Player) {
					Player p = (Player) event.getView().getPlayer();
					if(p.hasPermission(Permission.CRAFT_GENERATOR)) {
						if(DataManager.getInstance().getGenerators(p.getUniqueId()).size() < 2) {
							event.getRecipe().getResult().setItemMeta(Crafting.getGeneratorItem(p.getUniqueId()).getItemMeta());
						} else {
							p.sendMessage(Message.MAXIMUM_GENERATORS_CRAFTED);
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
