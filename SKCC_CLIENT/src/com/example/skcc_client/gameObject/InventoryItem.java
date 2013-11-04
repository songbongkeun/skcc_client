package com.example.skcc_client.gameObject;

public class InventoryItem extends Item {
	
	private int quantity;
	
	/**
	 * Constructor : ProductionItem�� �̿��Ͽ� ����
	 * @param item		ProductionItem
	 * @param quantity	����
	 */
	public InventoryItem(ProductionItem item, int quantity) {
		
		super(item.getId(), item.getCompanyId(), item.getItemType(), item.getName(), item.getDescription());
		
		this.quantity = quantity;
	}
	
	/**
	 * Constructor : �Ķ���͸� �Է¹޾� ����
	 * @param id			������ id
	 * @param companyId		ȸ�� id
	 * @param itemType		������ Ÿ��
	 * @param name			�̸�
	 * @param description	����
	 * @param quantity		����
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