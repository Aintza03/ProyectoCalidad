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
/**Clase que representa a una cesta en la base de datos.
 */
public class CestaJDO{
    @PrimaryKey
	/**Cliente al que pertenece la cesta. */
    ClienteJDO cliente = null;
	@Persistent
	@Column(name = "productos")
	/**Productos que contiene la cesta. */
    Set<ProductoJDO> cesta;

	/**Constructor de la clase. 
	 * @param cliente Cliente al que pertenece la cesta.
	*/
    public CestaJDO(ClienteJDO cliente) {
		this.cliente = cliente;
		this.cesta = new HashSet<ProductoJDO>();
	}
    /**Metodo que devuelve el cliente al que pertenece la cesta.
	 * @return Cliente al que pertenece la cesta.
	 */
    public ClienteJDO getCliente() {
            return this.cliente;
        }
	/**Metodo que modifica el cliente al que pertenece la cesta.
	 * @param cliente Nuevo cliente al que pertenece la cesta.
	 */
	public void setCliente(ClienteJDO cliente) {
        this.cliente = cliente;
    }
	/**Metodo que devuelve los productos que contiene la cesta.
	 * @return Productos que contiene la cesta.
	 */
    public Set<ProductoJDO> getCesta() {
		return this.cesta;
	}
	/**Metodo que modifica los productos que contiene la cesta.
	 * @param cesta Nuevos productos que contiene la cesta.
	 */
	public void AnadirCesta(ProductoJDO producto) {
		cesta.add(producto);
	}
	/**Metodo que borrar un producto de la cesta.
	 * @param producto Producto a borrar.
	 */
	public void borrarProductoDeCesta(ProductoJDO producto) {
		cesta.remove(producto);
	}
	/**Metodo que modifica los productos que contiene la cesta.
	 * @param cesta Nuevos productos que contiene la cesta.
	 */
	public void setCesta(Set<ProductoJDO> cesta) {
		this.cesta = cesta;
	}
	/**Metodo que devuelve la representacion en String de la cesta.
	 * @return Representacion en String de la cesta.
	 */
	public String toString(){
		return "Cliente: " + this.cliente + " Cesta: " + this.cesta;
	}
	/**Metodo que vacia la cesta. */
	public void clearCesta() {
		this.cesta.clear();
	}
	
}