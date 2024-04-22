package com.ikea.app.client;
import com.ikea.app.client.Pruebas;
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
import com.ikea.app.client.window.AdminIniciarSesionWindow;
import com.ikea.app.client.window.CestaWindow;
import com.ikea.app.client.window.ClientLogin;
import com.ikea.app.client.window.ProductList;
import com.ikea.app.client.window.ClientChangeEraseWindow;
import com.ikea.app.client.window.MenuPrincipalWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.ikea.app.pojo.Cesta;
import javax.ws.rs.ProcessingException;

public class ClientMain{
    protected static final Logger logger = LogManager.getLogger();
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
		Pruebas.guardarDatosEjemplo();
		ClientMain clientMain = new ClientMain(hostname, port);
		MenuPrincipalWindow window_menu = new MenuPrincipalWindow(clientMain.webTarget);
	}
}
