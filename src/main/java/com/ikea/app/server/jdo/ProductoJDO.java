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
/**Clase que representa a un producto en la base de datos.
 */
public class ProductoJDO {
    @PrimaryKey
    /**Identificador del producto. */
    int id = 0;
    /**Nombre del producto. */
    String nombre = null;
    /**Tipo del producto. */
    String tipo = null;
    /**Precio del producto. */
    double precio = 0.0;

    /**Constructor de la clase.
     * @param id Identificador del producto.
     * @param nombre Nombre del producto.
     * @param tipo Tipo del producto.
     * @param precio Precio del producto.
     */
    public ProductoJDO(int id,String nombre, String tipo, double precio){
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }
     /**Metodo que devuelve el id del producto.
     * @return Id del producto.
     */
    public int getId(){
        return this.id;
    }
    /**Metodo que modifica el identificador del producto.
     * @param id Nuevo identificador del producto.
     */
    public void setId(int id){
        this.id = id;
    }
    /**Metodo que devuelve el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre(){
        return this.nombre;
    }
    /**Metodo que devuelve el tipo del producto.
     * @return Tipo del producto.
     */
    public String getTipo(){
        return this.tipo;
    }
    /**Metodo que devuelve el precio del producto.
     * @return Precio del producto.
     */
    public double getPrecio(){
        return this.precio;
    }
    /**Metodo que modifica el nombre del producto.
     * @param nombre Nuevo nombre del producto.
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    /**Metodo que modifica el tipo del producto.
     * @param tipo Nuevo tipo del producto.
     */
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    /**Metodo que modifica el precio del producto.
     * @param precio Nuevo precio del producto.
     */
    public void setPrecio(double precio){
        this.precio = precio;
    }
    /**Metodo que devuelve la representacion en String de Producto.
	 * @return Representacion en String del Producto.
	 */
    public String toString(){
        return "Nombre: " + this.nombre + "Tipo: " + this.tipo + " Precio: " + this.precio;
    }
    
}


