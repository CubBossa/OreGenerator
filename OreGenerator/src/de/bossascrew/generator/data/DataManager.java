package de.bossascrew.generator.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bossascrew.generator.GeneratorObject;

public class DataManager {

	static DataManager instance;
	
	List<GeneratorObject> generators;
	

	public DataManager() {
		generators = new ArrayList<GeneratorObject>();
	}
	
	
	public List<GeneratorObject> getGenerators(Player p) {
		return null;
	}
	
	public List<GeneratorObject> getGenerator(Player p, Location loc) {
		return null;
	}
	
	
	public DataManager getInstance() {
		if(instance == null)
			instance = new DataManager();
		return instance;
	}
}
