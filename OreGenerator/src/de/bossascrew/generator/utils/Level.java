package de.bossascrew.generator.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum Level {

	ZERO(0, 0.004f, 0.009f, 0, 0, 0, 0, 0, 0, 0, 0),
	ONE(1, 0.0033f, 0.0111f, 0.01f, 0, 0, 0, 0, 0, 0.001f, 0),
	TWO(2, 0.002f, 0.0133f, 0.0111f,  0.00125f, 0.00166f, 0, 0, 0, 0.00133f, 0);
	
	
	
	int level;
	
	List<Ore> ores;
	
	float silverfish;
	float coal;
	float iron;
	float gold;
	float redstone;
	float lapis;
	float diamond;
	float emerald;
	float netherore;
	float debris;
	
	private Level(int level, float silverfish, float coal, float iron, float redstone, float lapis, float gold, float diamond, float emerald, float netherore, float debris) {
		
		this.level = level;
		
		ores = new ArrayList<Ore>();
		ores.add(new Ore(silverfish, Material.INFESTED_STONE));
		ores.add(new Ore(coal, Material.COAL_ORE));
		ores.add(new Ore(iron, Material.IRON_ORE));
		ores.add(new Ore(redstone, Material.REDSTONE_ORE));
		ores.add(new Ore(lapis, Material.LAPIS_ORE));
		ores.add(new Ore(gold, Material.GOLD_ORE));
		ores.add(new Ore(diamond, Material.DIAMOND_ORE));
		ores.add(new Ore(emerald, Material.EMERALD_ORE));
		ores.add(new Ore(netherore, Material.GILDED_BLACKSTONE, Dimension.NETHER));
		ores.add(new Ore(debris, Material.ANCIENT_DEBRIS, Dimension.NETHER));
	}
	
	public List<Ore> getOres(Dimension d) {
		List<Ore> ret = new ArrayList<Ore>();
		for(Ore o : ores) {
			if(o.d == d) ret.add(o);
		}
		float current = 0;
		for(Ore o : ret) {
			current += o.prob;
		}
		switch (d) {
		case OVERWORLD:
			ores.add(new Ore(1f-current, Material.STONE));
			break;
		case NETHER:
			ores.add(new Ore(1f-current, Material.BLACKSTONE));
			break;
		}
		return ret;		
	}

	public int getLevel() {
		return level;
	}
}
