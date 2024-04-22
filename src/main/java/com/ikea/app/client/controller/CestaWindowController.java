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
public class CestaWindowController{
    
   public CestaWindowController(){
    
    }          

    public void vaciarCesta(WebTarget webTarget, Cesta cesta) {
		WebTarget WebTargetLogin = webTarget.path("vaciarCesta");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {	
			ClientMain.getLogger().info("Cesta vaciada correctamente");
		}
	}

    public void borrarProductoDeCesta( WebTarget webTarget, Cesta cesta) {
        WebTarget WebTargetLogin = webTarget.path("borrarProductoDeCesta");
        Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
        } else {	
            ClientMain.getLogger().info("Producto borrado correctamente");
        }
}
}