package com.ikea.app.client.controller;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.ClientMain;

/**Controller de la ventana ClientRegistration. */
public class ClientRegistrationController{
    /**Contructor Vacio. */
	public ClientRegistrationController(){
    
    }
	/**Funcion que manda al servidor la orden de registrar un nuevo cliente. */
    public boolean registrarCliente(WebTarget webTarget,String email, String contrasena, String nombre) {
		WebTarget WebTargetRegistrarUsuario = webTarget.path("register");
		Invocation.Builder invocationBuilder = WebTargetRegistrarUsuario.request(MediaType.APPLICATION_JSON);
		
		Cliente cliente = new Cliente();
		cliente.setEmail(email);
		cliente.setContrasena(contrasena);
        cliente.setNombre(nombre);
		Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
			return false;
		} else {
			ClientMain.getLogger().info("Cliente registrado correctamente");
			return true;
		}
	}
}
