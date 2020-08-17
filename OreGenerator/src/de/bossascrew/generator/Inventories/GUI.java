package de.bossascrew.generator.Inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.utils.Dimension;
import de.bossascrew.generator.utils.Level;
import de.bossascrew.generator.utils.Ore;
import de.tr7zw.nbtapi.NBTItem;

public class GUI {

	public static final Material ITEM_REQ = Material.NAME_TAG;
	public static final Material ITEM_PROB = Material.MAP;
	public static final Material ITEM_DROP = Material.HOPPER;
	
	GeneratorObject generator;
	Inventory inv;
	
	public GUI(GeneratorObject generator) {
		this.generator = generator;
		inv = Bukkit.createInventory(null, 3 * 9, Generator.GUI_TITLE);
	}
	
	public Inventory getInventory() {
		return this.inv;
	}
	
	public void refresh() {
		//TODO Items setzen
		//TODO bei jedem item die generatorid mitsetzen
		
		for(Level level : Level.values()) {
			
			
		}
		//Drop picke
	}
	
	
	private ItemStack getDropItem() {
		ItemStack i = new ItemStack(ITEM_DROP);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName("§nGenerator abbauen");
		List<String> lore = new ArrayList<String>();
		//TODO schicken machen
		lore.add("Klick hier zu dropp");
		lore.add("Zeile 2");
		m.setLore(lore);
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_DROP);
		return inbt.getItem();
	}
	
	private ItemStack getLevelItemReq(Level level) {
		ItemStack i = new ItemStack(ITEM_REQ);
		
		boolean accessed = false;
		if(level.getLevel() <= generator.getLevel()) accessed = true;
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName("§f§nLevel " + level.getLevel());
		if(!accessed) {
			meta.setLore(getRequirements(level));
		} else {
			i = glowItem(i);
		}
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_LEVELINFO);
		inbt.setInteger(Generator.NBT_LEVEL_KEY, level.getLevel());
		return inbt.getItem();
	}
	
	private ItemStack getLevelItemProb(Level level) {
		ItemStack i = new ItemStack(ITEM_PROB);
		
		boolean accessed = false;
		boolean oneAbove = false;
		if(level.getLevel() <= generator.getLevel()) accessed = true;
		else if(level.getLevel() == generator.getLevel()+1) oneAbove = true;
		
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName("§f§nWahrscheinlichkeiten:");
		if(accessed || oneAbove) {
			meta.setLore(getPropabilities(level));
		} else {
			List<String> lore = new ArrayList<String>();
			lore.add("§8Schalte erst das vorige");
			lore.add("§8Level frei!");
			meta.setLore(lore);
		}
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_LEVELINFO);
		inbt.setInteger(Generator.NBT_LEVEL_KEY, level.getLevel());
		return inbt.getItem();
	}
	
	private List<String> getPropabilities(Level level) {
		List<String> ret = new ArrayList<String>();
		ret.add("§7Oberwelt:");
		for(Ore o : level.getOres(Dimension.OVERWORLD)) {
			ret.add("§8- " + o.mat + ": §7" + o.prob * 100 + "%");
		}
		ret.add("");
		ret.add("§7Im Nether:");
		for(Ore o : level.getOres(Dimension.NETHER)) {
			ret.add("§8- " + o.mat + ": §7" + o.prob * 100 + "%");
		}
		return ret;
	}
	
	private List<String> getRequirements(Level level) {
		return null;
	}
	
    public ItemStack glowItem(ItemStack i) {
    	i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
    	ItemMeta meta = i.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);
        return i;
    }
}
