package com.ikea.app.server;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.JDOHelper;
import javax.jdo.Transaction;
import javax.jdo.Extent;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.pojo.Admin;
import com.ikea.app.server.jdo.AdminJDO;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.server.jdo.CestaJDO;
import com.ikea.app.pojo.Producto;
import com.ikea.app.server.jdo.ProductoJDO;
import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.server.jdo.ReclamacionJDO;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;

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
import java.util.HashSet;
import javax.jdo.JDOObjectNotFoundException;
import com.ikea.app.server.jdo.HistorialJDO;
import com.ikea.app.pojo.Historial;

/**Es la parte servidor de la aplicacion.La parte cliente llama al servidor que se conecta con la base de datos
 * para devolverle al cliente lo que ha pedido a partir de los controllers.
 */
@Path("/resource")
@Produces(MediaType.APPLICATION_JSON)
public class Resource{
	/**Variable que guarda el Logger del resource. */
    protected static final Logger logger = LogManager.getLogger();

	private int cont = 0;
	private PersistenceManager pm=null;
	private Transaction tx=null;
	/**Constructor que inicializa el servidor. */
    public Resource(){
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		this.pm = pmf.getPersistenceManager();
		this.tx = pm.currentTransaction();
    }
	/**Metodo que registra un cliente en la base de datos. */
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
	/**Metodo que permite al cliente iniciar sesion. */
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

	/**Metodo que devuelve la lista de productos que hay en la base de datos, solo incluye los productos que
	 * se pueden comprar. */
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

	/**Metodo que permite obtener la cantidad de productos total. */
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
	
