package de.bossascrew.generator.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum Level {

	ZERO(0, 0.004f, 0.009f, 0, 0, 0, 0, 0, 0, 0, 0),
	ONE(1, 0.0033f, 0.01111f, 0.01f, 0, 0, 0, 0, 0, 0.001f, 0),
	TWO(2, 0.002f, 0.01333f, 0.01111f, 0.00125f, 0.00166f, 0, 0, 0, 0.00133f, 0),
	THREE(3, 0.00125f, 0.01667f, 0.01333f, 0.001538f, 0.00175f, 0.00246f, 0.0006667f, 0, 0.00133f, 0),
	FOUR(4, 0.0008333f, 0.02f, 0.01666f, 0.002f, 0.003f, 0.0037f, 0.0009f, 0.0004347f, 0.00333f, 0.0001f),
	FIVE(5, 0.000588f, 0.025f, 0.02f, 0.0029f, 0.005f, 0.0074f, 0.0014f, 0.001f, 0.0069f, 0.000143f);
	
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
		ores.add(new Ore(silverfish, "Infizierter Stein", Material.INFESTED_STONE));
		ores.add(new Ore(coal, "Kohle", Material.COAL_ORE));
		ores.add(new Ore(iron, "Eisen", Material.IRON_ORE));
		ores.add(new Ore(redstone, "Redstone", Material.REDSTONE_ORE));
		ores.add(new Ore(lapis, "Lapislazuli", Material.LAPIS_ORE));
		ores.add(new Ore(gold, "Gold", Material.GOLD_ORE));
		ores.add(new Ore(diamond, "Diamant", Material.DIAMOND_ORE));
		ores.add(new Ore(emerald, "Smaragd", Material.EMERALD_ORE));
		ores.add(new Ore(netherore, "Goldreicher Schwarzstein", Material.GILDED_BLACKSTONE, Dimension.NETHER));
		ores.add(new Ore(debris, "Antiker Schrott", Material.ANCIENT_DEBRIS, Dimension.NETHER));
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
			ret.add(new Ore(1f-current, "Stein", Material.STONE));
			break;
		case NETHER:
			ret.add(new Ore(1f-current, "Schwarzstein", Material.BLACKSTONE, Dimension.NETHER));
			break;
		}
		return ret;		
	}
	
	public List<Ore> addOres(Dimension d, List<Ore> ores) {
		List<Ore> ret = new ArrayList<Ore>();
		for(Ore o : ores) {
			for(Ore levelO : this.getOres(d)) {
				if(o.mat.equals(levelO.mat)) {
					ret.add(new Ore(o.prob + levelO.prob, o.friendlyName, o.mat));
				}
			}
		}
		return ret;
		
	}

	public int getLevel() {
		return level;
	}
	
	public static Level fromInt(int level) {
		for(Level l : Level.values()) {
			if(l.getLevel() == level) return l;
		}
		return null;
	}
}
