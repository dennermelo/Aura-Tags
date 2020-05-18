package com.github.dennermelo.aura.tags.manager;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.core.TagCore;
import com.github.dennermelo.aura.tags.objects.Tag;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagManager {

	private HashMap<String, Tag> tags;
	private List<Tag> tags_list;
	private Plugin plugin = Main.getPlugin();

	public TagManager() {
		this.tags = new HashMap<String, Tag>();
	}

	public void setTag(Player player, Tag tag) {
		tags.put(player.getName(), tag);
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

	@SuppressWarnings("deprecation")
	public void giveTag(Player player, Tag tag) {
		PermissionUser user = PermissionsEx.getUser(player);
		if (tag.getTipo().equalsIgnoreCase("cash")) {
			user.addPermission(tag.getPermissao());
			tags.put(player.getName(), tag);
			TagCore.getPlayerPoints().getAPI().take(player.getName(), tag.getValor());
			if (TagCore.USE_ACTION) {
				for (Player players : Bukkit.getOnlinePlayers()) {
					ActionBarAPI.sendActionBar(players,
							TagCore.getConfig().getString("Actionbar.Mensagens.comprou").replace("&", "§")
									.replace("%tag%", tag.getFormato()).replace("%valor%", tag.getValor() + " Cash")
									.replace("%player%", player.getName()));
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
		} else if (tag.getTipo().equalsIgnoreCase("coins")) {
			user.addPermission(tag.getPermissao());
			tags.put(player.getName(), tag);
			TagCore.getEco().withdrawPlayer(player.getName(), tag.getValor());
			if (TagCore.USE_ACTION) {
				for (Player players : Bukkit.getOnlinePlayers()) {
					ActionBarAPI.sendActionBar(players,
							TagCore.getConfig().getString("Actionbar.Mensagens.comprou").replace("&", "§")
									.replace("%tag%", tag.getFormato()).replace("%valor%", tag.getValor() + " Coins")
									.replace("%player%", player.getName()));
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

	public void removeTag(String player) {
		tags.remove(player);
	}

	public Tag getTag(String player) {
		return tags.get(player);
	}

	public boolean hasTag(String player) {
		return tags.containsKey(player);
	}

	public List<Tag> getTags() {
		return tags_list;
	}

	public void loadTags() {
		for (String key : plugin.getConfig().getConfigurationSection("Tags").getKeys(false)) {
			String nome = plugin.getConfig().getString("Tags." + key + ".nome");
			String permissao = plugin.getConfig().getString("Tags." + key + ".permissao");
			String formato = plugin.getConfig().getString("Tags." + key + ".formato").replace("&", "§");
			int valor = plugin.getConfig().getInt("Tags." + key + ".valor");
			String tipo = plugin.getConfig().getString("Tags." + key + ".tipo");

			tags_list.add(new Tag(nome,
					plugin.getConfig().getString("Item.Basico.nome").replace("&", "§").replace("%tag_nome%", nome),
					permissao, formato, valor, tipo));
			Bukkit.getConsoleSender()
					.sendMessage("§b[Aura-Tags] §7O prefixo " + formato + " §7foi carregado corretamente.");
		}
	}

}
