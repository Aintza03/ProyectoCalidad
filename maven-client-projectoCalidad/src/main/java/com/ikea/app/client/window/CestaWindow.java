package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
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
public class CestaWindow extends JFrame{
	protected DefaultListModel<Producto> modeloCesta;
	protected JList<Producto> listaCesta;
   public CestaWindow(WebTarget webTargets, Cesta cesta){
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(1, 1));
    modeloCesta = new DefaultListModel<Producto>();
    for(Producto producto : cesta.getCesta()){
        modeloCesta.addElement(producto);
    }
    listaCesta = new JList<Producto>(modeloCesta);
    listaCesta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollCesta = new JScrollPane(listaCesta);
    cp.add(scrollCesta);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Cesta");
	this.setLocationRelativeTo(null);
}
    public void addProducto(Producto producto){
        modeloCesta.addElement(producto);
    }
}