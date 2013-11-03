package com.example.skcc_client.gameObject;

public class InventoryItem extends Item {
	
	private int quantity;

	public InventoryItem(int id, int companyId, int itemType, String name, String description, int quantity) {
		
		super(id, companyId, itemType, name, description);
		
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQunatity(int qunatity) {
		this.quantity = qunatity;
	}
	
	public void addQuantity(int qunatity) {
		
		this.quantity += qunatity;
	}
	
	public void removeQuantity(int qunatity) {
		
		this.quantity -= qunatity;
	}
}