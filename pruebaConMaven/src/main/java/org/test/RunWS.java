package org.test;

import javax.xml.ws.Endpoint;

public class RunWS {
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8081/ws/server", new EnvioDoc());
		System.out.println("Webservice arrancao");
	}
}
