package com.ikea.app.server.jdo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.*;
public class CestaJDOTest{
    CestaJDO cestaTest;
    @Mock
    ClienteJDO cliente;
    @Mock
    ProductoJDO producto;
    @Before
    public void setUp() {
        cliente = new ClienteJDO("test-email", "test-contrasena", "test-nombre");
        cestaTest = new CestaJDO(cliente);
    }
    @Test
    public void testGetCliente() {
        assertEquals(cliente, cestaTest.getCliente());
    }
    
    @Test
    public void testSetCliente() {
        ClienteJDO cliente2 = new ClienteJDO("email", "contrasena", "nombre");
        cestaTest.setCliente(cliente2);
        assertEquals(cliente2, cestaTest.getCliente());
    }

    @Test
    public void testGetCesta() {
        assertEquals(new HashSet<ProductoJDO>(), cestaTest.getCesta());
    }
    @Test
    public void testAnadirCesta() {
        cestaTest.AnadirCesta(producto);
        assertEquals(1, cestaTest.getCesta().size());
    }
    @Test
    public void testBorrarProductoDeCesta() {
        cestaTest.AnadirCesta(producto);
        assertEquals(1, cestaTest.getCesta().size());
        cestaTest.borrarProductoDeCesta(producto);
        assertEquals(0, cestaTest.getCesta().size());
    }
    @Test
    public void testSetCesta() {
        Set<ProductoJDO> cesta = new HashSet<ProductoJDO>();
        cesta.add(producto);
        cestaTest.setCesta(cesta);
        assertEquals(cesta, cestaTest.getCesta());
    }
    @Test
    public void testToString() {
        assertEquals("Cliente: " + cliente + " Cesta: " + new HashSet<ProductoJDO>(), cestaTest.toString());
    }
    @Test
    public void testClearCesta() {
        cestaTest.AnadirCesta(producto);
        assertEquals(1, cestaTest.getCesta().size());
        cestaTest.clearCesta();
        assertEquals(0, cestaTest.getCesta().size());
    }
}