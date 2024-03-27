package com.ikea.app.server;
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
					producto.setCantidad(productoJDO.getCantidad());
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
		/*users.add(new User(0, "John", "Smith"));
        users.add(new User(1, "Isaac", "Newton"));
        users.add(new User(0, "Albert", "Einstein"));

        Stream<User> stream = users.stream();
        // check if the query parameter was passed in the URL
        if (str != null) {
            stream = stream.filter(user -> user.getSurname().contains(str));
        }

        // sort the stream by the passed parameter
        // as the parameter has a default value there is no need to
        // check if the parameter is null
        if (order == Order.DESC) {
            stream = stream.sorted(Comparator.comparing(User::getSurname).reversed());
        } else {
            stream = stream.sorted(Comparator.comparing(User::getSurname));
        }

        // return the resulting stream as a list
        return stream.collect(Collectors.toList());*/
		}
	}

