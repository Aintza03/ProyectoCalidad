package com.ikea.app.pojo;

import java.util.Set;
import java.util.HashSet;
import java.util.HashSet;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cliente;
public class Cesta{
    Cliente cliente = null;
    Set<Producto> cesta = new HashSet<Producto>();

    public Cesta() {
		
	}
    
    public Cliente getCliente() {
            return this.cliente;
        }
	
	public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Set<Producto> getCesta() {
		return this.cesta;
	}
	
	public void AnadirCesta(Producto producto) {
		cesta.add(producto);
	}
	public void setCesta(Set<Producto> cesta) {
		this.cesta = cesta;
	}
	
	public String toString(){
		return "Cliente: " + this.cliente + " Cesta: " + this.cesta;
	}	
}