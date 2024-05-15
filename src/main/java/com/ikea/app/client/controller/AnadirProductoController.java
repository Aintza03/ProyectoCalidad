package com.ikea.app.client.controller;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ProcessingException;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Admin;

/**Controller de la ventana AnadirProducto. */
public class AnadirProductoController{
    /**Constructor Vacio. */
	public AnadirProductoController(){
    
    }
	/**Funcion que recoge los datos de la ventana y los manda al servidor para guardarlos en la base de datos.*/
    public boolean anadirProducto(WebTarget webTarget,String nombre, String tipo, double precio, Admin admin) {
		int cantidad = 0;
		try {
            Response response = webTarget.path("cantidadProductos")
                .request(MediaType.APPLICATION_JSON)
                .get();

           
            if (response.getStatus() == Status.OK.getStatusCode()) {
                
                cantidad = response.readEntity(int.class);
            } else {
				System.out.format("Error obtaining product amount. %s%n", response);
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining product list. %s%n", e.getMessage());
        }
		WebTarget WebTargetAnadirProducto = webTarget.path("anadirProductoAdmin");
		Invocation.Builder invocationBuilderAnadirProducto = WebTargetAnadirProducto.request(MediaType.APPLICATION_JSON);
		
		Producto producto = new Producto();
		producto.setId(cantidad+1);
		producto.setNombre(nombre);
		producto.setTipo(tipo);
        producto.setPrecio(precio);
		admin.getLista().add(producto);
		Response responseAnadirProducto = invocationBuilderAnadirProducto.post(Entity.entity(admin, MediaType.APPLICATION_JSON));
		if (responseAnadirProducto.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", responseAnadirProducto.getStatus());
			return false;
		} else {
			ClientMain.getLogger().info("Producto añadido correctamente");
			return true;
		} 
	}
}
