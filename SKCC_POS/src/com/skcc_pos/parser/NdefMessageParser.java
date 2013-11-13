package com.skcc_pos.parser;

import java.util.ArrayList;
import java.util.List;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import com.skcc_pos.item.Item;
import com.skcc_pos.parser.vo.ItemNfcRecord;

public class NdefMessageParser {

	public static List<Item> parse(NdefMessage message) {
		return getRecords(message.getRecords());
	}

	public static List<Item> getRecords(NdefRecord[] records) {
		List<Item> elements = new ArrayList<Item>();
		for (NdefRecord record : records) {
			elements.add(ItemNfcRecord.parse(record));
		}
		return elements;
	}

}
