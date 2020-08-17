package de.bossascrew.generator.Inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.utils.Level;

public class GUI {

	int generatorID;
	Inventory inv;
	
	public GUI(int generatorID) {
		this.generatorID = generatorID;
		inv = Bukkit.createInventory(null, 3 * 9, Generator.GUI_TITLE);
	}
	
	public Inventory getInventory() {
		return this.inv;
	}
	
	public void refresh() {
		//TODO Items setzen
		//TODO bei jedem item die generatorid mitsetzen
		
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
