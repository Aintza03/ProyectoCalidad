package com.ikea.app.pojo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProductoTest{
    Producto productoTest;
    @Before
    public void setUp() {
        productoTest = new Producto();
        productoTest.setNombre("XYZ");
        productoTest.setPrecio(1.0);
        productoTest.setTipo("XYZ");
    }
    @Test
    public void getId() {
        productoTest.setId(1);
        assertEquals(1, productoTest.getId());
    }
    @Test
    public void setId() {
        productoTest.setId(2);
        assertEquals(2, productoTest.getId());
    }
    @Test
    public void setIdGeneral() {
        Producto.setIdGeneral(2);
        assertEquals(2, Producto.idGeneral);
    }
    @Test
    public void getNombre() {
        assertEquals("XYZ", productoTest.getNombre());
    }
    @Test
    public void getPrecio() {
        assertEquals(1.0, productoTest.getPrecio(),0);
    }
    @Test
    public void getTipo() {
        assertEquals("XYZ", productoTest.getTipo());
    }
    @Test
    public void setNombre() {
        productoTest.setNombre("XYZ2");
        assertEquals("XYZ2", productoTest.getNombre());
    }
    @Test
    public void setPrecio() {
        productoTest.setPrecio(2.0);
        assertEquals(2.0, productoTest.getPrecio(),0);
    }
    @Test
    public void setTipo() {
        productoTest.setTipo("XYZ2");
        assertEquals("XYZ2", productoTest.getTipo());
    }
    @Test
    public void toStringTest() {
        productoTest.setId(1);
        productoTest.setNombre("XYZ");
        productoTest.setPrecio(1.0);
        productoTest.setTipo("XYZ");
        assertEquals("-1: XYZ (XYZ , 1.0€ )", productoTest.toString());
    }
}