package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ikea.app.client.window.ProductList;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.ClientMain;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.controller.CestaWindowController;
import com.ikea.app.pojo.Historial;
/**Ventana que se usa para mostrar la cesta de un cliente y las acciones con la cesta como centro. Las acciones permitidas son
 * 1.Borrar producto de la cesta.
 * 2.Comprar cesta.
 * Adicionalmente tambien muestra el precio total de la cesta en todo momento.
 */
public class CestaWindow extends JFrame{
    /**Modelo en el que se basa la JList de la cesta. */
	protected DefaultListModel<Producto> modeloCesta;
    /**JList que muestra los productos de la cesta. */
	protected JList<Producto> listaCesta;
    /**Variable que guarda el precio total de la cesta. */
    protected double precioTotal = 0;
    /**Label que muestra el precio total de la cesta. */
    protected JLabel labelPrecioTotal; 
    /**Controller de esta ventana que guarda toda la funcionalidad.*/
    protected CestaWindowController cestaWindowController = new CestaWindowController();
    /**La siguiente ventana, se muestra a la par de cestaWindow pero se asocian entre si. */
    protected HistorialWindow historialWindow;
    /**Constructor que crea toda la interfaz de usuario y se encarga de llamar al controller correspondiente
     * a la ventana que tiene toda la funcionalidad.
     */
   public CestaWindow(WebTarget webTargets, Cesta cesta){
    Historial historial = cestaWindowController.getHistorial(webTargets, cesta.getCliente().getEmail());
    historialWindow = new HistorialWindow(webTargets, historial, cesta.getCliente());
    Container cp = this.getContentPane();
    cp.setLayout(new GridLayout(1, 2));
    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    panel.setLayout(new GridLayout(2, 1));
    panel2.setLayout(new GridLayout(2,1));
    JButton comprarCestaButton=new JButton("Comprar cesta");
    comprarCestaButton.setBounds(250,120,50,30);
    panel.add(comprarCestaButton);
    comprarCestaButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==comprarCestaButton) {
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "Se ha confirmado la compra de la cesta, el precio total de la compra es "+ precioTotal);
                for(Producto producto : cesta.getCesta()){
                    historialWindow.addProducto(producto);
                    historial.addProducto(producto);
                }
                cestaWindowController.guardarHistorial(webTargets, historial);
                cesta.getCesta().clear();
                modeloCesta.clear();
                precioTotal=0;
                labelPrecioTotal.setText(precioTotal + "€");
                cestaWindowController.vaciarCesta(webTargets, cesta);
            }
        }
    });
    JButton borrarProductoButton=new JButton("Borrar producto");
    borrarProductoButton.setBounds(250,120,50,30);
    panel.add(borrarProductoButton);
    borrarProductoButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==borrarProductoButton) {
                Producto producto = listaCesta.getSelectedValue();
                modeloCesta.removeElement(producto);
                precioTotal=precioTotal - producto.getPrecio();
                labelPrecioTotal.setText(precioTotal + "€");
                cesta.getCesta().remove(producto);
                cestaWindowController.borrarProductoDeCesta(webTargets, cesta);
            }
        }
    });

    modeloCesta = new DefaultListModel<Producto>();
    for(Producto producto : cesta.getCesta()){
        modeloCesta.addElement(producto);
        precioTotal=precioTotal + producto.getPrecio();
    }
    
    listaCesta = new JList<Producto>(modeloCesta);
    listaCesta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollCesta = new JScrollPane(listaCesta);
    panel2.add(scrollCesta);
    labelPrecioTotal = new JLabel(precioTotal + "€");
    panel2.add(labelPrecioTotal);
    cp.add(panel2);
    cp.add(panel);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(400,150);
	this.setTitle("Cesta");
	this.setLocationRelativeTo(null);
    this.setLocation(750,400);
}          
/**Funcion que añade un producto a la JList de la cesta y actualiza el precio total de la cesta.*/
public void addProducto(Producto producto){
    modeloCesta.addElement(producto);
    precioTotal=precioTotal + producto.getPrecio();
    labelPrecioTotal.setText(precioTotal + "€");

}
}