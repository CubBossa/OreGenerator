package de.bossascrew.generator.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.bossascrew.generator.GeneratorObject;

public class DataManager {

	static DataManager instance;
	List<GeneratorObject> generators;
	
	public DataManager() {
		generators = new ArrayList<GeneratorObject>();
	}
	
	public void savingRoutine() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000 * 60 * 1); // this routine runs every 1 minutes
                    for(GeneratorObject g : generators) {
                    	MySQLManager.getInstance().saveGenerator(g);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.start();
	}
	
	public void loadPlayer(UUID uuid) {
		for(GeneratorObject g : MySQLManager.getInstance().loadGenerators(uuid)) {
			generators.add(g);
		}
	}
	
	public void savePlayer(UUID uuid) {
		for(GeneratorObject g : getGenerators(uuid)) {
			if(MySQLManager.getInstance().saveGenerator(g)) {
				generators.remove(g);
			}
		}
	}
	
	public List<GeneratorObject> getGenerators(UUID uuid) {
		List<GeneratorObject> ret = new ArrayList<GeneratorObject>();
		for(GeneratorObject g : generators) {
			if(g.getOwnerUUID().equals(uuid)) {
				ret.add(g);
			}
		}
		return ret;
	}
	
	public GeneratorObject getGenerator(Player p, Location loc) {
		for(GeneratorObject g : generators) {
			if(g.getOwnerUUID().equals(p.getUniqueId()) && g.getLoc().getLocation().equals(loc)) {
				if(g.isPlaced()) {
					return g;
				} else {
					return null;
				}
			}
		}
		return null;
	}
	
	public static DataManager getInstance() {
		if(instance == null)
			instance = new DataManager();
		return instance;
	}
}
