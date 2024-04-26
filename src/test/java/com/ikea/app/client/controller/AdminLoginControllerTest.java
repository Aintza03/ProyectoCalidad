package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
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
import com.ikea.app.pojo.Admin;
import javax.ws.rs.core.Response.Status;
import static org.mockito.Mockito.*;
import com.ikea.app.client.controller.AdminLoginController;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
public class AdminLoginControllerTest{
    private AdminLoginController controllerTest;
    @Mock
    private WebTarget webTarget = mock(WebTarget.class);
    @Mock
    Admin admin = mock(Admin.class);
    @Mock
    private Response response = mock(Response.class);
    @Mock
    private Invocation.Builder invocation = mock(Invocation.Builder.class);
    @Captor
    private ArgumentCaptor<Entity<Admin>> AdminEntityCaptor;
    @Before
    public void setUp() {
        //Obligatorio con Mockito
        MockitoAnnotations.openMocks(this);
        controllerTest = new AdminLoginController();
    }
    @Test
    public void testLoginAdmin() {
        //Cuando se expecifique el path en el web target devolvera el webTarget
        when(webTarget.path("loginAdmin")).thenReturn(webTarget);
        //Cuando se especifique el request(MediaTypeApplication) devolvera la invocation
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(admin.getUsuario()).thenReturn("Alfredo");
        when(admin.getContrasena()).thenReturn("1234");
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Admin.class)).thenReturn(admin);
        //Se ejecuta la funcion de loginAdmin pero nunca se accede a la base de datos, los when especifican que hay que devolver en cada llamada
        assertTrue(controllerTest.loginAdmin(webTarget,"Alfredo","1234").equals(admin));
        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(AdminEntityCaptor.capture());
        assertEquals("Alfredo", AdminEntityCaptor.getValue().getEntity().getUsuario());
        assertEquals("1234", AdminEntityCaptor.getValue().getEntity().getContrasena());
    }
}