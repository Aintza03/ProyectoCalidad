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
public class ClientLoginController{
	
    public ClientLoginController(){
    
    }

    public boolean loginCliente(WebTarget webTarget,String email, String contrasena) {
		WebTarget WebTargetLogin = webTarget.path("login");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);

		Cliente cliente = new Cliente();
		cliente.setEmail(email);
		cliente.setContrasena(contrasena);
        cliente.setNombre("");
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
