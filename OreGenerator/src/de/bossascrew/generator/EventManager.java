package de.bossascrew.generator;

import org.bukkit.Bukkit;

import de.bossascrew.generator.listener.BlockBreakListener;
import de.bossascrew.generator.listener.EntityExplodeListener;
import de.bossascrew.generator.listener.BlockFromToListener;
import de.bossascrew.generator.listener.BlockPlaceListener;
import de.bossascrew.generator.listener.CraftItemListener;
import de.bossascrew.generator.listener.PlayerInteractListener;
import de.bossascrew.generator.listener.InventoryClickListener;
import de.bossascrew.generator.listener.InventoryMoveItemListener;
import de.bossascrew.generator.listener.PlayerJoinListener;
import de.bossascrew.generator.listener.PlayerQuitListener;
import de.bossascrew.generator.listener.PrepareItemCraftListener;

public class EventManager {

	public EventManager() {
		registerEvents();
	}
	
    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockFromToListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new PrepareItemCraftListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryMoveItemListener(), Generator.getInstance());
        Bukkit.getPluginManager().registerEvents(new CraftItemListener(), Generator.getInstance());
    }
}
