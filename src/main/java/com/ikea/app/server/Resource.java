package com.ikea.app.server;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;
import javax.jdo.Extent;

import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.server.jdo.AdminJDO;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Admin;
import com.ikea.app.server.jdo.CestaJDO;
import com.ikea.app.server.jdo.ProductoJDO;
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
import java.util.HashSet;
import javax.jdo.JDOObjectNotFoundException;
import com.ikea.app.server.jdo.HistorialJDO;
import com.ikea.app.pojo.Historial;
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
		try {	
            tx.begin();
            logger.info("Comprobando que el usuario no exista: '{}'", cliente.getEmail());
			ClienteJDO clienteJDO = null;
			try {
				clienteJDO = pm.getObjectById(ClienteJDO.class, cliente.getEmail());
			} catch (JDOObjectNotFoundException ex1) {
				logger.error("Exception launched: {}", ex1.getMessage());
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
				HistorialJDO historialJDO = new HistorialJDO(clienteJDO);
				pm.makePersistent(clienteJDO);
				pm.makePersistent(cestaJDO);
				pm.makePersistent(historialJDO);					 
				logger.info("Usuario creado: {}", clienteJDO);
			}
			tx.commit();
			return Response.ok().build();
        }
        finally {
            if (tx.isActive())
            {
                tx.rollback();
            }
			//pm.close();
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
			try (Query<ClienteJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "' AND contrasena = '" + cliente.getContrasena() + "'")) {
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
					return Response.ok(clienteJDO.getNombre()).build();
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
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE isnull(productoshistorial)")) {
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
	@Path("/cantidadProductos")
	@Produces(MediaType.APPLICATION_JSON)
	public int cantidadProductos() {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		int result = 0;
		try {	
            tx.begin();
            logger.info("Obteniendo productos");
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				System.out.println("Productos: " + results);
				result = results.size();
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
			}
			tx.commit();
			return result;
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
			try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '" + email +"'")) {
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
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cesta.getCliente().getEmail() +"'")) {
				//try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","UPDATE productojdo SET PRODUCTOS = '"+ cestajdo.getCliente() +"' WHERE ID = '"+productojdo.getId() +"'")) {
				q.setClass(CestaJDO.class);
				List<CestaJDO> results = q.executeList();
				cestajdo = results.get(0);
				for(Producto producto : cesta.getCesta()){
					ProductoJDO productojdo = null;
					try(Query<ProductoJDO> q2 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE ID = '"+producto.getId() +"'")){
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
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cesta.getCliente().getEmail() +"'")) {
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
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '" + cesta.getCliente().getEmail() + "'")) {
				q.setClass(CestaJDO.class);
				List<CestaJDO> results = q.executeList();
				cestajdo = results.get(0);
					try(Query<ProductoJDO> q2 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE PRODUCTOS = '" + cesta.getCliente().getEmail() + "'")){
						q2.setClass(ProductoJDO.class);
						List<ProductoJDO> resultsP = q2.executeList();
						boolean result = false;
						for(ProductoJDO productojdo: resultsP){
							result = false;
							for(Producto producto: cesta.getCesta()){
								if(productojdo.getId() == producto.getId()){
									result = true;
									break;
								}
							}
							if(result == false){
								cestajdo.borrarProductoDeCesta(productojdo);
							}
						}
					}catch(javax.jdo.JDOObjectNotFoundException ex1){
						logger.info("Exception1 launched: {}", ex1.getMessage());
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
	@Path("/modifyClient")
	public Response modifyClient(Cliente cliente){
		try{
			logger.info("Modificando el cliente: " + cliente.getEmail());
			tx.begin();
			ClienteJDO clienteJDO;
			try (Query<ClienteJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "'")) {
				q.setClass(ClienteJDO.class);
				List<ClienteJDO> results = q.executeList();
				clienteJDO = results.get(0);
				clienteJDO.setNombre(cliente.getNombre());
				clienteJDO.setContrasena(cliente.getContrasena());
				pm.makePersistent(clienteJDO);
				logger.info("Cliente modificado: {}", clienteJDO);
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
	@Path("/loginAdmin")
	public Response loginAdmin(Admin admin){
		AdminJDO adminJDO = null;
		try {	
            tx.begin();
            logger.info("Comprobando que el admin exista: '{}'", admin.getUsuario());
			logger.info("Comprobando si la contraseña es correcta: '{}", admin.getContrasena());
			try (Query<AdminJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM ADMINJDO WHERE usuario = '" + admin.getUsuario() + "' AND contrasena = '" + admin.getContrasena() + "'")) {
				q.setClass(AdminJDO.class);
				List<AdminJDO> results = q.executeList();
				adminJDO = results.get(0);
				logger.info("Client retrieved: {}", adminJDO);
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
			}
			tx.commit();
			if (adminJDO != null) {
				if(adminJDO.getContrasena().equals(admin.getContrasena())) {
					System.out.println("Contraseña correcta");
					admin.setContrasena(adminJDO.getContrasena());
					admin.setLista(new HashSet<Producto>());
					for(ProductoJDO productoJDO: adminJDO.getLista()){
						Producto producto = new Producto();
						producto.setId(productoJDO.getId());
						producto.setNombre(productoJDO.getNombre());
						producto.setTipo(productoJDO.getTipo());
						producto.setPrecio(productoJDO.getPrecio());
						admin.anadirLista(producto);
					}
					admin.setUsuario(adminJDO.getUsuario());
					return Response.ok(admin).build();
				} else{
					System.out.println("Contraseña incorrecta");
					return Response.status(Status.UNAUTHORIZED).build();
				}
			}else{
				System.out.println("Admin Vacio");
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

	@POST
	@Path("/borrarCliente")
	public Response borrarCliente(Cliente cliente){
		try{
			tx.begin();
			logger.info("Modificando cesta del cliente: " + cliente.getNombre());
			Extent<CestaJDO> ext = pm.getExtent(CestaJDO.class,true);
            try (Query<CestaJDO> q = pm.newQuery(ext, "cliente.email == '" + cliente.getEmail() + "'")) {
                q.deletePersistentAll();
                logger.info("Cesta del cliente " + cliente.getNombre() +" borrada");

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
			tx.begin();
			Extent<HistorialJDO> ext2 = pm.getExtent(HistorialJDO.class,true);
			try (Query<HistorialJDO> q2 = pm.newQuery(ext2, "cliente.email == '" + cliente.getEmail() + "'")) {
                q2.deletePersistentAll();
                logger.info("Historial del cliente " + cliente.getNombre() +" borrada");
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
			
			tx.begin();
			Extent<ClienteJDO> ext3 = pm.getExtent(ClienteJDO.class,true);
            try (Query<ClienteJDO> q3 = pm.newQuery(ext3, "email == '" + cliente.getEmail() + "'")) {
                long numberInstancesDeleted = q3.deletePersistentAll();
                logger.info("Cliente " + cliente.getNombre() + " borrado");

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
	@GET
	@Path("/listProductsAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> listaProductosAdministrador(@QueryParam("admin") String admin) {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		List<Producto> productos = new ArrayList<Producto>();
		try {	
            tx.begin();
            logger.info("Obteniendo productos");
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE VENDEDOR = '" + admin + "' AND isnull(productoshistorial)")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				System.out.println("Productos: " + results);
				for (ProductoJDO productoJDO : results) {
					Producto producto = new Producto();
					producto.setNombre(productoJDO.getNombre());
					producto.setPrecio(productoJDO.getPrecio());
					producto.setTipo(productoJDO.getTipo());
					producto.setId(productoJDO.getId());
					Producto.setIdGeneral(producto.getId());
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
	
	@POST
	@Path("/anadirProductoAdmin")
	public Response anadirProductoAdmin(Admin admin) {
		try{
			logger.info("Modificando productos del admin: " + admin.getUsuario());
			tx.begin();
				AdminJDO adminjdo = null;
				try (Query<AdminJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM ADMINJDO where usuario = '"+admin.getUsuario() +"'")) {
				q.setClass(AdminJDO.class);
				List<AdminJDO> results = q.executeList();
				adminjdo = results.get(0);
				try(Query<ProductoJDO> q2 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO")){
					q2.setClass(ProductoJDO.class);
					List<ProductoJDO> number = q2.executeList();
					if (number != null) {
						cont = number.size();
					}else{
						cont = 0;
					}
				}catch(javax.jdo.JDOObjectNotFoundException ex1){
					logger.info("Exception1 launched: {}", ex1.getMessage());
				}
				Producto producto = null;//new ArrayList<>(admin.getLista()).get(admin.getLista().size()-1);
				for(Producto productos: admin.getLista()){
					logger.info("Producto: " + productos.getId());
					if(productos.getId() == cont+1){
						producto = productos;
						
					}
				}
				ProductoJDO productojdo = new ProductoJDO(producto.getId(), producto.getNombre(), producto.getTipo(), producto.getPrecio());
				adminjdo.anadirLista(productojdo);
				pm.makePersistent(productojdo);
				pm.makePersistent(adminjdo);
				logger.info("Producto guardado: {}", productojdo);
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
	@Path("/eliminarProducto")
	public Response eliminarProducto(Producto producto){
		try{
			logger.info("Modificando productos del admin: " + producto.getNombre());
			tx.begin();
				ProductoJDO productojdo = null;
				try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO where id = '"+producto.getId() +"'")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				productojdo = results.get(0);
				pm.deletePersistent(productojdo);
				logger.info("Producto eliminado: {}", productojdo);
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
	@GET
	@Path("/historial")
	@Produces(MediaType.APPLICATION_JSON)
	public Historial getHistorial(@QueryParam("email") String email) {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		Historial historial = new Historial();
		Cliente cliente = new Cliente();
		Producto producto;
		try{
			tx.begin();
			logger.info("Obteniendo historial del cliente: " + email);
			HistorialJDO historialjdo = null;
			try (Query<HistorialJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM HISTORIALJDO WHERE CLIENTE_EMAIL_OID = '" + email +"'")) {
				q.setClass(HistorialJDO.class);
				List<HistorialJDO> results = q.executeList();
				historialjdo = results.get(0);
				logger.info("Se ha obtenido historial: " + historialjdo);
				cliente.setEmail(historialjdo.getCliente().getEmail());
				cliente.setContrasena(historialjdo.getCliente().getContrasena());
				cliente.setNombre(historialjdo.getCliente().getNombre());
				historial.setCliente(cliente);
				for(ProductoJDO productoJDO: historialjdo.getProductos()){
					producto = new Producto();
					producto.setId(productoJDO.getId());
					producto.setNombre(productoJDO.getNombre());
					producto.setTipo(productoJDO.getTipo());
					producto.setPrecio(productoJDO.getPrecio());
					historial.addProducto(producto);
				}
			} catch (javax.jdo.JDOObjectNotFoundException ex1) {
				logger.info("Exception1 launched: {}", ex1.getMessage());
			}
			tx.commit();
			if(historialjdo != null){
				logger.info("Historial encontrado");
				return historial;
			}else{
				logger.info("No hay historial");
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
	@Path("/modifyHistorial")
	public Response modifyHistorial(Historial historial){
		try{
			logger.info("Modificando historial del cliente: " + historial.getCliente());
			tx.begin();
			
				HistorialJDO historialjdo = null;
				try (Query<HistorialJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM HISTORIALJDO WHERE CLIENTE_EMAIL_OID = '"+historial.getCliente().getEmail() +"'")) {
				//try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","UPDATE productojdo SET PRODUCTOS = '"+ cestajdo.getCliente() +"' WHERE ID = '"+productojdo.getId() +"'")) {
				q.setClass(HistorialJDO.class);
				List<HistorialJDO> results = q.executeList();
				historialjdo = results.get(0);
				for(Producto producto : historial.getProductos()){
					ProductoJDO productojdo = null;
					try(Query<ProductoJDO> q2 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE ID = '"+producto.getId() +"'")){
						q2.setClass(ProductoJDO.class);
						List<ProductoJDO> resultsP = q2.executeList();
						productojdo = resultsP.get(0);
						historialjdo.addProducto(productojdo);
					}catch(javax.jdo.JDOObjectNotFoundException ex1){
						logger.info("Exception1 launched: {}", ex1.getMessage());
					}	
				}
				pm.makePersistent(historialjdo);
				logger.info("Historial guardado: {}", historial);
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
	@GET
	@Path("/listPedidosAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> listaPedidosAdministrador(@QueryParam("admin") String admin) {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		List<Producto> productos = new ArrayList<Producto>();
		try {	
            tx.begin();
            logger.info("Obteniendo productos");
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE VENDEDOR = '" + admin + "' AND NOT isnull(productoshistorial)") ) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				System.out.println("Productos: " + results);
				for (ProductoJDO productoJDO : results) {
					Producto producto = new Producto();
					producto.setNombre(productoJDO.getNombre());
					producto.setPrecio(productoJDO.getPrecio());
					producto.setTipo(productoJDO.getTipo());
					producto.setId(productoJDO.getId());
					Producto.setIdGeneral(producto.getId());
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

		//HAY QUE HACERLO DE MOMENTO ESTA CON LO QUE HE COPIADO
	@POST
	@Path("/modifyProduct")
	public Response modifyProduct(Producto producto){
		try{
			logger.info("Editando el producto: " + producto.getId());
			tx.begin();
			ProductoJDO productoJDO;
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE ID = '" + producto.getId() + "'")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				productoJDO = results.get(0);
				productoJDO.setNombre(producto.getNombre());
				productoJDO.setTipo(producto.gettipo());
				productoJDO.setPrecio(producto.getPrecio());
				pm.makePersistent(productoJDO);
				logger.info("Producto editado: {}", productoJDO);
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