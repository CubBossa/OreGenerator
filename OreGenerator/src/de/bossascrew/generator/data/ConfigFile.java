package de.bossascrew.generator.data;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.bossascrew.generator.Generator;

public class ConfigFile {

	String fileResource;
	File file;
	FileConfiguration cfg;
	
	public ConfigFile(String path, String name, String fileResource) {
		this.fileResource = fileResource;
		this.file = new File(path, name);
		this.cfg = YamlConfiguration.loadConfiguration(file);

		if(file != null)
			setup();
	}
	
	void setup() {
		if(!file.exists()) {
			Generator.printToConsole("Kein File gefunden: Starte Setup!");
			
			if(fileResource != null) {
				Generator.printToConsole("File wird aufgebaut");
				Generator.getInstance().saveResource(fileResource, true);
						        
				Generator.printToConsole("File gefunden: §c" + file.exists());
			} else {
				try {
					file.createNewFile();
				} catch (IOException e) {
					Generator.printToConsole("§cFilemanager konnte File nicht erstellen!");
					e.printStackTrace();
				}
			}
			
		} else {
			Generator.printToConsole("Config.yml existiert bereits und wird nicht generiert!");
		}
	}
	
	public void save() {
		try {
			cfg.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getCfg() {
		return this.cfg;
	}
	public File getFile() {
		return this.file;
	}
}
