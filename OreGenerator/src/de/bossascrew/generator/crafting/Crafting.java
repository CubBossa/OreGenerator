package de.bossascrew.generator.crafting;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.bossascrew.generator.Generator;

public class Crafting {

	
	public static ItemStack getGeneratorItem() {
		
		ItemStack generator = new ItemStack(Material.BLAST_FURNACE);
		ItemMeta genMeta = generator.getItemMeta();
		genMeta.setDisplayName(Generator.GENERATOR_NAME);

		
		generator.setItemMeta(genMeta);
		return generator;
	}
	
	public static void registerGeneratorCrafting() {
		
		ItemStack generator = Generator.GENERATOR_ITEM;
		NamespacedKey key = new NamespacedKey(Generator.getInstance(), "oreGenerator");
		ShapedRecipe genRecipe = new ShapedRecipe(key, generator);
		
		genRecipe.shape("% "," % ","  %");
		genRecipe.setIngredient('%', Material.DIRT);
		
		Generator.getInstance().getServer().addRecipe(genRecipe);
	}
}
