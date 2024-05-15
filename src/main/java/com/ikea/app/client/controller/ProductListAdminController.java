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
import com.ikea.app.pojo.Admin;
import com.ikea.app.client.ClientMain;

/**Controller de la ventana ListaPedidosAdmin. */
public class ProductListAdminController {
    /**Constructor Vacio. */
    public ProductListAdminController(){

    }
    /**Funcion que pide al servidor todos los productos no comprados de ese administrador. */
    public List<Producto> datosDeProductos(WebTarget webTarget, String usuario) {
        
        try {
            Response response = webTarget.path("listProductsAdmin")
                .queryParam("admin", usuario)
                .request(MediaType.APPLICATION_JSON)
                .get();

            
            if (response.getStatusInfo().toEnum() == Status.OK) {
                
                GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
                List<Producto> product = response.readEntity(listType);
                
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
    /**Funcion para eliminar un producto no comprado. */
    public boolean eliminarProducto(WebTarget webTargets, Producto producto) {
       
        try {
            WebTarget WebTargetLogin = webTargets.path("eliminarProducto");
            Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.entity(producto, MediaType.APPLICATION_JSON));
            if (response.getStatus() != Status.OK.getStatusCode()) {
                ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
                return false;
            } else {	
                ClientMain.getLogger().info("Producto eliminado correctamente");
                return true;
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining product list. %s%n", e.getMessage());
            return false;
        }

    }
}
