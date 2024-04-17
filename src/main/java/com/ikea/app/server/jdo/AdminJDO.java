package com.ikea.app.server.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.util.Set;
import javax.jdo.annotations.Column;
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
    }

    public String getUsuario() {
        return this.usuario;
    }

    public String getContrasena() {
        return this.contrasena;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String toString() {
        return "Usuario: " + this.usuario;
    }
    
}
