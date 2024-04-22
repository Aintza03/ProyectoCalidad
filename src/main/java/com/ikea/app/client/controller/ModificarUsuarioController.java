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

public class ModificarUsuarioController{


	public ModificarUsuarioController() {
        
    }

    public Cliente modificarUsuario(WebTarget webTarget, String gmail, String password, String name) {
        WebTarget WebTargetLogin = webTarget.path("modifyClient");
        Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
        Cliente cliente = new Cliente();
        cliente.setEmail(gmail);
        cliente.setContrasena(password);
        cliente.setNombre(name);
        Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
        } else {	
            ClientMain.getLogger().info("Cliente modificada correctamente");
        }
        return cliente;
    }
}
