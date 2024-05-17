package com.ikea.app.server.jdo;

import java.util.Set;
import java.util.HashSet;
import java.util.*;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;
import com.ikea.app.server.jdo.ProductoJDO;
@PersistenceCapable
/**Clase que representa a un cliente en la base de datos.
 */
public class ClienteJDO{
    @PrimaryKey
	/**Email del cliente. */
    String email = null;
	/**Contrasena del cliente. */
    String contrasena = null;
	/**Nombre del cliente. */
    String nombre = null;
	/**Constructor de la clase.
	 * @param email Email del cliente.
	 * @param contrasena Contrasena del cliente.
	 * @param nombre Nombre del cliente.
	*/
	public ClienteJDO(String email, String contrasena, String nombre) {
		this.email = email;
		this.contrasena = contrasena;
        this.nombre = nombre;
	}
	/**Constructor de la clase. */
	public ClienteJDO() {
		
	}
	/**Metodo que devuelve el email del cliente. 
	 * @return Email del cliente.
	*/
    public String getEmail() {
		return this.email;
	}
	/**Metodo que devuelve la contrasena del cliente.
	 * @return Contrasena del cliente.
	 */
	public String getContrasena() {
		return this.contrasena;
	}
	/**Metodo que devuelve el nombre del cliente.
	 * @return Nombre del cliente.
	 */
	public String getNombre() {
		return this.nombre;
	}
	/**Metodo que modifica el email del cliente.
	 * @param email Nuevo email del cliente.
	*/
    public void setEmail(String email) {
		this.email = email;
	}
	/**Metodo que modifica la contrasena del cliente.
	 * @param contrasena Nueva contrasena del cliente.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	/**Metodo que modifica el nombre del cliente.
	 * @param nombre Nuevo nombre del cliente.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**Metodo que devuelve la representacion en String del cliente.
	 * @return Representacion en String del cliente.
	 */
	public String toString(){
		return "Nombre: " + this.nombre + " Email: " + this.email;
	}
	
}