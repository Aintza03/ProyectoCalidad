package com.ikea.app.server.jdo;

import com.ikea.app.server.jdo.ProductoJDO;
import com.ikea.app.server.jdo.ClienteJDO;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
@PersistenceCapable
/**Clase que representa a un producto en la base de datos.
 */
public class ReclamacionJDO{
    @PrimaryKey
    /**Identificador de la reclamacion. */
    int id;
    /**Cliente que realiza la reclamacion. */
    ClienteJDO cliente = null;
    /**Producto que se reclama. */
    ProductoJDO producto = null;
    /**Texto de la reclamacion. */
    String reclamacion = null;
    /**Constructor de la clase. */
    public ReclamacionJDO(int id, String reclamacion, ProductoJDO producto, ClienteJDO cliente) {
		this.id = id;
        this.reclamacion = reclamacion;
        this.producto = producto;
        this.cliente = cliente;
	}

    /**Metodo que devuelve el id de la reclamacion.
     */
    public int getId() {
        return this.id;
    }
	
    /**Metodo que modifica el identificador de la reclamacion.
     */
	public void setId(int id) {
        this.id = id;
    }
    
    /** Metodo que devuelve el cliente de la reclamacion. */
    public ClienteJDO getCliente() {
        return this.cliente;
    }
	
    /** Metodo que modifica el cliente de la reclamacion. */
	public void setCliente(ClienteJDO cliente) {
        this.cliente = cliente;
    }
	
    /** Metodo que devuelve el producto de la reclamacion. */
    public ProductoJDO getProducto() {
		return this.producto;
	}
	
    /** Metodo que modifica el producto de la reclamacion. */
	public void setProducto(ProductoJDO producto) {
		this.producto = producto;
    }

    /** Metodo que devuelve el texto de la reclamacion. */
    public String getReclamacion() {
        return this.reclamacion;
    }

    /** Metodo que modifica el texto de la reclamacion. */
    public void setReclamacion(String reclamacion) {
        this.reclamacion = reclamacion;
    }

    /** Metodo que devuelve la representacion en cadena de la reclamacion. */
	public String toString(){
		return "Cliente: " + this.cliente + " Producto: " + this.producto;
	}	
}