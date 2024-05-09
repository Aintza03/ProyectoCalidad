package com.ikea.app.client.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.client.window.ProductList;
import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.pojo.Historial;
import javax.ws.rs.ProcessingException;
public class CestaWindowController{
    
   public CestaWindowController(){
    
    }          

    public boolean vaciarCesta(WebTarget webTarget, Cesta cesta) {
		WebTarget WebTargetLogin = webTarget.path("vaciarCesta");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
		} else {	
			ClientMain.getLogger().info("Cesta vaciada correctamente");
            return true;
		}
	}

    public boolean borrarProductoDeCesta( WebTarget webTarget, Cesta cesta) {
        WebTarget WebTargetLogin = webTarget.path("borrarProductoDeCesta");
        Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
        } else {	
            ClientMain.getLogger().info("Producto borrado correctamente");
            return true;
        }
    }

    public Historial getHistorial(WebTarget webTarget,String email){
		try {
            Response response = webTarget.path("historial")
                .queryParam("email", email)
				.request(MediaType.APPLICATION_JSON)
				.get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                Historial historial = response.readEntity(Historial.class);
				System.out.println(historial);
				return historial;		
            } else {
				System.out.format("Error obtaining historial. %s%n", response);
				return null;
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining historial. %s%n", e.getMessage());
			return null;
        }	
	}
    public boolean guardarHistorial(WebTarget webTarget, Historial historial) {
		WebTarget WebTargetHistorial = webTarget.path("modifyHistorial");
		Invocation.Builder invocationBuilder = WebTargetHistorial.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(historial, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
		} else {	
			ClientMain.getLogger().info("Historial guardado correctamente");
            return true;
		}
	}   
}