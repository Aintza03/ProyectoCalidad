package com.ikea.app.pojo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.*;
public class HistorialTest {
    Historial historialTest;
    @Mock
    Cliente cliente;
    @Mock
    Producto producto;
    @Before
    public void setUp() {
        cliente = new Cliente();
        cliente.setEmail("test-login");
        cliente.setContrasena("passwd");
        cliente.setNombre("nombre");
        historialTest = new Historial();
        historialTest.setCliente(cliente);

    }

    @Test
    public void testAddProducto() {
        historialTest.addProducto(producto);
        assertEquals(1, historialTest.getProductos().size());
    }
    @Test
    public void testSetProductos() {
        Set<Producto> productos = new HashSet<Producto>();
        productos.add(producto);
        historialTest.setProductos(productos);
        assertEquals(productos, historialTest.getProductos());
    }
    @Test
    public void testGetProductos() {
        Set<Producto> productos = new HashSet<Producto>();
        productos.add(producto);
        historialTest.setProductos(productos);
        assertEquals(productos, historialTest.getProductos());
    }
    @Test
    public void testGetCliente() {
        assertEquals(cliente, historialTest.getCliente());
    }
    @Test
    public void testSetCliente() {
        Cliente cliente2 = new Cliente();
        cliente2.setEmail("test-login2");
        cliente2.setContrasena("passwd2");
        cliente2.setNombre("nombre2");
        historialTest.setCliente(cliente2);
        assertEquals(cliente2, historialTest.getCliente());
    }
    @Test
    public void testToString() {
        assertEquals("Historial: " + this.cliente.toString() + " " + historialTest.getProductos().toString(), historialTest.toString());
    }
}