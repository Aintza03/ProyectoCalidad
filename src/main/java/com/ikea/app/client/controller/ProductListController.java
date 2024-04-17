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

import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.client.ClientMain;
public class ProductListController{

    public ProductListController(){

    }

    public List<Producto> datosDeProductos(WebTarget webTarget) {
        // issuing a GET request to the users endpoint with some query parameters
        try {
            Response response = webTarget.path("listProducts")
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                // the response is a generic type (a List<User>)
                GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
                List<Producto> product = response.readEntity(listType);
                //System.out.println(product);
				return product;
            } else {
				System.out.format("Error obtaining product list. %s%n", response);
				return new ArrayList<Producto>();
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining product list. %s%n", e.getMessage());
			return new ArrayList<Producto>();
        }

	}
	public Cesta getCesta(WebTarget webTarget,String email){
		try {
            Response response = webTarget.path("cesta")
                .queryParam("email", email)
				.request(MediaType.APPLICATION_JSON)
				.get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                Cesta cesta = response.readEntity(Cesta.class);
				System.out.println(cesta);
				return cesta;		
            } else {
				System.out.format("Error obtaining cesta. %s%n", response);
				return null;
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining cesta. %s%n", e.getMessage());
			return null;
        }	
	}
	
    public void modificarCesta(WebTarget webTarget,Cesta cesta) {
		WebTarget WebTargetLogin = webTarget.path("modifyCesta");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {	
			ClientMain.getLogger().info("Cesta modificada correctamente");
		}
	}
}
    

