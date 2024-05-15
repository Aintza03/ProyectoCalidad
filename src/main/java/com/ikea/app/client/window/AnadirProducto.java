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
/**Ventana que se usa para que el administrador pueda añadir un producto, el producto automaticamente tiene asociado
 * el nombre de la empresa como su vendedor.
 */
public class AnadirProducto extends JFrame{
    /**Label que muestra donde introducir el nombre del producto. */
    protected JLabel labelNombre = new JLabel("Nombre: ");
    /**Label que muestra donde introducir el tipo del producto. */
    protected JLabel labelTipo = new JLabel("Tipo: ");
    /**Label que muestra donde introducir el precio del producto. */
    protected JLabel labelPrecio = new JLabel("Precio: ");
    /**JTextField en el que se introduce el nombre del producto. */
    protected JTextField nombre = new JTextField();
    /**JTextField en el que se introduce el tipo del producto. */
    protected JTextField tipo = new JTextField();
    /**JTextField en el que se introduce el precio del producto. */
    protected JTextField precio = new JTextField();
    /**Boton que llama a la funcion del controller para añadir el producto.*/
    protected JButton anadir = new JButton("Añadir producto");
    /**Controller de esta ventana.*/
    protected AnadirProductoController controller = new AnadirProductoController();
	/**Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller.*/
    public AnadirProducto(WebTarget webTargets, Admin usuario, ProductListAdmin productListAdmin){
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
    cp.add(anadir);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Añadir Producto");
	this.setLocationRelativeTo(null);

    anadir.addActionListener(new ActionListener() {
			
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