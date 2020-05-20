package com.github.dennermelo.aura.tags;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.dennermelo.aura.tags.commands.TagsCommand;
import com.github.dennermelo.aura.tags.listeners.InventoryInteract;
import com.github.dennermelo.aura.tags.listeners.Join;
import com.github.dennermelo.aura.tags.listeners.PlayerChat;
import com.github.dennermelo.aura.tags.managers.TagManager;
import com.github.dennermelo.aura.tags.settings.Preferences;
import com.github.dennermelo.aura.tags.types.Messages;

import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Messages MessagesManager;
	private static Preferences PreferencesManager;
	private static TagManager TagManager;
	private static Economy econ = null;
	private static PlayerPoints playerPoints;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§b[Aura-Tags] §7Inicializando o plugin...");
		Bukkit.getConsoleSender().sendMessage("§b[Aura-Tags] §7Desenvolvido por: §bString#5764");
		Bukkit.getConsoleSender().sendMessage("§b[Aura-Tags] §7Github: §bgithub.com/dennermelo");
		getInstance().saveDefaultConfig();
		getInstance().getConfig().options().copyDefaults(false);
		MessagesManager = new Messages(this);
		PreferencesManager = new Preferences(this);
		TagManager = new TagManager();
		setupEconomy();
		hookPlayerPoints();
		registerEvents();
		registerCommands();
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§b[Aura-Tags] §7Desabilitando o plugin...");
		Bukkit.getConsoleSender().sendMessage("§b[Aura-Tags] §7Desenvolvido por: §bString#5764");
		Bukkit.getConsoleSender().sendMessage("§b[Aura-Tags] §7Github: §bgithub.com/dennermelo");
		HandlerList.unregisterAll();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new InventoryInteract(), this);
		pm.registerEvents(new Join(), this);
		pm.registerEvents(new PlayerChat(), this);

	}

	private void registerCommands() {
		getCommand("tags").setExecutor(new TagsCommand());
	}

	public static Messages getMessagesManager() {
		return MessagesManager;
	}

	public static Preferences getPreferencesManager() {
		return PreferencesManager;
	}

	public static Main getInstance() {
		return getPlugin(Main.class);
	}

	public static TagManager getTagManager() {
		return TagManager;
	}

	public static Economy getEco() {
		return econ;
	}

	public static PlayerPoints getPoints() {
		return playerPoints;
	}

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

	private boolean hookPlayerPoints() {
		final Plugin plugin = Bukkit.getPluginManager().getPlugin("PlayerPoints");
		playerPoints = PlayerPoints.class.cast(plugin);
		return playerPoints != null;
	}
}
