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

	public static ItemStack getGeneratorItem(int id, String uuid, int level) {
		
		ItemStack generator = new ItemStack(Material.BLAST_FURNACE);
		ItemMeta genMeta = generator.getItemMeta();
		genMeta.setDisplayName(Generator.GENERATOR_NAME);
		generator.setItemMeta(genMeta);
		
		NBTItem nbt = new NBTItem(generator);
		nbt.setString(Generator.NBT_TYPE_KEY, Generator.NBT_TYPE_VALUE_GENERATOR);
		nbt.setInteger(Generator.NBT_GENERATORID_KEY, id);
		nbt.setInteger(Generator.NBT_LEVEL_KEY, level);
		nbt.setString(Generator.NBT_OWNER_UUID_KEY, uuid.toString());
		return nbt.getItem();
	}
	
	public static ItemStack getGeneratorItem(UUID uuid) {
		return getGeneratorItem(0, uuid.toString(), -1);
	}
	
	public static void registerGeneratorCrafting() {
		
		ItemStack generator = getGeneratorItem(0, Generator.NBT_OWNER_NONE_VALUE, -1);
		NamespacedKey key = new NamespacedKey(Generator.getInstance(), "oreGenerator");
		ShapedRecipe genRecipe = new ShapedRecipe(key, generator);
		
		genRecipe.shape("   ","LCW","III");
		genRecipe.setIngredient('L', Material.LAVA_BUCKET);
		genRecipe.setIngredient('C', Material.COBBLESTONE);
		genRecipe.setIngredient('W', Material.WATER_BUCKET);
		genRecipe.setIngredient('I', Material.IRON_INGOT);
		
		Generator.getInstance().getServer().addRecipe(genRecipe);
	}
}
