package de.bossascrew.generator;

import java.util.UUID;

import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;

import de.bossascrew.generator.Inventories.GUI;

public class GeneratorObject {

	int id = 0;
	UUID ownerUUID;
	BlastFurnace furnace;
	boolean isPlaced = false;
	int level;
	GUI gui;
	
	public GeneratorObject(UUID ownerUUID, BlastFurnace furnace, int level) {
		this.ownerUUID = ownerUUID;
		this.furnace = furnace;
		gui = new GUI(level);
	}

	public boolean isPlaced() {
		return isPlaced;
	}
	
	public void place(BlastFurnace furnace) {
		this.furnace = furnace;
		this.isPlaced = true;
	}
	
	public void drop() {
		this.furnace = null;
		this.isPlaced = false;
	}
	
	public void open(Player p) {
		p.closeInventory();
		p.openInventory(gui.getInventory());
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	public void setOwnerUUID(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}
	
	public BlastFurnace getFurnace() {
		return furnace;
	}
	public void setLoc(BlastFurnace furnace) {
		this.furnace = furnace;
	}

}
