package com.ikea.app.pojo;
import com.ikea.app.pojo.Producto;
import java.util.ArrayList;
import java.util.List;
/**Clase a la que pertenece el usuario que se dedica a comprar los productos. El cliente se define por su
 * correo electronico, contrasena de la aplicacion y su nombre.
 */
public class Cliente{
    private String email;
    private String contrasena;
    private String nombre;
    /**Constructor vacio. */
    public Cliente(){

    }
    /**Obtiene el email del usuario. */
    public String getEmail(){
        return this.email;
    }
    /**Obtiene el nombre del usuario. */
    public String getNombre(){
        return this.nombre;
    }
    /**Obtiene la contrasena. */
    public String getContrasena(){
        return this.contrasena;
    }
    /**Cambia el email. */
    public void setEmail(String email){
        this.email = email;
    }
    /**Cambia la contrasena. */
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    /**Cambia el nombre. */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    /**Es la forma en la que esta clase aparece por pantalla, por motivos de seguridad no muestra la contrasena. */
    public String toString(){
        return "Nombre: " + this.nombre + " Email: " + this.email;
    }
}