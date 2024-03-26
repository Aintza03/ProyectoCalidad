package com.ikea.app.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.window.ClientRegistration;

import com.ikea.app.client.window.ClientLogin;
import com.ikea.app.client.window.ProductList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientMain{
    protected static final Logger logger = LogManager.getLogger();

	private String hola;
	private static final String EMAIL = "ABCD";
	private static final String CONTRASENA = "EFGH";
	private static final String NOMBRE = "IJKL";


	private Client client;
	private WebTarget webTarget;
	
    public ClientMain(String hostname, String port) {
		client = ClientBuilder.newClient();
		webTarget = client.target(String.format("http://%s:%s/rest/resource", hostname, port));
	}

	public static Logger getLogger(){
		return logger;
	}

    public static void main( String[] args )
    {
        if (args.length != 2) {
			logger.info("Use: java Client.Client [host] [port]");
			System.exit(0);
		}

		String hostname = args[0];
		String port = args[1];

		ClientMain clientMain = new ClientMain(hostname, port);
		ClientRegistration window = new ClientRegistration(clientMain.webTarget);
		ClientLogin window_login = new ClientLogin(clientMain.webTarget);
		ProductList window2 = new ProductList(clientMain.webTarget);
	}
}
