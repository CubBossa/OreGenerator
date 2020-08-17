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
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.utils.Dimension;
import de.bossascrew.generator.utils.Level;
import de.bossascrew.generator.utils.LevelRequirements;
import de.bossascrew.generator.utils.Ore;
import de.tr7zw.nbtapi.NBTItem;

public class GUI {

	public static final Material ITEM_REQ = Material.NAME_TAG;
	public static final Material ITEM_PROB = Material.MAP;
	public static final Material ITEM_DROP = Material.HOPPER;
	public static final Integer[] LEVEL_SLOTS = {0,1,2,3,4,5};
	
	GeneratorObject generator;
	Inventory inv;
	
	public GUI(int generatorID) {
		this.generator = DataManager.getInstance().getGenerator(generatorID);
		inv = Bukkit.createInventory(null, 3 * 9, Generator.GUI_TITLE);
	}
	
	public Inventory getInventory() {
		return this.inv;
	}
	
	public void refresh() {
		for(int i : LEVEL_SLOTS) {
			inv.setItem(i+9, getLevelItemProb(Level.fromInt(i)));
			if(i >= 1) {
				inv.setItem(i, getLevelItemReq(Level.fromInt(i)));
			}
		}
		inv.setItem(7+9, getDropItem());
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
		i.setItemMeta(m);
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_DROP);
		inbt.setInteger(Generator.NBT_GENERATORID_KEY, generator.getId());
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
		i.setItemMeta(meta);
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_LEVELINFO);
		inbt.setInteger(Generator.NBT_LEVEL_KEY, level.getLevel());
		inbt.setInteger(Generator.NBT_GENERATORID_KEY, generator.getId());
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
		i.setItemMeta(meta);
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_LEVELINFO);
		inbt.setInteger(Generator.NBT_LEVEL_KEY, level.getLevel());
		inbt.setInteger(Generator.NBT_GENERATORID_KEY, generator.getId());
		return inbt.getItem();
	}
	
	private List<String> getPropabilities(Level level) {
		//TODO Farbsetup, evtl auch als Message format auslagern
		List<String> ret = new ArrayList<String>();
		ret.add("§7Oberwelt:");
		for(Ore o : level.getOres(Dimension.OVERWORLD)) {
			ret.add("§7- §f" + o.friendlyName + "§7: §a" + o.prob * 100 + "§7%");
		}
		ret.add("");
		ret.add("§7Im Nether:");
		for(Ore o : level.getOres(Dimension.NETHER)) {
			ret.add("§7- §f" + o.friendlyName + "§7: §a" + o.prob * 100 + "§7%");
		}
		return ret;
	}
	
	private List<String> getRequirements(Level level) {
		//TODO auch farbsetup
		List<String> ret = new ArrayList<String>();
		LevelRequirements lr = LevelRequirements.fromLevel(level.getLevel());
		for(ItemStack i : lr.getRequirememts()) {
			ret.add("§7- §f" + i.getType() + "§7, §a" + i.getAmount() + "x");
		}
		return ret;
	}
	
    public ItemStack glowItem(ItemStack i) {
    	i.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
    	ItemMeta meta = i.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(meta);
        return i;
    }
}
