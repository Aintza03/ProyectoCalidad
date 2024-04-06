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
public class CestaWindow extends JFrame{
	protected DefaultListModel<Producto> modeloCesta;
	protected JList<Producto> listaCesta;
    protected double precioTotal = 0;
    protected JLabel labelPrecioTotal; 
    
   public CestaWindow(WebTarget webTargets, Cesta cesta){
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
                cesta.getCesta().clear();
                modeloCesta.clear();
                precioTotal=0;
                labelPrecioTotal.setText(precioTotal + "€");
                vaciarCesta(webTargets, cesta);
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
                borrarProductoDeCesta(webTargets, cesta);
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
}          
public void addProducto(Producto producto){
    modeloCesta.addElement(producto);
    precioTotal=precioTotal + producto.getPrecio();
    labelPrecioTotal.setText(precioTotal + "€");

}
    public void vaciarCesta(WebTarget webTarget, Cesta cesta) {
		WebTarget WebTargetLogin = webTarget.path("vaciarCesta");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {	
			ClientMain.getLogger().info("Cesta vaciada correctamente");
		}
	}

    public void borrarProductoDeCesta( WebTarget webTarget, Cesta cesta) {
        WebTarget WebTargetLogin = webTarget.path("borrarProductoDeCesta");
        Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
        if (response.getStatus() != Status.OK.getStatusCode()) {
            ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
        } else {	
            ClientMain.getLogger().info("Producto borrado correctamente");
        }
}
}