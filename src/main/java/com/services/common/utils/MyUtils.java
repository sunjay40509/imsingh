package com.services.common.utils;

public class MyUtils {

	
	public static Integer getIntegerFromString(String field) {
		if (field != null && !field.isEmpty()) {
			return Integer.parseInt(field);
		}
		return 0;
	}
	
	
	public static Integer getInteger(Integer integerValue) {
		return integerValue == null ? 0 : integerValue;
	}
	
	public static String getStringValue(Integer value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}
	
}
