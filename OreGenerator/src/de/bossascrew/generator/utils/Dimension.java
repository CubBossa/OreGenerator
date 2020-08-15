package de.bossascrew.generator.utils;

import java.util.ArrayList;
import java.util.List;

public enum Dimension {

	OVERWORLD("bskyblock_world"),
	NETHER("bskyblock_world_nether");
	
	List<String> worlds;
	
	Dimension() {
		worlds = new ArrayList<String>();
	}
	
	Dimension(String...world) {
		worlds = new ArrayList<String>();
		for(String w : world) {
			worlds.add(w);
		}
	}
	
	public List<String> getWorlds() {
		return worlds;
	}
}
