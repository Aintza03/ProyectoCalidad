package com.ikea.app.pojo;

import java.util.Set;
import java.util.HashSet;
import com.ikea.app.pojo.Producto;
/**Clase a la que pertenecen los fabricantes del producto. El fabricante se define por el nombre de la empresa
 * su contraseña para entrar en la aplicacion y la lista de productos que produce el fabricante.
 */
public class Admin {
    private String usuario;
    private String contrasena;
    private Set<Producto> lista;
    /**Constructor vacio, unicamente inicializa la lista. */
    public Admin() {
        this.lista = new HashSet<Producto>();
    }

    /**Obtiene el nombre de la empresa. */
    public String getUsuario() {
        return this.usuario;
    }
    /**Obtiene la contrasena. */
    public String getContrasena() {
        return this.contrasena;
    }
    /**Obtiene la lista de productos. */
    public Set<Producto> getLista() {
        return this.lista;
    }

    /**Cambia la lista por completo. */
    public void setLista(Set<Producto> lista) {
        this.lista = lista;
    }
    /**Cambia el usuario. */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**Anade productos a la lista. */
    public void anadirLista(Producto producto) {
        lista.add(producto);
    }
    /**Cambia la contrasena. */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**Es la forma en la que esta clase aparece por pantalla, por motivos de seguridad solo muestra el usuario. */
    public String toString() {
        return "Usuario: " + this.usuario;
    }
    
}
