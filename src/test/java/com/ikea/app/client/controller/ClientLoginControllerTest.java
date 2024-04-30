package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ikea.app.pojo.Cliente;
import javax.ws.rs.core.Response.Status;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.ikea.app.client.controller.ClientLoginController;
public class ClientLoginControllerTest{
    private ClientLoginController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    Cliente cliente = mock(Cliente.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new ClientLoginController();
    }
    @Test
    public void testLoginClienteTrue() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("login")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        
        when(cliente.getEmail()).thenReturn("Alfredo@gmail.com");
        when(cliente.getContrasena()).thenReturn("1234");
        when(response.readEntity(String.class)).thenReturn("Alfredo");
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Cliente.class)).thenReturn(cliente);
        //Se ejecuta la funcion de login pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        String a = controllerTest.loginCliente(webTarget,"Alfredo@gmail.com","1234");
        String b = "Alfredo";
        assertTrue(a.equals(b));
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertTrue(controllerTest.loginCliente(webTarget,"Alfredo@gmail.com","1234").equals(""));
    }
@Test
    public void testLoginClienteFalse() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("login")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(invocation.post(any(Entity.class))).thenReturn(response);
        
        when(cliente.getEmail()).thenReturn("Alfredo@gmail.com");
        when(cliente.getContrasena()).thenReturn("1234");
        when(response.readEntity(String.class)).thenReturn("Alfredo");
        when(response.readEntity(Cliente.class)).thenReturn(cliente);
        //Se ejecuta la funcion de login pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        
        
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        assertTrue(controllerTest.loginCliente(webTarget,"Alfredo@gmail.com","1234").equals(""));
    }
}