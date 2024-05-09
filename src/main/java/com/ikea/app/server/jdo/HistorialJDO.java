package com.ikea.app.server.jdo;
import java.util.*;
import javax.jdo.annotations.*;
@PersistenceCapable
public class HistorialJDO{
    @PrimaryKey
    public ClienteJDO cliente;
    @Persistent
    @Column(name = "productosHistorial")
    public Set<ProductoJDO> productos;
    public HistorialJDO(ClienteJDO cliente){
        this.cliente = cliente;
        this.productos = new HashSet<ProductoJDO>();
    }
    public void addProducto(ProductoJDO producto){
        this.productos.add(producto);
    }
    public void setProductos(Set<ProductoJDO> productos){
        this.productos = productos;
    }
    public Set<ProductoJDO> getProductos(){
        return this.productos;
    }
    public ClienteJDO getCliente(){
        return this.cliente;
    }
    public void setCliente(ClienteJDO cliente){
        this.cliente = cliente;
    }
    public String toString(){
        return "Historial: " + this.cliente.toString() + " " + this.productos.toString();
    }
}