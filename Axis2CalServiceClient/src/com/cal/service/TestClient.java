package com.cal.service;

public class TestClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		CalServiceStub stub = new CalServiceStub();
		
		CalServiceStub.Add params =
	new CalServiceStub.Add();
		
		params.setI(40);
		params.setJ(55);
		
		CalServiceStub.AddResponse res = stub.add(params);
		
		int result = res.get_return();
		
		SubServiceStub stub2 = new SubServiceStub();
		
		SubServiceStub.Sub params2 = new SubServiceStub.Sub();
		
		params2.setI(40);
		params2.setJ(20);
		
		SubServiceStub.SubResponse res2 = stub2.sub(params2);
		
		int result2 = res2.get_return();
		
		System.out.println("ADD RESULT IS: "+result+"\nSUB RESULT IS: "+result2);
	}

}
