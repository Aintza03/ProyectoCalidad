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

public static void main( String[] args ) {
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
            productos.add(new ProductoJDO(7,"Product 7","Tipo 2", 3));
            productos.add(new ProductoJDO(8,"Product 8","Tipo 1", 13));
            productos.add(new ProductoJDO(9,"Product 9","Tipo 4", 45));
            productos.add(new ProductoJDO(10,"Product 10","Tipo 2", 23));
            productos.add(new ProductoJDO(11,"Product 11","Tipo 4", 54));
            productos.add(new ProductoJDO(12,"Product 12","Tipo 1", 76));
            productos.add(new ProductoJDO(13,"Product 13","Tipo 4", 32));
            productos.add(new ProductoJDO(14,"Product 14","Tipo 4", 36));
            productos.add(new ProductoJDO(15,"Product 15","Tipo 2", 13));
            productos.add(new ProductoJDO(16,"Product 16","Tipo 3", 57));
            productos.add(new ProductoJDO(17,"Product 17","Tipo 4", 43));
            productos.add(new ProductoJDO(18,"Product 18","Tipo 3", 44));
            productos.add(new ProductoJDO(19,"Product 19","Tipo 1", 64));
            productos.add(new ProductoJDO(20,"Product 20","Tipo 1", 16));

            
            AdminJDO admin = new AdminJDO("Alfredo", "1234");
            admin.anadirLista(productos.get(0));
            admin.anadirLista(productos.get(1));
            admin.anadirLista(productos.get(4));
            admin.anadirLista(productos.get(7));
            admin.anadirLista(productos.get(8));    
            admin.anadirLista(productos.get(9));
            admin.anadirLista(productos.get(10));
            admin.anadirLista(productos.get(15));
            admin.anadirLista(productos.get(16));
            admin.anadirLista(productos.get(17));

            AdminJDO admin2 = new AdminJDO("Rocio", "1234");
            admin2.anadirLista(productos.get(2));
            admin2.anadirLista(productos.get(3));
            admin2.anadirLista(productos.get(5));
            admin2.anadirLista(productos.get(6));
            admin2.anadirLista(productos.get(11));
            admin2.anadirLista(productos.get(12));
            admin2.anadirLista(productos.get(13));
            admin2.anadirLista(productos.get(14));
            admin2.anadirLista(productos.get(18));
            admin2.anadirLista(productos.get(19));
            
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