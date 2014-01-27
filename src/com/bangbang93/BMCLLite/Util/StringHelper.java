package com.bangbang93.BMCLLite.Util;

public class StringHelper {
	public static String replace(String str, String oldValue, String newValue){
		return replace(new StringBuilder(str), oldValue, newValue).toString();
	}
	
	public static StringBuilder replace(StringBuilder str, String oldValue, String newValue){
		int index = str.indexOf(oldValue);
		if (index == -1){
			return str;
		} else {
			str.replace(index, index + oldValue.length(), newValue);
			return str;
		}
	}
}
