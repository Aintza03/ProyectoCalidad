package com.ikea.app.pojo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import java.util.*;

public class ReclamacionTest{
    Reclamacion reclamacionTest;
    @Mock
    Cliente cliente;
    @Mock
    Producto producto;
    @Mock
    Admin admin;

    @Before
    public void setUp() {
        reclamacionTest = new Reclamacion();
        reclamacionTest.setReclamacion("LMN");
        reclamacionTest.setCliente(cliente);
        reclamacionTest.setProducto(producto);
        reclamacionTest.setAdmin(admin);
    }
    @Test
    public void getId() {
        reclamacionTest.setId(1);
        assertEquals(1, reclamacionTest.getId());
    }
    @Test
    public void setId() {
        reclamacionTest.setId(2);
        assertEquals(2, reclamacionTest.getId());
    }
    @Test
    public void setIdGeneral() {
        Reclamacion.setIdGeneral(2);
        assertEquals(2, Reclamacion.idGeneral);
    }
    @Test
    public void getReclamacion() {
        assertEquals("LMN", reclamacionTest.getReclamacion());
    }
    @Test
    public void getCliente() {
        assertEquals(cliente, reclamacionTest.getCliente());
    }
    @Test
    public void getProducto() {
        assertEquals(producto, reclamacionTest.getProducto());
    }
    @Test
    public void getAdmin() {
        assertEquals(admin, reclamacionTest.getAdmin());
    }
    @Test
    public void setReclamacion() {
        reclamacionTest.setReclamacion("LMN2");
        assertEquals("LMN2", reclamacionTest.getReclamacion());
    }
    @Test
    public void setCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("cliente");
        reclamacionTest.setCliente(cliente);
        assertEquals(cliente, reclamacionTest.getCliente());
    }
    @Test
    public void setProducto() {
        Producto producto = new Producto();
        producto.setNombre("producto");
        reclamacionTest.setProducto(producto);
        assertEquals(producto, reclamacionTest.getProducto());
    }
    @Test
    public void setAdmin() {
        Admin admin = new Admin();
        admin.setUsuario("admin");
        reclamacionTest.setAdmin(admin);
        assertEquals(admin, reclamacionTest.getAdmin());
    }
    @Test
    public void toStringTest() {
        reclamacionTest.setId(1);
        reclamacionTest.setReclamacion("LMN");
        Cliente clientes = new Cliente();
        clientes.setEmail("XYZ");
        clientes.setContrasena("XYZ");
        clientes.setNombre("XYZ");
        reclamacionTest.setCliente(clientes);
        Producto productos = new Producto();
        productos.setNombre("ABC");
        productos.setPrecio(1.0);
        productos.setTipo("ABC");
        reclamacionTest.setProducto(productos);
        Admin admins = new Admin();
        admins.setUsuario("HIJ");
        admins.setContrasena("HIJ");
        Set<Producto> lista = new HashSet<Producto>();
        admins.setLista(lista);
        reclamacionTest.setAdmin(admins);
        assertEquals("-" + reclamacionTest.getId() + ": " + " (" + reclamacionTest.getCliente() + " , " + reclamacionTest.getProducto() + ") '" + reclamacionTest.getAdmin() + "'", reclamacionTest.toString());
    }
}