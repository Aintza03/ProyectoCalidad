package com.ikea.app.client.controller;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Producto;
import com.ikea.app.client.ClientMain;
public class AnadirProductoController{
    
	public AnadirProductoController(){
    
    }

    public void anadirProducto(WebTarget webTarget,String nombre, String tipo, double precio, String usuario) {
		WebTarget WebTargetRegistrarUsuario = webTarget.path("anadir");
		Invocation.Builder invocationBuilder = WebTargetRegistrarUsuario.request(MediaType.APPLICATION_JSON);
		
		Producto producto = new Producto();
		Producto.setIdGeneral(producto.getId());
		producto.setNombre(nombre);
		producto.setTipo(tipo);
        producto.setPrecio(precio);
		producto.setVendedor(usuario);
		Response response = invocationBuilder.post(Entity.entity(producto, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			ClientMain.getLogger().info("Producto añadido correctamente");
		} 
	}
}
