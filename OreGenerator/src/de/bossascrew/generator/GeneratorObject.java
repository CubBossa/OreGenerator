package de.bossascrew.generator;

import java.beans.ParameterDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.bossascrew.generator.Inventories.GUI;
import de.bossascrew.generator.crafting.Crafting;
import de.bossascrew.generator.utils.LevelRequirements;

public class GeneratorObject {

	int id = 0;
	UUID ownerUUID;
	BlastFurnace furnace;
	boolean isPlaced = false;
	int level;
	GUI gui;
	
	public GeneratorObject(UUID ownerUUID, BlastFurnace furnace, int level) {
		this.ownerUUID = ownerUUID;
		this.furnace = furnace;
	}

	public boolean tryUpgrade(int level) {
		if(canUpgrade(level)) {
			LevelRequirements lr = LevelRequirements.fromLevel(level);
			//TODO upgrade sound
			//TODO partikel
			Player p = Bukkit.getPlayer(ownerUUID);
			boolean ret = false;
			for(ItemStack req : lr.getRequirememts()) {
				if(p.getInventory().contains(req)) {
					int counter = 0;
					List<ItemStack> slotsToClear = new ArrayList<ItemStack>();
					for(ItemStack given : p.getInventory()) {
						if(given != null && given.getType() == req.getType()) {
							if(given.getAmount() > req.getAmount()) {
								given.setAmount(given.getAmount() - req.getAmount());
								ret = true;
								break;
							} else if(given.getAmount() == req.getAmount()) {
								given.setType(Material.AIR);
								ret = true;
								break;
							} else {
								counter += given.getAmount();
								slotsToClear.add(given);
								if(counter > req.getAmount()) break;
							}
						}
					}
					if(counter > req.getAmount()) {
						for(int i = 0; i < slotsToClear.size()-1; i++) {
							slotsToClear.get(i).setType(Material.AIR);
						}
						slotsToClear.get(slotsToClear.size()-1).setAmount(counter-req.getAmount());
						ret = true;
					} else if(counter == req.getAmount()) {
						for(ItemStack i : slotsToClear) {
							i.setType(Material.AIR);
						}
						ret = true;
					}
				}
			}
			return ret;
		}
		return false;
	}
	
	private boolean canUpgrade(int level) {
		boolean ret = false;
		LevelRequirements lr = LevelRequirements.fromLevel(level);
		if(lr.canBuy(Bukkit.getPlayer(ownerUUID).getInventory())) {
			ret = true;
		}
		return ret;
	}
	
	public void refreshGUI() {
		gui = new GUI(id);
		this.gui.refresh();
	}
	
	public boolean isPlaced() {
		return isPlaced;
	}
	
	public void setPlaced(boolean placed) {
		this.isPlaced = placed;
	}
	
	public void place(BlastFurnace furnace) {
		this.furnace = furnace;
		this.isPlaced = true;
	}
	
	public void drop() {
		//TODO Sound & Partikel
		
		Bukkit.getPlayer(ownerUUID).closeInventory();
		this.furnace.getBlock().setType(Material.AIR);
		this.furnace = null;
		Bukkit.getPlayer(ownerUUID).getInventory().addItem(Crafting.getGeneratorItem(id, ownerUUID.toString(), level));
		this.isPlaced = false;
	}
	
	public void open(Player p) {
		p.closeInventory();
		gui = new GUI(id);
		gui.refresh();
		p.openInventory(gui.getInventory());
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	public void setOwnerUUID(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}
	
	public BlastFurnace getFurnace() {
		return furnace;
	}
	public void setLoc(BlastFurnace furnace) {
		this.furnace = furnace;
	}
}
