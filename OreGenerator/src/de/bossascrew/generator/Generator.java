package de.bossascrew.generator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.bossascrew.generator.data.DataManager;

public class Generator extends JavaPlugin {

	static Generator instance;
	
	EventManager events;
	
	public final static String PREFIX = "§6Jeff §>";
	public final static String GENERATOR_NAME = "akskfp19e7askd"; //"verschlüsselt"
	public final static String USE_GUI_TO_DROP = PREFIX + " §7Benutze das Menü des Generators, um ihn zu droppen!";
	
	public final static int USE_GUI_TO_DROP_DELAY = 5;
	
	@Override
	public void onEnable() {
		instance = this;
		printToConsole("Plugin geladen");
		
		events = new EventManager();
	}
	
	@Override
	public void onDisable() {
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			DataManager.getInstance().savePlayer(p.getUniqueId());
		}
		
		printToConsole("Plugin heruntergefahren");
	}
	
	public static void printToConsole(String message) {
		Bukkit.getConsoleSender().sendMessage("§7[§9§lErzgenerator§7] §f" + message);
	}
	
	public static Generator getInstance() {
		return instance;
	}
}
