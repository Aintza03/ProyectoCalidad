package com.ikea.app.client.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertEquals(1, controllerTest.borrarCliente(webTarget,cliente));

        /*when(responseCesta.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertEquals(controllerTest.borrarCliente(webTarget,cliente), 0);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation3);
        when(invocation3.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Cliente.class)).thenReturn(cliente);
        //Se ejecuta la funcion de borrarProductoDeCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertEquals(controllerTest.borrarCliente(webTarget,cliente), 2);

        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertEquals(controllerTest.borrarCliente(webTarget,cliente), 3);*/
    }
}