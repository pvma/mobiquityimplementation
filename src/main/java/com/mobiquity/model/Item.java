package com.mobiquity.model;

public class Item {
	private int index;
	private float weight;
	private float price;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return getIndex() + "|" + getWeight() + "|" + getPrice();
	}
}
