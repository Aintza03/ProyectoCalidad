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

/**Ventana que se usa para que el admin edite un producto, la ventana pide el nombre, tipo y precio del producto
 * para poder editarlo.
 */
public class EditarProducto extends JFrame{
    /** Label que muestra donde introducir el nombre del producto. */
    protected JLabel labelNombre = new JLabel("Nombre: ");
    /** JTextField en el que se introduce el nombre del producto. */
    protected JTextField nombre;

    /** Label que muestra donde introducir el tipo del producto. */
    protected JLabel labelTipo = new JLabel("Tipo: ");
    /** JTextField en el que se introduce el tipo del producto. */
    protected JTextField tipo;

    /** Label que muestra donde introducir el precio del producto. */
    protected JLabel labelPrecio = new JLabel("Precio: ");
    /** JTextField en el que se introduce el precio del producto. */
    protected JTextField precio;

    /** Ventana que se usa para mostrar la lista de productos del admin. */
    protected ProductListAdmin productListAdmin;

    /** Boton que llama a la funcion del controller para editar el producto. */
    protected JButton editar = new JButton("Editar producto");

    /** Producto que se va a editar. */
    protected Producto producto;

    /** Controller de esta ventana que guarda toda la funcionalidad. */
    protected EditarProductoController controller = new EditarProductoController();
    /** Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
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