package com.ikea.app.pojo;

public class Producto {
    private String nombre;
    private String tipo;
    private double precio;
    private int cantidad;

    public Producto(){

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
