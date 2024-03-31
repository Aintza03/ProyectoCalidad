package com.ikea.app.pojo;
import com.ikea.app.pojo.Producto;
import java.util.ArrayList;
import java.util.List;
public class Cliente{
    private String email;
    private String contrasena;
    private String nombre;
    private List<Producto> cesta = new ArrayList<Producto>();
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
    public List<Producto> getCesta(){
        return this.cesta;
    }
    public void setCesta(List<Producto> cesta){
        this.cesta = cesta;
    }
    public void anadirCesta(Producto producto){
        this.cesta.add(producto);
    }
    public String toString(){
        return "Nombre: " + this.nombre + " Email: " + this.email;
    }
}