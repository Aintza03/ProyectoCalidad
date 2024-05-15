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

/**Controller de la ventana Cesta. */
public class CestaWindowController{
    /**Constructor Vacio. */
   public CestaWindowController(){
    
    }          
    /**Funcion que manda al servidor la instruccion de eliminar todos los productos de la cesta del usuario.*/
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
    /**Funcion que manda al servidor la orden de eliminar un unico producto de la cesta.*/
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
    /**Funcion que pide al servidor todos los productos en el historial de ese usuario. */
    public Historial getHistorial(WebTarget webTarget,String email){
		try {
            Response response = webTarget.path("historial")
                .queryParam("email", email)
				.request(MediaType.APPLICATION_JSON)
				.get();

            
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
    /**Funcion que manda al servidor la orden de guardar el historial de un usuario.*/
    
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