package com.ikea.app.server.jdo;
import java.util.*;
import javax.jdo.annotations.*;
@PersistenceCapable
/**Clase que representa a un historial en la base de datos.
 */
public class HistorialJDO{
    @PrimaryKey
    /**Cliente al que pertenece el historial. */
    public ClienteJDO cliente;
    @Persistent
    @Column(name = "productosHistorial")
    /**Productos que contiene el historial. */
    public Set<ProductoJDO> productos;
    /**Constructor de la clase.
     * @param cliente Cliente al que pertenece el historial.
     */
    public HistorialJDO(ClienteJDO cliente){
        this.cliente = cliente;
        this.productos = new HashSet<ProductoJDO>();
    }
     /**Metodo para añadir un nuevo producto al historial.
     * @param producto Nuevo producto a añadir.
     */
    public void addProducto(ProductoJDO producto){
        this.productos.add(producto);
    }
     /**Metodo que modifica la lista de productos.
     * @param producto Nueva lista de productos.
     */
    public void setProductos(Set<ProductoJDO> productos){
        this.productos = productos;
    }
    /**Metodo que devuelve los productos que contiene el historial.
     * @return Productos que contiene el historial.
     */
    public Set<ProductoJDO> getProductos(){
        return this.productos;
    }
     /**Metodo que devuelve el cliente.
     * @return Cliente que contiene el historial.
     */
    public ClienteJDO getCliente(){
        return this.cliente;
    }
        /**Metodo que modifica el cliente.
        * @param cliente Nuevo cliente.
        */
    public void setCliente(ClienteJDO cliente){
        this.cliente = cliente;
    }
   
    /**Metodo que devuelve la representacion en String del Historial.
	 * @return Representacion en String del Historial.
	 */
    public String toString(){
        return "Historial: " + this.cliente.toString() + " " + this.productos.toString();
    }
}