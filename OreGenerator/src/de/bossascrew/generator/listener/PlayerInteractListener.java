package de.bossascrew.generator.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.Message;

public class PlayerInteractListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		if(event.useInteractedBlock().equals(Result.DENY)) return;
		if(event.getClickedBlock() != null) {
			if(event.getClickedBlock().getType() == Material.BLAST_FURNACE) {
				BlastFurnace bf = (BlastFurnace) event.getClickedBlock().getState();
				Player p = event.getPlayer();
				GeneratorObject g = DataManager.getInstance().getGenerator(bf.getLocation());
				if(g != null) {
					if(g.getOwnerUUID().equals(p.getUniqueId())) {
						if(event.getAction() == Action.RIGHT_CLICK_BLOCK && !p.isSneaking()) {
							event.setCancelled(true);
							g.open(p);
						}
					} else {
						event.setCancelled(true);
						p.sendMessage(Message.NOT_YOUR_GENERATOR.replace("[player]", Bukkit.getPlayer(g.getOwnerUUID()).getName()));
					}
				}
			}
		}
	}
}
