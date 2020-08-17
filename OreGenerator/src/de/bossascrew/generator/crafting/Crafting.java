package de.bossascrew.generator.crafting;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import de.bossascrew.generator.Generator;
import de.tr7zw.nbtapi.NBTItem;

public class Crafting {

	public static ItemStack getGeneratorItem(String uuid, int level) {
		
		ItemStack generator = new ItemStack(Material.BLAST_FURNACE);
		ItemMeta genMeta = generator.getItemMeta();
		genMeta.setDisplayName(Generator.GENERATOR_NAME);
		generator.setItemMeta(genMeta);
		
		NBTItem nbt = new NBTItem(generator);
		nbt.setInteger(Generator.NBT_LEVEL_KEY, level);
		nbt.setString(Generator.NBT_OWNER_UUID_KEY, uuid.toString());
		return nbt.getItem();
	}
	
	public static ItemStack getGeneratorItem(UUID uuid) {
		return getGeneratorItem(uuid.toString(), -1);
	}
	
	public static void registerGeneratorCrafting() {
		
		ItemStack generator = getGeneratorItem(Generator.NBT_OWNER_NONE_VALUE, -1);
		NamespacedKey key = new NamespacedKey(Generator.getInstance(), "oreGenerator");
		ShapedRecipe genRecipe = new ShapedRecipe(key, generator);
		
		genRecipe.shape("%  "," % ","  %");
		genRecipe.setIngredient('%', Material.DIRT);
		
		Generator.getInstance().getServer().addRecipe(genRecipe);
	}
}
