package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.block.BlastFurnace;
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
			
			NBTItem nbt = new NBTItem(event.getItemInHand());
			if(!nbt.getString("customname").equals(Generator.GENERATOR_CODENAME)) return;

			String level = nbt.getString("level");
			GeneratorObject go;
			if(level == "-1") {
				go = DataManager.getInstance().createGenerator(event.getPlayer().getUniqueId(), (BlastFurnace) event.getBlockPlaced(), 0);
			} else {
				go = DataManager.getInstance().getGenerator(event.getPlayer(), event.getBlockPlaced().getLocation());
			}
			BlastFurnace bf = (BlastFurnace) event.getBlockPlaced();
			bf.setCustomName(Generator.GENERATOR_CODENAME);
			go.place((BlastFurnace) event.getBlockPlaced());
		}
	}
}
