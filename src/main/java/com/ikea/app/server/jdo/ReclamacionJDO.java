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
    int id;
    ClienteJDO cliente = null;
    ProductoJDO producto = null;
    String reclamacion = null;
    AdminJDO admin = null;

    public ReclamacionJDO(int id, String reclamacion, ProductoJDO producto, ClienteJDO cliente, AdminJDO admin) {
		this.id = id;
        this.reclamacion = reclamacion;
        this.producto = producto;
        this.cliente = cliente;
        this.admin = admin;
	}

    public int getId() {
        return this.id;
    }
	
	public void setId(int id) {
        this.id = id;
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

    public AdminJDO getAdmin() {
        return this.admin;
    }

    public void setAdmin(AdminJDO admin) {
        this.admin = admin;
    }

	public String toString(){
		return "Cliente: " + this.cliente + " Producto: " + this.producto + " Admin: " + this.admin;
	}	
}