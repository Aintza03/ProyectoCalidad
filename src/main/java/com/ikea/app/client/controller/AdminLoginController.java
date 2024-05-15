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
/**Controller de la ventana AdminLogin */
public class AdminLoginController{
	/**Constructor Vacio */	
    public AdminLoginController(){
    
    }
	/**Funcion que busca la información de log del administrador para aceptar o denegar el inicio de sesion */
    public Admin loginAdmin(WebTarget webTarget,String usuario, String contrasena) {
		WebTarget WebTargetLogin = webTarget.path("loginAdmin");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);

		Admin admin = new Admin();
		admin.setUsuario(usuario);
		admin.setContrasena(contrasena);
		Response response = invocationBuilder.post(Entity.entity(admin, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
            return null;
		} else {	
			ClientMain.getLogger().info("Admin iniciado correctamente");
            return response.readEntity(Admin.class);
		}
	}
}

