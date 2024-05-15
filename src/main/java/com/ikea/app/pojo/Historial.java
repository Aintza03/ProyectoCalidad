package com.ikea.app.pojo;
import java.util.*;
/**Clase que guarda el historial de todos los productos que el cliente a comprado. Se define por el cliente a quien
 * pertenece el historial y la lista de los productos comprados. 
 */
public class Historial{
    public Cliente cliente;
    public Set<Producto> productos;
    /**Constructor vacio. */
    public Historial(){
        this.productos = new HashSet<Producto>();
    }
    /**Añade un producto a la lista de productos. */
    public void addProducto(Producto producto){
        this.productos.add(producto);
    }
    /**Cambia la lista por completo. */
    public void setProductos(Set<Producto> productos){
        this.productos = productos;
    }
    /**Obtiene la lista de productos. */
    public Set<Producto> getProductos(){
        return this.productos;
    }
    /**Obtiene el cliente asociado a este historial. */
    public Cliente getCliente(){
        return this.cliente;
    }
    /**Cambia el cliente. */
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    /**Es la forma en la que esta clase aparece por pantalla. */
    public String toString(){
        return "Historial: " + this.cliente.toString() + " " + this.productos.toString();
    }
}