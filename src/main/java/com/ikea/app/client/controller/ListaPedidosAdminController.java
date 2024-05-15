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
        
        ProductListAdminController controller = new ProductListAdminController();
        return controller.eliminarProducto(webTargets, producto);
        
    }

}
