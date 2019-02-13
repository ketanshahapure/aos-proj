package com.aos.client;

import java.util.Scanner;

import javax.xml.ws.soap.SOAPFaultException;

public class Client {

	public static void main(String[] args) throws Exception {
		
		while (true) {
			
			int se=0;
			System.out.println("Enter the name of the service you want to access:");
			System.out.println("add\nsub\nmul\ndiv\nmod");
			Scanner sc = new Scanner(System.in);
			String serv = sc.nextLine();
			if(serv.equals("exit"))
				break;
			if(!(serv.equals("add")||serv.equals("sub")||serv.equals("mul")||serv.equals("div")||serv.equals("mod"))) {
				System.out.println("No such service available");
				continue;
			}	
			System.out.println("Enter 2 values");
			int i = sc.nextInt();
			int j = sc.nextInt();
			
			FullWSDLParser parser = new FullWSDLParser();
			String serviceWSDL = null;
			long startTime = System.nanoTime();
			try {
				serviceWSDL = parser.callSDALB(serv);
			}catch(SOAPFaultException e){
				se=1;
			}
			if (null == serviceWSDL||serviceWSDL.equals("")||se == 1) {
				System.out.println("This service is not available right now\n");
				continue;
			}
			String val = parser.callServices(serviceWSDL, serv, i, j);
			long endTime = System.nanoTime();
			if(null == val) {
				System.out.println("This service is not available right now\n");
				continue;
			}
			long output = endTime-startTime;
			System.out.println("The result is: "+val);
			System.out.println("Time taken for this request in milliseconds:"+output/1000000);
		}
	}

}
