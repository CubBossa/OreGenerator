package de.bossascrew.generator.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlastFurnace;

import de.bossascrew.generator.GeneratorObject;

public class DataManager {

	static DataManager instance;
	List<GeneratorObject> generators;
	
	public DataManager() {
		generators = new ArrayList<GeneratorObject>();
		instance = this;
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
	
	public GeneratorObject createGenerator(UUID uuid, BlastFurnace bf, int level) {
		GeneratorObject go = new GeneratorObject(uuid, bf, level);
		go.setPlaced(true);
		MySQLManager.getInstance().registerGenerator(go);
		
		loadPlayer(uuid);
		return getGenerator(uuid, bf.getLocation());
	}
	
	public void loadPlayer(UUID uuid) {
		for(GeneratorObject g : MySQLManager.getInstance().loadGenerators(uuid)) {
			boolean isSet = false;
			for(GeneratorObject gg : generators) {
				if(g.getId() == gg.getId()) {
					isSet = true;
				}
			}
			if(!isSet) {
				generators.add(g);
			}
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
	
	public GeneratorObject getGenerator(int id) {
		for(GeneratorObject g : generators) {
			if(g.getId() == id) {
				return g;
			}
		}
		return null;
	}
	
	public GeneratorObject getGenerator(Location loc) {
		GeneratorObject g = null;
		for(GeneratorObject go : generators) {
			if(go.getFurnace().getLocation() == loc) {
				g = go;
			}
		}
		if(g == null) g = MySQLManager.getInstance().loadGenerator(loc);
		return g;
	}
	
	public GeneratorObject getGenerator(UUID uuid, Location loc) {
		for(GeneratorObject g : generators) {
			System.out.println("Wir suchen generator. Furnace: " + g.getFurnace());
			
			if(g.getOwnerUUID().equals(uuid) && g.getFurnace() != null && g.getFurnace().getLocation().equals(loc)) {
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
			new DataManager();
		return instance;
	}
}
