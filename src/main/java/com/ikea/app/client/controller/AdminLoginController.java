package com.ikea.app.client.controller;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.pojo.Admin;
import com.ikea.app.client.ClientMain;
public class AdminLoginController{
	
    public AdminLoginController(){
    
    }

    public boolean loginAdmin(WebTarget webTarget,String usuario, String contrasena) {
		WebTarget WebTargetLogin = webTarget.path("login");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);

		Admin admin = new Admin();
		admin.setUsuario(usuario);
		admin.setContrasena(contrasena);
		Response response = invocationBuilder.post(Entity.entity(admin, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return false;
		} else {	
			ClientMain.getLogger().info("Admin iniciado correctamente");
            return true;
		}
	}
}

