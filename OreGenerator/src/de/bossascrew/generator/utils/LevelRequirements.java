package de.bossascrew.generator.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public enum LevelRequirements {

	ONE(1, new ItemStack(Material.MUSIC_DISC_PIGSTEP, 1)),
	TWO(2, new ItemStack(Material.POISONOUS_POTATO, 64)),
	THREE(3, new ItemStack(Material.TRIDENT, 1)),
	FOUR(4, new ItemStack(Material.NETHER_STAR, 1)),
	FIVE(5, new ItemStack(Material.CREEPER_HEAD, 1));
	

	int level;
	List<ItemStack> requirements;
	
	private LevelRequirements(int level, ItemStack...requirements) {
		this.level = level;
		this.requirements =  new ArrayList<ItemStack>();
		for(ItemStack i : requirements) {
			this.requirements.add(i);
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public List<ItemStack> getRequirememts() {
		return this.requirements;
	}
	
	public static LevelRequirements fromLevel(int level) {
		for(LevelRequirements lr : LevelRequirements.values()) {
			if(level == lr.getLevel()) {
				return lr;
			}
		}
		return null;
	}
}
