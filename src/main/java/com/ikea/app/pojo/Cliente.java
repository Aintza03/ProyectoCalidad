package com.ikea.app.pojo;
import com.ikea.app.pojo.Producto;
import java.util.ArrayList;
import java.util.List;
public class Cliente{
    private String email;
    private String contrasena;
    private String nombre;
    public Cliente(){

    }
    public String getEmail(){
        return this.email;
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getContrasena(){
        return this.contrasena;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String toString(){
        return "Nombre: " + this.nombre + " Email: " + this.email;
    }
}