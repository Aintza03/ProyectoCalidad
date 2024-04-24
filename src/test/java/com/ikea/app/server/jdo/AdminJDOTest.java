package com.ikea.app.server.jdo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.*;
public class AdminJDOTest{
    AdminJDO adminTest;
    @Mock
    ProductoJDO producto;
    @Before
    public void setUp() {
        adminTest = new AdminJDO("test-login", "passwd");
    }
    @Test
    public void testGetUsuario() {
        assertEquals("test-login", adminTest.getUsuario());
    }
    @Test
    public void testGetContrasena() {
        assertEquals("passwd", adminTest.getContrasena());
    }
    @Test
    public void testGetLista() {
        assertEquals(new HashSet<ProductoJDO>(), adminTest.getLista());
    }
    @Test
    public void testSetUsuario() {
        adminTest.setUsuario("new-login");
        assertEquals("new-login", adminTest.getUsuario());
    }
    @Test
    public void testSetContrasena() {
        adminTest.setContrasena("newpasswd");
        assertEquals("newpasswd", adminTest.getContrasena());
    }
    @Test
    public void testSetLista() {
        Set<ProductoJDO> lista = new HashSet<ProductoJDO>();
        lista.add(producto);
        adminTest.setLista(lista);
        assertEquals(lista, adminTest.getLista());
    }
    @Test
    public void testAnadirLista() {
        adminTest.anadirLista(producto);
        assertEquals(1, adminTest.getLista().size());
    }
    @Test
    public void testToString() {
        assertEquals("Usuario: test-login", adminTest.toString());
    }
}