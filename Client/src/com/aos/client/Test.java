package com.aos.client;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "http://localhost:8080/ServerOne/services/AddService?wsdl";
		String nstr = str.replace("?wsdl", "");
		System.out.println(nstr);
	}

}
