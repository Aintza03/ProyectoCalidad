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
    JButton comprarCestaButton=new JButton("Comprar cesta");
        comprarCestaButton.setBounds(250,120,50,30);
        add(comprarCestaButton);
        comprarCestaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==comprarCestaButton) {
                    double precioTotal = 0;
                    for(Producto producto : cesta.getCesta()){
                        precioTotal=precioTotal + producto.getPrecio();
                    }
                    JFrame jFrame = new JFrame();
                    JOptionPane.showMessageDialog(jFrame, "Se a confirmado la compra de la cesta, el precio total es de: "+precioTotal);
<<<<<<< Updated upstream
                    WebTarget WebTargetLogin = webTargets.path("clearCesta");
                    Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
                    cesta.clearCesta();
                    Response response = invocationBuilder.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
                    if (response.getStatus() != Status.OK.getStatusCode()) {
                        ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
                    } else {	
                        ClientMain.getLogger().info("Cesta borrada correctamente");
                    }
                                modeloCesta.clear();
                                cp.repaint();
                            }
                        }}
=======
                    cesta.getCesta().clear();
                    modeloCesta.clear();
                    vaciarCesta(webTargets, cesta);
                    }
                }
            }
>>>>>>> Stashed changes
        );
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