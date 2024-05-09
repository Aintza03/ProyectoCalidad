package com.ikea.app.server;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.UUID;

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
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import javax.jdo.Query;
import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import javax.ws.rs.core.Response.Status;
import categories.PerformanceTest;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Admin;
import com.ikea.app.server.jdo.AdminJDO;
import com.ikea.app.server.jdo.CestaJDO;
import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.server.jdo.ProductoJDO;
import java.util.UUID;
import java.util.*;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.pojo.Historial;
import com.ikea.app.server.jdo.HistorialJDO;
@Category(PerformanceTest.class)
public class ServerPerformanceTest {
    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
    
    private static HttpServer server;
    private WebTarget target;
    protected static int id = 0;
    @Rule
    public JUnitPerfRule perfTestRule = new JUnitPerfRule(new HtmlReportGenerator("target/junitperf/report.html"));

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
            id = 1;
            ProductoJDO productoJDO2 = new ProductoJDO(2,"ProductoTest2","TipoTest2",20);
            id = 2;
            CestaJDO cestaJDO = new CestaJDO(clienteJDO);
            CestaJDO cestaJDO2 = new CestaJDO(clienteJDO2);
            CestaJDO cestaJDO3 = new CestaJDO(clienteJDO3);
            HistorialJDO historial = new HistorialJDO(clienteJDO);
            HistorialJDO historial2 = new HistorialJDO(clienteJDO2);
            HistorialJDO historial3 = new HistorialJDO(clienteJDO3);
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
            pm.makePersistent(historial);
            pm.makePersistent(historial2);
            pm.makePersistent(historial3);
            pm.makePersistent(adminJDO);
            pm.makePersistent(adminJDO2);
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
    

    //POST
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testRegistrarClientePerf() {
        Cliente cliente = new Cliente();
        cliente.setEmail(UUID.randomUUID().toString());
        cliente.setNombre("john");
        cliente.setContrasena("1234");

        Response response = target.path("register")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(cliente, MediaType.APPLICATION_JSON));

        assertEquals(Family.SUCCESSFUL, response.getStatusInfo().getFamily());
        response.close();
    }
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void tesListaProductos(){
        Response response = target.path("listProducts").request().get();
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        List<Producto> product = response.readEntity(listType); 
        assertTrue(product.size() >= 1);
    }
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testCantidadProductos(){
        Response response = target.path("cantidadProductos").request().get();
        int res = response.readEntity(Integer.class);
        assertTrue(res >= 2);
    }
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testGetCesta(){
        String email = "EMAIL";
        Response response = target.path("cesta").queryParam("email", email).request().get();
        assertTrue(response.readEntity(Cesta.class) != null);  
    }
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testListaProductosAdministrador(){
        String admin = "ADMINS2";
        Response response = target.path("listProductsAdmin").queryParam("admin", admin).request().get();
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        List<Producto> product = response.readEntity(listType); 
        assertTrue(product.size() == 1);    
    }
    
    @Test
    @JUnitPerfTest(threads = 1, durationMs = 2000)
    //devido al ID no puede hacerse en multi-thread
    public void anadirProductoAdmin(){
        Producto producto = new Producto();
        producto.setNombre("ProductoTest3");
        producto.setTipo("TipoTest3");
        producto.setId(id+1);
        producto.setPrecio(15);

        Admin admin = new Admin();
        admin.setUsuario("ADMINS");
        admin.setContrasena("1212");
        admin.anadirLista(producto);
        Response response = target.path("anadirProductoAdmin")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(admin, MediaType.APPLICATION_JSON));

        assertEquals(Status.OK.getStatusCode(), response.getStatus()); 
        assertEquals(Status.OK.getStatusCode(), response.getStatus()); 
        Response response2 = target.path("eliminarProducto").request(MediaType.APPLICATION_JSON).post(Entity.entity(producto, MediaType.APPLICATION_JSON));
        assertEquals(Status.OK.getStatusCode(), response2.getStatus());
    }
    @Test
    @JUnitPerfTest(threads = 10, durationMs = 2000)
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
    @JUnitPerfTest(threads = 10, durationMs = 2000)
    public void testGetHistorial(){
        String email = "EMAIL";
        Response response = target.path("historial").queryParam("email", email).request().get();
        assertTrue(response.readEntity(Historial.class) != null);
        
    }
    }