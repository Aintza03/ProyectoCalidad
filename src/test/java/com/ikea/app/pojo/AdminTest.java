package com.ikea.app.pojo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import java.util.*;
public class AdminTest{
    Admin adminTest;
    @Mock
    Producto producto;
    @Before
    public void setUp() {
        adminTest = new Admin();
        adminTest.setUsuario("admin");
        adminTest.setContrasena("admin");

    }
    @Test
    public void getUsuario() {
        assertEquals("admin", adminTest.getUsuario());
    }
    @Test
    public void getContrasena() {
        assertEquals("admin", adminTest.getContrasena());
    }
    @Test
    public void getLista() {
        assertEquals(new HashSet<Producto>(), adminTest.getLista());
    }
    @Test
    public void setLista() {
        Set<Producto> lista = new HashSet<Producto>();
        lista.add(new Producto());
        adminTest.setLista(lista);
        assertEquals(lista, adminTest.getLista());
    }
    @Test
    public void setUsuario() {
        adminTest.setUsuario("admin2");
        assertEquals("admin2", adminTest.getUsuario());
    }
    @Test
    public void anadirLista() {
        producto = new Producto();
        adminTest.anadirLista(producto);
        Set<Producto> lista = new HashSet<Producto>();
        lista.add(producto);
        assertEquals(lista, adminTest.getLista());
    }
    @Test
    public void setContrasena() {
        adminTest.setContrasena("admin2");
        assertEquals("admin2", adminTest.getContrasena());
    }
    @Test
    public void toStringTest() {
        assertEquals("Usuario: admin", adminTest.toString());
    }
}