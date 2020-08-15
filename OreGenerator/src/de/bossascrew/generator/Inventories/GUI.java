package de.bossascrew.generator.Inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.utils.Level;

public class GUI {

	Inventory inv;
	int level;
	
	public GUI(int level) {
		this.level = level;
		inv = Bukkit.createInventory(null, 3 * 9, Generator.GUI_TITLE);
	}
	
	public Inventory getInventory() {
		return this.inv;
	}
	
	public void refresh() {
		//TODO Items setzen
		
		for(Level level : Level.values()) {
			
			
		}
		
		//Drop picke
	}
	
	private List<String> getLore(Level level, boolean unlocked) {
		if(unlocked) {
			
		} else {
			
		}
		return null;
	}
	
	private List<String> getPropabilities(Level level) {
		return null;
	}
	
	private List<String> getRequirements(Level level) {
		return null;
	}
}
