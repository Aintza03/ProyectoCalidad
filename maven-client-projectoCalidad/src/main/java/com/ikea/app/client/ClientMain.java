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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void registrarCliente(String email, String contrasena, String nombre) {
		WebTarget WebTargetRegistrarUsuario = webTarget.path("register");
		Invocation.Builder invocationBuilder = WebTargetRegistrarUsuario.request(MediaType.APPLICATION_JSON);
		
		Cliente cliente = new Cliente();
		cliente.setEmail(email);
		cliente.setContrasena(contrasena);
        cliente.setNombre(nombre);
		Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			logger.error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			logger.info("Cliente registrado correctamente");
		}
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
		clientMain.registrarCliente(EMAIL,CONTRASENA,NOMBRE);	
	}
}
