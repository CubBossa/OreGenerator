package de.bossascrew.generator;

import org.bukkit.Bukkit;

import de.bossascrew.generator.listener.BlockBreakListener;
import de.bossascrew.generator.listener.BlockBreakNaturallyListener;
import de.bossascrew.generator.listener.BlockFormListener;
import de.bossascrew.generator.listener.BlockPlaceListener;
import de.bossascrew.generator.listener.GeneratorInteractListener;
import de.bossascrew.generator.listener.InventoryInteractListener;
import de.bossascrew.generator.listener.InventoryMoveListener;
import de.bossascrew.generator.listener.PlayerJoinListener;
import de.bossascrew.generator.listener.PlayerQuitListener;
import de.bossascrew.generator.listener.PrepareItemCraftListener;

public class EventManager {

	public EventManager() {
		registerEvents();
	}
	
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockFormListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new GeneratorInteractListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryInteractListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PrepareItemCraftListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockBreakNaturallyListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryMoveListener(), Generator.getInstance());
    }
}
