package com.ikea.app.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.math3.stat.descriptive.summary.Product;

import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Producto;

/**Controller de la ventana ListaPedidosAdmin. */
public class ListaPedidosAdminController {
    /**Funcion que pide al servidor todos los pedidos de ese administrador. */
    public List<Producto> verPedidos(WebTarget webTarget, String usuario) {
        
        try {
            Response response = webTarget.path("listPedidosAdmin")
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
    /**Funcion para eliminar un producto ya comprado. */
    public boolean eliminarPedido(WebTarget webTargets, Producto producto) {    
        try {
            WebTarget WebTargetLogin = webTargets.path("modifyPedidos");
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
