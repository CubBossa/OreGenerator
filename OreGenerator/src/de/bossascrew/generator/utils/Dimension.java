package de.bossascrew.generator.utils;

import java.util.ArrayList;
import java.util.List;

import de.bossascrew.generator.Generator;

public enum Dimension {

	OVERWORLD(Generator.getInstance().getCfg().getOverworlds()),
	NETHER(Generator.getInstance().getCfg().getNetherworlds());
	
	List<String> worlds;
	
	Dimension() {
		worlds = new ArrayList<String>();
	}
	
	Dimension(List<String> world) {
		worlds = new ArrayList<String>();
		for(String w : world) {
			worlds.add(w);
		}
	}
	
	public List<String> getWorlds() {
		return worlds;
	}
	
	public boolean isDimension(String world) {
		return worlds.contains(world);
	}
}
