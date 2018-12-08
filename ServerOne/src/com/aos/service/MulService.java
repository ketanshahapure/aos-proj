package com.aos.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.axis2.AxisFault;

import com.aos.sdalb.service.PutWSDLStub;

public class MulService implements ServletContextListener{

	public int mul(int i, int j) {
		return i*j;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		PutWSDLStub stub = null;
		try {
			stub = new PutWSDLStub();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PutWSDLStub.RemoveWSDL params = new PutWSDLStub.RemoveWSDL();
		
		params.setServiceName("mul1");
		
				
		try {
			stub.removeWSDL(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("WSDL for Multiplication service on server1 deleted from Service registry on stopping service or failure of service");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		PutWSDLStub stub = null;
		try {
			stub = new PutWSDLStub();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PutWSDLStub.StoreWSDL params = new PutWSDLStub.StoreWSDL();
		
		params.setServiceName("mul1");
		
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			//Sending own WSDL
			String wsdl = "http://"+ip.getHostAddress()+":8080/ServerOne/services/MulService?wsdl";
			params.setWSDL(wsdl);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stub.storeWSDL(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("WSDL is sent from mul service to service registry");
	
	}

	

}
