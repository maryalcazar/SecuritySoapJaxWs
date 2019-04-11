package org.test.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class CustomCallbackHandler implements CallbackHandler{
	private String password;
	
	public CustomCallbackHandler(String password) {
		this.password = password;
	}
	
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback cb : callbacks) {
            if (cb instanceof WSPasswordCallback) {
                WSPasswordCallback pc = (WSPasswordCallback) cb;

                if (pc.getIdentifier() != null) {
                    pc.setPassword(this.password);
                }
            }
        }
    }
}
