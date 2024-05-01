package com.ikea.app.server;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.ws.rs.core.Response;
import javax.jdo.Extent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.server.jdo.ProductoJDO;
import com.ikea.app.server.jdo.AdminJDO;
import com.ikea.app.server.jdo.CestaJDO;
import javax.jdo.JDOObjectNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class ServerTest {
    private Resource resourceTest;
    @Mock
    private PersistenceManager persistenceManager;

    @Mock
    private Transaction transaction;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        try (MockedStatic<JDOHelper> jdoHelper = Mockito.mockStatic(JDOHelper.class)) {
            PersistenceManagerFactory pmf = mock(PersistenceManagerFactory.class);
            jdoHelper.when(() -> JDOHelper.getPersistenceManagerFactory("datanucleus.properties")).thenReturn(pmf);
            
            when(pmf.getPersistenceManager()).thenReturn(persistenceManager);
            when(persistenceManager.currentTransaction()).thenReturn(transaction);
            resourceTest = new Resource();
        }
    }
    @Test
    public void testRegistrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("nombre");
        cliente.setEmail("email");
        cliente.setContrasena("contrasena");
        ClienteJDO clienteJDO = spy(ClienteJDO.class);
        when(persistenceManager.getObjectById(ClienteJDO.class, cliente.getEmail())).thenReturn(clienteJDO);
        Response response = resourceTest.registrarCliente(cliente);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(clienteJDO).setContrasena(passwordCaptor.capture());
        assertEquals("contrasena", passwordCaptor.getValue());
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testRegistrarClienteElseFinally() {
        Cliente cliente = new Cliente();
        cliente.setNombre("nombre");
        cliente.setEmail("email");
        cliente.setContrasena("contrasena");
        when(transaction.isActive()).thenReturn(true);
        ClienteJDO clienteJDO = spy(ClienteJDO.class);
        when(persistenceManager.getObjectById(any(), anyString())).thenThrow(new JDOObjectNotFoundException());
        when(clienteJDO.getContrasena()).thenReturn("contrasena");
        when(clienteJDO.getEmail()).thenReturn("email");
        when(clienteJDO.getNombre()).thenReturn("nombre");
        Response response = resourceTest.registrarCliente(cliente);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testLogin(){
        Cliente cliente = new Cliente();
        cliente.setNombre("nombre");
        cliente.setEmail("email");
        cliente.setContrasena("contrasena");
        @SuppressWarnings("unchecked") Query<ClienteJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "' AND contrasena = '" + cliente.getContrasena() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        ClienteJDO clienteJDO = spy(ClienteJDO.class);
        clienteJDO.setEmail("email");
        clienteJDO.setContrasena("contrasena");
        clienteJDO.setNombre("nombre");
        List<ClienteJDO> clienteJDOS = spy(ArrayList.class);
        clienteJDOS.add(clienteJDO);
        when(query.executeList()).thenReturn(clienteJDOS);
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.loginCliente(cliente);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testLoginContrasenaIncorrecta(){
        Cliente cliente = new Cliente();
        cliente.setNombre("nombre");
        cliente.setEmail("email");
        cliente.setContrasena("contrasena");
        @SuppressWarnings("unchecked") Query<ClienteJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "' AND contrasena = '" + cliente.getContrasena() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        ClienteJDO clienteJDO = spy(ClienteJDO.class);
        clienteJDO.setEmail("email");
        clienteJDO.setContrasena("contrasenya");
        clienteJDO.setNombre("nombre");
        List<ClienteJDO> clienteJDOS = spy(ArrayList.class);
        clienteJDOS.add(clienteJDO);
        when(query.executeList()).thenReturn(clienteJDOS);
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.loginCliente(cliente);
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
    @Test
    public void testLoginException(){
        Cliente cliente = new Cliente();
        cliente.setNombre("nombre");
        cliente.setEmail("email");
        cliente.setContrasena("contrasena");
        @SuppressWarnings("unchecked") Query<ClienteJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "' AND contrasena = '" + cliente.getContrasena() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        ClienteJDO clienteJDO = spy(ClienteJDO.class);
        clienteJDO.setEmail("email");
        clienteJDO.setContrasena("contrasenya");
        clienteJDO.setNombre("nombre");
        List<ClienteJDO> clienteJDOS = spy(ArrayList.class);
        clienteJDOS.add(clienteJDO);
        when(query.executeList()).thenReturn(clienteJDOS);
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.loginCliente(cliente);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }
    
    @Test
    public void testListProducts() {
        List<Producto> productosA = new ArrayList<Producto>();
        Producto producto = new Producto();
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        producto.setId(10);
        productosA.add(producto);
        Producto producto2 = new Producto();
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        producto2.setId(20);
        productosA.add(producto2);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        ProductoJDO productojdo = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        productos.add(productojdo);
        ProductoJDO productojdo2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        productos.add(productojdo2);
        when(query.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(false);
        List response = resourceTest.listaProductos();
        assertEquals(response.toString(), productosA.toString());
    }
    @Test
    public void testListProductsSize() {
        List<Producto> productosA = new ArrayList<Producto>(); 
        @SuppressWarnings("unchecked") Query<ProductoJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        List response = resourceTest.listaProductos();
        assertEquals(response.toString(), productosA.toString());
    } 
    @Test
    public void testCantidad() {
        List<Producto> productosA = new ArrayList<Producto>();
        Producto producto = new Producto();
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        producto.setId(10);
        productosA.add(producto);
        Producto producto2 = new Producto();
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        producto2.setId(20);
        productosA.add(producto2);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        ProductoJDO productojdo = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        productos.add(productojdo);
        ProductoJDO productojdo2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        productos.add(productojdo2);
        when(query.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(false);
        int response = resourceTest.cantidadProductos();
        assertEquals(response, 2);
    }
    @Test
    public void testCantidadException() {
        @SuppressWarnings("unchecked") Query<ProductoJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        
        int response = resourceTest.cantidadProductos();
        assertEquals(response,0);
    }
    @Test
    public void testGetCesta() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '" + cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        cestaJDO.AnadirCesta(productoJDO2);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        when(query.executeList()).thenReturn(cestas);
        when(transaction.isActive()).thenReturn(false);
        Cesta response = resourceTest.getCesta(cliente.getEmail());
        assertEquals(cesta.getCliente().getEmail(), response.getCliente().getEmail());
    }
    @Test
    public void testGetCestaCaso2() {
        String email = "EMAIL";
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '" + email +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Cesta response = resourceTest.getCesta(email);
        assertNull(response);
    }
    @Test
    public void testGetCestaCaso3() {
        String email = "EMAIL";
        doThrow(new RuntimeException("Test exception")).when(transaction).commit();
        when(transaction.isActive()).thenReturn(true);
        Cesta response = resourceTest.getCesta(email);
        assertNull(response);
    }
    @Test
    public void testmodifyCesta() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(cestas);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO WHERE ID = '"+10 +"'";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenReturn(query2);
        String queryStr3 = "SELECT * FROM PRODUCTOJDO WHERE ID = '"+20 +"'";
        when(persistenceManager.newQuery(sql2, queryStr3)).thenReturn(query2);
        when(query2.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.modifyCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testmodifyCestaCaso2() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.modifyCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testmodifyCestaCaso3() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(cestas);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO WHERE ID = '"+10 +"'";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenThrow(new JDOObjectNotFoundException(""));
        String queryStr3 = "SELECT * FROM PRODUCTOJDO WHERE ID = '"+20 +"'";
        when(persistenceManager.newQuery(sql2, queryStr3)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.modifyCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testmodifyCestaCaso4() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        doThrow(new RuntimeException("Test exception")).when(transaction).commit();
        Response response = resourceTest.modifyCesta(cesta);

        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }
    @Test
    public void testvaciarCesta() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        cestaJDO.AnadirCesta(productoJDO2);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        when(query.executeList()).thenReturn(cestas);        
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.vaciarCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testvaciarCestaCaso2() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);        
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));     
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.vaciarCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testvaciarCestaCaso3() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        doThrow(new RuntimeException("Test exception")).when(transaction).commit();
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.vaciarCesta(cesta);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }        
    @Test
    public void testborrarProductoNoTieneQueBorrar() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        cesta.anadirCesta(producto2);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(cestas);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO WHERE PRODUCTOS = '" + cliente.getEmail() + "'";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenReturn(query2);
        when(query2.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.borrarProductoDeCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testborrarProductoCesta() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        cestaJDO.AnadirCesta(productoJDO2);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(cestas);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO WHERE PRODUCTOS = '" + cliente.getEmail() + "'";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenReturn(query2);
        when(query2.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.borrarProductoDeCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testborrarProductoCestaEx1() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        cestaJDO.AnadirCesta(productoJDO2);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(cestas);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO WHERE PRODUCTOS = '" + cliente.getEmail() + "'";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.borrarProductoDeCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testborrarProductoCestaEx2() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        cestaJDO.AnadirCesta(productoJDO2);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.borrarProductoDeCesta(cesta);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testborrarProductoCestaEx3() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Cesta cesta = new Cesta();
        cesta.setCliente(cliente);
        Producto producto = new Producto();
        producto.setId(10);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(20);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        cesta.anadirCesta(producto);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENA", "NOMBRE");
        CestaJDO cestaJDO = new CestaJDO(clienteJDO);
        ProductoJDO productoJDO = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        cestaJDO.AnadirCesta(productoJDO);
        cestaJDO.AnadirCesta(productoJDO2);
        List<CestaJDO> cestas = new ArrayList<CestaJDO>();
        cestas.add(cestaJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        productos.add(productoJDO2);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CESTAJDO WHERE CLIENTE_EMAIL_OID = '"+cliente.getEmail() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(cestas);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO WHERE PRODUCTOS = '" + cliente.getEmail() + "'";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenReturn(query2);
        when(query2.executeList()).thenReturn(productos);
        doThrow(new RuntimeException("Test exception")).when(transaction).commit();
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.borrarProductoDeCesta(cesta);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }
    @Test
    public void testmodificarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        @SuppressWarnings("unchecked") Query<ClienteJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENASIN", "NOMBRESIN");
        List<ClienteJDO> results = new ArrayList<ClienteJDO>();
        results.add(clienteJDO);
        when(query.executeList()).thenReturn(results);        
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.modifyClient(cliente);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testmodificarClienteEx1() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        @SuppressWarnings("unchecked") Query<ClienteJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM CLIENTEJDO WHERE email = '" + cliente.getEmail() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        ClienteJDO clienteJDO = new ClienteJDO("EMAIL", "CONTRASENASIN", "NOMBRESIN");
        List<ClienteJDO> results = new ArrayList<ClienteJDO>();
        results.add(clienteJDO);
        when(query.executeList()).thenReturn(results);        
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.modifyClient(cliente);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testmodificarClienteEx2() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        doThrow(new RuntimeException("Test exception")).when(transaction).commit();
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.modifyClient(cliente);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }
    @Test
    public void testLoginAdmin(){
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO WHERE usuario = '" + admin.getUsuario() + "' AND contrasena = '" + admin.getContrasena() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        AdminJDO adminJDO = spy(AdminJDO.class);
        adminJDO.setUsuario("nombre");
        adminJDO.setContrasena("contrasena");
        adminJDO.anadirLista(new ProductoJDO(10,"nombre", "descripcion", 10.0));
        List<AdminJDO> adminJDOS = spy(ArrayList.class);
        adminJDOS.add(adminJDO);
        when(query.executeList()).thenReturn(adminJDOS);
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.loginAdmin(admin);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testLoginAdminContrasenaIncorrecta(){
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO WHERE usuario = '" + admin.getUsuario() + "' AND contrasena = '" + admin.getContrasena() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        AdminJDO adminJDO = spy(AdminJDO.class);
        adminJDO.setUsuario("nombre");
        adminJDO.setContrasena("contrasenya");
        List<AdminJDO> adminJDOS = spy(ArrayList.class);
        adminJDOS.add(adminJDO);
        when(query.executeList()).thenReturn(adminJDOS);
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.loginAdmin(admin);
        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }
    @Test
    public void testLoginAdminException(){
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO WHERE usuario = '" + admin.getUsuario() + "' AND contrasena = '" + admin.getContrasena() + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        AdminJDO adminJDO = spy(AdminJDO.class);
        adminJDO.setUsuario("nombre");
        adminJDO.setContrasena("contrasenya");
        List<AdminJDO> adminJDOS = spy(ArrayList.class);
        adminJDOS.add(adminJDO);
        when(query.executeList()).thenReturn(adminJDOS);
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.loginAdmin(admin);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }
    @Test
    public void testborrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Extent<CestaJDO> Extent = mock(Extent.class);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        when(persistenceManager.getExtent(CestaJDO.class, true)).thenReturn(Extent);
        when(persistenceManager.newQuery(Extent, "cliente.email == '" + cliente.getEmail() + "'")).thenReturn(query);
        Extent<ClienteJDO> Extent2 = mock(Extent.class);
        @SuppressWarnings("unchecked") Query<ClienteJDO> query2 = mock(Query.class);
        when(persistenceManager.getExtent(ClienteJDO.class,true)).thenReturn(Extent2);
        when(persistenceManager.newQuery(Extent2,"email == '" + cliente.getEmail() + "'")).thenReturn(query2);
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.borrarCliente(cliente);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testborrarClienteEx2_3() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        Extent<CestaJDO> Extent = mock(Extent.class);
        @SuppressWarnings("unchecked") Query<CestaJDO> query = mock(Query.class);
        when(persistenceManager.getExtent(CestaJDO.class, true)).thenReturn(Extent);
        when(persistenceManager.newQuery(Extent, "cliente.email == '" + cliente.getEmail() + "'")).thenThrow(new JDOObjectNotFoundException(""));
        Extent<ClienteJDO> Extent2 = mock(Extent.class);
        @SuppressWarnings("unchecked") Query<ClienteJDO> query2 = mock(Query.class);
        when(persistenceManager.getExtent(ClienteJDO.class,true)).thenReturn(Extent2);
        when(persistenceManager.newQuery(Extent2,"email == '" + cliente.getEmail() + "'")).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.borrarCliente(cliente);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testborrarClienteEx1() {
        Cliente cliente = new Cliente();
        cliente.setEmail("EMAIL");
        cliente.setContrasena("CONTRASENA");
        cliente.setNombre("NOMBRE");
        doThrow(new RuntimeException("Test exception")).when(persistenceManager).getExtent(CestaJDO.class, true);
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.borrarCliente(cliente);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }  
    @Test
    public void testListProductsAdmin() {
        List<Producto> productosA = new ArrayList<Producto>();
        Producto producto = new Producto();
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        producto.setId(10);
        productosA.add(producto);
        Producto producto2 = new Producto();
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        producto2.setId(20);
        productosA.add(producto2);
        String admin = "Admin";
        @SuppressWarnings("unchecked") Query<ProductoJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM PRODUCTOJDO WHERE VENDEDOR = '" + admin + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        ProductoJDO productojdo = new ProductoJDO(10,"nombre", "descripcion", 10.0);
        productos.add(productojdo);
        ProductoJDO productojdo2 = new ProductoJDO(20,"nombre2", "descripcion2", 20.0);
        productos.add(productojdo2);
        when(query.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(false);
        List response = resourceTest.listaProductosAdministrador(admin);
        assertEquals(response.toString(), productosA.toString());
    }
    @Test
    public void testListProductsSizeAdmin() {
        List<Producto> productosA = new ArrayList<Producto>(); 
        String admin = "Admin";
        @SuppressWarnings("unchecked") Query<ProductoJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM PRODUCTOJDO WHERE VENDEDOR = '" + admin + "'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        List response = resourceTest.listaProductosAdministrador(admin);
        assertEquals(response.toString(), productosA.toString());
    } 
    @Test
    public void testanadirProductosAdmin() {
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        admin.anadirLista(producto);
        admin.anadirLista(producto2);
        AdminJDO adminJDO = new AdminJDO("nombre", "contrasena");
        ProductoJDO productoJDO = new ProductoJDO(1,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(2,"nombre2", "descripcion2", 20.0);
        adminJDO.anadirLista(productoJDO);
        List<AdminJDO> admins = new ArrayList<AdminJDO>();
        admins.add(adminJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO where usuario = '"+admin.getUsuario() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(admins);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenReturn(query2);
        when(query2.executeList()).thenReturn(productos);
        when(transaction.isActive()).thenReturn(false);
        Response response = resourceTest.anadirProductoAdmin(admin);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testanadirProductosAdminNull() {
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        AdminJDO adminJDO = new AdminJDO("nombre", "contrasena");
        List<AdminJDO> admins = new ArrayList<AdminJDO>();
        admins.add(adminJDO);
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO where usuario = '"+admin.getUsuario() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(admins);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenReturn(query2);
        when(query2.executeList()).thenReturn(null);

        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.anadirProductoAdmin(admin);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    } 
    @Test
    public void testanadirProductosAdminEx2() {
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        admin.anadirLista(producto);
        admin.anadirLista(producto2);
        AdminJDO adminJDO = new AdminJDO("nombre", "contrasena");
        ProductoJDO productoJDO = new ProductoJDO(1,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(2,"nombre2", "descripcion2", 20.0);
        adminJDO.anadirLista(productoJDO);
        List<AdminJDO> admins = new ArrayList<AdminJDO>();
        admins.add(adminJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO where usuario = '"+admin.getUsuario() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenReturn(query);
        when(query.executeList()).thenReturn(admins);
        @SuppressWarnings("unchecked") Query<ProductoJDO> query2 = mock(Query.class);
        String sql2 = "javax.jdo.query.SQL";
        String queryStr2 = "SELECT * FROM PRODUCTOJDO";
        when(persistenceManager.newQuery(sql2, queryStr2)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.anadirProductoAdmin(admin);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }
    @Test
    public void testanadirProductosAdminEx3() {
        Admin admin = new Admin();
        admin.setUsuario("nombre");
        admin.setContrasena("contrasena");
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("nombre");
        producto.setTipo("descripcion");
        producto.setPrecio(10.0);
        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("nombre2");
        producto2.setTipo("descripcion2");
        producto2.setPrecio(20.0);
        admin.anadirLista(producto);
        admin.anadirLista(producto2);
        AdminJDO adminJDO = new AdminJDO("nombre", "contrasena");
        ProductoJDO productoJDO = new ProductoJDO(1,"nombre", "descripcion", 10.0);
        ProductoJDO productoJDO2 = new ProductoJDO(2,"nombre2", "descripcion2", 20.0);
        adminJDO.anadirLista(productoJDO);
        List<AdminJDO> admins = new ArrayList<AdminJDO>();
        admins.add(adminJDO);
        List<ProductoJDO> productos = new ArrayList<ProductoJDO>();
        productos.add(productoJDO);
        @SuppressWarnings("unchecked") Query<AdminJDO> query = mock(Query.class);
        String sql = "javax.jdo.query.SQL";
        String queryStr = "SELECT * FROM ADMINJDO where usuario = '"+admin.getUsuario() +"'";
        when(persistenceManager.newQuery(sql, queryStr)).thenThrow(new JDOObjectNotFoundException(""));
        when(transaction.isActive()).thenReturn(true);
        Response response = resourceTest.anadirProductoAdmin(admin);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }   
}
