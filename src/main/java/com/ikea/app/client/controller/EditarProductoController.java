package com.ikea.app.client.controller;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.ClientMain;

public class EditarProductoController{


	public EditarProductoController() {
        
    }
    
    public Producto editarProducto(WebTarget webTarget, String name, String tipo, double precio, Producto producto) {
        WebTarget WebTargetLogin = webTarget.path("modifyProduct");
        Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
        
        producto.setNombre(name);
        producto.setTipo(tipo);
        producto.setPrecio(precio);
        Response response = invocationBuilder.post(Entity.entity(producto, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
        } else {	
            ClientMain.getLogger().info("Producto editado correctamente");
        }
        return producto;
    }
}
