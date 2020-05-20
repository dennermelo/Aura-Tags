package com.github.dennermelo.aura.tags.settings;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.dennermelo.aura.tags.Main;

public class Preferences {

	public boolean USE_TITLE;
	public boolean USE_ACTIONBAR;

	public String BASIC_ITEM_NAME;
	public List<String> BASIC_ITEM_LORE;

	public List<String> ADDITIONAL_LORE_BUY;
	public List<String> ADDITIONAL_LORE_SELECTED;
	public List<String> ADDITIONAL_LORE_SELECT;

	public Preferences(Main plugin) {
		FileConfiguration cf = plugin.getConfig();

		USE_TITLE = cf.getBoolean("Title.ativar");
		USE_ACTIONBAR = cf.getBoolean("Actionbar.ativar");

		BASIC_ITEM_NAME = cf.getString("Item.Basico.nome").replace("&", "§");
		BASIC_ITEM_LORE = cf.getStringList("Item.Basico.lore");

		ADDITIONAL_LORE_BUY = cf.getStringList("Item.Comprar.lore");
		ADDITIONAL_LORE_SELECT = cf.getStringList("Item.Selecionar.lore");
		ADDITIONAL_LORE_SELECTED = cf.getStringList("Item.Selecionado.lore");
	}

}
