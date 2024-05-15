package com.ikea.app.pojo;

import java.util.Set;
import java.util.HashSet;
import java.util.HashSet;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cliente;
/**Clase que guarda la cesta del cliente, cada cliente tiene una unica cesta. La cesta guarda el cliente y
 * la lista de productos que el cliente desea comprar.
*/
public class Cesta{
    Cliente cliente = null;
    Set<Producto> cesta = new HashSet<Producto>();
	/**Constructor vacio. */
    public Cesta() {
		
	}
    /**Obtiene el cliente al que le pertenece esta cesta. */
    public Cliente getCliente() {
            return this.cliente;
        }
	/**Cambia el cliente al que le pertenece la cesta. */
	public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
	/**Obtiene la lista de productos que el cliente quiere comprar. */
    public Set<Producto> getCesta() {
		return this.cesta;
	}
	/**Anade un producto a la cesta. */
	public void anadirCesta(Producto producto) {
		cesta.add(producto);
	}
	/**Cambia la lista de productos por completo. */
	public void setCesta(Set<Producto> cesta) {
		this.cesta = cesta;
	}
	/**Vacia la cesta por completo. */
	public void clearCesta() {
		this.cesta.clear();
	}
	/**Es la forma en la que esta clase aparece por pantalla. */
	public String toString(){
		return "Cliente: " + this.cliente + " Cesta: " + this.cesta;
	}	
}