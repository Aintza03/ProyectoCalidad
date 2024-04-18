package com.ikea.app.pojo;

public class Admin {
    private String usuario;
    private String contrasena;

    public Admin() {

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
