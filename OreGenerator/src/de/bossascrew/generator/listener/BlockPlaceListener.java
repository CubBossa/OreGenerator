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
import de.bossascrew.generator.data.Message;
import de.bossascrew.generator.data.Permission;
import de.tr7zw.nbtapi.NBTItem;

public class BlockPlaceListener implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		if(event.isCancelled()) return;
		if(event.getPlayer() == null) return;
		if(event.getBlockPlaced() == null) return; 
		
		if(event.getBlock().getType() == Material.BLAST_FURNACE) {

			Player p = event.getPlayer();

			NBTItem nbt = new NBTItem(event.getItemInHand());
			if(!nbt.getString(Generator.NBT_TYPE_KEY).equals(Generator.NBT_TYPE_VALUE_GENERATOR)) {
				return;
			}
			
			if(!p.hasPermission(Permission.PLACE_GENERATOR)) {
				p.sendMessage(Message.NO_PERMISSION);
				return;
			}
			int size = DataManager.getInstance().getGenerators(p.getUniqueId()).size();
			if(size >= Generator.getInstance().getCfg().getMaximumGeneratorCount() && !p.hasPermission(Permission.BYPASS_PLACELIMIT)) {
				p.sendMessage(Message.MAXIMUM_GENERATORS_PLACED);
				event.setCancelled(true);
				return;
			}

			String ownerString = nbt.getString(Generator.NBT_OWNER_UUID_KEY);
			UUID owner;
			if(ownerString.equals(Generator.NBT_OWNER_NONE_VALUE)) {
				owner = p.getUniqueId();
			} else {
				owner = UUID.fromString(ownerString);
			}
			if(!p.getUniqueId().equals(owner)) return;
			
			int level = nbt.getInteger(Generator.NBT_LEVEL_KEY);
			GeneratorObject go;
			if(level == -1) {
				go = DataManager.getInstance().createGenerator(event.getPlayer().getUniqueId(), event.getBlock().getLocation(), 0);
			} else {
				NBTItem i = new NBTItem(event.getItemInHand());
				int id = i.getInteger(Generator.NBT_GENERATORID_KEY);
				String uuid = i.getString(Generator.NBT_OWNER_UUID_KEY);
				int l = i.getInteger(Generator.NBT_LEVEL_KEY);
				go = DataManager.getInstance().recreateGenerator(id, UUID.fromString(uuid), event.getBlock().getLocation(), l);
				System.out.println(go);
			}
			BlastFurnace bf = (BlastFurnace) event.getBlockPlaced().getState();
			bf.setCustomName(Generator.GENERATOR_NAME);
			go.place(bf);
			
			if(p.getGameMode().equals(GameMode.CREATIVE)) {
				if(event.getItemInHand().getAmount() > 1) {
					event.getItemInHand().setAmount(event.getItemInHand().getAmount()-1);
				} else {
					p.getInventory().setItem(event.getHand(), null);
				}
			}
			int sizeAfter = DataManager.getInstance().getGenerators(p.getUniqueId()).size();
			p.sendMessage(Message.OUT_OF_PLACED.replace("[placed]", sizeAfter + "").replace("[maximum]", "" + Generator.getInstance().getCfg().getMaximumGeneratorCount()));
		}
	}
}
