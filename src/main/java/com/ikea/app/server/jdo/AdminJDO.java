package com.ikea.app.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.util.Set;
import javax.jdo.annotations.Column;
import java.util.HashSet;
@PersistenceCapable
public class AdminJDO {
    @PrimaryKey

    String usuario = null;
    String contrasena = null;
    @Column(name = "vendedor")
    Set<ProductoJDO> lista;

    public AdminJDO(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.lista = new HashSet<ProductoJDO>();
    }

    public String getUsuario() {
        return this.usuario;
    }
    public Set<ProductoJDO> getLista() {
        return this.lista;
    }
    public String getContrasena() {
        return this.contrasena;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setLista(Set<ProductoJDO> lista) {
        this.lista = lista;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public void anadirLista(ProductoJDO producto) {
        lista.add(producto);
    }

    public String toString() {
        return "Usuario: " + this.usuario;
    }
    
}
