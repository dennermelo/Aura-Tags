package com.github.dennermelo.aura.tags.core;

import java.util.ArrayList;
import java.util.List;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.command.TagsCommand;
import com.github.dennermelo.aura.tags.listener.TagsListener;
import com.github.dennermelo.aura.tags.manager.TagManager;
import com.github.dennermelo.aura.tags.objects.Tag;

import net.milkbowl.vault.economy.Economy;

public class TagCore {

	private static Economy econ = null;
	private static PlayerPoints playerPoints;
	private static List<Tag> tags = new ArrayList<Tag>();
	private static TagManager tagManager;
	private static Main plugin;

	public static boolean USE_TITLE;
	public static boolean USE_ACTION;

	public TagCore(Main plugin) {
		TagCore.plugin = plugin;
		loadConfig();
		loadTags();
		loadCommands();
		loadParameters();
		loadEvents();
		hookPlayerPoints();
	}

	public static Economy getEco() {
		return econ;
	}

	private boolean setupEconomy(Plugin plugin) {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private void loadTags() {
		for (String key : plugin.getConfig().getConfigurationSection("Tags").getKeys(false)) {
			String nome = plugin.getConfig().getString("Tags." + key + ".nome");
			String permissao = plugin.getConfig().getString("Tags." + key + ".permissao");
			String formato = plugin.getConfig().getString("Tags." + key + ".formato").replace("&", "§");
			int valor = plugin.getConfig().getInt("Tags." + key + ".valor");
			String tipo = plugin.getConfig().getString("Tags." + key + ".tipo");

			tags.add(new Tag(nome,
					getConfig().getString("Item.Basico.nome").replace("&", "§").replace("%tag_nome%", nome), permissao,
					formato, valor, tipo));
			Bukkit.getConsoleSender()
					.sendMessage("§b[Aura-Tags] §7O prefixo " + formato + " §7foi carregado corretamente.");
		}
	}

	private void loadParameters() {
		hookPlayerPoints();
		setupEconomy(plugin);
		tagManager = new TagManager();

		USE_TITLE = getConfig().getBoolean("Title.ativar");
		USE_ACTION = getConfig().getBoolean("Actionbar.ativar");
	}

	private void loadEvents() {
		Bukkit.getPluginManager().registerEvents(new TagsListener(), plugin);
	}

	private void loadCommands() {
		plugin.getCommand("tags").setExecutor(new TagsCommand());
	}

	private void loadConfig() {
		plugin.saveDefaultConfig();
		plugin.getConfig().options().copyDefaults(false);
	}

	private boolean hookPlayerPoints() {
		final Plugin plugin = Bukkit.getPluginManager().getPlugin("PlayerPoints");
		playerPoints = PlayerPoints.class.cast(plugin);
		return playerPoints != null;
	}

	public static PlayerPoints getPlayerPoints() {
		return playerPoints;
	}

	public static TagManager getTagManger() {
		return tagManager;
	}

	public static List<Tag> getTags() {
		return tags;
	}

	public static FileConfiguration getConfig() {
		return plugin.getConfig();
	}

}
