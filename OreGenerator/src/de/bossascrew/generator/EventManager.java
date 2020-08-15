package de.bossascrew.generator;

import org.bukkit.Bukkit;

import de.bossascrew.generator.listener.BlockFormListener;

public class EventManager {

	public EventManager() {
		registerEvents();
	}
	
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockFormListener(), Generator.getInstance());
    }
}
