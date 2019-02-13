package com.aos.client;

import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.WSDLParser;
import com.predic8.xml.util.ResourceDownloadException;
 
public class FullWSDLParser {
	
	static int i = 0;
 
	public String callSDALB(String service) throws Exception {
		
		final String sdalbRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.sdalb.aos.com\">\r\n" + 
									"   <soapenv:Header/>\r\n" + 
									"   <soapenv:Body>\r\n" + 
									"      <ser:retrieveWSDL>\r\n" + 
									"         <ser:serviceName>"+service+"</ser:serviceName>\r\n" + 
									"      </ser:retrieveWSDL>\r\n" + 
									"   </soapenv:Body>\r\n" + 
									"</soapenv:Envelope>";
		
        WSDLParser parser = new WSDLParser();
 
        String wsdl = "";
        Definitions defs = new Definitions();
        
        String sdalb1 = "http://localhost:8084/SDALB/services/PutWSDL?wsdl";
        String sdalb2 = "http://localhost:8085/SDALB2/services/PutWSDL2?wsdl";
        int s1 = 0;
        int s2 = 0;
        
        try {
				parser.parse(sdalb1);
		} catch (ResourceDownloadException e) {
			// TODO: handle exception
			s1=1;
		}
        
        try {
			parser.parse(sdalb2);
		} catch (ResourceDownloadException e) {
			// TODO: handle exception
			s2=1;
		}
        
        /*if(s1==0) {
        	wsdl = sdalb1;
        }else if (s2==0) {
			wsdl = sdalb2;
		}else if(s1==1 && s2==1){
			return null;
		}else if(s1==1) {
        	wsdl = sdalb2;
        }else if (s2==1) {
			wsdl = sdalb1;
		}*/
        
        //0 - SDALB is up
        //1 - SDALB is down
        
        if(s1==0 && s2==0) {
        	//if both SDALB are working then load balance using Round Robin algorithm
        	i=i%2;
        	if(i==0)
        		wsdl = sdalb1;
        	else {
				wsdl = sdalb2;
			}
        	i++;
        }else if (s1==0 && s2==1) {
			wsdl = sdalb1;
		}else if (s1==1 && s2==0) {
			wsdl = sdalb2;
		}else if (s1==1 && s2==1) {
			return null;
		}
        
        defs = parser.parse(wsdl);
        
        String targetNameSpace = defs.getTargetNamespace();
        QName serviceName = new QName(targetNameSpace, defs.getServices().get(0).getName());
        QName portName = new QName(targetNameSpace, defs.getServices().get(0).getPorts().get(0).getName());
        String endpointUrl = wsdl.replace("?wsdl", "");
        //String SOAPAction = defs.getBindings().get(0).getOperations().get(0).getOperation().getSoapAction();
        String SOAPAction = "urn:retrieveWSDL";
        //System.out.println("in callSDALB before calling invoke");
        SOAPMessage response = invoke(serviceName, portName, endpointUrl, SOAPAction, sdalbRequest);
        //System.out.println("SOAPMessage response is:"+response);
        if(response == null) {
        	return null;
        }
        SOAPBody body = response.getSOAPBody();
        String val = body.getFirstChild().getFirstChild().getTextContent();
        return val;
	}
	
    public String callServices(String wsdl,String service, int firstParam, int secondParam) throws Exception {
    	
    	String request = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.aos.com\">\r\n" + 
					  	 "   <soap:Header/>\r\n" +
					  	 "   <soap:Body>\r\n" + 
					  	 "      <ser:"+service+">\r\n" + 
					  	 "         <ser:i>"+firstParam+"</ser:i>\r\n" + 
					  	 "         <ser:j>"+secondParam+"</ser:j>\r\n" + 
					  	 "      </ser:"+service+">\r\n" + 
					  	 "   </soap:Body>\r\n" + 
					  	 "</soap:Envelope>";
    	
        WSDLParser parser = new WSDLParser();
 
        Definitions defs = new Definitions();
        try {
        	defs = parser.parse(wsdl);
        }catch(ResourceDownloadException e) {
        	return null;
        }
        
        String targetNameSpace = defs.getTargetNamespace();
        //System.out.println(targetNameSpace);
        QName serviceName = new QName(targetNameSpace, defs.getServices().get(0).getName());
        QName portName = new QName(targetNameSpace, defs.getServices().get(0).getPorts().get(0).getName());
        String endpointUrl = wsdl.replace("?wsdl", "");
        String SOAPAction = defs.getBindings().get(0).getOperations().get(0).getOperation().getSoapAction();
        
        SOAPMessage response = invoke(serviceName, portName, endpointUrl, SOAPAction, request);
        //System.out.println(response);
        if(response == null) {
        	return null;
        }
        SOAPBody body = response.getSOAPBody();
        String val = body.getFirstChild().getFirstChild().getTextContent();
        return val;
    }
 
    public static SOAPMessage invoke(QName serviceName, QName portName, String endpointUrl, 
            String soapActionUri, String data) throws Exception {
    	
    	javax.xml.ws.Service service = javax.xml.ws.Service.create(serviceName);
    	
        service.addPort(portName, javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);

        Dispatch dispatch = service.createDispatch(portName, SOAPMessage.class, javax.xml.ws.Service.Mode.MESSAGE);

        dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, true);
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapActionUri);

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();

        StreamSource preppedMsgSrc = new StreamSource(new StringReader(data));
        soapPart.setContent(preppedMsgSrc);

        message.saveChanges();

        //System.out.println(message.getSOAPBody().getFirstChild().getTextContent());
        SOAPMessage response = (SOAPMessage) dispatch.invoke(message);
        //System.out.println("SOAPMessage response in invoke"+response);
        if(response == null)
        	return null;

        return response;
    }
}