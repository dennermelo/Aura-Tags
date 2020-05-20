package com.github.dennermelo.aura.tags.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.dennermelo.aura.tags.Main;
import com.github.dennermelo.aura.tags.objects.Tag;
import com.github.dennermelo.aura.tags.utils.Scroller;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TagsInventory {

	public TagsInventory(Player player) {

		List<ItemStack> itens = new ArrayList<ItemStack>();
		for (Tag tag : Main.getTagManager().getTagList()) {

			ItemStack item = new ItemStack(Material.NAME_TAG);
			ItemMeta item_meta = item.getItemMeta();
			item_meta.setDisplayName(Main.getPreferencesManager().BASIC_ITEM_NAME.replace("%tag_nome%", tag.getNome())
					.replace("%tag_formato%", tag.getFormato()));
			List<String> lore = new ArrayList<String>();
			for (String linhas : Main.getPreferencesManager().BASIC_ITEM_LORE) {
				lore.add(linhas.replace("&", "§").replace("%tag_formato%", tag.getFormato())
						.replace("%tag_nome%", tag.getNome())
						.replace("%tag_valor%", tag.getValor() + " " + tag.getTipo()));
			}

			if (PermissionsEx.getUser(player).has(tag.getPermissao()) && Main.getTagManager().getTag(player) != tag) {
				for (String linhas : Main.getPreferencesManager().ADDITIONAL_LORE_SELECT) {
					lore.add(linhas.replace("&", "§"));
				}
			}
			if (PermissionsEx.getUser(player).has(tag.getPermissao()) && Main.getTagManager().getTag(player) == tag) {
				for (String linhas : Main.getPreferencesManager().ADDITIONAL_LORE_SELECTED) {
					lore.add(linhas.replace("&", "§"));
				}
			}
			if (!PermissionsEx.getUser(player).has(tag.getPermissao()) && Main.getTagManager().getTag(player) != tag) {
				for (String linhas : Main.getPreferencesManager().ADDITIONAL_LORE_BUY) {
					lore.add(linhas.replace("&", "§"));
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
