package com.skcc_pos.test;

import java.util.ArrayList;
import java.util.List;

import com.skcc_pos.common.Constants;
import com.skcc_pos.item.Item;

public class TestData {

	public static List<Item> getData() {
		List<Item> returnTempList = new ArrayList<Item>();
		Item tempItem = new Item(1001, 1, Constants.code.ITEM_TYPE_MATERIAL, "Ŀ�Ǻ�", "Ŀ�Ǻ�");
		returnTempList.add(tempItem);
		tempItem = new Item(1002, 1, Constants.code.ITEM_TYPE_MATERIAL, "����", "����");
		returnTempList.add(tempItem);
		tempItem = new Item(1003, 1, Constants.code.ITEM_TYPE_MATERIAL, "�а���", "�а���");
		returnTempList.add(tempItem);
		tempItem = new Item(1004, 1, Constants.code.ITEM_TYPE_MATERIAL, "���ڷ�", "����� ���� Ȱ���� �Ҿ� �־� �� ... ���ڷ� ... ��.. �޴�~~");
		returnTempList.add(tempItem);
		tempItem = new Item(2001, 1, Constants.code.ITEM_TYPE_DRINK, "�Ƹ޸�ī��", "�Ƹ޸�ī��");
		returnTempList.add(tempItem);
		tempItem = new Item(2002, 1, Constants.code.ITEM_TYPE_DRINK, "ī���", "�ε巯�� ������ ���յ� �޴��� ī���");
		returnTempList.add(tempItem);
		tempItem = new Item(3001, 1, Constants.code.ITEM_TYPE_BREAD, "��", "���� �ִ� �а���� ���� ����� ���� ���� ����?");
		returnTempList.add(tempItem);
		tempItem = new Item(3002, 1, Constants.code.ITEM_TYPE_DRINK, "���ڿ���", "���ڷ��� ������ Ȳ�� ����");
		returnTempList.add(tempItem);
		tempItem = new Item(3003, 1, Constants.code.ITEM_TYPE_BREAD, "��ī��", "Ŀ�Ǻ�� ���� ��ȭ~~ �ѹ� ��ź�����.");
		returnTempList.add(tempItem);
		tempItem = new Item(3004, 1, Constants.code.ITEM_TYPE_BREAD, "���ڸ���", "�׳� ���� ����... ���ڷ��� ��� ��� ����");
		returnTempList.add(tempItem);
		tempItem = new Item(8001, 1, Constants.code.ITEM_TYPE_COUPON, "Cafe4U ī���", "Cafe4U �����Ǹ� ������ ����� ȯ���� Ŀ�ǿ���~ ���մϴ�. ����");
		returnTempList.add(tempItem);
		
		return returnTempList;
	}
}
