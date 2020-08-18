package de.bossascrew.generator.listener;

import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import de.bossascrew.generator.Generator;
import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;
import de.tr7zw.nbtapi.NBTItem;

public class BlockPlaceListener implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		if(event.getPlayer() == null) return;
		if(event.getBlockPlaced() == null) return; 
		
		if(event.getBlock().getType() == Material.BLAST_FURNACE) {

			Player p = event.getPlayer();
			NBTItem nbt = new NBTItem(event.getItemInHand());
			String ownerString = nbt.getString(Generator.NBT_OWNER_UUID_KEY);
			UUID owner;
			if(ownerString.equals(Generator.NBT_OWNER_NONE_VALUE)) {
				owner = p.getUniqueId();
			} else {
				owner = UUID.fromString(ownerString);
			}
			if(!p.getUniqueId().equals(owner)) return;
			
			if(p.getGameMode().equals(GameMode.CREATIVE)) {
				event.getItemInHand().setType(Material.AIR);
			}
			
			int level = nbt.getInteger(Generator.NBT_LEVEL_KEY);
			GeneratorObject go;
			if(level == -1) {
				go = DataManager.getInstance().createGenerator(event.getPlayer().getUniqueId(), event.getBlock().getLocation(), 0);
				System.out.println(go);
			} else {
				NBTItem i = new NBTItem(event.getItemInHand());
				int id = i.getInteger(Generator.NBT_GENERATORID_KEY);
				go = DataManager.getInstance().getGenerator(id);
			}
			BlastFurnace bf = (BlastFurnace) event.getBlockPlaced().getState();
			bf.setCustomName(Generator.GENERATOR_NAME);
			go.place(bf);
		}
	}
}
