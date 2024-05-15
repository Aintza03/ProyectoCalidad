package com.ikea.app.client.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Producto;

import javax.ws.rs.core.Response.Status;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import com.ikea.app.client.controller.EditarProductoController;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Answers;

import javax.ws.rs.client.ClientBuilder;
public class EditarProductoControllerTest{
    private EditarProductoController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Response response = mock(Response.class);

    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);

    @Captor
    private ArgumentCaptor<Entity<Admin>> AdminEntityCaptor;
    
    @Captor
    private ArgumentCaptor<Entity<Producto>> ProductoEntityCaptor;

    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new EditarProductoController();
    }
    @Test
    public void editarProducto() {
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

        when(webTarget.path("modifyProduct")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Producto.class)).thenReturn(producto);

        Producto productoEditado = controllerTest.editarProducto(webTarget, "Mesa", "10", 10, producto);
        assertEquals(productoEditado, producto);
       
    }
    @Test
    public void editarProductoError() {
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

        when(webTarget.path("modifyProduct")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Producto.class)).thenReturn(producto);

        Producto productoEditado = controllerTest.editarProducto(webTarget, "Mesa", "10", 10, producto);
        assertEquals(productoEditado, producto);
    }
}