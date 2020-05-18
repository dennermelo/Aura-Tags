package com.github.dennermelo.aura.tags.core;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.command.TagsCommand;
import com.github.dennermelo.aura.tags.listener.TagsListener;
import com.github.dennermelo.aura.tags.manager.TagManager;

import net.milkbowl.vault.economy.Economy;

public class TagCore {

	private static Economy econ = null;
	private static PlayerPoints playerPoints;
	private static TagManager tagManager = new TagManager();
	private final Main plugin;

	public static boolean USE_TITLE;
	public static boolean USE_ACTION;

	public TagCore(Main plugin) {
		this.plugin = plugin;

		hookPlayerPoints();
		setupEconomy();

		loadConfig();
		tagManager.loadTags();
		loadCommands();
		loadParameters();
		loadEvents();
	}

	/*
	 * Load Section;
	 */

	private void loadParameters() {
		hookPlayerPoints();
		setupEconomy();
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

	/*
	 * Get Section;
	 */

	public static PlayerPoints getPlayerPoints() {
		return playerPoints;
	}

	public static TagManager getTagManger() {
		return tagManager;
	}

	public static FileConfiguration getConfig() {
		return Main.getPlugin().getConfig();
	}

	public static Economy getEco() {
		return econ;
	}

	/*
	 * Extra Section;
	 */

	private boolean setupEconomy() {
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

}
