package com.ikea.app.client.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cesta;

import javax.ws.rs.core.Response.Status;
import static org.mockito.Mockito.*;

import com.ikea.app.client.controller.ProductListController;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

public class ProductListAdminControllerTest {
    /*private ProductListController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Captor
    private ArgumentCaptor<Entity<Producto>> ProductoEntityCaptor;
    @Captor
    private ArgumentCaptor<Entity<Cesta>> CestaEntityCaptor;
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new ProductListAdminController();
    }

    @Test
    public void testDatosDeProductos(){

        ArrayList<Producto> lista = new ArrayList<Producto>();
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

        when(webTarget.path("listProductsAdmin")).thenReturn(webTarget);
        when(webTarget.queryParam("admin", "Federico")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.OK);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.OK);
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        when(response.readEntity(listType)).thenReturn(lista);
        List<Producto> listaP = controllerTest.datosDeProductos(webTarget);
        assertEquals(listaP.size(), 2);

    }

    @Test
    public void testDatosDeProductosError() {
        ArrayList<Producto> lista = new ArrayList<Producto>();
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

        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("listProductsAdmin")).thenReturn(webTarget);
        when(webTarget.queryParam("admin", "Federico")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.BAD_REQUEST);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.BAD_REQUEST);
        GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
        when(response.readEntity(listType)).thenReturn(lista);
        List<Producto> listaP = controllerTest.datosDeProductos(webTarget);
        assertEquals(listaP.size(), 0);
    }
*/
}
