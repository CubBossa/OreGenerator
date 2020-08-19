package de.bossascrew.generator.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.Message;
import de.tr7zw.nbtapi.NBTItem;

public class ConfirmGUI {

	int level = 0;
	Inventory inv = null;
	GeneratorObject g;
	
	public ConfirmGUI(GeneratorObject g, int level) {
		inv = Bukkit.createInventory(null, InventoryType.HOPPER, Message.GUI_CONFIRM_TITLE);
		this.level = level;
		this.g = g;
	}

	public void refresh() {
		inv.setItem(1, createItem(Material.MELON, Message.GUI_CONFIRM_CONFIRM, 1, Generator.NBT_ACTION_VALUE_CONFIRM));
		inv.setItem(3, createItem(Material.RED_MUSHROOM_BLOCK, Message.GUI_CONFIRM_DENY, 1, Generator.NBT_ACTION_VALUE_DENY));
	}
	
	public Inventory getInv() {
		refresh();
		return inv;
	}
	
	public ItemStack createItem(Material m, String displayname, int amount, String action) {
		ItemStack i = new ItemStack(m, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(displayname);
		i.setItemMeta(im);
		
		NBTItem in = new NBTItem(i);
		in.setInteger(Generator.NBT_GENERATORID_KEY, g.getId());
		in.setString(Generator.NBT_ACTION_KEY, action);
		in.setInteger(Generator.NBT_LEVEL_KEY, level);
		return in.getItem();
	}
}
