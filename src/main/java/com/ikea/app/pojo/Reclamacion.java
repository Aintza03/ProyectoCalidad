package com.ikea.app.pojo;
/**Clase a la que pertenecen las reclamaciones. La reclamacion se define por un id unico, el texto de la reclamacion,
 * el cliente que la realiza y el producto que se reclama.
 */
public class Reclamacion {
    /**Se usa para asignar los ids. La primera reclamacion registrada tendra el id = 1 entonces el idGeneral aumentara 
     * su valor en 1 (es decir su valor sera 2). Asi la siguiente reclamacion obtendra el id = 2 a partir del idGeneral y asi
     * sucesivamente.
     */
    public static int idGeneral = 1;
    private int id;
    private String reclamacion;
    private Cliente cliente;
    private Producto producto;
    /**Constructor vacio, se encarga de aumentar el idGeneral. */
    public Reclamacion(){
        this.id = idGeneral + 1;
    }
    /**Obtiene el id de la reclamacion. */
    public int getId(){
        return this.id;
    }
    /**A partir del id general obtienes el idGeneral de la reclamacion es decir el siguiente id a asignar. */
    public static void setIdGeneral(int idGeneral){
        Reclamacion.idGeneral = idGeneral++;
    }
    /**Asigna ids. */
    public void setId(int id){
        this.id = id;
    }
    /**Obtiene el texto de la reclamacion. */
    public String getReclamacion(){
        return this.reclamacion;
    }
    /**Obtiene el cliente de la reclamacion. */
    public Cliente getCliente(){
        return this.cliente;
    }
    /**Obtiene el producto de la reclamacion. */
    public Producto getProducto(){
        return this.producto;
    }
    /**Cambia el texto de la reclamacion.*/
    public void setReclamacion(String reclamacion){
        this.reclamacion = reclamacion;
    }
    /**Cambia el cliente de la reclamacion. */
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    /**Cambia el producto de la reclamacion. */
    public void setProducto(Producto producto){
        this.producto = producto;
    }

    /**Es la forma en la que esta clase aparece por pantalla. */
    public String toString(){
        return "-" + this.id + ": " + " (" + this.cliente + " , " + this.producto + ") '";
    }   
}
