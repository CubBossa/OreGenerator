package de.bossascrew.generator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.bossascrew.generator.crafting.Crafting;
import de.bossascrew.generator.data.ConfigFile;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.MySQLManager;

public class Generator extends JavaPlugin {

	static Generator instance;
	
	EventManager events;
	
	public final static ItemStack GENERATOR_ITEM = Crafting.getGeneratorItem();
	public final static String PREFIX = "§6Jeff §>";
	public final static String GENERATOR_NAME = "§fErze-Generator";
	public final static String GENERATOR_CODENAME = "akskfp19e7askd"; //"verschlüsselt"
	public final static String GUI_TITLE = "§9Erze Generator";
	public final static String USE_GUI_TO_DROP = PREFIX + " §7Benutze das Menü des Generators, um ihn zu droppen!";
	public final static int USE_GUI_TO_DROP_DELAY = 5;
	
	ConfigFile config;
	
	@Override
	public void onEnable() {
		
		config = new ConfigFile(getDataFolder().getPath(), "config.yml");
		
		instance = this;
		printToConsole("Plugin geladen");
		
		Crafting.registerGeneratorCrafting();
		events = new EventManager();
		
		boolean databaseSetup = MySQLManager.getInstance().setData(config.getCfg().getString("database.host"),
				config.getCfg().getString("database.port"),
				config.getCfg().getString("database.database"),
				config.getCfg().getString("database.username"),
				config.getCfg().getString("database.password"),
				config.getCfg().getString("database.tablename"));
		
		if(!databaseSetup) {
			printToConsole("§4Datenkbankwerte nicht korrekt gesetzt!");
			getPluginLoader().disablePlugin(this);
		}
		
		DataManager.getInstance().savingRoutine();
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
