package de.bossascrew.generator.utils;

import org.bukkit.Material;

public class Ore {

	public float prob = 0;
	public String friendlyName = "Stein";
	public Material mat = Material.STONE;
	public Dimension d = Dimension.OVERWORLD;
	
	public Ore(float prob, String friendlyName, Material mat) {
		this.prob = prob;
		this.friendlyName = friendlyName;
		this.mat = mat;
	}
	
	public Ore(float prob, String friendlyName, Material mat, Dimension d) {
		this.prob = prob;
		this.friendlyName = friendlyName;
		this.mat = mat;
		this.d = d;
	}
}
