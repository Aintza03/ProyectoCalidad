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
    protected double precioTotal;
    
   public CestaWindow(WebTarget webTargets, Cesta cesta){
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(3, 1));
    //Anadimos la lista de la cesta
    modeloCesta = new DefaultListModel<Producto>();
    listaCesta = new JList<Producto>(modeloCesta);
    listaCesta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollCesta = new JScrollPane(listaCesta);
    cp.add(scrollCesta);
    //Anadimos el precio final
    JLabel labelPrecioTotal = new JLabel(precioTotal + "€");
    cp.add(labelPrecioTotal);
    //Anadimos el boton para comprar la cesta
    JButton comprarCestaButton=new JButton("Comprar cesta");
    cp.add(comprarCestaButton);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Cesta");
	this.setLocationRelativeTo(null);
    
    for(Producto producto : cesta.getCesta()){
        modeloCesta.addElement(producto);
    }
        cp.add(comprarCestaButton);
        comprarCestaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==comprarCestaButton) {
                    JFrame jFrame = new JFrame();
                    JOptionPane.showMessageDialog(jFrame, "Se a confirmado la compra de la cesta, el precio total es de: " + precioTotal);
                    cesta.getCesta().clear();
                    modeloCesta.clear();
                    vaciarCesta(webTargets, cesta);
                    }
                }
            }
        );
}
    public void addProducto(Producto producto){
        modeloCesta.addElement(producto);
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
}