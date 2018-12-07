package com.aos.sdalb.service;

import java.util.HashMap;

public class PutWSDL {
	
	HashMap<String, String> map = new HashMap<String, String>();
	
	public void storeWSDL(String serviceName,String WSDL) {
		map.put(serviceName, WSDL);
	}
	
	public String retrieveWSDL(String serviceName) {
		return map.get(serviceName);
	}
	
	public void removeWSDL(String serviceName) {
		map.remove(serviceName);
	}
	
	public void printMap() {
		for (HashMap.Entry<String,String> entry : map.entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
    	}
}
