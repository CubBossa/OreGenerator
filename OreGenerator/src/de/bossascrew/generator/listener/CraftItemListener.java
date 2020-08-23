package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import de.bossascrew.generator.data.Permission;

public class CraftItemListener implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent event) {
		if(event.getResult() != null) {
			if(event.getRecipe().getResult().getType() == Material.BLAST_FURNACE) {
				if(event.getInventory().contains(new ItemStack(Material.LAVA_BUCKET))) {
					if(event.getView().getPlayer() instanceof Player) {
						if(!((Player) event.getView().getPlayer()).hasPermission(Permission.CRAFT_GENERATOR)) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
