package com.ikea.app.client.controller;

import javax.ws.rs.ProcessingException;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.client.window.ProductList;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Cesta;
public class ConfirmacionEliminarWindowController{
    public ConfirmacionEliminarWindowController() {
        
    }

    public int borrarCliente(WebTarget webTarget, Cliente cliente) {
        int i = 0;
        try {
            Response responseEncontrar = webTarget.path("cesta")
                .queryParam("email", cliente.getEmail())
				.request(MediaType.APPLICATION_JSON)
				.get();
            // check that the response was HTTP OK
            if (responseEncontrar.getStatusInfo().toEnum() == Status.OK) {
                Cesta cesta = responseEncontrar.readEntity(Cesta.class);
                WebTarget WebTargetCesta = webTarget.path("vaciarCesta");
                Invocation.Builder invocationBuilderCesta = WebTargetCesta.request(MediaType.APPLICATION_JSON);
                Response responseCesta = invocationBuilderCesta.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
                if (responseCesta.getStatus() != Status.OK.getStatusCode()) {
                    ClientMain.getLogger().error("Error connecting with the server. Code: {}", responseCesta.getStatus());
                } else {	
                    ClientMain.getLogger().info("Cesta borrada correctamente");
                }
                WebTarget WebTargetLogin = webTarget.path("borrarCliente");
                Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
                Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
                if (response.getStatus() != Status.OK.getStatusCode()) {
                    ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
                    i = 2;
                } else {	
                    ClientMain.getLogger().info("Cliente borrado correctamente");
                    i = 3;
                }
            } else {
				System.out.format("Error obtaining cesta. %s%n", responseEncontrar);
                i = 4;
			}
        } catch (ProcessingException e) {
            System.out.format("Error obtaining cesta. %s%n", e.getMessage());
            i = 5;
        }
        return i;       
    }
}