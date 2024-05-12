package com.ikea.app.pojo;

public class Reclamacion {
    public static int idGeneral = 1;
    private int id;
    private String reclamacion;
    private Cliente cliente;
    private Producto producto;
    
    public Reclamacion(){
        this.id = idGeneral + 1;
    }

    public int getId(){
        return this.id;
    }

    public static void setIdGeneral(int idGeneral){
        Reclamacion.idGeneral = idGeneral++;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getReclamacion(){
        return this.reclamacion;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public Producto getProducto(){
        return this.producto;
    }

    public void setReclamacion(String reclamacion){
        this.reclamacion = reclamacion;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setProducto(Producto producto){
        this.producto = producto;
    }

    public String toString(){
        return "-" + this.id + ": " + " (" + this.cliente + " , " + this.producto + ")";
    }   
}
