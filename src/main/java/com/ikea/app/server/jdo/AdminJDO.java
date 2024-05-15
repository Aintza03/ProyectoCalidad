package com.ikea.app.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.util.Set;
import javax.jdo.annotations.Column;
import java.util.HashSet;
@PersistenceCapable
/**Clase que representa a un vendedor en la base de datos.
 */
public class AdminJDO {
    @PrimaryKey
    /**Usuario del vendedor. */
    String usuario = null;
    /**Contrasena del vendedor. */
    String contrasena = null;
    @Column(name = "vendedor")
    /**Lista de productos que vende el vendedor. */
    Set<ProductoJDO> lista;

    /**Constructor de la clase.
     * @param usuario Usuario del vendedor.
     * @param contrasena Contrasena del vendedor.
     */
    public AdminJDO(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.lista = new HashSet<ProductoJDO>();
    }
    /**Constructor de la clase.
     */
    public AdminJDO() {
        this.lista = new HashSet<ProductoJDO>();
    }
    /**Metodo que devuelve el usuario del vendedor.
     * @return Usuario del vendedor.
     */
    public String getUsuario() {
        return this.usuario;
    }
    /**Metodo que devuelve la lista de productos que vende el vendedor.
     * @return Lista de productos que vende el vendedor.
     */
    public Set<ProductoJDO> getLista() {
        return this.lista;
    }
    /**Metodo que devuelve la contrasena del vendedor.
     * @return Contrasena del vendedor.
     */
    public String getContrasena() {
        return this.contrasena;
    }
      /**Metodo que modifica el usuario del vendedor.
     * @param usuario Nuevo usuario del vendedor.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**Metodo que modifica la lista de productos que vende el vendedor.
     * @param lista Nueva lista de productos que vende el vendedor.
     */
    public void setLista(Set<ProductoJDO> lista) {
        this.lista = lista;
    }
    /**Metodo que modifica la contrasena del vendedor.
     * @param contrasena Nueva contrasena del vendedor.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**Metodo que anade un producto a la lista de productos que vende el vendedor.
     * @param producto Producto que se va a anadir a la lista.
     */
    public void anadirLista(ProductoJDO producto) {
        lista.add(producto);
    }
    /**Metodo que devuelve la representacion en String del Admin.
	 * @return Representacion en String del Admin.
	 */
    public String toString() {
        return "Usuario: " + this.usuario;
    }
    
}
