package com.ikea.app.server.jdo;

import java.util.Set;
import java.util.HashSet;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import com.ikea.app.server.jdo.ClienteJDO;
import com.ikea.app.server.jdo.ProductoJDO;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
@PersistenceCapable
public class ProductoJDO {
    @PrimaryKey
    int id = 0;
    String nombre = null;
    String tipo = null;
    double precio = 0.0;
    String vendedor = null;

    public ProductoJDO(int id,String nombre, String tipo, double precio, String vendedor){
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.vendedor = vendedor;
    }
    public int getId(){
        return this.id;
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
    public String getVendedor(){
        return this.vendedor;
    }
    public String toString(){
        return "Nombre: " + this.nombre + "Tipo: " + this.tipo + " Precio: " + this.precio + " Vendedor: " + this.vendedor;
    }
    
}


