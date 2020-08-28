package de.bossascrew.generator.events;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.bossascrew.generator.GeneratorObject;

public class OreGenerationEvent extends Event implements Cancellable {

	private List<GeneratorObject> generators;
	Location loc;
	private Material type;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCancelled;
	
	public OreGenerationEvent(Location loc, Material type, List<GeneratorObject> generator) {
		
		this.loc = loc;
		this.type = type;
		this.generators = generator;
		
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
	
	public List<GeneratorObject> getGenerator() {
		return generators;
	}
	
	public Material getType() {
		return type;
	}
	
	public Location getLocation() {
		return loc;
	}
}
