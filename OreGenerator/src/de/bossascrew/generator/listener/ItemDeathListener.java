package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

public class ItemDeathListener implements Listener {

	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		if(event.getEntityType() == EntityType.DROPPED_ITEM) {
			ItemStack i = (ItemStack) event.getEntity();
			NBTItem ib = new NBTItem(i);
			if(!ib.hasNBTData()) return;
			if(i.getType() == Material.FURNACE) {
				//TODO DELETE HERE;
				System.out.println("Deleting entry");
				//i.getInteger(Generator.NBT_GENERATORID_KEY)
			}
		}
	}
}
