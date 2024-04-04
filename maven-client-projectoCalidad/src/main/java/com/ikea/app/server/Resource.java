package com.ikea.app.server;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;
import javax.jdo.Extent;

import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.server.jdo.CestaJDO;
import com.ikea.app.server.jdo.ProductoJDO;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import javax.ws.rs.QueryParam;
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
import com.ikea.app.pojo.Cesta;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.List;
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
				CestaJDO cestaJDO = new CestaJDO(clienteJDO);
				pm.makePersistent(clienteJDO);
				pm.makePersistent(cestaJDO);					 
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
			pm.close();
		}
	}

	@POST
	@Path("/login")
	public Response loginCliente(Cliente cliente){
		ClienteJDO clienteJDO = null;
		try {	
            tx.begin();
            logger.info("Comprobando que el usuario exista: '{}'", cliente.getEmail());
			logger.info("Comprobando si la contraseña es correcta: '{}", cliente.getContrasena());
			try (Query<ClienteJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM clientejdo WHERE email = '" + cliente.getEmail() + "' AND contrasena = '" + cliente.getContrasena() + "'")) {
				q.setClass(ClienteJDO.class);
				List<ClienteJDO> results = q.executeList();
				clienteJDO = results.get(0);
				logger.info("Client retrieved: {}", clienteJDO);
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
			}
			tx.commit();
			if (clienteJDO != null) {
				if(clienteJDO.getContrasena().equals(cliente.getContrasena())) {
					System.out.println("Contraseña correcta");
					return Response.ok("Dentro").build();
				} else{
					System.out.println("Contraseña incorrecta");
					return Response.status(Status.UNAUTHORIZED).build();
				}
			}else{
				System.out.println("Cliente Vacio");
				return Response.status(Status.NOT_FOUND).build();
			}
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
			 
	}

	@GET
	@Path("/listProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> listaProductos() {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		List<Producto> productos = new ArrayList<Producto>();
		try {	
            tx.begin();
            logger.info("Obteniendo productos");
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM productojdo")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				System.out.println("Productos: " + results);
				for (ProductoJDO productoJDO : results) {
					Producto producto = new Producto();
					producto.setNombre(productoJDO.getNombre());
					producto.setPrecio(productoJDO.getPrecio());
					producto.setTipo(productoJDO.getTipo());
					producto.setId(productoJDO.getId());
					productos.add(producto);
					logger.info("Product retrieved: {}", productoJDO);
				}
				
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
			}
			tx.commit();
			if (productos.size() != 0) {	
        		return productos;
			}else{
				System.out.println("Producto Vacio");
				return productos;
			}
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		}
	@GET
	@Path("/cesta")
	@Produces(MediaType.APPLICATION_JSON)
	public Cesta getCesta(@QueryParam("email") String email) {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		Cesta cesta = new Cesta();
		Cliente cliente = new Cliente();
		Producto producto;
		try{
			tx.begin();
			logger.info("Obteniendo cesta del cliente: " + email);
			CestaJDO cestajdo = null;
			try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM cestajdo WHERE CLIENTE_EMAIL_OID = '" + email +"'")) {
				q.setClass(CestaJDO.class);
				List<CestaJDO> results = q.executeList();
				cestajdo = results.get(0);
				logger.info("Se ha obtenido cesta: " + cestajdo);
				cliente.setEmail(cestajdo.getCliente().getEmail());
				cliente.setContrasena(cestajdo.getCliente().getContrasena());
				cliente.setNombre(cestajdo.getCliente().getNombre());
				cesta.setCliente(cliente);
				for(ProductoJDO productoJDO: cestajdo.getCesta()){
					producto = new Producto();
					producto.setId(productoJDO.getId());
					producto.setNombre(productoJDO.getNombre());
					producto.setTipo(productoJDO.getTipo());
					producto.setPrecio(productoJDO.getPrecio());
					cesta.anadirCesta(producto);
				}
			} catch (javax.jdo.JDOObjectNotFoundException ex1) {
				logger.info("Exception1 launched: {}", ex1.getMessage());
			}
			tx.commit();
			if(cestajdo != null){
				logger.info("Cesta encontrada");
				return cesta;
			}else{
				logger.info("No hay cesta");
				return null;
			}
		}catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
				return null;
		}finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		}
	@POST
	@Path("/modifyCesta")
	public Response modifyCesta(Cesta cesta){
		try{
			logger.info("Modificando cesta del cliente: " + cesta.getCliente());
			tx.begin();
			
				CestaJDO cestajdo = null;
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM cestajdo WHERE CLIENTE_EMAIL_OID = '"+cesta.getCliente().getEmail() +"'")) {
				//try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","UPDATE productojdo SET PRODUCTOS = '"+ cestajdo.getCliente() +"' WHERE ID = '"+productojdo.getId() +"'")) {
				q.setClass(CestaJDO.class);
				List<CestaJDO> results = q.executeList();
				cestajdo = results.get(0);
				for(Producto producto: cesta.getCesta()){
					ProductoJDO productojdo = null;
					try(Query<ProductoJDO> q2 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM productojdo WHERE ID = '"+producto.getId() +"'")){
						q2.setClass(ProductoJDO.class);
						List<ProductoJDO> resultsP = q2.executeList();
						productojdo = resultsP.get(0);
						cestajdo.AnadirCesta(productojdo);
					}catch(javax.jdo.JDOObjectNotFoundException ex1){
						logger.info("Exception1 launched: {}", ex1.getMessage());
					}	
				}
				pm.makePersistent(cestajdo);
				logger.info("Cesta guardada: {}", cesta);
			} catch (javax.jdo.JDOObjectNotFoundException ex1) {
				logger.info("Exception1 launched: {}", ex1.getMessage());
			}	
			tx.commit();
			return Response.ok().build();
		}catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
				return Response.status(Status.NOT_FOUND).build();
		}finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		}
		@POST
	@Path("/vaciarCesta")
	public Response vaciarCesta(Cesta cesta){
		try{
			logger.info("Modificando cesta del cliente: " + cesta.getCliente());
			tx.begin();
			
				CestaJDO cestajdo = null;
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM cestajdo WHERE CLIENTE_EMAIL_OID = '"+cesta.getCliente().getEmail() +"'")) {
				q.setClass(CestaJDO.class);
				List<CestaJDO> results = q.executeList();
				cestajdo = results.get(0);
				cestajdo.getCesta().clear();
				pm.makePersistent(cestajdo);
				logger.info("Cesta guardada: {}", cesta);
			} catch (javax.jdo.JDOObjectNotFoundException ex1) {
				logger.info("Exception1 launched: {}", ex1.getMessage());
			}	
			tx.commit();
			return Response.ok().build();
		}catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
				return Response.status(Status.NOT_FOUND).build();
		}finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		}
		@POST
	@Path("/borrarProductoDeCesta")
	public Response borrarProductoDeCesta(Cesta cesta){
		try{
			logger.info("Modificando cesta del cliente: " + cesta.getCliente());
			tx.begin();
			
				CestaJDO cestajdo = null;
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM cestajdo WHERE CLIENTE_EMAIL_OID = '"+cesta.getCliente().getEmail() +"'")) {
				q.setClass(CestaJDO.class);
				List<CestaJDO> results = q.executeList();
				cestajdo = results.get(0);
				for(Producto producto: cesta.getCesta()){
					ProductoJDO productojdo = null;
					try(Query<ProductoJDO> q2 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM productojdo WHERE ID = '"+producto.getId() +"'")){
						q2.setClass(ProductoJDO.class);
						List<ProductoJDO> resultsP = q2.executeList();
						productojdo = resultsP.get(0);
						cestajdo.borrarProductoDeCesta(productojdo);
					}catch(javax.jdo.JDOObjectNotFoundException ex1){
						logger.info("Exception1 launched: {}", ex1.getMessage());
					}	
				}
				pm.makePersistent(cestajdo);
				logger.info("Cesta guardada: {}", cesta);
			} catch (javax.jdo.JDOObjectNotFoundException ex1) {
				logger.info("Exception1 launched: {}", ex1.getMessage());
			}	
			tx.commit();
			return Response.ok().build();
		}catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
				return Response.status(Status.NOT_FOUND).build();
		}finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
		}
}	

