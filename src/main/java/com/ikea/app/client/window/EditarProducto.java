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

import com.ikea.app.pojo.Admin;
import com.ikea.app.client.controller.AnadirProductoController;

public class EditarProducto extends JFrame{
    protected JLabel labelNombre = new JLabel("Nombre: ");
    protected JTextField nombre = new JTextField();

    protected JLabel labelTipo = new JLabel("Tipo: ");
    protected JTextField tipo = new JTextField();

    protected JLabel labelPrecio = new JLabel("Precio: ");
     protected JTextField precio = new JTextField();
   
    protected JButton editar = new JButton("Editar producto");

    protected EditarProductoController controller = new EditarProductoController();
	public EditarProducto(WebTarget webTargets, String name, String tipo, double precio){
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2,1));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3,3));

    panel.add(labelNombre);
    panel.add(nombre);

    panel.add(labelTipo);
    panel.add(tipo);

    panel.add(labelPrecio);
    panel.add(precio);

    cp.add(panel);
    cp.add(editar);

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Editar Producto");
	this.setLocationRelativeTo(null);

    editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
                   double num = Double.parseDouble(precio.getText());
                    controller.anadirProducto(webTargets,nombre.getText(),tipo.getText(),num, usuario);
                    productListAdmin.loadProducto(webTargets, usuario.getUsuario());
                    dispose();
                } catch (Exception f) {
                    // TODO: handle exception
                    JFrame jFrame = new JFrame();
                    JOptionPane.showMessageDialog(jFrame, "Inserte un número valido en el campo precio", "Error", JOptionPane.ERROR_MESSAGE);
                
                }
                        
            }
        });
    }

}