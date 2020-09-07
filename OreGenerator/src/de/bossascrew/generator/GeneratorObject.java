package de.bossascrew.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.bossascrew.generator.Inventories.GUI;
import de.bossascrew.generator.crafting.Crafting;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.utils.LevelRequirements;

public class GeneratorObject {

	int id = 0;
	UUID ownerUUID;
	BlastFurnace furnace;
	
	String world;
	int posx;
	int posy;   
	int posz;
	
	boolean isPlaced = false;
	boolean isLoading = true;
	int level;
	GUI gui;
	
	public GeneratorObject(UUID ownerUUID, BlastFurnace furnace, int level) {
		this.ownerUUID = ownerUUID;
		initialize(furnace, level);
	}
	
	public GeneratorObject(int id, UUID ownerUUID, BlastFurnace furnace, int level) {
		this.id = id;
		this.ownerUUID = ownerUUID;
		initialize(furnace, level);
	}

	public void initialize(BlastFurnace furnace, int level) {
		
		this.level = level;
		this.furnace = furnace;
		
		if(furnace != null) isPlaced = true;
		toNonBukkit();
	}
	
	public void toNonBukkit() {
		world = furnace.getLocation().getWorld().getName();
		posx = furnace.getLocation().getBlockX();
		posy = furnace.getLocation().getBlockY();
		posz = furnace.getLocation().getBlockZ();
	}
	
	public void loadFurnaceFromLoc() {
		furnace = (BlastFurnace) new Location(Bukkit.getWorld(world), posx, posy, posz).getBlock().getState();
	}
	
	public boolean tryUpgrade(int level) {
		if(hasRequiredItems(level)) {
			removeItems(level);
			Player p = Bukkit.getPlayer(ownerUUID);
			System.out.println("Erzgenerator > " + p.getName() + " upgradet seinen Generator (ID:" + id + ") auf Level " + level);
			p.playSound(furnace.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			p.playSound(furnace.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
			this.level++;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasRequiredItems(int level) {
		LevelRequirements lr = LevelRequirements.fromLevel(level);
		Player p = Bukkit.getPlayer(ownerUUID);

		for(ItemStack req : lr.getRequirememts()) {
			int counter = 0;
			for(int x = 0; x < p.getInventory().getSize(); x++) {
				ItemStack given = p.getInventory().getItem(x);
				if(given != null && given.getType() == req.getType()) {
					counter += given.getAmount();
				}
			}
			if(counter < req.getAmount()) {
				return false;
			}
		}
		return true;
	}
	
	public void removeItems(int level) {
		LevelRequirements lr = LevelRequirements.fromLevel(level);

		Player p = Bukkit.getPlayer(ownerUUID);
		for(ItemStack req : lr.getRequirememts()) {
			int counter = 0;
			List<Integer> slotsToClear = new ArrayList<Integer>();
			for(int x = 0; x < p.getInventory().getSize(); x++) {
				ItemStack given = p.getInventory().getItem(x);
				if(given != null && given.getType() == req.getType()) {
					if(given.getAmount() > req.getAmount()) {
						given.setAmount(given.getAmount() - req.getAmount());
						break;
					} else if(given.getAmount() == req.getAmount()) {
						p.getInventory().setItem(x, null);
						break;
					} else {
						counter += given.getAmount();
						slotsToClear.add(x);
						if(counter > req.getAmount()) break;
					}
				}
			}
			if(counter > req.getAmount()) {
				for(int i = 0; i < slotsToClear.size()-1; i++) {
					p.getInventory().setItem(slotsToClear.get(i), null);
				}
				p.getInventory().getItem(slotsToClear.get(slotsToClear.size()-1)).setAmount(counter-req.getAmount());;
			} else if(counter == req.getAmount()) {
				for(int i : slotsToClear) {
					p.getInventory().setItem(i, null);
				}
			}
		}
	}
	
	public void refreshGUI() {
		this.gui.refresh();
	}
	
	public boolean isLoading() {
		return isLoading;
	}
	
	public boolean isPlaced() {
		return isPlaced;
	}
	
	public void setPlaced(boolean placed) {
		this.isPlaced = placed;
	}
	
	public void place() {
		this.isPlaced = true;
		this.isLoading = false;
	}

	public void setLoading(boolean loading) {
		this.isLoading = loading;
	}
	
	public void drop() {
		this.loadFurnaceFromLoc();
		Player p = Bukkit.getPlayer(ownerUUID);
		p.playSound(furnace.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
		p.closeInventory();
		this.furnace.getBlock().setType(Material.AIR);
		this.furnace = null;
		p.getInventory().addItem(Crafting.getGeneratorItem(id, ownerUUID.toString(), level));
		this.isPlaced = false;
		DataManager.getInstance().dropGenerator(this);
	}
	
	public void open(Player p) {
		p.closeInventory();
		gui = new GUI(this);
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
	public String getWorld() {
		return world;
	}

	public int getPosx() {
		return posx;
	}

	public int getPosy() {
		return posy;
	}

	public int getPosz() {
		return posz;
	}
}
