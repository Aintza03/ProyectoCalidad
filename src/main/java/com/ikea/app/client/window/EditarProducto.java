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
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.controller.EditarProductoController;

public class EditarProducto extends JFrame{
    protected JLabel labelNombre = new JLabel("Nombre: ");
    protected JTextField nombre;

    protected JLabel labelTipo = new JLabel("Tipo: ");
    protected JTextField tipo;

    protected JLabel labelPrecio = new JLabel("Precio: ");
    protected JTextField precio;

    protected ProductListAdmin productListAdmin;

    protected JButton editar = new JButton("Editar producto");

    protected Producto producto;

    protected EditarProductoController controller = new EditarProductoController();
	public EditarProducto(WebTarget webTargets, Producto producto, Admin usuario){
    this.producto = producto;
    String name = producto.getNombre();
    String tipos = producto.getTipo();
    double precios = producto.getPrecio();
    
    nombre = new JTextField(name);
    tipo = new JTextField(tipos);
    precio = new JTextField(String.valueOf(precios));

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
    JPanel panelButton = new JPanel();
    panelButton.add(editar);
    cp.add(panelButton);

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
                    controller.editarProducto(webTargets,nombre.getText(),tipo.getText(),num, producto);
                    new ProductListAdmin(webTargets, usuario);
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