	/**Metodo que devuelve la cesta de un cliente. */
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
	/**Metodo que permite anadir un producto a la cesta del cliente. */
	@POST
	@Path("/modifyCesta")
	public Response modifyCesta(Cesta cesta){
		try{
			logger.info("Modificando cesta del cliente: " + cesta.getCliente());
			tx.begin();
			
				CestaJDO cestajdo = null;
				try (Query<CestaJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cesta.getCliente().getEmail() +"'")) {
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
	/**Metodo que permite vaciar la cesta del cliente. */
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
	/**Metodo que permite quitar un producto concreto de la cesta del cliente. */
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
	/**Metodo que permite modificar el nombre y la contrasena del cliente. */
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
	/**Metodo que permite iniciar sesion para el administrador. */
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
	/**Metodo que permite borrar a un cliente concreto junto a su cesta y historial. */
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
	/**Metodo que obtiene la lista de productos que son vendidos por un fabricante (administrador) en concreto. */
	@GET
	@Path("/listProductsAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> listaProductosAdministrador(@QueryParam("admin") String admin) {
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
	
	/**Metodo que permite crear productos y los asocia al vendedor. */
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
	/**Metodo que elimina un producto concreto. */
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
	/**Metodo que obtiene el historial. */
	@GET
	@Path("/historial")
	@Produces(MediaType.APPLICATION_JSON)
	public Historial getHistorial(@QueryParam("email") String email) {
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
	/**Metodo que anade productos comprados al historial. */
	@POST
	@Path("/modifyHistorial")
	public Response modifyHistorial(Historial historial){
		try{
			logger.info("Modificando historial del cliente: " + historial.getCliente());
			tx.begin();
			
				HistorialJDO historialjdo = null;
				try (Query<HistorialJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM HISTORIALJDO WHERE CLIENTE_EMAIL_OID = '"+historial.getCliente().getEmail() +"'")) {
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
	/**Obtiene la lista de productos de el administrador que el usuario no ha comprado. */
	@GET
	@Path("/listPedidosAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> listaPedidosAdministrador(@QueryParam("admin") String admin) {
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
	@POST
	@Path("/hacerReclamacion")
	/** Guarda la reclamacion en la base de datos */
	public Response makeReclamation(Reclamacion reclamacionA){
		try{
			logger.info("Insertando la reclamacion de: " + reclamacionA.getCliente());
			tx.begin();
			ReclamacionJDO reclamacionJDO = null;
			ClienteJDO clienteJDO = null;
			try (Query<ClienteJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM CLIENTEJDO WHERE EMAIL = '" + reclamacionA.getCliente().getEmail() + "'")) {
				q.setClass(ClienteJDO.class);
				q.setParameters(reclamacionA.getCliente().getEmail());
				List<ClienteJDO> results = q.executeList();
				clienteJDO = results.get(0);
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
			}
			ProductoJDO productoJDO = null;
			try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE ID = '" + reclamacionA.getProducto().getId() +"'")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				productoJDO = results.get(0);
				logger.info("Product retrieved: {}", productoJDO);
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
			}
			try {
				reclamacionJDO = pm.getObjectById(ReclamacionJDO.class, reclamacionA.getId());
				logger.info("3");
			} catch (JDOObjectNotFoundException ex1) {
				logger.error("Exception launched: {}", ex1.getMessage());
			}
			logger.info("Reclamacion: {}", reclamacionJDO);
			if (reclamacionJDO != null) {
				logger.info("Añadiendo cliente: {}", reclamacionA.getCliente());
				reclamacionJDO.setCliente(clienteJDO);
				logger.info("Cliente añadida: {}", reclamacionJDO.getCliente());
                logger.info("Añadiendo producto: {}", reclamacionA.getProducto());
				reclamacionJDO.setProducto(productoJDO);
				logger.info("Producto Añadido: {}", reclamacionJDO.getProducto());
                logger.info("Añadiendo Reclamacion: {}", reclamacionA.getReclamacion());
				reclamacionJDO.setReclamacion(reclamacionA.getReclamacion());
				logger.info("Producto Añadido: {}", reclamacionJDO.getReclamacion());
                
			} else {
				logger.info("Creando reclamacion: {}", reclamacionJDO);
				reclamacionJDO = new ReclamacionJDO(reclamacionA.getId(), reclamacionA.getReclamacion(), productoJDO, clienteJDO);
				pm.makePersistent(reclamacionJDO);					 
				logger.info("Reclamacion creada: {}", reclamacionJDO);
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

	@GET
	@Path("/sendReclamation")
	@Produces(MediaType.APPLICATION_JSON)
	/** Recupera una lista de reclamaciones de la lista de datos. */
	public List<Reclamacion> sendReclamation(@QueryParam("admin") String adminString) {
		//Tiene que devolver la lista de todos los productos que estan en el sistema
		Admin admin = null;
		try (Query<AdminJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM ADMINJDO WHERE usuario = '" + adminString + "'")) {
			q.setClass(AdminJDO.class);
			logger.info("Obteniendo admin: {}", adminString);
			List<AdminJDO> results = q.executeList();
			admin = new Admin();
			admin.setUsuario(results.get(0).getUsuario());
			logger.info("Admin retrieved: {}", admin);
			admin.setContrasena(results.get(0).getContrasena());
			admin.setLista(new HashSet<Producto>());
			logger.info("Admin retrieved: {}", admin);
			for(ProductoJDO productoJDO: results.get(0).getLista()){
				Producto producto = new Producto();
				producto.setId(productoJDO.getId());
				producto.setNombre(productoJDO.getNombre());
				producto.setTipo(productoJDO.getTipo());
				producto.setPrecio(productoJDO.getPrecio());
				admin.anadirLista(producto);
			}
			logger.info("Admin retrieved: {}", admin);
		} catch (Exception ex1) {
			logger.info("Exception launched: {}", ex1.getMessage());
			ex1.printStackTrace();
		}
		List<Reclamacion> reclamaciones = new ArrayList<Reclamacion>();
		try {	
            tx.begin();
            logger.info("Obteniendo reclamaciones de admin: {}", adminString);
			try (Query<ReclamacionJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM RECLAMACIONJDO WHERE PRODUCTO_ID_OID IN (SELECT ID FROM PRODUCTOJDO WHERE VENDEDOR = '"+adminString+"')") ) {
				q.setClass(ReclamacionJDO.class);
				List<ReclamacionJDO> results = q.executeList();
				System.out.println("Reclamacion: " + results);
				for (ReclamacionJDO reclamacionJDO : results) {
					Reclamacion reclamacion = new Reclamacion();
					reclamacion.setId(reclamacionJDO.getId());
					reclamacion.setReclamacion(reclamacionJDO.getReclamacion());
					Cliente cliente = new Cliente();
					cliente.setEmail(reclamacionJDO.getCliente().getEmail());
					cliente.setContrasena(reclamacionJDO.getCliente().getContrasena());
					cliente.setNombre(reclamacionJDO.getCliente().getNombre());
					reclamacion.setCliente(cliente);
					Producto producto = new Producto();
					producto.setNombre(reclamacionJDO.getProducto().getNombre());
					producto.setPrecio(reclamacionJDO.getProducto().getPrecio());
					producto.setTipo(reclamacionJDO.getProducto().getTipo());
					producto.setId(reclamacionJDO.getProducto().getId());
					Producto.setIdGeneral(producto.getId());
					reclamacion.setProducto(producto);
					reclamaciones.add(reclamacion);
					logger.info("Product retrieved: {}", reclamacionJDO);
				}
			} catch (Exception ex1) {
				logger.info("Exception launched: {}", ex1.getMessage());
				ex1.printStackTrace();
			}
			tx.commit();
			if (reclamaciones.size() != 0) {	
        		return reclamaciones;
			}else{
				System.out.println("No hay reclamaciones");
				return reclamaciones;
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
	@Path("/resolverReclamacion")
	/** Resuelve una reclamacion eliminandola de la base de datos. */
	public Response resolverReclamacion(Reclamacion reclamacion){
		try{
			tx.begin();
			logger.info("Resolviendo reclamacion del cliente: " + reclamacion.getCliente().getNombre());
			try (Query<ReclamacionJDO> q = pm.newQuery("javax.jdo.query.SQL", "SELECT * FROM RECLAMACIONJDO WHERE ID = '" + reclamacion.getId() + "'")) {
				q.setClass(ReclamacionJDO.class);
				List<ReclamacionJDO> results = q.executeList();
				ReclamacionJDO reclamacionJDO = results.get(0);
				pm.deletePersistent(reclamacionJDO);
				logger.info("Reclamacion resuelta: {}", reclamacionJDO);
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
	/**Metodo que permite modificar el nombre, tipo y precio de un producto. */
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
				productoJDO.setId(producto.getId());
				productoJDO.setNombre(producto.getNombre());
				productoJDO.setTipo(producto.getTipo());
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
	/**Metodo que anade productos comprados al historial. */
	@POST
	@Path("/modifyPedidos")
	public Response modifyPedidos(Producto pedido){
		try{
			logger.info("Modificando pedido: " + pedido);
			tx.begin();
			
				ProductoJDO pedidojdo = null;
				HistorialJDO historialjdo = null;
				try (Query<ProductoJDO> q = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM PRODUCTOJDO WHERE ID = '"+ pedido.getId() +"'")) {
				q.setClass(ProductoJDO.class);
				List<ProductoJDO> results = q.executeList();
				pedidojdo = results.get(0);
					try(Query<HistorialJDO> q3 = pm.newQuery( "javax.jdo.query.SQL","SELECT * FROM HISTORIALJDO where CLIENTE_EMAIL_OID = (SELECT PRODUCTOSHISTORIAL FROM PRODUCTOJDO WHERE ID = '"+pedido.getId()+"')")){
						q3.setClass(HistorialJDO.class);
						List<HistorialJDO> resultsH = q3.executeList();
						historialjdo = resultsH.get(0);
						historialjdo.getProductos().remove(pedidojdo);
					}catch(javax.jdo.JDOObjectNotFoundException ex1){
						logger.info("Exception1 launched: {}", ex1.getMessage());
					}
				pm.makePersistent(pedidojdo);
				pm.makePersistent(historialjdo);
				logger.info("Pedido borrado");
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
