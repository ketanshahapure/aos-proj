package com.aos.client;

import java.io.BufferedOutputStream;
import java.io.StringReader;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;

import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.WSDLParser;
 
public class FullWSDLParser {
	
	static String request = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.aos.com\">\r\n" + 
							"   <soap:Header/>\r\n" +
							"   <soap:Body>\r\n" + 
							"      <ser:add>\r\n" + 
							"         <ser:i>142</ser:i>\r\n" + 
							"         <ser:j>5</ser:j>\r\n" + 
							"      </ser:add>\r\n" + 
							"   </soap:Body>\r\n" + 
							"</soap:Envelope>";

 
    public static void main(String[] args) throws Exception {
        WSDLParser parser = new WSDLParser();
 
        Definitions defs = parser.parse("http://localhost:8080/ServerOne/services/AddService?wsdl");
        String wsdl = "http://localhost:8080/ServerOne/services/AddService?wsdl";
        
        String targetNameSpace = defs.getTargetNamespace();
        System.out.println(targetNameSpace);
        QName serviceName = new QName(targetNameSpace, defs.getServices().get(0).getName());
        QName portName = new QName(targetNameSpace, defs.getServices().get(0).getPorts().get(0).getName());
        String endpointUrl = wsdl.replace("?wsdl", "");
        String SOAPAction = defs.getBindings().get(0).getOperations().get(0).getOperation().getSoapAction();
        
        SOAPMessage response = invoke(serviceName, portName, endpointUrl, SOAPAction, request);
        SOAPBody body = response.getSOAPBody();
        if (body.hasFault()) {
            System.out.println(body.getFault());
        } else {
            BufferedOutputStream out = new BufferedOutputStream(System.out);
            response.writeTo(out);
            out.flush();
            System.out.println();
        }
    }
 
    public static SOAPMessage invoke(QName serviceName, QName portName, String endpointUrl, 
            String soapActionUri, String data) throws Exception {
    	
    	javax.xml.ws.Service service = javax.xml.ws.Service.create(serviceName);
    	
        service.addPort(portName, javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING, endpointUrl);

        Dispatch dispatch = service.createDispatch(portName, SOAPMessage.class, javax.xml.ws.Service.Mode.MESSAGE);

        // The soapActionUri is set here. otherwise we get a error on .net based services.
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_USE_PROPERTY, true);
        dispatch.getRequestContext().put(Dispatch.SOAPACTION_URI_PROPERTY, soapActionUri);

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();

        SOAPPart soapPart = message.getSOAPPart();
        SOAPEnvelope envelope = soapPart.getEnvelope();

        StreamSource preppedMsgSrc = new StreamSource(new StringReader(data));
        soapPart.setContent(preppedMsgSrc);

        message.saveChanges();

        System.out.println(message.getSOAPBody().getFirstChild().getTextContent());
        SOAPMessage response = (SOAPMessage) dispatch.invoke(message);

        return response;
    }
}