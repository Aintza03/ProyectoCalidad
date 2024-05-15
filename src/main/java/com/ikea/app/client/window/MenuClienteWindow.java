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
import com.ikea.app.client.window.ProductList;
import com.ikea.app.client.controller.AdminLoginController;
import com.ikea.app.client.window.CestaWindow;
import com.ikea.app.client.window.ClientChangeEraseWindow;
/**Ventana que navegar en la parte de cliente.
 */
public class MenuClienteWindow extends JFrame{
    /** Boton para ir a la ventana ProductList. */
    protected JButton producto = new JButton("Comprar Productos");
    /** Boton para ir a la ventana ClientChangeEraseWindow. */
    protected JButton cuenta = new JButton("Personalizar Cuenta");

    /** Ventana que se usa para mostrar la lista de productos. */
	protected ProductList window1;
    /** Ventana que se usa para editar o borrar el cliente. */
    protected ClientChangeEraseWindow window2;

    /** Controller de esta ventana que guarda toda la funcionalidad. */
	protected AdminLoginController controller = new AdminLoginController();
	
    /** Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
    public MenuClienteWindow(WebTarget webTargets, String email, String contra, String nom){

    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1)); 

    cp.add(producto);
    cp.add(cuenta);

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Menu Cliente");
	this.setLocationRelativeTo(null);

    producto.addActionListener(new ActionListener() {
			
        @Override
        public void actionPerformed(ActionEvent e) {
            window1 = new ProductList(webTargets, email);
            setLocation(100,100);
        }
    });	

    cuenta.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
            window2 = new ClientChangeEraseWindow(webTargets, email, contra, nom);
            setLocation(100,100);
        }
    });	
}
}
