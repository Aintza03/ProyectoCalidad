package com.ikea.app.client.controller;

import java.util.*;
import java.util.List;
 
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Admin;

public class HacerReclamacionWindowController{
	
    public HacerReclamacionWindowController(){
    
    }

    public void hacerReclamacion(WebTarget webTarget, String reclamacion, Cliente cliente, Producto producto) {
        WebTarget WebTargetReclamacion = webTarget.path("hacerReclamacion");
        Invocation.Builder invocationBuilder = WebTargetReclamacion.request(MediaType.APPLICATION_JSON);
        Reclamacion reclamacionFinal = new Reclamacion();
        reclamacionFinal.setReclamacion(reclamacion);
        reclamacionFinal.setCliente(cliente);
        reclamacionFinal.setProducto(producto);
        
        Response response = invocationBuilder.post(Entity.entity(reclamacionFinal, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
        } else {	
            ClientMain.getLogger().info("Reclamacion realizada correctamente");
        }
    }

    public List<Reclamacion> sendReclamation(WebTarget webTarget, Admin admin) {
        // issuing a GET request to the users endpoint with some query parameters
        try {
            Response response = webTarget.path("sendReclamation")
                .queryParam("admin", admin.getUsuario())
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                // the response is a generic type (a List<User>)
                GenericType<List<Reclamacion>> listType = new GenericType<List<Reclamacion>>(){};
                List<Reclamacion> reclamaciones = response.readEntity(listType);
                //System.out.println(product);
				return reclamaciones;
            } else {
				System.out.format("Error obtaining product list. %s%n", response);
				return new ArrayList<Reclamacion>();
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining product list. %s%n", e.getMessage());
			return new ArrayList<Reclamacion>();
        }
    }

    public void resolverReclamacion(WebTarget webTarget, Reclamacion reclamacion) {
        WebTarget WebTargetReclamacion = webTarget.path("resolverReclamacion");
        Invocation.Builder invocationBuilder = WebTargetReclamacion.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(reclamacion, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
        } else {	
            ClientMain.getLogger().info("Reclamacion eliminada correctamente");
        }
    }
}