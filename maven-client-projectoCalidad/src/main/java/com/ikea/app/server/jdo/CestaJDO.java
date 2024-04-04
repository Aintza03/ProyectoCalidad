package com.ikea.app.server.jdo;

import java.util.Set;
import java.util.HashSet;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;
import com.ikea.app.server.jdo.ProductoJDO;
import com.ikea.app.server.jdo.ClienteJDO;
import javax.jdo.annotations.Column;
@PersistenceCapable
public class CestaJDO{
    @PrimaryKey
    ClienteJDO cliente = null;
	@Persistent
	@Column(name = "productos")
    Set<ProductoJDO> cesta;

    public CestaJDO(ClienteJDO cliente) {
		this.cliente = cliente;
		this.cesta = new HashSet<ProductoJDO>();
	}
    
    public ClienteJDO getCliente() {
            return this.cliente;
        }
	
	public void setCliente(ClienteJDO cliente) {
        this.cliente = cliente;
    }
    public Set<ProductoJDO> getCesta() {
		return this.cesta;
	}
	
	public void AnadirCesta(ProductoJDO producto) {
		cesta.add(producto);
	}
	public void borrarProductoDeCesta (ProductoJDO producto) {
		cesta.remove(producto);
	}
	
	public void setCesta(Set<ProductoJDO> cesta) {
		this.cesta = cesta;
	}
	
	public String toString(){
		return "Cliente: " + this.cliente + " Cesta: " + this.cesta;
	}

	public void clearCesta() {
		this.cesta.clear();
	}
	
}