package com.github.dennermelo.aura.tags.types;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.dennermelo.aura.tags.Main;

public class Messages {

	public String MESSAGE_PLAYER_SELECTED;
	public String MESSAGE_PLAYER_PURCHASED;
	public String MESSAGE_PLAYER_INSUFFICIENT_FUNDS;

	public String TITLE_SELECTED;
	public String SUBTITLE_SELECTED;

	public String TITLE_PURCHASED;
	public String SUBTITLE_PURCHASED;

	public String ACTIONBAR_PURCHASED;

	public Messages(Main plugin) {

		FileConfiguration cf = plugin.getConfig();

		MESSAGE_PLAYER_SELECTED = cf.getString("Mensagens.Player.selecionou").replace("&", "§");
		MESSAGE_PLAYER_PURCHASED = cf.getString("Mensagens.Player.comprou").replace("&", "§");
		MESSAGE_PLAYER_INSUFFICIENT_FUNDS = cf.getString("Mensagens.Player.saldo-insuficiente").replace("&", "§");

		TITLE_SELECTED = cf.getString("Title.Mensagens.Selecionou.titulo").replace("&", "§");
		SUBTITLE_SELECTED = cf.getString("Title.Mensagens.Selecionou.subtitulo").replace("&", "§");
		TITLE_PURCHASED = cf.getString("Title.Mensagens.Comprou.titulo").replace("&", "§");
		SUBTITLE_PURCHASED = cf.getString("Title.Mensagens.Comprou.subtitulo").replace("&", "§");

		ACTIONBAR_PURCHASED = cf.getString("Actionbar.Mensagens.comprou").replace("&", "§");
	}

}
