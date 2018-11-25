package com.ketan.service;

public class MulService {
	
	public int mul(int i, int j) {
		return i*j;
	}
	
	public String printMul(String str) {
		System.out.println("Multiplication Service");
		System.out.println("\n"+str);
		return "Incoming communication from multiplication service";
	}
}
