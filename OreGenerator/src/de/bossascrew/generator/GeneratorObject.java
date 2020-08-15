package de.bossascrew.generator;

import java.util.UUID;

import org.bukkit.block.BlastFurnace;

public class GeneratorObject {

	int id = 0;
	UUID ownerUUID;
	BlastFurnace furnace;
	boolean isPlaced = false;
	
	public GeneratorObject(UUID ownerUUID, BlastFurnace furnace) {
		this.ownerUUID = ownerUUID;
		this.furnace = furnace;
	}

	public boolean isPlaced() {
		return isPlaced;
	}
	
	public void place() {
		this.isPlaced = true;
	}
	
	public void drop() {
		this.isPlaced = false;
	}
	
	public void open() {
		
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
	
	public BlastFurnace getLoc() {
		return furnace;
	}
	public void setLoc(BlastFurnace furnace) {
		this.furnace = furnace;
	}

}
