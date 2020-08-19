package de.bossascrew.generator.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.events.OreGenerationEvent;

public class OreGenerationListener implements Listener {

	@EventHandler
	public void onOre(OreGenerationEvent e) {
		
		if(e.getGenerator() == null) return;
		for(Player p : Bukkit.getOnlinePlayers()) {
			
			for(int i = 0; i < e.getGenerator().getLevel(); i++) {
				p.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, e.getGenerator().getFurnace().getLocation().add(new Vector(0.35 + Math.random() * 0.3, 1, 0.35 + Math.random() * 0.3)), 0, 0.0, 0.1, 0.0);
			}
			if(e.getType() != Material.STONE) {
				p.playSound(e.getGenerator().getFurnace().getLocation(), Sound.BLOCK_PISTON_EXTEND, 1.0f, 1.0f);
				p.playSound(e.getGenerator().getFurnace().getLocation(), Sound.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 1.0f);
			}
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Generator.getInstance(), new Runnable() {
			@Override
			public void run() {
				if(e.getType() != Material.STONE) {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(e.getGenerator().getFurnace().getLocation(), Sound.BLOCK_PISTON_CONTRACT, 1.0f, 1.0f);
					}
				}
			}
		}, 10L);
	}
}
