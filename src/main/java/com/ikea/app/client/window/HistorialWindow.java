package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.ikea.app.pojo.*;
import javax.ws.rs.client.WebTarget;
/**Ventana que se usa para mostrar el historial de compras del cliente.
 */
public class HistorialWindow extends JFrame{
    /**Modelo de la lista que se usa para mostrar los productos del historial. */
	protected DefaultListModel<Producto> modeloHistorial;
    /**Lista que muestra los productos del historial. */
	protected JList<Producto> listaHistorial;
    /**Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
   public HistorialWindow(WebTarget webTargets, Historial historial){
    Container cp = this.getContentPane();
    cp.setLayout(new GridLayout(1, 1));
    
    modeloHistorial = new DefaultListModel<Producto>();
    for(Producto producto : historial.getProductos()){
        modeloHistorial.addElement(producto);
    }
    
    listaHistorial = new JList<Producto>(modeloHistorial);
    listaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollHistorial = new JScrollPane(listaHistorial);
    cp.add(scrollHistorial);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(400,150);
	this.setTitle("Historial");
	this.setLocationRelativeTo(null);
    this.setLocation(750,400);
}          
/**Funcion que se usa para anadir un producto al historial.
 * @param producto Producto que se va a anadir al historial.
 */
public void addProducto(Producto producto){
    modeloHistorial.addElement(producto);
}
}