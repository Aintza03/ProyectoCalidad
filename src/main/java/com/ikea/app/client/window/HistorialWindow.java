package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.ikea.app.pojo.*;
import javax.ws.rs.client.WebTarget;
import com.ikea.app.client.window.HacerReclamacionWindow;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.controller.HistorialWindowController;
/**Ventana que se usa para mostrar el historial de compras del cliente.
 */
public class HistorialWindow extends JFrame{
    /**Modelo de la lista que se usa para mostrar los productos del historial. */
	protected DefaultListModel<Producto> modeloHistorial;
    /**Lista que muestra los productos del historial. */
	protected JList<Producto> listaHistorial;
    protected JButton reclamacion = new JButton("Hacer reclamacion");
    protected JButton devolver = new JButton("Devolver producto");
    protected HistorialWindowController controller = new HistorialWindowController();
    /**Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
   public HistorialWindow(WebTarget webTargets, Historial historial, Cliente cliente){
    Container cp = this.getContentPane();
    cp.setLayout(new GridLayout(2, 1));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 2));
    modeloHistorial = new DefaultListModel<Producto>();
    for(Producto producto : historial.getProductos()){
        modeloHistorial.addElement(producto);
    }
    
    listaHistorial = new JList<Producto>(modeloHistorial);
    listaHistorial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollHistorial = new JScrollPane(listaHistorial);
    panel.add(reclamacion);
    panel.add(devolver);
    cp.add(scrollHistorial);
    cp.add(panel);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(400,150);
	this.setTitle("Historial");
	this.setLocationRelativeTo(null);
    this.setLocation(750,400);

    reclamacion.addActionListener(new ActionListener() {
            
        @Override
        public void actionPerformed(ActionEvent e) {
            Producto producto = listaHistorial.getSelectedValue();
            if(producto != null){
                HacerReclamacionWindow hrw = new HacerReclamacionWindow(webTargets, producto, cliente);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto");
            }
        }
    });
    devolver.addActionListener(new ActionListener() {
            
        @Override
        public void actionPerformed(ActionEvent e) {
            Producto producto = listaHistorial.getSelectedValue();
            if(producto != null){
                controller.devolverProducto(webTargets,producto);
                modeloHistorial.removeElement(producto);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un producto");
            }
        }
    });	
    	
    }          
              
/**Funcion que se usa para anadir un producto al historial.
 * @param producto Producto que se va a anadir al historial.
 */
public void addProducto(Producto producto){
    modeloHistorial.addElement(producto);
}
}