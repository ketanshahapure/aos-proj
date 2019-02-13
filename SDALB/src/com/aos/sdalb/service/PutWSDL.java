package com.aos.sdalb.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PutWSDL implements ServletContextListener{
	
	static HashMap<String, String> map = new HashMap<String, String>();
	static int addPointer, subPointer, mulPointer, divPointer, modPointer = 0;
	
	public void storeWSDL(String serviceName,String WSDL) {
		map.put(serviceName, WSDL);
		/*//For counting the number of services of each type
		if(serviceName.contains("add")) {
			addServiceCounter++;
		}else if (serviceName.contains("sub")) {
			subServiceCounter++;
		}else if (serviceName.contains("mul")) {
			mulServiceCounter++;
		}else if (serviceName.contains("div")) {
			divServiceCounter++;
		}else if (serviceName.contains("mod")) {
			modServiceCounter++;
		}*/
	}
	
	public String retrieveWSDL(String serviceName) {
		int addServiceCounter, subServiceCounter, mulServiceCounter, divServiceCounter, modServiceCounter;
		addServiceCounter = subServiceCounter = mulServiceCounter = divServiceCounter = modServiceCounter=3;
		
		System.out.println("Request routed through SDALB1");
		String loadBalancedServiceName = null;
		if(serviceName.contains("add")) {
			for(int i=0;i<=addServiceCounter;i++) {
				addPointer = addPointer%addServiceCounter;
				loadBalancedServiceName = "add"+(addPointer+1);
				String loadBalancedWSDL = map.get(loadBalancedServiceName);
				addPointer=addPointer+1;
				if(loadBalancedWSDL!=null)
					return loadBalancedWSDL;
			}
			return null;
		}else if(serviceName.contains("sub")) {
			for(int i=0;i<=subServiceCounter;i++) {
				subPointer = subPointer%subServiceCounter;
				loadBalancedServiceName = "sub"+(subPointer+1);
				String loadBalancedWSDL = map.get(loadBalancedServiceName);
				subPointer=subPointer+1;
				if(loadBalancedWSDL!=null)
					return loadBalancedWSDL;
			}
			return null;
		}else if(serviceName.contains("mul")) {
			for(int i=0;i<=mulServiceCounter;i++) {
				mulPointer = mulPointer%mulServiceCounter;
				loadBalancedServiceName = "mul"+(mulPointer+1);
				String loadBalancedWSDL = map.get(loadBalancedServiceName);
				mulPointer=mulPointer+1;
				if(loadBalancedWSDL!=null)
					return loadBalancedWSDL;
			}
			return null;
		}else if(serviceName.contains("div")) {
			for(int i=0;i<=divServiceCounter;i++) {
				divPointer = divPointer%divServiceCounter;
				loadBalancedServiceName = "div"+(divPointer+1);
				String loadBalancedWSDL = map.get(loadBalancedServiceName);
				divPointer=divPointer+1;
				if(loadBalancedWSDL!=null)
					return loadBalancedWSDL;
			}
			return null;
		}else if(serviceName.contains("mod")) {
			for(int i=0;i<=modServiceCounter;i++) {
				modPointer = modPointer%modServiceCounter;
				loadBalancedServiceName = "mod"+(modPointer+1);
				String loadBalancedWSDL = map.get(loadBalancedServiceName);
				modPointer=modPointer+1;
				if(loadBalancedWSDL!=null)
					return loadBalancedWSDL;
			}
			return null;
		}
		return null;
	}
	
	public void removeWSDL(String serviceName) {
		map.remove(serviceName);
	}
	
	public String printMap(String sName) {
		String sname = sName;
		for (HashMap.Entry<String,String> entry : map.entrySet())  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
		return map.toString();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("Called on server shutdown");
		Properties properties = new Properties();
		properties.putAll(map);
		try {
			properties.store(new FileOutputStream("C:\\Users\\ketan\\eclipse-workspace\\SDALB\\service_registry_backup.properties"), null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found. Please check the location of the file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IO Exception");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("Called on server startup");
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("C:\\Users\\ketan\\eclipse-workspace\\SDALB\\service_registry_backup.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found. Please check the location of the file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String key : properties.stringPropertyNames()) {
			map.put(key, properties.get(key).toString());
			/*if(key.contains("add")) {
				addServiceCounter++;
			}else if (key.contains("sub")) {
				subServiceCounter++;
			}else if (key.contains("mul")) {
				mulServiceCounter++;
			}else if (key.contains("div")) {
				divServiceCounter++;
			}else if (key.contains("mod")) {
				modServiceCounter++;
			}*/
		}
	}
}
