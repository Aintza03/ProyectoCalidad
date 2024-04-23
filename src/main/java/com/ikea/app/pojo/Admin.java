package com.ikea.app.pojo;

import java.util.Set;
import java.util.HashSet;
import com.ikea.app.pojo.Producto;

public class Admin {
    private String usuario;
    private String contrasena;
    private Set<Producto> lista;
    public Admin() {
        this.lista = new HashSet<Producto>();
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getContrasena() {
        return this.contrasena;
    }
    public Set<Producto> getLista() {
        return this.lista;
    }

    
    public void setLista(Set<Producto> lista) {
        this.lista = lista;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void anadirLista(Producto producto) {
        lista.add(producto);
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String toString() {
        return "Usuario: " + this.usuario;
    }
    
}
