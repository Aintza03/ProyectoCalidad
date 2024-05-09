package com.ikea.app.server.jdo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.*;
public class HistorialJDOTest {
    HistorialJDO historialTest;
    @Mock
    ClienteJDO cliente;
    @Mock
    ProductoJDO producto;
    @Before
    public void setUp() {
        cliente = new ClienteJDO("test-login", "passwd", "nombre");
        historialTest = new HistorialJDO(cliente);
    }

    @Test
    public void testAddProducto() {
        historialTest.addProducto(producto);
        assertEquals(1, historialTest.getProductos().size());
    }
    @Test
    public void testSetProductos() {
        Set<ProductoJDO> productos = new HashSet<ProductoJDO>();
        productos.add(producto);
        historialTest.setProductos(productos);
        assertEquals(productos, historialTest.getProductos());
    }
    @Test
    public void testGetProductos() {
        Set<ProductoJDO> productos = new HashSet<ProductoJDO>();
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
        ClienteJDO cliente2 = new ClienteJDO("test-login2", "passwd2", "nombre2");
        historialTest.setCliente(cliente2);
        assertEquals(cliente2, historialTest.getCliente());
    }
    @Test
    public void testToString() {
        assertEquals("Historial: " + this.cliente.toString() + " " + historialTest.getProductos().toString(), historialTest.toString());
    }
}