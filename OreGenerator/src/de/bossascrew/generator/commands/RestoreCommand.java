package de.bossascrew.generator.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.bossascrew.generator.GeneratorObject;
import de.bossascrew.generator.crafting.Crafting;
import de.bossascrew.generator.data.DataManager;
import de.bossascrew.generator.data.Message;

public class RestoreCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("Bitte sei keine Konsole bro");
			return false;
		}
		if(!args[0].equalsIgnoreCase("restore")) {
			sender.sendMessage(Message.WRONG_COMMAND_USAGE);
			return false;
		}
		int id = 0;
		try {
			id = Integer.parseInt(args[1]);
		} catch(NumberFormatException e) {
			sender.sendMessage(Message.WRONG_COMMAND_USAGE);
			return false;
		}
		GeneratorObject g = DataManager.getInstance().getGenerator(id);
		if(g != null) {
			((Player) sender).getInventory().addItem(Crafting.getGeneratorItem(g.getId(), g.getOwnerUUID().toString(), g.getLevel()));
		}	
		return false;
	}
}
