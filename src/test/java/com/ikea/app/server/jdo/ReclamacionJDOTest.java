package com.ikea.app.server.jdo;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import java.util.*;

public class ReclamacionJDOTest{
    ReclamacionJDO reclamacionTest;
    @Mock
    ClienteJDO cliente;
    @Mock
    ProductoJDO producto;
    @Mock
    AdminJDO admin;

    @Before
    public void setUp() {
        cliente = new ClienteJDO("test-email", "test-contrasena", "test-nombre");
        producto = new ProductoJDO(1, "test-nombre", "test-tipo", 1.0);
        admin = new AdminJDO("test-usuario", "test-contrasena");
        reclamacionTest = new ReclamacionJDO(1,"LMN", producto, cliente, admin);
    }
    @Test
    public void getId() {
        reclamacionTest.setId(1);
        assertEquals(1, reclamacionTest.getId());
    }
    @Test
    public void getReclamacion() {
        assertEquals("LMN", reclamacionTest.getReclamacion());
    }
    @Test
    public void getCliente() {
        assertEquals(cliente, reclamacionTest.getCliente());
    }
    @Test
    public void getProducto() {
        assertEquals(producto, reclamacionTest.getProducto());
    }
    @Test
    public void getAdmin() {
        assertEquals(admin, reclamacionTest.getAdmin());
    }
    @Test
    public void setReclamacion() {
        reclamacionTest.setReclamacion("LMN2");
        assertEquals("LMN2", reclamacionTest.getReclamacion());
    }
    @Test
    public void setCliente() {
        ClienteJDO cliente = new ClienteJDO("email", "contrasena", "nombre");
        reclamacionTest.setCliente(cliente);
        assertEquals(cliente, reclamacionTest.getCliente());
    }
    @Test
    public void setProducto() {
        ProductoJDO producto = new ProductoJDO(2, "nombre", "tipo", 2.0);
        reclamacionTest.setProducto(producto);
        assertEquals(producto, reclamacionTest.getProducto());
    }
    @Test
    public void setAdmin() {
        AdminJDO admin = new AdminJDO("usuario", "contrasena");
        reclamacionTest.setAdmin(admin);
        assertEquals(admin, reclamacionTest.getAdmin());
    }
    @Test
    public void toStringTest() {
        assertEquals("Cliente: " + cliente + " Producto: " + producto + " Admin: " + admin, reclamacionTest.toString());
    }
}
