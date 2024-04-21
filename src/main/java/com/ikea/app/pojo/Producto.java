package com.ikea.app.pojo;

public class Producto {
    public static int idGeneral = 0;
    private int id;
    private String nombre;
    private String tipo;
    private double precio;
    private String vendedor;

    public Producto(){
        this.id = idGeneral + 1;
    }
    public int getId(){
        return this.id;
    }
    public static void setIdGeneral(int idGeneral){
        Producto.idGeneral = idGeneral;
    }
    public void setId(int id){
        this.id = id;
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
    public String getVendedor(){
        return this.vendedor;
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
    public void setVendedor(String vendedor){
        this.vendedor = vendedor;
    }
    public String toString(){
        return "-" + this.id + ": " + this.nombre + " (" + this.tipo + " , " + this.precio + "€ )" + this.vendedor;
    }
    
}
