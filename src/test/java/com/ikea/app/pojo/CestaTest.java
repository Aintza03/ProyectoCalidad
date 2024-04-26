package com.ikea.app.pojo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.junit.Assert.assertEquals;
import java.util.*;
public class CestaTest{
    Cesta cestaTest;
    @Mock
    Cliente cliente;
    @Mock
    Producto producto;
    @Before
    public void setUp() {
        cestaTest = new Cesta();
        cestaTest.setCliente(cliente);
    }
    @Test
    public void getCliente() {
        assertEquals(cliente, cestaTest.getCliente());
    }
    @Test
    public void getCesta() {
        assertEquals(new HashSet<Producto>(), cestaTest.getCesta());
    }
    @Test
    public void setCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("cliente");
        cestaTest.setCliente(cliente);
        assertEquals(cliente, cestaTest.getCliente());
    }
    @Test
    public void anadirCesta() {
        producto = new Producto();
        producto.setNombre("producto");
        cestaTest.anadirCesta(producto);
        Set<Producto> cesta = new HashSet<Producto>();
        cesta.add(producto);
        assertEquals(cesta, cestaTest.getCesta());
    }
    @Test
    public void setCesta() {
        Set<Producto> cesta = new HashSet<Producto>();
        cesta.add(new Producto());
        cestaTest.setCesta(cesta);
        assertEquals(cesta, cestaTest.getCesta());
    }
    @Test
    public void clearCesta() {
        cestaTest.clearCesta();
        assertEquals(new HashSet<Producto>(), cestaTest.getCesta());
    }
    @Test
    public void toStringTest() {
        assertEquals("Cliente: " + cliente + " Cesta: " + new HashSet<Producto>(), cestaTest.toString());
    }
}