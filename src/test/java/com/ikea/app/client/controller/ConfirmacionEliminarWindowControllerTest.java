package com.ikea.app.client.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ikea.app.pojo.Cesta;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.controller.ConfirmacionEliminarWindowController;

public class ConfirmacionEliminarWindowControllerTest{
    
    private ConfirmacionEliminarWindowController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);

    @Mock
    private Cliente cliente = mock(Cliente.class);
    @Mock
    private Cesta cesta = mock(Cesta.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Response responseEncontrar = mock(Response.class);
    @Mock
    Response responseCesta = mock(Response.class);
    @Mock
    Response responseCliente = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Mock
    private Invocation.Builder invocation2 = mock(Invocation.Builder.class);
    @Mock
    private Invocation.Builder invocation3 = mock(Invocation.Builder.class);
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new ConfirmacionEliminarWindowController();
    }

    @Test
    public void borrarClienteTest() {
        Cliente cliente = new Cliente();
        cliente.setEmail("Javier");
        cliente.setNombre("Javier");
        cliente.setContrasena("Javier");
        when(webTarget.path("cesta")).thenReturn(webTarget);
        when(webTarget.path("cesta").queryParam("email", "Javier")).thenReturn(webTarget);
        when(webTarget.path("cesta").queryParam("email", "Javier").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(responseEncontrar);
        when(responseEncontrar.readEntity(Cesta.class)).thenReturn(cesta);
        when(responseEncontrar.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(responseEncontrar.getStatusInfo()).thenReturn(Status.OK);
        when(responseEncontrar.getStatusInfo().toEnum()).thenReturn(Status.OK);
        when(webTarget.path("vaciarCesta")).thenReturn(webTarget);
        when(webTarget.path("vaciarCesta").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(webTarget.path("borrarCliente")).thenReturn(webTarget);
        when(webTarget.path("borrarCliente").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(responseEncontrar);
        assertEquals(3, controllerTest.borrarCliente(webTarget,cliente));
    }
    
    @Test
    public void borrarClienteTestError() {
        Cliente cliente = new Cliente();
        cliente.setEmail("Javier");
        cliente.setNombre("Javier");
        cliente.setContrasena("Javier");
        when(webTarget.path("cesta")).thenReturn(webTarget);
        when(webTarget.path("cesta").queryParam("email", "Javier")).thenReturn(webTarget);
        when(webTarget.path("cesta").queryParam("email", "Javier").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(responseEncontrar);
        when(responseEncontrar.readEntity(Cesta.class)).thenReturn(cesta);
        when(responseEncontrar.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(responseEncontrar.getStatusInfo()).thenReturn(Status.BAD_REQUEST);
        when(responseEncontrar.getStatusInfo().toEnum()).thenReturn(Status.BAD_REQUEST);
        when(webTarget.path("vaciarCesta")).thenReturn(webTarget);
        when(webTarget.path("vaciarCesta").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(webTarget.path("borrarCliente")).thenReturn(webTarget);
        when(webTarget.path("borrarCliente").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(responseEncontrar);
        assertEquals(4, controllerTest.borrarCliente(webTarget,cliente));
    }
    @Test
    public void borrarClienteTestError2() {
        Cliente cliente = new Cliente();
        cliente.setEmail("Javier");
        cliente.setNombre("Javier");
        cliente.setContrasena("Javier");
        when(webTarget.path("cesta")).thenReturn(webTarget);
        when(webTarget.path("cesta").queryParam("email", "Javier")).thenReturn(webTarget);
        when(webTarget.path("cesta").queryParam("email", "Javier").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(responseEncontrar);
        when(responseEncontrar.readEntity(Cesta.class)).thenReturn(cesta);
        when(responseEncontrar.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(responseEncontrar.getStatusInfo()).thenReturn(Status.OK);
        when(responseEncontrar.getStatusInfo().toEnum()).thenReturn(Status.OK);
        when(webTarget.path("vaciarCesta")).thenReturn(webTarget);
        when(webTarget.path("vaciarCesta").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(webTarget.path("borrarCliente")).thenReturn(webTarget);
        when(webTarget.path("borrarCliente").request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(responseEncontrar);
        assertEquals(2, controllerTest.borrarCliente(webTarget,cliente));
    }
    @Test
    public void borrarClienteException(){
        when(webTarget.path("cesta")).thenThrow(new ProcessingException("Error"));
        when(webTarget.path("vaciarCesta")).thenThrow(new ProcessingException("Error"));
        when(webTarget.path("borrarCliente")).thenThrow(new ProcessingException("Error"));
        assertEquals(5, controllerTest.borrarCliente(webTarget,cliente));
    }
}