package com.mobiquity.model;

import java.util.List;

public class PackageLine {
	private float maxWeightAllowed;
	private List<Item> packages;

	public float getMaxWeightAllowed() {
		return maxWeightAllowed;
	}

	public void setMaxWeightAllowed(float maxWeightAllowed) {
		this.maxWeightAllowed = maxWeightAllowed;
	}

	public List<Item> getPackages() {
		return packages;
	}

	public void setPackages(List<Item> packages) {
		this.packages = packages;
	}
}
