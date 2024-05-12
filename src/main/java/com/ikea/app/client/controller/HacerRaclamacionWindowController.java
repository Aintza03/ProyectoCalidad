package com.ikea.app.client.controller;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.ClientMain;

public class HacerRaclamacionWindowController{
	
    public HacerRaclamacionWindowController(){
    
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
}