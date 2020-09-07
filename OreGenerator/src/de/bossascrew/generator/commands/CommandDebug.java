package de.bossascrew.generator.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.data.DataManager;

public class CommandDebug implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player) || ((Player) sender).isOp()) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				for(GeneratorObject go : DataManager.getInstance().getGenerators(p.getUniqueId())) {
					System.out.println("Generator: " + go);
				}
			}
		}
		return false;
	}
}
