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
import de.bossascrew.generator.data.Message;
import de.bossascrew.generator.utils.Dimension;
import de.bossascrew.generator.utils.Level;
import de.bossascrew.generator.utils.LevelRequirements;
import de.bossascrew.generator.utils.Ore;
import de.tr7zw.nbtapi.NBTItem;

public class GUI {

	public static final Material ITEM_REQ_ACCESSED = Material.ENCHANTED_BOOK;
	public static final Material ITEM_REQ_UNACCESSED = Material.BOOK;
	public static final Material ITEM_PROB_ACCESSED = Material.PAPER;
	public static final Material ITEM_PROB_UNACCESSED = Material.MAP;
	public static final Material ITEM_DROP = Material.HOPPER;
	public static final Integer[] LEVEL_SLOTS = {0,1,2,3,4,5};
	
	GeneratorObject generator;
	Inventory inv;
	
	public GUI(GeneratorObject go) {
		this.generator = go;
		
		inv = Bukkit.createInventory(null, 3 * 9, Message.GUI_TITLE);
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
		m.setDisplayName(Message.GUI_DROP_TITLE);
		List<String> lore = new ArrayList<String>();
		lore.add(Message.GUI_DROP_LORE1);
		lore.add(Message.GUI_DROP_LORE2);
		m.setLore(lore);
		i.setItemMeta(m);
		NBTItem inbt = new NBTItem(i);
		inbt.setString(Generator.NBT_ACTION_KEY, Generator.NBT_ACTION_VALUE_DROP);
		inbt.setInteger(Generator.NBT_GENERATORID_KEY, generator.getId());
		return inbt.getItem();
	}
	
	private ItemStack getLevelItemReq(Level level) {
		ItemStack i = new ItemStack(ITEM_REQ_UNACCESSED);
		
		boolean accessed = false;
		if(level.getLevel() <= generator.getLevel()) accessed = true;
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(Message.GUI_REQUIREMENTS_TITLE.replace("[level]", "" + level.getLevel()));
		if(!accessed) {
			meta.setLore(getRequirements(level));
		} else {
			i.setType(ITEM_REQ_ACCESSED);
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
		ItemStack i = new ItemStack(ITEM_PROB_UNACCESSED);
		
		boolean accessed = false;
		boolean oneAbove = false;
		
		if(level.getLevel() <= generator.getLevel()) accessed = true;
		else if(level.getLevel() == generator.getLevel()+1) oneAbove = true;
		
		ItemMeta meta = i.getItemMeta();
		meta.setDisplayName(Message.GUI_PROBABILITY_PROBS);
		if(accessed || oneAbove) {
			if(accessed)
				i.setType(ITEM_PROB_ACCESSED);
			meta.setLore(getPropabilities(level));
		} else {
			List<String> lore = new ArrayList<String>();
			lore.add(Message.GUI_REQUIREMENTS_LORE1);
			lore.add(Message.GUI_REQUIREMENTS_LORE2);
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
		List<String> ret = new ArrayList<String>();
		ret.add(Message.GUI_PROBABILITY_OVERWORLD);
		for(Ore o : level.getOres(Dimension.OVERWORLD)) {
			ret.add(Message.GUI_PROBABILITY_FORMAT.replace("[name]", o.friendlyName).replace("[probability]", o.getFriendlyProb()));
		}
		ret.add("");
		ret.add(Message.GUI_PROBABILITY_NETHER);
		for(Ore o : level.getOres(Dimension.NETHER)) {
			ret.add(Message.GUI_PROBABILITY_FORMAT.replace("[name]", o.friendlyName).replace("[probability]", o.getFriendlyProb()));
		}
		return ret;
	}
	
	private List<String> getRequirements(Level level) {
		List<String> ret = new ArrayList<String>();
		LevelRequirements lr = LevelRequirements.fromLevel(level.getLevel());
		if(level.getLevel() > 1)
			ret.add(Message.GUI_REQUIREMENTS_LEVEL);
		for(ItemStack i : lr.getRequirememts()) {
			ret.add(Message.GUI_REQUIREMENTS_FORMAT.replace("[item]", i.getType() + "").replace("[amount]", i.getAmount() + ""));
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
