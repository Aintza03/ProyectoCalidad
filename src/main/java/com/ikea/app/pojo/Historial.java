package com.ikea.app.pojo;
import java.util.*;
public class Historial{
    public Cliente cliente;
    public Set<Producto> productos;
    public Historial(){
        this.cliente = cliente;
        this.productos = new HashSet<Producto>();
    }
    public void addProducto(Producto producto){
        this.productos.add(producto);
    }
    public void setProductos(Set<Producto> productos){
        this.productos = productos;
    }
    public Set<Producto> getProductos(){
        return this.productos;
    }
    public Cliente getCliente(){
        return this.cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public String toString(){
        return "Historial: " + this.cliente.toString() + " " + this.productos.toString();
    }
}