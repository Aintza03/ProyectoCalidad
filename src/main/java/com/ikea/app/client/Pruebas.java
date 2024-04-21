package com.ikea.app.client;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;
import javax.jdo.Extent;

import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.server.jdo.AdminJDO;
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

            productos.add(new ProductoJDO(1,"Product 1","Tipo 1", 10));
            productos.add(new ProductoJDO(2,"Product 2","Tipo 1", 19));
            productos.add(new ProductoJDO(3,"Product 3","Tipo 1", 5));
            productos.add(new ProductoJDO(4,"Product 4","Tipo 2", 15));
            productos.add(new ProductoJDO(5,"Product 5","Tipo 2", 20));
            productos.add(new ProductoJDO(6,"Product 6","Tipo 3", 25));
            productos.add(new ProductoJDO(7,"Product 7","Tipo 3", 30));
            
            AdminJDO admin = new AdminJDO("Alfredo", "1234");
            admin.anadirLista(productos.get(0));
            admin.anadirLista(productos.get(1));
            admin.anadirLista(productos.get(4));

            AdminJDO admin2 = new AdminJDO("Rocio", "1234");
            admin2.anadirLista(productos.get(2));
            admin2.anadirLista(productos.get(3));
            admin2.anadirLista(productos.get(5));
            admin2.anadirLista(productos.get(6));
            
            for (ProductoJDO producto: productos){
            pm.makePersistent(producto);					 
            }
            pm.makePersistent(admin);
            pm.makePersistent(admin2);
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