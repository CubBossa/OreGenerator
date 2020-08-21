package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import de.bossascrew.generator.data.Permission;

public class CraftListener implements Listener {

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if(e.getResult() != null) {
			if(e.getRecipe().getResult().getType() == Material.BLAST_FURNACE) {
				if(e.getInventory().contains(new ItemStack(Material.LAVA_BUCKET))) {
					if(e.getView().getPlayer() instanceof Player) {
						if(!((Player) e.getView().getPlayer()).hasPermission(Permission.CRAFT_GENERATOR)) {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
}
