package com.ikea.app.client.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Producto;

import javax.ws.rs.core.Response.Status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ikea.app.client.controller.ListaPedidosAdminController;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

public class ListaPedidosAdminControllerTest {
    private ListaPedidosAdminController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Mock
    private ProductListAdminController con = mock(ProductListAdminController.class);
    @Captor
    private ArgumentCaptor<Entity<Producto>> ProductoEntityCaptor;
    @Captor
    private ArgumentCaptor<Entity<Admin>> AdminEntityCaptor;
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new ListaPedidosAdminController();
    }

    @Test
    public void testDatosDeProductos(){

        List<Producto> lista = new ArrayList<Producto>();

        Admin admin = new Admin();
        admin.setUsuario("Federico");
        admin.setContrasena("1234");
       

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Mesa");
        producto.setPrecio(10);
        producto.setTipo("10");
        lista.add(producto);

        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("Silla");
        producto2.setPrecio(5);
        producto2.setTipo("5");
        lista.add(producto2);

        when(webTarget.path("listPedidosAdmin")).thenReturn(webTarget);
        when(webTarget.queryParam("admin", "Federico")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.OK);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.OK);
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        when(response.readEntity(listType)).thenReturn(lista);
        List<Producto> listaP = controllerTest.verPedidos(webTarget, "Federico");
        assertEquals(listaP.size(), 2);

    }

    @Test
    public void testDatosDeProductosError() {
        List<Producto> lista = new ArrayList<Producto>();

        Admin admin = new Admin();
        admin.setUsuario("Federico");
        admin.setContrasena("1234");

        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Mesa");
        producto.setPrecio(10);
        producto.setTipo("10");
        lista.add(producto);

        Producto producto2 = new Producto();
        producto2.setId(2);
        producto2.setNombre("Silla");
        producto2.setPrecio(5);
        producto2.setTipo("5");
        lista.add(producto2);

        when(webTarget.path("listPedidosAdmin")).thenReturn(webTarget);
        when(webTarget.queryParam("admin", "Federico")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.BAD_REQUEST);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.BAD_REQUEST);
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        when(response.readEntity(listType)).thenReturn(lista);
        List<Producto> listaP = controllerTest.verPedidos(webTarget, "Federico");
        assertEquals(listaP.size(), 0);
    }

    @Test
    public void testDatosDeProductosException() {
    
        when(webTarget.path("listPedidosAdmin")).thenThrow(new ProcessingException("Error"));
        when(webTarget.queryParam("admin", "Federico")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenThrow(new ProcessingException("2"));
        List<Producto> productos = controllerTest.verPedidos(webTarget, "Federico");
        assertEquals(0, productos.size());

    }

    @Test
    public void testEliminarPedido() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Mesa");
        producto.setPrecio(10);
        producto.setTipo("10");

        when(webTarget.path("modifyPedidos")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        assertTrue(controllerTest.eliminarPedido(webTarget, producto));
    }
    @Test
    public void testEliminarPedidoError() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Mesa");
        producto.setPrecio(10);
        producto.setTipo("10");

        when(webTarget.path("modifyPedidos")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertTrue(!controllerTest.eliminarPedido(webTarget, producto));
    }
    @Test
    public void testEliminarPedidoError2() {
        Producto producto = new Producto();
        producto.setId(1);
        producto.setNombre("Mesa");
        producto.setPrecio(10);
        producto.setTipo("10");

        when(webTarget.path("modifyPedidos")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenThrow(new ProcessingException("Error"));
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertTrue(!controllerTest.eliminarPedido(webTarget, producto));
    }
    
}