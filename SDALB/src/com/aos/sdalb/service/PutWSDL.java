package com.aos.sdalb.service;

import java.util.HashMap;

public class PutWSDL {
	
	HashMap<String, String> map = new HashMap<String, String>();
	public static void main() {
		
	}
	
	public void storeWSDL(String serviceName,String WSDL) {
		map.put(serviceName, WSDL);
	}
	
	public String retrieveWSDL(String serviceName) {
		return map.get(serviceName);
	}
}
