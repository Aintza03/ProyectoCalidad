package com.ikea.app.server;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;

import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.pojo.Cliente;

import com.ikea.app.server.jdo.ProductoJDO;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import structures.ArrayList;

import com.ikea.app.pojo.Producto;

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

@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource{
    protected static final Logger logger = LogManager.getLogger();

	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;

    public Resource(){
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
    }

    @POST
	@Path("/register")
	public Response registrarCliente(Cliente cliente) {
		try
        {	
            tx.begin();
            logger.info("Comprobando que el usuario no exista: '{}'", cliente.getEmail());
			ClienteJDO clienteJDO = null;
			try {
				clienteJDO = pm.getObjectById(ClienteJDO.class, cliente.getEmail());
			} catch (javax.jdo.JDOObjectNotFoundException ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
			}
			logger.info("Cliente: {}", clienteJDO);
			if (clienteJDO != null) {
				logger.info("Añadiendo contraseña: {}", clienteJDO);
				clienteJDO.setContrasena(cliente.getContrasena());
				logger.info("Contraseña añadida: {}", clienteJDO);
                logger.info("Añadiendo nombre: {}", clienteJDO);
				clienteJDO.setNombre(cliente.getNombre());
				logger.info("Nombre Añadido: {}", clienteJDO);
                
			} else {
				logger.info("Creando usuario: {}", clienteJDO);
				clienteJDO = new ClienteJDO(cliente.getEmail(), cliente.getContrasena(), cliente.getNombre());
				pm.makePersistent(clienteJDO);					 
				logger.info("Usuario creado: {}", clienteJDO);
			}
			tx.commit();
			return Response.ok().build();
        }
        finally
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
      
		}
	}

	@GET
	@Path("/listProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listaProductos(ArrayList<Producto> listaProductos) {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		return Response.ok(listaProductos).build();
		}
	}

