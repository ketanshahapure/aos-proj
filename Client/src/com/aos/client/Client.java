package com.aos.client;

import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Which service do you want to access?");
		System.out.println("1. Add");
		Scanner sc = new Scanner(System.in);
		int serv = sc.nextInt();
		System.out.println("Enter values");
		int i = sc.nextInt();
		int j = sc.nextInt();
		if(serv == 1) {
			FullWSDLParser parser = new FullWSDLParser();
			String serviceWSDL = parser.callSDALB();
			System.out.println(serviceWSDL);
			String val = parser.callServices(serviceWSDL,i,j);
			System.out.println(val);
		}
	}

}
