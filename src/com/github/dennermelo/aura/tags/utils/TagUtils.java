package com.github.dennermelo.aura.tags.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import com.github.dennermelo.aura.tags.core.TagCore;
import com.github.dennermelo.aura.tags.objects.Tag;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagUtils {

	@SuppressWarnings("deprecation")
	public static void giveTag(Player player, Tag tag) {
		PermissionUser user = PermissionsEx.getUser(player);
		if (tag.getTipo().equalsIgnoreCase("cash")) {
			user.addPermission(tag.getPermissao());
			TagCore.getTagManger().setTag(player.getName(), tag);
			TagCore.getPlayerPoints().getAPI().take(player.getName(), tag.getValor());
			if (TagCore.USE_ACTION) {
				for (Player players : Bukkit.getOnlinePlayers()) {
					ActionBarAPI.sendActionBar(players,
							TagCore.getConfig().getString("Actionbar.Mensagens.comprou").replace("&", "§")
									.replace("%tag%", tag.getFormato())
									.replace("%valor%", tag.getValor() + " Cash").replace("%player%", player.getName()));
				}
			}
			if (TagCore.USE_TITLE) {
				TitleAPI.sendTitle(player, 40, 50, 40,
						TagCore.getConfig().getString("Title.Mensagens.Comprou.titulo").replace("&", "§"),
						TagCore.getConfig().getString("Title.Mensagens.Comprou.subtitulo").replace("&", "§")
								.replace("%tag%", tag.getFormato()));
			}
			player.sendMessage(TagCore.getConfig().getString("Mensagens.Player.comprou").replace("&", "§")
					.replace("%tag%", tag.getFormato()));
			return;
		} else if (tag.getTipo().equalsIgnoreCase("coins")) {
			user.addPermission(tag.getPermissao());
			TagCore.getTagManger().setTag(player.getName(), tag);
			TagCore.getEco().withdrawPlayer(player.getName(), tag.getValor());
			if (TagCore.USE_ACTION) {
				for (Player players : Bukkit.getOnlinePlayers()) {
					ActionBarAPI.sendActionBar(players,
							TagCore.getConfig().getString("Actionbar.Mensagens.comprou").replace("&", "§")
									.replace("%tag%", tag.getFormato())
									.replace("%valor%", tag.getValor() + " Coins").replace("%player%", player.getName()));
				}
			}
			if (TagCore.USE_TITLE) {
				TitleAPI.sendTitle(player, 40, 50, 40,
						TagCore.getConfig().getString("Title.Mensagens.Comprou.titulo").replace("&", "§"),
						TagCore.getConfig().getString("Title.Mensagens.Comprou.subtitulo").replace("&", "§")
								.replace("%tag%", tag.getFormato()));
			}
			player.sendMessage(TagCore.getConfig().getString("Mensagens.Player.comprou").replace("&", "§")
					.replace("%tag%", tag.getFormato()));
			player.closeInventory();
			return;
		}
	}

	public static void setTag(Player player, Tag tag) {
		TagCore.getTagManger().setTag(player.getName(), tag);
		player.sendMessage(TagCore.getConfig().getString("Mensagens.Player.selecionou").replace("&", "§")
				.replace("%tag%", tag.getFormato()));
		if (TagCore.USE_TITLE) {
			TitleAPI.sendTitle(player, 40, 50, 40,
					TagCore.getConfig().getString("Title.Mensagens.Selecionou.titulo").replace("&", "§"),
					TagCore.getConfig().getString("Title.Mensagens.Selecionou.subtitulo").replace("&", "§")
							.replace("%tag%", tag.getFormato()));
		}
		player.closeInventory();
	}
}
