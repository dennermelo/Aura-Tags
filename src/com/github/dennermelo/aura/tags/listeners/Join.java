package com.github.dennermelo.aura.tags.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.objects.Tag;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Join implements Listener {

	@EventHandler
	public void event(PlayerJoinEvent e) {
		for (Tag tag : Main.getTagManager().getTagList()) {
			if (PermissionsEx.getUser(e.getPlayer()).has(tag.getPermissao()) && !Main.getTagManager().hasTag(e.getPlayer())) {
				Main.getTagManager().defineTag(e.getPlayer(), tag);
				return;
			}
		}

	}

}
