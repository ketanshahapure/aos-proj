package com.aos.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.axis2.AxisFault;

import com.aos.sdalb.service.PutWSDL2Stub;
import com.aos.sdalb.service.PutWSDLStub;

public class AddService implements ServletContextListener{

	public int add(int i, int j) {
		System.out.println("Add Service on server2 called");
		return i+j;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		PutWSDLStub stub = null;
		PutWSDL2Stub stub2 = null;
		try {
			stub = new PutWSDLStub();
			stub2 = new PutWSDL2Stub();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PutWSDLStub.RemoveWSDL params = new PutWSDLStub.RemoveWSDL();
		PutWSDL2Stub.RemoveWSDL params2 = new PutWSDL2Stub.RemoveWSDL();
		
		params.setServiceName("add2");
		params2.setServiceName("add2");
		
				
		try {
			stub.removeWSDL(params);
			stub2.removeWSDL(params2);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				stub2.removeWSDL(params2);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		System.out.println("WSDL for add service on server2 deleted from Service registry on stopping service or failure of service");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		PutWSDLStub stub = null;
		PutWSDL2Stub stub2 = null;
		try {
			stub = new PutWSDLStub();
			stub2 = new PutWSDL2Stub();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PutWSDLStub.StoreWSDL params = new PutWSDLStub.StoreWSDL();
		PutWSDL2Stub.StoreWSDL params2 = new PutWSDL2Stub.StoreWSDL();
		
		params.setServiceName("add2");
		params2.setServiceName("add2");
		
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			//Sending own WSDL
			String wsdl = "http://"+ip.getHostAddress()+":8081/ServerTwo/services/AddService?wsdl";
			params.setWSDL(wsdl);
			params2.setWSDL(wsdl);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			stub.storeWSDL(params);
			stub2.storeWSDL(params2);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				stub2.storeWSDL(params2);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		System.out.println("WSDL is sent from add service on server2 to service registry");
	}
}
