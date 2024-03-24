package com.ikea.app.server.jdo;

import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ProductoJDO {
    @PrimaryKey
    String nombre = null;
    String tipo = null;
    double precio = 0.0;
    int cantidad = 0;

    public ProductoJDO(String nombre, String tipo, double precio, int cantidad){
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.cantidad = cantidad;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    public String getTipo(){
        return this.tipo;
    }
    public double getPrecio(){
        return this.precio;
    }
    public int getCantidad(){
        return this.cantidad;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setPrecio(double precio){
        this.precio = precio;
    }
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
    public String toString(){
        return "Nombre: " + this.nombre + "Tipo: " + this.tipo + " Precio: " + this.precio;
    }
    
}

}
