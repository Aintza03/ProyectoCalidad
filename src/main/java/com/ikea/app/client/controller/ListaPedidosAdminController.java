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

public class ListaPedidosAdminController {

    public List<Producto> verPedidos(WebTarget webTarget, String usuario) {
        // TODO Auto-generated method stub
        try {
            Response response = webTarget.path("listPedidosAdmin")
                .queryParam("admin", usuario)
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

    public boolean eliminarPedido(WebTarget webTargets, Producto producto) {
        // TODO Auto-generated method stub
        ProductListAdminController controller = new ProductListAdminController();
        return controller.eliminarProducto(webTargets, producto);
        
    }

}
