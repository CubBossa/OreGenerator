package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import de.bossascrew.generator.crafting.Crafting;

public class PrepareItemCraftListener implements Listener {

	@EventHandler
	public void prepareCraft(PrepareItemCraftEvent event) {
		
		Recipe r = event.getRecipe();
		if(r == null) return;
		if(r.getResult() == null) return;
		if(event.isRepair()) return;
		if(r.getResult().getType() != Material.BLAST_FURNACE) return;
		
		if(event.getInventory().contains(new ItemStack(Material.DIRT))) {
			if(event.getView().getPlayer() instanceof Player) {
				Player p = (Player) event.getView().getPlayer();
				event.getRecipe().getResult().setItemMeta(Crafting.getGeneratorItem(p.getUniqueId()).getItemMeta());
			}
		}
		//TODO vom vercraften abhalten
	}
}
