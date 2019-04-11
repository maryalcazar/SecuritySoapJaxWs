package org.test.client;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;

import com.atlante.bmb.post.web_services.test.EnvioDoc;
import com.atlante.bmb.post.web_services.test.EnvioDocService;
import com.qoppa.pdf.SigningInformation;
import com.qoppa.pdf.form.SignatureField;
import com.qoppa.pdfSecure.PDFSecure;

public class TestWS {
	public static void main(String[] args) throws Exception {
		EnvioDocService ws = new EnvioDocService(new URL("http://localhost:8080/pruebaConMaven/EnvioDoc"));
		EnvioDoc wsPort = ws.getEnvioDocPort();
//        BindingProvider bp = (BindingProvider) wsPort;
//        List<Handler> handlers = new ArrayList<Handler>();
//        handlers.add(new ClientSecurityHandler());
//        bp.getBinding().setHandlerChain(handlers);
        
		byte[] valor = wsPort.getValor();
		System.out.println(new String(valor));
		FileOutputStream fos = new FileOutputStream(new File("C:\\cliente\\" +"archivo.pdf"));
		fos.write(valor);
		fos.close();

 
	}
}
