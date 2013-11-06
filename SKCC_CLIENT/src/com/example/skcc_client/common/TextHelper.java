package com.example.skcc_client.common;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TextHelper {
	
	public static String remainTime(long timeMSec) {

		StringBuffer time = new StringBuffer();
		
		int units = 2; // Show just 2 units
		// days
		if(units > 0 && timeMSec > 24 * 60 * 60 * 1000) {
			
			SimpleDateFormat dd = new SimpleDateFormat("d", Locale.KOREA);
			time.append(dd.format(timeMSec));
			time.append("d ");
			--units;
		}
		// hours
		if(units > 0 && timeMSec > 60 * 60 * 1000) {
			
			SimpleDateFormat hh = new SimpleDateFormat("h", Locale.KOREA);
			time.append(hh.format(timeMSec));
			time.append("h ");
			--units;
		}
		// minutes
		if(units > 0 && timeMSec > 60 * 1000) {
			
			SimpleDateFormat mm = new SimpleDateFormat("m", Locale.KOREA);
			time.append(mm.format(timeMSec));
			time.append("m ");
			--units;
		}
		// seconds
		if(units > 0) {
			
			SimpleDateFormat ss = new SimpleDateFormat("s", Locale.KOREA);
			time.append(ss.format(timeMSec));
			time.append("s ");
		}
		
		return time.toString();
	}
}
