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

/**Controller de la ventana para hacer reclamaciones */
public class HacerReclamacionWindowController{
	
    /**Constructor Vacio */
    public HacerReclamacionWindowController(){
    
    }
    /**Funcion para hacer reclamaciones */
    public boolean hacerReclamacion(WebTarget webTarget, String reclamacion, Cliente cliente, Producto producto) {
        WebTarget WebTargetReclamacion = webTarget.path("hacerReclamacion");
        Invocation.Builder invocationBuilder = WebTargetReclamacion.request(MediaType.APPLICATION_JSON);
        Reclamacion reclamacionFinal = new Reclamacion();
        reclamacionFinal.setReclamacion(reclamacion);
        reclamacionFinal.setCliente(cliente);
        reclamacionFinal.setProducto(producto);
        
        Response response = invocationBuilder.post(Entity.entity(reclamacionFinal, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
        } else {	
            ClientMain.getLogger().info("Reclamacion realizada correctamente");
            return true;
        }
    }

    /**Funcion para recibir una lista de reclamaciones */
    public List<Reclamacion> sendReclamation(WebTarget webTarget, Admin admin) {
        try {
            Response response = webTarget.path("sendReclamation")
                .queryParam("admin", admin.getUsuario())
                .request(MediaType.APPLICATION_JSON)
                .get();

            if (response.getStatusInfo().toEnum() == Status.OK) {
                GenericType<List<Reclamacion>> listType = new GenericType<List<Reclamacion>>(){};
                List<Reclamacion> reclamaciones = response.readEntity(listType);
              
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

    /**Funcion para resolver una reclamacion */
    public boolean resolverReclamacion(WebTarget webTarget, Reclamacion reclamacion) {
        WebTarget WebTargetReclamacion = webTarget.path("resolverReclamacion");
        Invocation.Builder invocationBuilder = WebTargetReclamacion.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(reclamacion, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
        } else {	
            ClientMain.getLogger().info("Reclamacion eliminada correctamente");
            return true;
        }
    }
}