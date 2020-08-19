package de.bossascrew.generator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.bossascrew.generator.crafting.Crafting;
import de.bossascrew.generator.data.ConfigFile;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.MySQLManager;

public class Generator extends JavaPlugin {

	static Generator instance;
	
	EventManager events;
	
	//TODO Messages in Messages verschieben
	
	public static final String GENERATOR_NAME = "§a§f§g§fErze-Generator";
	public static final String GUI_TITLE = "§9Erze Generator";
	public static final String GUI_CONFIRM_TITLE = "§cDie nötigen Items verschwinden!";
	
	public static final int USE_GUI_TO_DROP_DELAY = 5;
	
	public static final String NBT_TYPE_KEY = "itemtyp";
	public static final String NBT_TYPE_VALUE_GENERATOR = "oregenerator";
	public static final String NBT_GENERATORID_KEY = "generatorID";
	public static final String NBT_LEVEL_KEY = "level";
	public static final String NBT_OWNER_UUID_KEY = "ownerUUID";
	public static final String NBT_OWNER_NONE_VALUE = "none";
	public static final String NBT_ACTION_KEY = "action";
	public static final String NBT_ACTION_VALUE_DROP = "drop";
	public static final String NBT_ACTION_VALUE_LEVELINFO = "levelinform";
	public static final String NBT_ACTION_VALUE_CONFIRM = "confirm";
	public static final String NBT_ACTION_VALUE_DENY = "deny";
	
	ConfigFile config;
	
	@Override
	public void onEnable() {
		
		instance = this;
		config = new ConfigFile(instance.getDataFolder().getPath(), "config.yml", "config.yml");
		
		printToConsole("Plugin geladen");
		
		Crafting.registerGeneratorCrafting();
		events = new EventManager();
		
		boolean databaseSetup = MySQLManager.getInstance().setData(config.getCfg().getString("database.host"),
				config.getCfg().getString("database.port"),
				config.getCfg().getString("database.database"),
				config.getCfg().getString("database.username"),
				config.getCfg().getString("database.password"),
				config.getCfg().getString("database.tablename"));
		MySQLManager.getInstance().createTable();
		
		if(!databaseSetup) {
			printToConsole("§4Datenkbankwerte nicht korrekt gesetzt!");
			getPluginLoader().disablePlugin(this);
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			DataManager.getInstance().loadPlayer(p.getUniqueId());
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
	
	public ConfigFile getCfg() {
		return config;
	}
}
