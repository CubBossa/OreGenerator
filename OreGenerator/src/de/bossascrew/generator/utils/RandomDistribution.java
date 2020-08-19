package de.bossascrew.generator.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

public class RandomDistribution {

    private Map<Material, Double> distribution;
    private double distSum;

    public RandomDistribution() {
        distribution = new HashMap<>();
    }

    public void addNumber(Material value, double distribution) {
        if (this.distribution.get(value) != null) {
            distSum -= this.distribution.get(value);
        }
        this.distribution.put(value, distribution);
        distSum += distribution;
    }

    public Material getDistributedRandomNumber() {
        double rand = Math.random();
        double ratio = 1.0f / distSum;
        double tempDist = 0;
        for (Material i : distribution.keySet()) {
            tempDist += distribution.get(i);
            if (rand / ratio <= tempDist) {
                return i;
            }
        }
        return Material.STONE;
    }
}