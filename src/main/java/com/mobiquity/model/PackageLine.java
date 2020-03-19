package com.mobiquity.model;

import java.util.List;

public class PackageLine {
	private float maxWeightAllowed;
	private List<Item> items;

	public PackageLine(float maxWeightAllowed, List<Item> items) {
		this.maxWeightAllowed = maxWeightAllowed;
		this.items = items;
	}

	public float getMaxWeightAllowed() {
		return maxWeightAllowed;
	}

	public void setMaxWeightAllowed(float maxWeightAllowed) {
		this.maxWeightAllowed = maxWeightAllowed;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
}
