package de.bossascrew.generator.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;

public class GeneratorInteractListener implements Listener {

	List<UUID> isInformed = new ArrayList<UUID>();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		if(event.getClickedBlock() != null) {
			if(event.getClickedBlock().getType() == Material.BLAST_FURNACE) {
				BlastFurnace bf = (BlastFurnace) event.getClickedBlock().getState();
				if(bf.getCustomName() != null && bf.getCustomName().equals(Generator.GENERATOR_CODENAME)) {
					event.setCancelled(true);
					Player p = event.getPlayer();
					if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						GeneratorObject g = DataManager.getInstance().getGenerator(p.getUniqueId(), bf.getLocation());
						g.open(p);
					} else {
						informPlayerOnDrop(p);
					}
				}
			}
		}
	}
	
	private void informPlayerOnDrop(Player p) {
		if(isInformed.contains(p.getUniqueId())) return;
		
		p.sendMessage(Generator.USE_GUI_TO_DROP);
		isInformed.add(p.getUniqueId());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Generator.getInstance(), new Runnable() {
			@Override
			public void run() {
				isInformed.remove(p.getUniqueId());
			}
		}, 20*Generator.USE_GUI_TO_DROP_DELAY);
	}
}
