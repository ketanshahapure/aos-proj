package com.cal.service;

import java.rmi.RemoteException;

import com.ketan.service.MulServiceStub;

public class SubService {

	public int sub(int i, int j) throws RemoteException {
		CalService cal = new CalService();
		cal.printAdd("Test123");
		/*MulService mul = new MulService();
		mul.printMul();*/
		MulServiceStub stub = new MulServiceStub();
		MulServiceStub.PrintMul param = new MulServiceStub.PrintMul();
		param.setStr("Incoming communication from Axis2Cal Service to Axis2MulService");
		MulServiceStub.PrintMulResponse res = stub.printMul(param);
		String result = res.get_return();
		System.out.println(result);
		return i-j;
	}
	
	public void printSub() {
		System.out.println("printSub method");
	}
}
