package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.ikea.app.pojo.*;
import javax.ws.rs.client.WebTarget;
public class HistorialWindow extends JFrame{
	protected DefaultListModel<Producto> modeloHistorial;
	protected JList<Producto> listaHistorial;
    //protected CestaWindowController cestaWindowController = new CestaWindowController();
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
public void addProducto(Producto producto){
    modeloHistorial.addElement(producto);
}
}