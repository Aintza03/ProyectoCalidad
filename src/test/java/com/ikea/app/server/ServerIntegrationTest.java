package com.ikea.app.server;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.UUID;
import java.util.*;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.GenericType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import categories.IntegrationTest;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.pojo.Admin;
import com.ikea.app.server.jdo.AdminJDO;
import com.ikea.app.server.jdo.CestaJDO;
import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.server.jdo.ProductoJDO;
import javax.jdo.Query;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.pojo.Historial;
import com.ikea.app.server.jdo.HistorialJDO;
import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.server.jdo.ReclamacionJDO;
@Category(IntegrationTest.class)
public class ServerIntegrationTest {

    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    
    private static HttpServer server;
    private WebTarget target;

    @BeforeClass
    public static void prepareTests() throws Exception {
        // start the server
        server = Main.startServer();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "1234", "john");
            ClienteJDO clienteJDO2 = new ClienteJDO("EMAILS","johnny","1234457");
            ClienteJDO clienteJDO3 = new ClienteJDO("EMAIL2","1234","john2");
            ProductoJDO productoJDO = new ProductoJDO(1,"ProductoTest","TipoTest",15);
            ProductoJDO productoJDO2 = new ProductoJDO(2,"ProductoTest2","TipoTest2",20);
            CestaJDO cestaJDO = new CestaJDO(clienteJDO);
            CestaJDO cestaJDO2 = new CestaJDO(clienteJDO2);
            CestaJDO cestaJDO3 = new CestaJDO(clienteJDO3);
            HistorialJDO historialJDO = new HistorialJDO(clienteJDO);
            HistorialJDO historialJDO2 = new HistorialJDO(clienteJDO2);
            HistorialJDO historialJDO3 = new HistorialJDO(clienteJDO3);
            ReclamacionJDO reclamacionJDO = new ReclamacionJDO(1,"ReclamacionTest",productoJDO2,clienteJDO2);
            AdminJDO adminJDO = new AdminJDO("ADMINS","1212");
            AdminJDO adminJDO2 = new AdminJDO("ADMINS2","12121");
            cestaJDO.AnadirCesta(productoJDO2);
            adminJDO2.anadirLista(productoJDO2);
            pm.makePersistent(productoJDO);
            pm.makePersistent(productoJDO2);
            pm.makePersistent(clienteJDO);
            pm.makePersistent(clienteJDO2);
            pm.makePersistent(clienteJDO3);
            pm.makePersistent(cestaJDO);
            pm.makePersistent(cestaJDO2);
            pm.makePersistent(cestaJDO3);
            pm.makePersistent(historialJDO);
            pm.makePersistent(historialJDO2);
            pm.makePersistent(historialJDO3);
            pm.makePersistent(adminJDO);
            pm.makePersistent(adminJDO2);
            pm.makePersistent(reclamacionJDO);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    @Before
    public void setUp() {
        // create the client
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI).path("resource");
    }

    @AfterClass
    public static void tearDownServer() throws Exception {
        server.shutdown();

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            try (Query<ReclamacionJDO> q = pm.newQuery(ReclamacionJDO.class)) {
                long numberInstancesDeleted = q.deletePersistentAll();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tx.begin();
            try (Query<ProductoJDO> q1 = pm.newQuery(ProductoJDO.class)) {
                long numberInstancesDeleted1 = q1.deletePersistentAll();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tx.begin();
            try (Query<CestaJDO> q = pm.newQuery(CestaJDO.class)) {
                long numberInstancesDeleted = q.deletePersistentAll();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tx.begin();
            try (Query<HistorialJDO> q = pm.newQuery(HistorialJDO.class)) {
                long numberInstancesDeleted = q.deletePersistentAll();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tx.begin();
            try (Query<ClienteJDO> q = pm.newQuery(ClienteJDO.class)) {
                long numberInstancesDeleted = q.deletePersistentAll();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            tx.begin();
            try (Query<AdminJDO> q = pm.newQuery(AdminJDO.class)) {
                long numberInstancesDeleted = q.deletePersistentAll();

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
    @Test
    public void testRegistrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAILRegister");
        cliente.setNombre("johnRegister");
        cliente.setContrasena("12345678910");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLoginCliente(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("john");
        cliente.setContrasena("1234");

        Response response = target.path("login")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

        assertEquals(cliente.getNombre(), response.readEntity(String.class));       
    }

    @Test
    public void tesListaProductos(){
        Response response = target.path("listProducts").request().get();
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        List<Producto> product = response.readEntity(listType); 
        assertTrue(product.size() == 3 || product.size() == 2 || product.size() == 1);
    }

    @Test
    public void testCantidadProductos(){
        Response response = target.path("cantidadProductos").request().get();
        int res = response.readEntity(Integer.class);
        assertTrue(res == 3 || res == 2);
    }
    
    @Test
    public void testGetCesta(){
        String email = "EMAIL";
        Response response = target.path("cesta").queryParam("email", email).request().get();
        assertTrue(response.readEntity(Cesta.class) != null);
        
    }

    @Test
    public void testModifyCesta(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("john");
        cliente.setContrasena("1234");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setNombre("ProductoTest");
        producto.setTipo("TipoTest");
        producto.setId(1);
        producto.setPrecio(15);
        cesta.anadirCesta(producto);
        Response response = target.path("modifyCesta")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cesta, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());       
    }
    @Test
    public void testVaciarCesta(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("john");
        cliente.setContrasena("1234");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Response response = target.path("vaciarCesta")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cesta, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
    @Test
    public void testBorrarProductoCesta(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("john");
        cliente.setContrasena("1234");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Response response = target.path("borrarProductoDeCesta")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cesta, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }
    @Test
    public void testModifyClient(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAILS");
        cliente.setNombre("johnny");
        cliente.setContrasena("12344457");
        Response response = target.path("modifyClient")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());       
    }

    @Test
    public void testLoginAdmin(){
        Admin admin = new Admin();
        admin.setUsuario("ADMINS");
        admin.setContrasena("1212");

        Response response = target.path("loginAdmin")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(admin, MediaType.APPLICATION_JSON));
        Admin admin2 = response.readEntity(Admin.class);
        assertEquals(admin.getUsuario(), admin2.getUsuario());
        assertEquals(admin.getContrasena(),admin2.getContrasena());
    }

    @Test
    public void testBorrarCliente(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL2");
        cliente.setNombre("john2");
        cliente.setContrasena("1234");
        Response response = target.path("borrarCliente")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testListaProductosAdministrador(){
        String admin = "ADMINS2";
        Response response = target.path("listProductsAdmin").queryParam("admin", admin).request().get();
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        List<Producto> product = response.readEntity(listType); 
        assertTrue(product.size() == 1);    
    }
    @Test
    public void anadirProductoAdmin(){
        Producto producto = new Producto();
        producto.setNombre("ProductoTest3");
        producto.setTipo("TipoTest3");
        producto.setId(3);
        producto.setPrecio(15);
        
        Admin admin = new Admin();
        admin.setUsuario("ADMINS");
        admin.setContrasena("1212");
        admin.anadirLista(producto);
        Response response = target.path("anadirProductoAdmin")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(admin, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus()); 
        Response response2 = target.path("eliminarProducto").request(MediaType.APPLICATION_JSON).post(Entity.entity(producto, MediaType.APPLICATION_JSON));
        assertEquals(Status.OK.getStatusCode(), response2.getStatus());
    }
    @Test
    public void testModifyHistorial(){
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("john");
        cliente.setContrasena("1234");
        Historial historial = new Historial();
        historial.setCliente(cliente);
        Producto producto = new Producto();
        producto.setNombre("ProductoTest");
        producto.setTipo("TipoTest");
        producto.setId(1);
        producto.setPrecio(15);
        historial.addProducto(producto);
        Response response = target.path("modifyHistorial")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(historial, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());       
    }
    @Test
    public void testGetHistorial(){
        String email = "EMAIL";
        Response response = target.path("historial").queryParam("email", email).request().get();
        assertTrue(response.readEntity(Historial.class) != null);
        
    } 
    //NUEVOS TESTS
    @Test
    public void testmakeReclamacion(){
        Reclamacion reclamacion = new Reclamacion();
        reclamacion.setId(2);
        reclamacion.setReclamacion("ReclamacionTest");
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("johnRegister");
        cliente.setContrasena("12345678910");
        reclamacion.setCliente(cliente);
        Producto producto = new Producto();
        producto.setNombre("ProductoTest");
        producto.setTipo("TipoTest");
        producto.setId(1);
        producto.setPrecio(15);
        reclamacion.setProducto(producto);
        Response response = target.path("hacerReclamacion")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(reclamacion, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
    
    }
    @Test
    public void testsendReclamation(){
        String admins = "ADMINS2";
        Response response = target.path("sendReclamation").queryParam("admin", admins).request().get();
        GenericType<List<Reclamacion>> listType = new GenericType<List<Reclamacion>>(){};
        List<Reclamacion> reclamacion = response.readEntity(listType); 
        
        assertTrue(reclamacion.size() == 1);
    }
    @Test
    public void testresolverReclamacion(){
        Reclamacion reclamacion = new Reclamacion();
        reclamacion.setId(1);
        reclamacion.setReclamacion("ReclamacionTest");
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAILS");
        cliente.setNombre("johnny");
        cliente.setContrasena("12345678910");
        reclamacion.setCliente(cliente);
        Producto producto = new Producto();
        producto.setNombre("ProductoTest2");
        producto.setTipo("TipoTest2");
        producto.setId(2);
        producto.setPrecio(20);
        reclamacion.setProducto(producto);
        Response response = target.path("resolverReclamacion")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(reclamacion, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());
        response = target.path("hacerReclamacion").request(MediaType.APPLICATION_JSON).post(Entity.entity(reclamacion, MediaType.APPLICATION_JSON));
    }
    @Test
    public void testModifyProducts(){
        Producto producto = new Producto();
        producto.setNombre("ProductoTestModificado");
        producto.setTipo("TipoTest");
        producto.setId(1);
        producto.setPrecio(15);
        Response response = target.path("modifyProduct")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(producto, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());       
    }
    @Test
    public void testModifyPedidos(){
        Historial historial = new Historial();
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setNombre("john");
        cliente.setContrasena("1234");
        historial.setCliente(cliente);
        Producto producto = new Producto();
        producto.setNombre("ProductoTest");
        producto.setTipo("TipoTest");
        producto.setId(1);
        producto.setPrecio(15);
        historial.addProducto(producto);
        Response response = target.path("modifyHistorial")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(historial, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus());       
    }
}