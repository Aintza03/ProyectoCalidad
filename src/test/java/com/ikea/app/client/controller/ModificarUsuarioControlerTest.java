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
import com.ikea.app.client.controller.ModificarUsuarioController;
public class ModificarUsuarioControlerTest{
    private ModificarUsuarioController controllerTest;
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
        controllerTest = new ModificarUsuarioController();
    }
    @Test
    public void testLoginCliente() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("modifyClient")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        //Cuando se especifique el post con cualquier entidad devolvera el response
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        //Se ejecuta la funcion de login pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
                Cliente clientes = new Cliente();
        clientes.setEmail("Alfredo@gmail.com");
        clientes.setContrasena("1234");
        clientes.setNombre("Alfredo");
        Cliente cliente2 = controllerTest.modificarUsuario(webTarget,"Alfredo@gmail.com","1234", "Alfredo");
        assertTrue(cliente2.getEmail().equals(clientes.getEmail()));
        assertTrue(cliente2.getNombre().equals(clientes.getNombre()));
        assertTrue(cliente2.getContrasena().equals(clientes.getContrasena()));
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        cliente2 = controllerTest.modificarUsuario(webTarget,"Alfredo@gmail.com","1234", "Alfredo");
        assertTrue(cliente2.getEmail().equals(clientes.getEmail()));
        assertTrue(cliente2.getNombre().equals(clientes.getNombre()));
        assertTrue(cliente2.getContrasena().equals(clientes.getContrasena()));
       
    }
}