package com.github.dennermelo.aura.tags.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.dennermelo.aura.tags.core.TagCore;
import com.github.dennermelo.aura.tags.objects.Tag;
import com.github.dennermelo.aura.tags.utils.Scroller;
import com.github.dennermelo.aura.tags.utils.Scroller.PlayerRunnable;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagsInventory {

	public TagsInventory(Player player) {

		List<ItemStack> itens = new ArrayList<ItemStack>();
		PermissionUser user = PermissionsEx.getUser(player);
		for (Tag tag : TagCore.getTags()) {
			ItemStack item = new ItemStack(Material.NAME_TAG);
			ItemMeta item_meta = item.getItemMeta();
			item_meta.setDisplayName(TagCore.getConfig().getString("Item.Basico.nome").replace("&", "§")
					.replace("%tag_nome%", tag.getNome()));
			List<String> lore = new ArrayList<String>();
			for (String lore_config : TagCore.getConfig().getStringList("Item.Basico.lore")) {
				lore.add(lore_config.replace("&", "§").replace("%tag_formato%", tag.getFormato()).replace("%tag_valor%",
						tag.getValor() + " " + tag.getTipo()));
			}
			if (user.has(tag.getPermissao()) && TagCore.getTagManger().getTag(player.getName()) != tag) {
				for (String lore_selecionar : TagCore.getConfig().getStringList("Item.Selecionar.lore")) {
					lore.add(lore_selecionar.replace("&", "§"));
				}
			}
			if (!user.has(tag.getPermissao()) && TagCore.getTagManger().getTag(player.getName()) != tag) {
				for (String lore_selecionar : TagCore.getConfig().getStringList("Item.Comprar.lore")) {
					lore.add(lore_selecionar.replace("&", "§"));
				}
			}
			if (TagCore.getTagManger().getTag(player.getName()) == tag) {
				for (String lore_selecionar : TagCore.getConfig().getStringList("Item.Selecionado.lore")) {
					lore.add(lore_selecionar.replace("&", "§"));
				}
			}
			item_meta.setLore(lore);
			item.setItemMeta(item_meta);

			itens.add(item);

		}

		Scroller scroller = new Scroller.ScrollerBuilder().withName("§7Tags do Servidor").withItems(itens)
				.withArrowsSlots(18, 26).build();

		scroller.open(player);

	}

}
