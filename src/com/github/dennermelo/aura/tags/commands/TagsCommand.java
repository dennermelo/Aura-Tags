package com.github.dennermelo.aura.tags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.dennermelo.aura.tags.inventories.TagsInventory;

public class TagsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("tags")) {
			new TagsInventory(player);
			return true;
		}
		return false;
	}

}
