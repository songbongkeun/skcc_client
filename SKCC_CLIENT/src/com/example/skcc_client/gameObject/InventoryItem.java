package com.example.skcc_client.gameObject;

public class InventoryItem extends Item {
	
	private int quantity;
	
	/**
	 * Constructor : ProductionItem을 이용하여 생성
	 * @param item		ProductionItem
	 * @param quantity	수량
	 */
	public InventoryItem(ProductionItem item, int quantity) {
		
		super(item.getId(), item.getCompanyId(), item.getItemType(), item.getName(), item.getDescription());
		
		this.quantity = quantity;
	}
	
	/**
	 * Constructor : 파라미터를 입력받아 생성
	 * @param id			아이템 id
	 * @param companyId		회사 id
	 * @param itemType		아이템 타입
	 * @param name			이름
	 * @param description	설명
	 * @param quantity		수량
	 */
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