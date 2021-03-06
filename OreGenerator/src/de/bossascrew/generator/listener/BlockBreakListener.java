package de.bossascrew.generator.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.Message;

public class BlockBreakListener implements Listener {

	List<UUID> isInformed = new ArrayList<UUID>();
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		
		if(event.getBlock() != null && event.getBlock().getType() == Material.BLAST_FURNACE) {
			GeneratorObject g = DataManager.getInstance().getGenerator(event.getBlock().getLocation());
			if(g != null) {
				event.setCancelled(true);
				if(g.getOwnerUUID().equals(event.getPlayer().getUniqueId())) {
					informPlayerOnDrop(event.getPlayer());
				} else {
					event.getPlayer().sendMessage(Message.NOT_YOUR_GENERATOR);
				}
			}
		}
	}
	
	
	private void informPlayerOnDrop(Player p) {
		if(isInformed.contains(p.getUniqueId())) return;
		
		p.sendMessage(Message.USE_GUI_TO_DROP);
		isInformed.add(p.getUniqueId());
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Generator.getInstance(), new Runnable() {
			@Override
			public void run() {
				isInformed.remove(p.getUniqueId());
			}
		}, 20*Generator.USE_GUI_TO_DROP_DELAY);
	}
}
