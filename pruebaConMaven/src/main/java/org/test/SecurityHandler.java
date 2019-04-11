package org.test;

import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.crypto.dsig.SignatureMethod;
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
import org.apache.ws.security.components.crypto.Crypto;
import org.apache.ws.security.components.crypto.CryptoFactory;
import org.apache.ws.security.message.WSSecEncrypt;
import org.apache.ws.security.message.WSSecEncryptedKey;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecSignature;
import org.w3c.dom.Document;

public class SecurityHandler implements SOAPHandler<SOAPMessageContext>{

	public boolean handleMessage(SOAPMessageContext messageContext) {
		try {
            /**
             * Getting document from soap context
             */
            SOAPMessage message = messageContext.getMessage();
            Document doc = toDocument(message);
            /**
             * Creating crypto Object.
             */
            Crypto crypto = CryptoFactory.getInstance("crypto.properties");
            /**
             * Using WSS4J API to create encryptor key
             */
            WSSecEncryptedKey encrKey = new WSSecEncryptedKey();
            encrKey.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);
            encrKey.setUserInfo("serverkey", "changeit");
            encrKey.prepare(doc, crypto);
            /**
             * Creating encrypter out of encrypt key
             */
            WSSecEncrypt builder = new WSSecEncrypt();
            builder.setUserInfo("serverkey", "changeit");
            builder.setEncKeyId(encrKey.getId());
            builder.setEphemeralKey(encrKey.getEphemeralKey());
            builder.setEncryptSymmKey(false);
            builder.setEncryptedKeyElement(encrKey.getEncryptedKeyElement());
            builder.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);
            builder.setUseKeyIdentifier(true);
            /**
             * Preparing to sign the document
             */
            WSSecSignature sign = new WSSecSignature();
            sign.setCustomTokenId(encrKey.getId());
            sign.setSignatureAlgorithm(SignatureMethod.RSA_SHA1);
            sign.setKeyIdentifierType(WSConstants.SKI_KEY_IDENTIFIER);
            sign.setUserInfo("serverkey", "changeit");
            /**
             * Creating Security Header to insert into document
             */
            WSSecHeader secHeader = new WSSecHeader();
            secHeader.insertSecurityHeader(doc);
            /**
             * Encrypting document with crypto and security header
             */
            Document signedDoc = sign.build(doc, crypto, secHeader);
            builder.build(signedDoc, crypto, secHeader);
            /**
             * Getting encrypted document
             */
            Document encryptedDoc = doc;
            /**
             * Updating message with encrypted document
             */
            DOMSource domSource = new DOMSource(encryptedDoc);
            message.getSOAPPart().setContent(domSource);
            String outputString = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(encryptedDoc);
//            System.out.println("Encrypted Document : " + outputString);
            /**
             * Logging encrypted soap into file
             */
            Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            if (outboundProperty) {
                logToSystemOut(message);
            }
            /**
             * Updating message before sending to service.
             */
            message.saveChanges();

        } catch (Exception ex) {
            Logger.getLogger(SecurityHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
	}

	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	public void close(MessageContext context) {
	}

	public Set<QName> getHeaders() {
		return Collections.emptySet();
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
}
