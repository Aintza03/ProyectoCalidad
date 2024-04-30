package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;
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

import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cesta;

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
    @Captor
    private ArgumentCaptor<Entity<Cesta>> CestaEntityCaptor;
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new ProductListController();
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
        when(webTarget.path("listProducts")).thenReturn(webTarget);
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

    @Test
    public void testDatosDeProductosBien() {
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

        when(webTarget.path("listProducts")).thenReturn(webTarget);
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
    public void testDatosDeProductosException() {
    
        when(webTarget.path("listProducts")).thenThrow(new ProcessingException("Error"));
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenThrow(new ProcessingException("2"));
        List<Producto> productos = controllerTest.datosDeProductos(webTarget);
        assertEquals(0, productos.size());

    }
    
    @Test
    public void testGetCestaBien() {
        
        Cesta cesta = new Cesta();
        Cliente cliente = new Cliente();
        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        cesta.setCliente(cliente);

        when(webTarget.path("cesta")).thenReturn(webTarget);
        when(webTarget.queryParam("email", "haizea")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);

        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.OK);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.OK);
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        
        assertTrue(controllerTest.getCesta(webTarget,"haizea").getCliente().getEmail() == "haizea");
    }
    
    @Test
    public void testGetCestaError(){

        Cesta cesta = new Cesta();
        Cliente cliente = new Cliente();
        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        cesta.setCliente(cliente);

        when(webTarget.path("cesta")).thenReturn(webTarget);
        when(webTarget.queryParam("email", "haizea")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.get()).thenReturn(response);

        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.getStatusInfo()).thenReturn(Status.BAD_REQUEST);
        when(response.getStatusInfo().toEnum()).thenReturn(Status.BAD_REQUEST);
        when(response.readEntity(Cesta.class)).thenReturn(cesta);

        assertNull(controllerTest.getCesta(webTarget,"haizea"));
    }
    @Test
    public void testGetCestaException() {

        when(webTarget.path("cesta")).thenThrow(new ProcessingException("Error"));
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenThrow(new ProcessingException("2"));
        assertNull(controllerTest.getCesta(webTarget,"haizea"));

    }
    
    @Test
    public void testModificarCesta() {

        Cesta cesta = new Cesta();
        Cliente cliente = new Cliente();
        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        cesta.setCliente(cliente);
        when(webTarget.path("modifyCesta")).thenReturn(webTarget);
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        //Se ejecuta la funcion de modificarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.modificarCesta(webTarget,cesta));
    }

    @Test
    public void testModificarCestaError() {

        
        Cesta cesta = new Cesta();
        Cliente cliente = new Cliente();
        cliente.setEmail("haizea");
        cliente.setNombre("Haizea");
        cliente.setContrasena("1234");
        cesta.setCliente(cliente);
        when(webTarget.path("modifyCesta")).thenReturn(webTarget);
        when(response.readEntity(Cesta.class)).thenReturn(cesta);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        //Se ejecuta la funcion de modificarCesta pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertFalse(controllerTest.modificarCesta(webTarget,cesta));


    }

}
