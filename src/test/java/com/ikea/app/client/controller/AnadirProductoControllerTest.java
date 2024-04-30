package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Producto;
import javax.ws.rs.core.Response.Status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.ikea.app.client.controller.AnadirProductoController;
public class AnadirProductoControllerTest{
    private AnadirProductoController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Response responseAnadirProducto = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    
    @Mock
    private Invocation.Builder invocationBuilderAnadirProducto = mock(Invocation.Builder.class);
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new AnadirProductoController();
    }
    @Test
    public void testAnadirProducto() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("cantidadProductos")).thenReturn(webTarget);
        //Cuando se haga una peticion GET devolvera el invocation
        when(webTarget.path("cantidadProductos").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        //Cuando se haga una peticion GET devolvera el response
        when(invocation.get()).thenReturn(response);
        //Cuando se haga una peticion GET devolvera el status OK
        when(response.getStatusInfo()).thenReturn(Status.OK);
        //Cuando se haga una peticion GET devolvera la cantidad de productos
        when(response.readEntity(int.class)).thenReturn(0);
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("anadirProductoAdmin")).thenReturn(webTarget);
        //Cuando se haga una peticion POST devolvera el invocation
        when(webTarget.path("anadirProductoAdmin").request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilderAnadirProducto);
        //Cuando se haga una peticion POST devolvera el response
        when(invocationBuilderAnadirProducto.post(any(Entity.class))).thenReturn(responseAnadirProducto);
        //Cuando se haga una peticion POST devolvera el status OK
        when(responseAnadirProducto.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.OK);
        

        Admin admin = new Admin();
        when(invocationBuilderAnadirProducto.post(Entity.entity(admin, MediaType.APPLICATION_JSON))).thenReturn(responseAnadirProducto);
        
        admin.setLista(new HashSet<Producto>());
        assertTrue(controllerTest.anadirProducto(webTarget,"mesa","madera", 50, admin));
        when(responseAnadirProducto.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertTrue(!controllerTest.anadirProducto(webTarget,"mesa","madera", 50, admin));
       
    }
}