package de.bossascrew.generator.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.bossascrew.generator.GeneratorObject;

public class OreGenerationEvent extends Event implements Cancellable {

	private GeneratorObject generator;
	Location loc;
	private Material type;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCancelled;
	
	public OreGenerationEvent(Location loc, Material type, GeneratorObject generator) {
		
		this.loc = loc;
		this.type = type;
		this.generator = generator;
		
		//System.out.println("Erzgeneration level " + generator == null ? 0 : generator.getLevel());
		isCancelled = false;
	}
	
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
	public GeneratorObject getGenerator() {
		return generator;
	}
	
	public Material getType() {
		return type;
	}
	
	public Location getLocation() {
		return loc;
	}
}
