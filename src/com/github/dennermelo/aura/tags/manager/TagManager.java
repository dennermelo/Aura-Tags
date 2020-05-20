package com.github.dennermelo.aura.tags.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.objects.Tag;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagManager {

	private HashMap<String, Tag> tags;
	private List<Tag> tag_list;

	public TagManager() {
		this.tags = new HashMap<String, Tag>();
		this.tag_list = new ArrayList<Tag>();
		FileConfiguration cf = Main.getInstance().getConfig();
		for (String tag : Main.getInstance().getConfig().getConfigurationSection("Tags").getKeys(false)) {
			String nome = cf.getString("Tags." + tag + ".nome");
			String item_nome = Main.getPreferencesManager().BASIC_ITEM_NAME.replace("%tag_nome%", nome)
					.replace("%tag_formato", cf.getString("Tags." + tag + ".formato").replace("&", "§"));
			String permissao = cf.getString("Tags." + tag + ".permissao");
			String formato = cf.getString("Tags." + tag + ".formato").replace("&", "§");
			int valor = cf.getInt("Tags." + tag + ".valor");
			String tipo = cf.getString("Tags." + tag + ".tipo");

			tag_list.add(new Tag(nome, item_nome, permissao, formato, valor, tipo));
		}
	}

	public void setTag(Player player, Tag tag) {

		tags.put(player.getName(), tag);
		player.sendMessage(Main.getMessagesManager().MESSAGE_PLAYER_SELECTED.replace("%tag%", tag.getFormato()));
		if (Main.getPreferencesManager().USE_TITLE) {
			TitleAPI.sendTitle(player, 40, 50, 40,
					Main.getMessagesManager().TITLE_SELECTED.replace("%tag%", tag.getFormato()),
					Main.getMessagesManager().SUBTITLE_SELECTED.replace("%tag%", tag.getFormato()));
		}
	}

	@SuppressWarnings("deprecation")
	public void giveTag(Player player, Tag tag) {

		tags.put(player.getName(), tag);
		PermissionsEx.getUser(player).addPermission(tag.getPermissao());

		player.sendMessage(Main.getMessagesManager().MESSAGE_PLAYER_PURCHASED.replace("%tag%", tag.getFormato()));
		if (Main.getPreferencesManager().USE_TITLE) {
			TitleAPI.sendTitle(player, 40, 50, 40, Main.getMessagesManager().TITLE_PURCHASED,
					Main.getMessagesManager().SUBTITLE_PURCHASED.replace("%tag%", tag.getFormato()).replace("%valor%",
							tag.getValor() + " " + tag.getTipo()));
		}
		if (Main.getPreferencesManager().USE_ACTIONBAR) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				ActionBarAPI.sendActionBar(players,
						Main.getMessagesManager().ACTIONBAR_PURCHASED.replace("%player%", player.getName())
								.replace("%tag%", tag.getFormato())
								.replace("%valor%", tag.getValor() + " " + tag.getTipo()));
			}
		}
		if (tag.getTipo().equalsIgnoreCase("cash")) {
			Main.getPoints().getAPI().take(player.getName(), tag.getValor());
		} else if (tag.getTipo().equalsIgnoreCase("coins")) {
			Main.getEco().bankWithdraw(player.getName(), tag.getValor());
		}
	}

	public Tag getTag(Player player) {
		return tags.get(player.getName());
	}

	public boolean hasTag(Player player) {
		return tags.containsKey(player.getName());
	}

	public void defineTag(Player player, Tag tag) {
		tags.put(player.getName(), tag);
	}

	public List<Tag> getTagList() {
		return tag_list;
	}
}
