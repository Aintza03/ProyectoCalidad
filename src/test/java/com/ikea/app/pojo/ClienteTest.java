package com.ikea.app.pojo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ClienteTest{
    Cliente clienteTest;
    @Before
    public void setUp() {
        clienteTest = new Cliente();
        clienteTest.setEmail("XYZ");
        clienteTest.setContrasena("XYZ");
        clienteTest.setNombre("XYZ");
    }
    @Test
    public void getEmail() {
        assertEquals("XYZ", clienteTest.getEmail());
    }
    @Test
    public void getNombre() {
        assertEquals("XYZ", clienteTest.getNombre());
    }
    @Test
    public void getContrasena() {
        assertEquals("XYZ", clienteTest.getContrasena());
    }
    @Test
    public void setEmail() {
        clienteTest.setEmail("XYZ2");
        assertEquals("XYZ2", clienteTest.getEmail());
    }
    @Test
    public void setContrasena() {
        clienteTest.setContrasena("XYZ2");
        assertEquals("XYZ2", clienteTest.getContrasena());
    }
    @Test
    public void setNombre() {
        clienteTest.setNombre("XYZ2");
        assertEquals("XYZ2", clienteTest.getNombre());
    }
    @Test
    public void toStringTest() {
        assertEquals("Nombre: XYZ Email: XYZ", clienteTest.toString());
    }
}