
package org.test;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.HandlerChain;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@HandlerChain(file = "client-handlers.xml")
@WebServiceClient(name = "EnvioDocService", targetNamespace = "http://test.org/", wsdlLocation = "http://localhost:8080/ws/server/EnvioDoc?wsdl")
public class EnvioDocService
    extends Service
{

    private final static URL ENVIODOCSERVICE_WSDL_LOCATION;
    private final static WebServiceException ENVIODOCSERVICE_EXCEPTION;
    private final static QName ENVIODOCSERVICE_QNAME = new QName("http://test.org/", "EnvioDocService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/ws/server/EnvioDoc?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ENVIODOCSERVICE_WSDL_LOCATION = url;
        ENVIODOCSERVICE_EXCEPTION = e;
    }

    public EnvioDocService() {
        super(__getWsdlLocation(), ENVIODOCSERVICE_QNAME);
    }

    public EnvioDocService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ENVIODOCSERVICE_QNAME, features);
    }

    public EnvioDocService(URL wsdlLocation) {
        super(wsdlLocation, ENVIODOCSERVICE_QNAME);
    }

    public EnvioDocService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ENVIODOCSERVICE_QNAME, features);
    }

    public EnvioDocService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EnvioDocService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns EnvioDoc
     */
    @WebEndpoint(name = "EnvioDocPort")
    public EnvioDoc getEnvioDocPort() {
        return super.getPort(new QName("http://test.org/", "EnvioDocPort"), EnvioDoc.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EnvioDoc
     */
    @WebEndpoint(name = "EnvioDocPort")
    public EnvioDoc getEnvioDocPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://test.org/", "EnvioDocPort"), EnvioDoc.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ENVIODOCSERVICE_EXCEPTION!= null) {
            throw ENVIODOCSERVICE_EXCEPTION;
        }
        return ENVIODOCSERVICE_WSDL_LOCATION;
    }

}