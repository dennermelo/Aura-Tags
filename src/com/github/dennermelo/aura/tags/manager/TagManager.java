package com.github.dennermelo.aura.tags.manager;

import java.util.HashMap;

import com.github.dennermelo.aura.tags.objects.Tag;

public class TagManager {

	private HashMap<String, Tag> tags;

	public TagManager() {
		this.tags = new HashMap<String, Tag>();
	}

	public void setTag(String player, Tag tag) {
		tags.put(player, tag);
	}

	public void removeTag(String player) {
		tags.remove(player);
	}

	public Tag getTag(String player) {
		return tags.get(player);
	}

	public boolean hasTag(String player) {
		return tags.containsKey(player);
	}

	public HashMap<String, Tag> getTags() {
		return tags;
	}

}
