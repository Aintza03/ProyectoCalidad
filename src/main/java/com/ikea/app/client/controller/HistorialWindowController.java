package com.ikea.app.client.controller;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Producto;

public class HistorialWindowController{
    public HistorialWindowController(){
    
    }          
    public boolean devolverProducto(WebTarget webTarget, Producto producto) {
        WebTarget WebTargetLogin = webTarget.path("modifyPedidos");
        Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(producto, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
        } else {	
            ClientMain.getLogger().info("Producto devuelto correctamente");
            return true;
        }
    }
    }