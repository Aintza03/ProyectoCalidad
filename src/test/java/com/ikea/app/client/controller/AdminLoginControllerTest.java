package com.ikea.app.client.controller;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNull;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import com.ikea.app.pojo.Admin;
import javax.ws.rs.core.Response.Status;
import static org.mockito.Mockito.*;
import com.ikea.app.client.controller.AdminLoginController;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Answers;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
public class AdminLoginControllerTest{
    private AdminLoginController controllerTest;
    @Mock
    private Client client;

    @Mock(answer=Answers.RETURNS_DEEP_STUBS)
    private WebTarget webTarget;
    
    @Mock
    Admin admin = mock(Admin.class);
   
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
        try (MockedStatic<ClientBuilder> clientBuilder = Mockito.mockStatic(ClientBuilder.class)) {
            clientBuilder.when(ClientBuilder::newClient).thenReturn(client);
            when(client.target("http://localhost:8080/rest/resource")).thenReturn(webTarget);
        }
        Admin adminResponse = new Admin();
        adminResponse.setUsuario("Alfredo");
        adminResponse.setContrasena("1234");
        Response response = mock(Response.class);
        when(webTarget.path("loginAdmin")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.OK.getStatusCode());
        when(response.readEntity(Admin.class)).thenReturn(adminResponse);
        Admin admin = controllerTest.loginAdmin(webTarget,"Alfredo", "1234");
        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(AdminEntityCaptor.capture());
        assertEquals("Alfredo", AdminEntityCaptor.getValue().getEntity().getUsuario());
        assertEquals("1234", AdminEntityCaptor.getValue().getEntity().getContrasena());
    }
    @Test
    public void testLoginAdminError() {
        try (MockedStatic<ClientBuilder> clientBuilder = Mockito.mockStatic(ClientBuilder.class)) {
            clientBuilder.when(ClientBuilder::newClient).thenReturn(client);
            when(client.target("http://localhost:8080/rest/resource")).thenReturn(webTarget);
        }
        Admin adminResponse = new Admin();
        adminResponse.setUsuario("Alfredo");
        adminResponse.setContrasena("1234");
        Response response = mock(Response.class);
        when(webTarget.path("loginAdmin")).thenReturn(webTarget);
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(invocation);
        when(invocation.post(any(Entity.class))).thenReturn(response);
        when(response.getStatus()).thenReturn(Status.BAD_REQUEST.getStatusCode());
        when(response.readEntity(Admin.class)).thenReturn(adminResponse);
        assertNull(controllerTest.loginAdmin(webTarget,"Alfredo", "1234"));
        verify(webTarget.request(MediaType.APPLICATION_JSON)).post(AdminEntityCaptor.capture());
    }
}