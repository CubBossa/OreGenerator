package de.bossascrew.generator.utils;

import org.bukkit.Material;

public class Ore {

	public float prob = 0;
	public Material mat = Material.STONE;
	public Dimension d = Dimension.OVERWORLD;
	
	public Ore(float prob, Material mat) {
		this.prob = prob;
		this.mat = mat;
	}
	
	public Ore(float prob, Material mat, Dimension d) {
		this.prob = prob;
		this.mat = mat;
		this.d = d;
	}
}
