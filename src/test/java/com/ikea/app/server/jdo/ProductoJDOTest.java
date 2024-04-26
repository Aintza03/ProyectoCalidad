package com.ikea.app.server.jdo;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
public class ProductoJDOTest{
    ProductoJDO productoTest;
    @Before
    public void setUp() {
        productoTest = new ProductoJDO(1, "test-nombre", "test-tipo", 1.0);
    }
    @Test
    public void testGetId() {
        assertEquals(1, productoTest.getId());
    }
    @Test
    public void testGetNombre() {
        assertEquals("test-nombre", productoTest.getNombre());
    }
    @Test
    public void testGetTipo() {
        assertEquals("test-tipo", productoTest.getTipo());
    }
    @Test
    public void testGetPrecio() {
        assertEquals(1.0, productoTest.getPrecio(), 0.0);
    }
    @Test
    public void testSetId() {
        productoTest.setId(2);
        assertEquals(2, productoTest.getId());
    }
    @Test
    public void testSetNombre() {
        productoTest.setNombre("new-nombre");
        assertEquals("new-nombre", productoTest.getNombre());
    }
    @Test
    public void testSetTipo() {
        productoTest.setTipo("new-tipo");
        assertEquals("new-tipo", productoTest.getTipo());
    }
    @Test
    public void testSetPrecio() {
        productoTest.setPrecio(2.0);
        assertEquals(2.0, productoTest.getPrecio(), 0.0);
    }
    @Test
    public void testToString() {
        assertEquals("Nombre: test-nombreTipo: test-tipo Precio: 1.0", productoTest.toString());
    }
    
}