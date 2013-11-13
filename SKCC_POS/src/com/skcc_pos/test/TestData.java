package com.skcc_pos.test;

import java.util.ArrayList;
import java.util.List;

import com.skcc_pos.common.Constants;
import com.skcc_pos.item.Item;

public class TestData {

	public static List<Item> getData() {
		List<Item> returnTempList = new ArrayList<Item>();
		Item tempItem = new Item(1001, 1, Constants.code.ITEM_TYPE_MATERIAL, "커피빈", "커피빈");
		returnTempList.add(tempItem);
		tempItem = new Item(1002, 1, Constants.code.ITEM_TYPE_MATERIAL, "우유", "우유");
		returnTempList.add(tempItem);
		tempItem = new Item(1003, 1, Constants.code.ITEM_TYPE_MATERIAL, "밀가루", "밀가루");
		returnTempList.add(tempItem);
		tempItem = new Item(1004, 1, Constants.code.ITEM_TYPE_MATERIAL, "초코렛", "당산의 뇌에 활력을 불어 넣어 줄 ... 초코렛 ... 아.. 달다~~");
		returnTempList.add(tempItem);
		tempItem = new Item(2001, 1, Constants.code.ITEM_TYPE_DRINK, "아메리카노", "아메리카노");
		returnTempList.add(tempItem);
		tempItem = new Item(2002, 1, Constants.code.ITEM_TYPE_DRINK, "카페라떼", "부드러운 우유와 조합된 달달한 카페라떼");
		returnTempList.add(tempItem);
		tempItem = new Item(3001, 1, Constants.code.ITEM_TYPE_BREAD, "빵", "남아 있는 밀가루로 빵을 만들어 보는 것은 어떨까요?");
		returnTempList.add(tempItem);
		tempItem = new Item(3002, 1, Constants.code.ITEM_TYPE_DRINK, "초코우유", "초코렛과 우유의 황금 비율");
		returnTempList.add(tempItem);
		tempItem = new Item(3003, 1, Constants.code.ITEM_TYPE_BREAD, "모카번", "커피빈과 빵의 조화~~ 한번 드셔보세요.");
		returnTempList.add(tempItem);
		tempItem = new Item(3004, 1, Constants.code.ITEM_TYPE_BREAD, "초코머핀", "그냥 빵은 가라... 초코렛이 듬뿍 담긴 머핀");
		returnTempList.add(tempItem);
		tempItem = new Item(8001, 1, Constants.code.ITEM_TYPE_COUPON, "Cafe4U 카페라떼", "Cafe4U 레시피를 가지고 만드는 환상의 커피우유~ 반합니다. 강추");
		returnTempList.add(tempItem);
		
		return returnTempList;
	}
}
