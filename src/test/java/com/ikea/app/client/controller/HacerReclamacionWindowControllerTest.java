package com.ikea.app.client.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

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
    public void sendReclamationTest3() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("sendReclamation")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.queryParam("admin", reclamacion.getAdmin().getUsuario())).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Reclamacion.class)).thenReturn(reclamacion);
        //Se ejecuta la funcion de vaciarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertNull(controllerTest.sendReclamation(webTarget, admin));
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