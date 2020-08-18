package de.bossascrew.generator.utils;

import java.text.DecimalFormat;

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
	
	public String getFriendlyProb() {
		double ret = prob;
		DecimalFormat df = new DecimalFormat("#.#####");
		return df.format(ret * 100).toString();
	}
}
