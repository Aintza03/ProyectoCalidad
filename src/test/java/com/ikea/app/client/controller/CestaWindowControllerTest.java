package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import com.ikea.app.pojo.Cesta;
import javax.ws.rs.core.Response.Status;
import static org.mockito.Mockito.*;
import com.ikea.app.client.controller.CestaWindowController;
import com.ikea.app.pojo.Historial;
import com.ikea.app.pojo.Cliente;
import javax.ws.rs.ProcessingException;
public class CestaWindowControllerTest{
    
    private CestaWindowController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    private Cesta cesta = mock(Cesta.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new CestaWindowController();
    }
    @Test
    public void vaciarCestaTest() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("vaciarCesta")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        //Se ejecuta la funcion de vaciarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.vaciarCesta(webTarget, cesta));
    }

    @Test
    public void vaciarCestaTest2() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("vaciarCesta")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        assertFalse(controllerTest.vaciarCesta(webTarget,cesta));
    }

    @Test
    public void borrarProductoDeCestaTest() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("borrarProductoDeCesta")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        //Se ejecuta la funcion de borrarProductoDeCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.borrarProductoDeCesta(webTarget,cesta));
    }

    @Test
    public void borrarProductoDeCestaTest2() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("borrarProductoDeCesta")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        assertFalse(controllerTest.borrarProductoDeCesta(webTarget,cesta));
    }
    @Test
    public void getHistorialTest(){
        Historial historial = new Historial();
        Cliente cliente = new Cliente();

        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        historial.setCliente(cliente);

        when(webTarget.path("historial")).thenReturn(webTarget);
        when(webTarget.queryParam("email", "haizea")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);

        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.OK);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.OK);
        when(response.readEntity(Historial.class)).thenReturn(historial);
        
        assertTrue(controllerTest.getHistorial(webTarget,"haizea").getCliente().getEmail() == "haizea");
    }
    @Test
    public void getHistorialTestNOTFOUND(){
        Historial historial = new Historial();
        Cliente cliente = new Cliente();

        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        historial.setCliente(cliente);

        when(webTarget.path("historial")).thenReturn(webTarget);
        when(webTarget.queryParam("email", "haizea")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);

        when(response.getStatus()).thenReturn(Status.NOT_FOUND.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.NOT_FOUND);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.NOT_FOUND);
        when(response.readEntity(Historial.class)).thenReturn(historial);
        
        assertNull(controllerTest.getHistorial(webTarget,"haizea"));
    }
    @Test
    public void getHistorialTestException(){
        Historial historial = new Historial();
        Cliente cliente = new Cliente();

        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        historial.setCliente(cliente);

        when(webTarget.path("historial")).thenReturn(webTarget);
        when(webTarget.queryParam("email", "haizea")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenThrow(new ProcessingException("Error"));

        assertNull(controllerTest.getHistorial(webTarget,"haizea"));
    }
    @Test
    public void testGuardarHistorial() {

        Historial historial = new Historial();
        Cliente cliente = new Cliente();
        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        historial.setCliente(cliente);
        when(webTarget.path("modifyHistorial")).thenReturn(webTarget);
        when(response.readEntity(Historial.class)).thenReturn(historial);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        //Se ejecuta la funcion de modificarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.guardarHistorial(webTarget,historial));
    }
    @Test
    public void testGuardarHistorialNotFound() {

        Historial historial = new Historial();
        Cliente cliente = new Cliente();
        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        historial.setCliente(cliente);
        when(webTarget.path("modifyHistorial")).thenReturn(webTarget);
        when(response.readEntity(Historial.class)).thenReturn(historial);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        //Se ejecuta la funcion de modificarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertFalse(controllerTest.guardarHistorial(webTarget,historial));
    }
}