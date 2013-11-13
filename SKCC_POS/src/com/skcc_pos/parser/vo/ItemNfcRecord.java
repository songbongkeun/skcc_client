package com.skcc_pos.parser.vo;

import java.io.UnsupportedEncodingException;

import com.skcc_pos.item.Item;
import com.skcc_pos.parser.util.JsonUtil;

import android.nfc.NdefRecord;

public class ItemNfcRecord {

	public static Item parse(NdefRecord record) {
		try {
			byte[] payload = record.getPayload();
			Item item = JsonUtil.toObject(payload.toString(), Item.class);
			String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
			int languageCodeLength = payload[0] & 0077;
			String text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
			return null;
		} catch (UnsupportedEncodingException e) {
			// should never happen unless we get a malformed tag.
			throw new IllegalArgumentException(e);
		}
	}
	
	public static void main(String[] args) {
		Item item = new Item(0, 1, 2, "1", "2");
		System.out.println("targetItem : " + item);
		String transString = JsonUtil.toString(item);
		byte[] byteValue = transString.getBytes();
		transString = byteValue.toString();
		Item transItem = JsonUtil.toObject(transString, Item.class);
		System.out.println("transItem : " + transItem);
	}
}
