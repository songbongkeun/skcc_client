package com.example.skcc_client.common;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextHelper {
	
	static final long DAY = 24 * 60 * 60 * 1000;
	static final long HOUR = 60 * 60 * 1000;
	static final long MINUTE = 60 * 1000;
	static final long SECOND = 1000;
	
	@SuppressLint("SimpleDateFormat")
	public static String getFormattedDate(Date date) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy³â M¿ù ddÀÏ");
		
		return format.format(date);
	}
	
	public static String remainTime(long timeMSec) {

		StringBuffer time = new StringBuffer();
		long present = 0;
		long remain = timeMSec;
		
		int units = 2; // Show just 2 units
		
		// days
		if(units > 0 && timeMSec >= DAY) {
			
			present = remain / DAY;
			remain = remain - (present * DAY);
			
			time.append(present);
			time.append("d ");
			
			--units;
		}
		// hours
		if(units > 0 && timeMSec >= HOUR) {
			
			present = remain / HOUR;
			remain = remain - (present * HOUR);
			
			if(present > 0) {
				
				time.append(present);
				time.append("h ");
			}
			--units;
		}
		// minutes
		if(units > 0 && timeMSec >= MINUTE) {
			
			present = remain / MINUTE;
			remain = remain - (present * MINUTE);
			
			if(present > 0) {
				
				time.append(present);
				time.append("m ");
			}
			--units;
		}
		// seconds
		if(units > 0) {
			
			present = remain / SECOND;
			remain = remain - (present * SECOND);
			
			if(present > 0) {
				
				time.append(present);
				time.append("s ");
			}
		}
		
		return time.toString();
	}
}
