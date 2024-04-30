package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;

import javax.ws.rs.core.Response.Status;
import static org.mockito.Mockito.*;

import com.ikea.app.client.controller.ProductListController;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

public class ProductListControllerTest {
    private ProductListController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Captor
    private ArgumentCaptor<Entity<Producto>> ProductoEntityCaptor;
    @Captor
    private ArgumentCaptor<Entity<Cliente>> ClienteEntityCaptor;
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new ProductListController();
    }
    @Test(expected = ProcessingException.class)
    public void testDatosDeProductosException() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("listProducts")).thenThrow(new ProcessingException("Error"));
        when(webTarget.path("listProducts")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        //Se ejecuta la funcion de datosDeProductos pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        List<Producto> productos = controllerTest.datosDeProductos(webTarget);
        assertEquals(0, productos.size());
    }
    /*@Test
    public void testGetCesta() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("cesta")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.queryParam("email", "email")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        //Se ejecuta la funcion de getCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.getCesta(webTarget,"email").getProductos().size() == 0);
    }
    @Test
    public void testModificarCesta() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("modifyCesta")).thenReturn(webTarget);
        when(webTarget.quertParam(cesta, cesta)).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        //Se ejecuta la funcion de modificarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        controllerTest.modificarCesta(webTarget,producto);
        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(ProductoEntityCaptor.capture());
        assertEquals(producto, ProductoEntityCaptor.getValue().getEntity());
    }
*/
}
