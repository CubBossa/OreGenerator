package de.bossascrew.generator.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.bossascrew.generator.data.DataManager;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		DataManager.getInstance().savePlayer(event.getPlayer().getUniqueId());
	}
}