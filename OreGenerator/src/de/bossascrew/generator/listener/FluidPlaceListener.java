package de.bossascrew.generator.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FluidPlaceListener implements Listener {

    @EventHandler
    public void onPlaceFluid(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Material type;
        if (event.getItem() == null) return;
        if (event.getItem().getType() == Material.WATER_BUCKET) {
            type = Material.WATER;
        } else if (event.getItem().getType() == Material.LAVA_BUCKET) {
            type = Material.LAVA;
        } else return;
        Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        if (generates(type, block)) {
            event.setCancelled(true);
            return;
        } else {
            BlockFace[] nesw = {BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            for (BlockFace face : nesw) {
                if (generates(type, block.getRelative(face))) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
	
    private boolean generates(Material from, Block to) {
        if (!to.isLiquid()) return false;
        return generates(from, to.getType());
    }

    private boolean generates(Material from, Material to) {
        return from != to;
    }
}
