package com.aos.service;

public class ReverseStringService {
	
	public String reverseString(String str) {
		StringBuilder rev = new StringBuilder();
		rev.append(str);
		rev.reverse();
		return rev.toString();
	}

}
