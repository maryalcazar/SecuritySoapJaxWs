package org.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Base64;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@HandlerChain(file = "handlers.xml")
public class EnvioDoc {
	@WebMethod
	public byte[] getValor() {
		byte[] res = new byte[]{};
		
		try {
			String directorio = "C:\\servicio\\";
			File f = new File(directorio);
			String[] archivos = f.list();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < archivos.length; i++) {
				FileReader fr=new FileReader(directorio+archivos[i]);
				BufferedReader bf=new BufferedReader(fr);
				
				String linea;
				while ((linea = bf.readLine())!=null) {
					sb.append(linea + System.getProperty("line.separator"));
				}
				  
				fr.close();
				bf.close();
			}
			
		   res = sb.toString().getBytes();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
