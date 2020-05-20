package com.github.dennermelo.aura.tags.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.objects.Tag;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class InventoryInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(InventoryClickEvent e) {

		if (e.getWhoClicked() instanceof Player) {

			if (e.getInventory() == null || e.getClickedInventory() == null || e.getCurrentItem() == null) {
				e.setCancelled(true);
				return;
			}

			Player player = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals("§7Tags do Servidor")) {
				for (Tag tag : Main.getTagManager().getTagList()) {
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals(tag.getItemNome())) {
						if (PermissionsEx.getUser(player).has(tag.getPermissao())
								&& Main.getTagManager().getTag(player) != tag) {
							Main.getTagManager().setTag(player, tag);
							e.setCancelled(true);
							player.closeInventory();
							return;
						}
						if (!PermissionsEx.getUser(player).has(tag.getPermissao())
								&& Main.getTagManager().getTag(player) != tag) {
							if (tag.getTipo().equalsIgnoreCase("cash")) {
								if (Main.getPoints().getAPI().look(player.getName()) >= tag.getValor()) {
									Main.getTagManager().giveTag(player, tag);
									e.setCancelled(true);
									player.closeInventory();
									return;
								} else {
									player.sendMessage(Main.getMessagesManager().MESSAGE_PLAYER_INSUFFICIENT_FUNDS);
									e.setCancelled(true);
									player.closeInventory();
									return;
								}
							}
							if (tag.getTipo().equalsIgnoreCase("coins")) {
								if (Main.getEco().has(player, tag.getValor())) {
									Main.getTagManager().giveTag(player, tag);
									e.setCancelled(true);
									player.closeInventory();
									return;
								} else {
									player.sendMessage(Main.getMessagesManager().MESSAGE_PLAYER_INSUFFICIENT_FUNDS);
									e.setCancelled(true);
									player.closeInventory();
									return;
								}
							}
						}

					}
				}
			}
		}

	}

}
