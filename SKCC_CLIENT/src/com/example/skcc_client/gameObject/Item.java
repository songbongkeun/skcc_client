package com.example.skcc_client.gameObject;

import java.util.Locale;

public class Item {
	
	private int id;
	private int companyId;
	private int itemType;
	private String name;
	private String description;
	
	public Item(Item item) {
		
		super();
		this.id = item.getId();
		this.companyId = item.getCompanyId();
		this.itemType = item.getItemType();
		this.name = item.getName();
		this.description = item.getDescription();
	}
	
	public Item(int id, int companyId, int itemType, String name, String description) {
		
		super();
		this.id = id;
		this.companyId = companyId;
		this.itemType = itemType;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public int getCompanyId() {
		return companyId;
	}

	public int getItemType() {
		return itemType;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public String getImageName() {
		return "item" + String.format(Locale.KOREA, "%04d", this.id);
	}
}
