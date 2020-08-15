package de.bossascrew.generator;

import java.util.UUID;

import org.bukkit.Location;

public class GeneratorObject {

	int id;
	UUID ownerUUID;
	Location loc;
	
	public GeneratorObject(int id, UUID ownerUUID, Location loc) {
		this.id = id;
		this.ownerUUID = ownerUUID;
		this.loc = loc;
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
	
	public Location getLoc() {
		return loc;
	}
	public void setLoc(Location loc) {
		this.loc = loc;
	}
}
