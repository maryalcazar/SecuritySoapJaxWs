package org.test.client;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.WSSecurityEngine;
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecHeader;
import org.w3c.dom.Document;

public class ClientSecurityHandler implements SOAPHandler<SOAPMessageContext>, CallbackHandler{
	private String password = "changeit";
	
	public boolean handleMessage(SOAPMessageContext messageContext) {
		try {
            SOAPMessage msg = messageContext.getMessage();
            WSSecurityEngine secEngine = new WSSecurityEngine();
            Crypto crypto = CryptoFactory.getInstance("crypto.properties");
            Document doc = toDocument(msg);
            Boolean isOutGoing = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
//            WSSecHeader secHeader = new WSSecHeader();
//            secHeader.insertSecurityHeader(doc);
            if (!isOutGoing && secEngine.processSecurityHeader(doc, null, this, crypto) == null) {
                throw new RuntimeException("No se ha validado la cabecera de seguridad de la respuesta");
            }
            updateSOAPMessage(doc, msg);
        } catch (Exception e) {
            throw new RuntimeException("Error validando respuesta con WSS4J", e);
        }
		
		return true;
	}
	
	private static SOAPMessage updateSOAPMessage(Document doc, SOAPMessage message)
            throws SOAPException {

        DOMSource domSource = new DOMSource(doc);
        message.getSOAPPart().setContent(domSource);

        return message;

    }

	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	public void close(MessageContext context) {
	}

	public Set<QName> getHeaders() {
		Set<QName> headers = new HashSet<QName>();
        headers.add(new QName(WSConstants.WSSE_NS, "Security"));
        headers.add(new QName(WSConstants.WSSE11_NS, "Security"));
        headers.add(new QName(WSConstants.ENC_NS, "EncryptedData"));
        return headers;
	}
	
	public static org.w3c.dom.Document toDocument(SOAPMessage soapMsg)
            throws SOAPException, TransformerException {
        Source src = soapMsg.getSOAPPart().getContent();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMResult result = new DOMResult();
        transformer.transform(src, result);
        return (Document) result.getNode();
    }

    private void logToSystemOut(SOAPMessage message) throws SOAPException {
        try {
        	System.out.println("Outbound message:");
        	message.writeTo(System.out);
        } catch (Exception e) {
            System.out.println("Exception in handler: " + e);
        }
    }

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback cb : callbacks) {
            if (cb instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback) cb;
                try {
                } catch (Exception e) {
                    throw new UnsupportedCallbackException(pc, "Error recuperando propiedades de seguridad WSS4J");
                }
                if (pc.getIdentifier() != null) {
                    pc.setPassword(password);
                }
            }
        }
	}
}
