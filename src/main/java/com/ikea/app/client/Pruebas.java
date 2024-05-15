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
/**Clase que permite hacer el volcado inicial de datos en la base de datos. */
public class Pruebas{
    
/**Se guardan ciertos productos y ciertos admin en la base de datos.*/
public static void main( String[] args ) {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
        try
        {	
            tx.begin();

            List<ProductoJDO> productos = new ArrayList<ProductoJDO>();

            productos.add(new ProductoJDO(1,"Product 1","Salon", 10));
            productos.add(new ProductoJDO(2,"Product 2","Salon", 19));
            productos.add(new ProductoJDO(3,"Product 3","Salon", 5));
            productos.add(new ProductoJDO(4,"Product 4","Baño", 15));
            productos.add(new ProductoJDO(5,"Product 5","Salon", 20));
            productos.add(new ProductoJDO(6,"Product 6","Baño", 25));
            productos.add(new ProductoJDO(7,"Product 7","Salon", 3));
            productos.add(new ProductoJDO(8,"Product 8","Habitacion", 13));
            productos.add(new ProductoJDO(9,"Product 9","Habitacion", 45));
            productos.add(new ProductoJDO(10,"Product 10","Comedor", 23));
            productos.add(new ProductoJDO(11,"Product 11","Jardin", 54));
            productos.add(new ProductoJDO(12,"Product 12","Entrada", 76));
            productos.add(new ProductoJDO(13,"Product 13","Salon", 32));
            productos.add(new ProductoJDO(14,"Product 14","Baño", 36));
            productos.add(new ProductoJDO(15,"Product 15","Salon", 13));
            productos.add(new ProductoJDO(16,"Product 16","Habitacion", 57));
            productos.add(new ProductoJDO(17,"Product 17","Habitacion", 43));
            productos.add(new ProductoJDO(18,"Product 18","Salon", 44));
            productos.add(new ProductoJDO(19,"Product 19","Entrada", 64));
            productos.add(new ProductoJDO(20,"Product 20","Salon", 16));

            
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