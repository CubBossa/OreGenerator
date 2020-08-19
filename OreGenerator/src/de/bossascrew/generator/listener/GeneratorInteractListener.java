package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;

public class GeneratorInteractListener implements Listener {

	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		if(event.getClickedBlock() != null) {
			if(event.getClickedBlock().getType() == Material.BLAST_FURNACE) {
				BlastFurnace bf = (BlastFurnace) event.getClickedBlock().getState();
				Player p = event.getPlayer();
				GeneratorObject g = DataManager.getInstance().getGenerator(p.getUniqueId(), bf.getLocation());
				if(g != null) {
					if(event.getAction() == Action.RIGHT_CLICK_BLOCK && !p.isSneaking()) {
						event.setCancelled(true);
						g.open(p);
					}
				}
			}
		}
	}
}
