package com.aos.client;

import java.rmi.RemoteException;

import com.aos.service.AddServiceStub;
import com.aos.service.ReverseStringServiceStub;

public class Client {

	public static void main(String[] args) throws RemoteException {
		// TODO Auto-generated method stub
		AddServiceStub stub = new AddServiceStub();
		
		AddServiceStub.Add params = new AddServiceStub.Add();
		
		params.setI(40);
		params.setJ(55);
		
		AddServiceStub.AddResponse res = stub.add(params);
		
		int result = res.get_return();
		
		System.out.println("ADD RESULT IS: "+result);
		
		ReverseStringServiceStub stub2 = new ReverseStringServiceStub();
		
		ReverseStringServiceStub.ReverseString params2 = new ReverseStringServiceStub.ReverseString();
		
		params2.setStr("ABCD");
		
		ReverseStringServiceStub.ReverseStringResponse res2 = stub2.reverseString(params2);
		
		String revString = res2.get_return();
		
		System.out.println("Reverse String result is: "+revString);
		
	}

}
