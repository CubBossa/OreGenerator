package de.bossascrew.skyblock.inventories;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;

public class confirmGUI extends InventoryManager {

	String confirmCommand;
	
	public InventoryConfirm(int rows, String title, String confirmCommand) {
		super(rows, title, InventoryType.HOPPER);
		this.confirmCommand = confirmCommand;
	}

	@Override
	public void refresh() {
		
		inv.setItem(1, createItem(Material.MELON, "§f§nBestätigen", 1));
		inv.setItem(3, createItem(Material.RED_MUSHROOM_BLOCK, "§f§nAbbrechen", 1));
	}
	
	public String getConfirmCommand() {
		return confirmCommand;
	}
}
