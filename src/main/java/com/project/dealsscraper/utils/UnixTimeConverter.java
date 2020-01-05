package com.project.dealsscraper.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UnixTimeConverter {
	public static String toUnixTime(String date) {
		Long unixTime = null;
		try {
			unixTime = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.US)
					.parse(date.replaceAll("(?<=\\d)(st|nd|rd|th)", "")).getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return unixTime.toString();
	}
}
