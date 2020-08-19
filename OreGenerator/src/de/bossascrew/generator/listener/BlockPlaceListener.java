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
import de.tr7zw.nbtapi.NBTItem;

public class BlockPlaceListener implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		if(event.isCancelled()) return;
		if(event.getPlayer() == null) return;
		if(event.getBlockPlaced() == null) return; 
		
		if(event.getBlock().getType() == Material.BLAST_FURNACE) {

			Player p = event.getPlayer();

			if(DataManager.getInstance().getGenerators(p.getUniqueId()).size() >= Generator.getInstance().getCfg().getMaximumGeneratorCount()) {
				//TODO bypasspermission
				p.sendMessage(Message.MAXIMUM_GENERATORS_PLACED);
				event.setCancelled(true);
				return;
			} else {
				//TODO spieler sagen wie viele er noch setzen kann
				
			}
			
			NBTItem nbt = new NBTItem(event.getItemInHand());
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
		}
	}
}
