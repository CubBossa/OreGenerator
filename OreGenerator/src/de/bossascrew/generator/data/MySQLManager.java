package de.bossascrew.generator.data;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import de.bossascrew.generator.GeneratorObject;

public class MySQLManager {

	static MySQLManager instance;
	
	
	public MySQLManager() {
		instance = this;
	}
	
	public boolean saveGenerator(GeneratorObject g) {
		return true;
	}

	public List<GeneratorObject> loadGenerators(UUID uuid) {
		return null;
	}
	
	public GeneratorObject loadGenerator(UUID uuid, Location loc) {
		return null;
	}
	
	public static MySQLManager getInstance() {
		if(instance == null) instance = new MySQLManager();
		return instance;
	}
}
