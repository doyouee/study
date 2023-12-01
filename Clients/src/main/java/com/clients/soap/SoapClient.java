package com.clients.soap;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Iterator;

import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.util.logging.Logger;

public class SoapClient 
{
    public static void main( String[] args ) // 1
    {
    	String soapEndpointUrl = "http://localhost:8080/javainuse/ws/";
        String soapAction = "";

        callSoapWebService(soapEndpointUrl, soapAction);
    }
  
    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {  // 2
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();
            System.out.println();
            System.out.println("Response SOAP Data:");
            Iterator it = soapResponse.getSOAPBody().getChildElements();
            while (it.hasNext()) {
                SOAPBodyElement bodyElement = (SOAPBodyElement) it.next();
                Iterator it2 = bodyElement.getChildElements();
                while (it2.hasNext()) {
                    SOAPElement element2 = (SOAPElement) it2.next();
                    System.out.println("결과 :: " + element2.getTextContent());
                }
            }
            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\n에러 : : : Error occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }

    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {  // 3
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
//        createSoapEnvelope(soapMessage);
        createSoapEnvelopeService(soapMessage);
                
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
    
    private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {  // 4
        SOAPPart soapPart = soapMessage.getSOAPPart();
        String myNamespace = "wsdl";
        String myNamespaceURI = "http://javainuse.com";
        
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("inputSOATest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("test");
        soapBodyElem1.addTextNode("World");
    }
    
    	
    public static void createSoapEnvelopeService(SOAPMessage soapMessage) throws SOAPException, SAXException, IOException {
    	SOAPPart soapPart = soapMessage.getSOAPPart();
    	SOAPEnvelope envelope = soapPart.getEnvelope();
    	String sendMessage =
			"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsdl=\"http://javainuse.com\">" +
			"<SOAP-ENV:Header/>" +
			"<SOAP-ENV:Body>" +
			"<wsdl:inputSOATest>" +
			"<test>1</test>" +
			"</wsdl:inputSOATest>" +
			"</SOAP-ENV:Body>" +
			"</SOAP-ENV:Envelope>";
    	
    	byte[] buffer = sendMessage.getBytes();
    	ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
    	StreamSource source = new StreamSource(stream);
    	soapPart.setContent(source);
    }
}