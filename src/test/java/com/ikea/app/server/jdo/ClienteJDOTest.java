package com.ikea.app.server.jdo;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
public class ClienteJDOTest{
    ClienteJDO clienteTest;
    @Before
    public void setUp() {
        clienteTest = new ClienteJDO("test-email", "test-contrasena", "test-nombre");
    }
    @Test
    public void testGetEmail() {
        assertEquals("test-email", clienteTest.getEmail());
    }
    @Test
    public void testGetContrasena() {
        assertEquals("test-contrasena", clienteTest.getContrasena());
    }
    @Test
    public void testGetNombre() {
        assertEquals("test-nombre", clienteTest.getNombre());
    }
    @Test
    public void testSetEmail() {
        clienteTest.setEmail("new-email");
        assertEquals("new-email", clienteTest.getEmail());
    }
    @Test
    public void testSetContrasena() {
        clienteTest.setContrasena("new-contrasena");
        assertEquals("new-contrasena", clienteTest.getContrasena());
    }
    @Test
    public void testSetNombre() {
        clienteTest.setNombre("new-nombre");
        assertEquals("new-nombre", clienteTest.getNombre());
    }
    @Test
    public void testToString() {
        assertEquals("Nombre: test-nombre Email: test-email", clienteTest.toString());
    }
}