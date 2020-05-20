package com.github.dennermelo.aura.tags.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.dennermelo.aura.tags.Main;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;

public class PlayerChat implements Listener {

	@EventHandler
	public void event(ChatMessageEvent e) {
		if (Main.getTagManager().hasTag(e.getSender())) {
			e.setTagValue("aura_tag", Main.getTagManager().getTag(e.getSender()).getFormato());
		}
	}

}
