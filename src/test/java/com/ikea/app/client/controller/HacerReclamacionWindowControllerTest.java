package com.ikea.app.client.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import java.util.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.GenericType;
import com.ikea.app.client.controller.CestaWindowController;
import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Admin;
import javax.ws.rs.ProcessingException;

public class HacerReclamacionWindowControllerTest{
    
    private HacerReclamacionWindowController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Reclamacion reclamacion = mock(Reclamacion.class);
    @Mock
    private Cliente cliente = mock(Cliente.class);
    @Mock
    private Producto producto = mock(Producto.class);
    @Mock
    private Admin admin = mock(Admin.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new HacerReclamacionWindowController();
    }
    @Test
    public void hacerReclamacionTest() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("hacerReclamacion")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Reclamacion.class)).thenReturn(reclamacion);
        //Se ejecuta la funcion de vaciarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.hacerReclamacion(webTarget, reclamacion.getReclamacion(), cliente, producto));
    }

    @Test
    public void hacerReclamacionTest2() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("hacerReclamacion")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Reclamacion.class)).thenReturn(reclamacion);
        //Se ejecuta la funcion de vaciarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertFalse(controllerTest.hacerReclamacion(webTarget, reclamacion.getReclamacion(), cliente, producto));
    }

    @Test
    public void sendReclamationTest() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("sendReclamation")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.queryParam("admin", admin.getUsuario())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatusInfo()).thenReturn(Status.OK);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.OK);
        GenericType<List<Reclamacion>> listType = new GenericType<List<Reclamacion>>(){};
        List<Reclamacion> reclamaciones = new ArrayList<Reclamacion>();
        when(response.readEntity(listType)).thenReturn(reclamaciones);
        assertTrue(controllerTest.sendReclamation(webTarget, admin).equals(reclamaciones));
    }
    @Test
    public void sendReclamationTest2() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("sendReclamation")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.queryParam("admin", admin.getUsuario())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);
        when(response.getStatusInfo()).thenReturn(Status.BAD_REQUEST);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.BAD_REQUEST);
        GenericType<List<Reclamacion>> listType = new GenericType<List<Reclamacion>>(){};
        List<Reclamacion> reclamaciones = new ArrayList<Reclamacion>();
        when(response.readEntity(listType)).thenReturn(reclamaciones);
        assertTrue(controllerTest.sendReclamation(webTarget, admin).equals(reclamaciones));
    }
    @Test
    public void sendReclamationTest3() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("sendReclamation")).thenThrow(new ProcessingException("Error"));
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        GenericType<List<Reclamacion>> listType = new GenericType<List<Reclamacion>>(){};
        List<Reclamacion> reclamaciones = new ArrayList<Reclamacion>();
        assertTrue(controllerTest.sendReclamation(webTarget, admin).equals(reclamaciones));
    }
    
    @Test
    public void resolverReclamacionTest() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("resolverReclamacion")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Reclamacion.class)).thenReturn(reclamacion);
        //Se ejecuta la funcion de vaciarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.resolverReclamacion(webTarget, reclamacion));
    }

    @Test
    public void resolverReclamacionTest2() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("resolverReclamacion")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Reclamacion.class)).thenReturn(reclamacion);
        //Se ejecuta la funcion de vaciarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertFalse(controllerTest.resolverReclamacion(webTarget, reclamacion));
    }
}