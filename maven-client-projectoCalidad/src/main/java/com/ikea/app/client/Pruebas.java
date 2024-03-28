package com.ikea.app.client;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;
import javax.jdo.Extent;

import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.pojo.Cliente;

import com.ikea.app.server.jdo.ProductoJDO;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import java.util.ArrayList;

import com.ikea.app.pojo.Producto;
import java.util.stream.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;

public class Pruebas{

	
    public Pruebas() {
			}

public static void guardarDatosEjemplo() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
        try
        {	
            tx.begin();
            List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
            productos.add(new ProductoJDO("Product 1","Tipo 1", 10, 10));
            productos.add(new ProductoJDO("Product 2", "Tipo 2", 19,20));
            productos.add(new ProductoJDO("Product 3","Tipo 3", 5,20));
            for (ProductoJDO producto: productos){
            pm.makePersistent(producto);					 
            }
			tx.commit();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
			pm.close();
		}
	}

}