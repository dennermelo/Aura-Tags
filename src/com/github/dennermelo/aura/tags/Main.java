package com.github.dennermelo.aura.tags;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.dennermelo.aura.tags.core.TagCore;

public class Main extends JavaPlugin {

	private static Plugin plugin;
	private static TagCore tagCore;

	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("[Aura-Tags] Inicializando instâncias...");
		plugin = this;
		tagCore = new TagCore(this);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("[Aura-Tags] Finalizando instâncias...");
		HandlerList.unregisterAll();
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static TagCore getTagCore() {
		return tagCore;
	}

}
