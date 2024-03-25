package com.ikea.app.server.jdo;

import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import java.util.HashSet;
@PersistenceCapable
public class ClienteJDO{
    @PrimaryKey
    String email = null;
    String contrasena = null;
    String nombre = null;

    public ClienteJDO(String email, String contrasena, String nombre) {
		this.email = email;
		this.contrasena = contrasena;
        this.nombre = nombre;
	}

    public String getEmail() {
		return this.email;
	}
	
	public String getContrasena() {
		return this.contrasena;
	}
	public String getNombre() {
		return this.nombre;
	}
    public void setEmail(String email) {
		this.email = email;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

    public String toString(){
        return "Nombre: " + this.nombre + " Email: " + this.email;
    }
	
}