package com.ikea.app.pojo;
/**Clase a la que pertenecen los productos. El producto se define por un id unico, su nombre, el tipo de mueble
 * y su precio.
 */
public class Producto {
    /**Se usa para asignar los ids. El primer producto registrado tendra el id = 1 entonces el idGeneral aumentara 
     * su valor en 1 (es decir su valor sera 2). Asi el siguiente producto obtendra el id = 2 a partir del idGeneral y asi
     * sucesivamente.
     */
    public static int idGeneral = 1;
    private int id;
    private String nombre;
    private String tipo;
    private double precio;
    /**Constructor vacio, se encarga de aumentar el idGeneral. */
    public Producto(){
        this.id = idGeneral + 1;
    }
    /**Obtiene el id del producto. */
    public int getId(){
        return this.id;
    }
    /**A partir del id general obtienes el idGeneral del producto es decir el siguiente id a asignar. */
    public static void setIdGeneral(int idGeneral){
        Producto.idGeneral = idGeneral++;
    }
    /**Asigna ids. */
    public void setId(int id){
        this.id = id;
    }
    /**Obtiene el nombre del producto. */
    public String getNombre(){
        return this.nombre;
    }
    /**Obtiene el tipo del producto. */
    public String getTipo(){
        return this.tipo;
    }
    /**Obtiene el precio del producto. */
    public double getPrecio(){
        return this.precio;
    }
    /**Cambia el nombre del producto.*/
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    /**Cambia el tipo del producto. */
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    /**Cambia el precio del producto. */
    public void setPrecio(double precio){
        this.precio = precio;
    }
    /**Es la forma en la que esta clase aparece por pantalla. */
    public String toString(){
        return "-" + this.id + ": " + this.nombre + " (" + this.tipo + " , " + this.precio + "€ )";
    }
    
}
