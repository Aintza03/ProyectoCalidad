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
public class ReclamacionJDO{
    @PrimaryKey
    ClienteJDO cliente = null;
    ProductoJDO producto = null;
    String reclamacion = null;

    public ReclamacionJDO(String reclamacion, ProductoJDO producto, ClienteJDO cliente) {
		this.reclamacion = reclamacion;
        this.producto = producto;
        this.cliente = cliente;
	}
    
    public ClienteJDO getCliente() {
        return this.cliente;
    }
	
	public void setCliente(ClienteJDO cliente) {
        this.cliente = cliente;
    }
	
    public ProductoJDO getProducto() {
		return this.producto;
	}
	
	public void setProducto(ProductoJDO producto) {
		this.producto = producto;
    }

    public String getReclamacion() {
        return this.reclamacion;
    }

    public void setReclamacion(String reclamacion) {
        this.reclamacion = reclamacion;
    }

	public String toString(){
		return "Cliente: " + this.cliente + " Producto: " + this.producto;
	}	
}