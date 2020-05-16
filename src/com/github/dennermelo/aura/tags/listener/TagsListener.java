package com.github.dennermelo.aura.tags.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.github.dennermelo.aura.tags.core.TagCore;
import com.github.dennermelo.aura.tags.objects.Tag;
import com.github.dennermelo.aura.tags.utils.TagUtils;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagsListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void eventChat(ChatMessageEvent e) {
		if (TagCore.getTagManger().hasTag(e.getSender().getName())) {
			e.setTagValue("aura_tag", TagCore.getTagManger().getTag(e.getSender().getName()).getFormato());
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH)
	public void eventInventory(InventoryClickEvent e) {
		if (e.getInventory().getTitle().equals("§7Tags do Servidor") && e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			PermissionUser user = PermissionsEx.getUser(player);

			if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getInventory() == null) {
				e.setCancelled(true);
				return;
			}
			for (Tag tag : TagCore.getTags()) {

				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(tag.getItem_nome())) {

					if (TagCore.getTagManger().getTag(player.getName()) == tag) {
						e.setCancelled(true);
						return;
					} else if (user.has(tag.getPermissao())) {
						TagUtils.setTag(player, tag);
						return;
					}

					if (tag.getTipo().equalsIgnoreCase("cash")) {
						if (TagCore.getPlayerPoints().getAPI().look(player.getName()) >= tag.getValor()) {
							TagUtils.giveTag(player, tag);
							return;
						} else {
							player.sendMessage(
									TagCore.getConfig().getString("Mensagens.Player.sem-saldo").replace("&", "§"));
							player.closeInventory();
							return;
						}
					} else if (tag.getTipo().equalsIgnoreCase("coins")) {
						if (TagCore.getEco().has(player.getName(), tag.getValor())) {
							TagUtils.giveTag(player, tag);
							return;
						} else {
							player.sendMessage(
									TagCore.getConfig().getString("Mensagens.Player.sem-saldo").replace("&", "§"));
							player.closeInventory();
							return;
						}
					}
				}
			}
		}
	}

}
