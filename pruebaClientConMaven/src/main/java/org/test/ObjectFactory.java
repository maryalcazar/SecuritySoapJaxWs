
package org.test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.test package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetValorResponse_QNAME = new QName("http://test.org/", "getValorResponse");
    private final static QName _GetValor_QNAME = new QName("http://test.org/", "getValor");
    private final static QName _GetValorResponseReturn_QNAME = new QName("", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.test
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetValor }
     * 
     */
    public GetValor createGetValor() {
        return new GetValor();
    }

    /**
     * Create an instance of {@link GetValorResponse }
     * 
     */
    public GetValorResponse createGetValorResponse() {
        return new GetValorResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetValorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://test.org/", name = "getValorResponse")
    public JAXBElement<GetValorResponse> createGetValorResponse(GetValorResponse value) {
        return new JAXBElement<GetValorResponse>(_GetValorResponse_QNAME, GetValorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetValor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://test.org/", name = "getValor")
    public JAXBElement<GetValor> createGetValor(GetValor value) {
        return new JAXBElement<GetValor>(_GetValor_QNAME, GetValor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetValorResponse.class)
    public JAXBElement<byte[]> createGetValorResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetValorResponseReturn_QNAME, byte[].class, GetValorResponse.class, ((byte[]) value));
    }

}
