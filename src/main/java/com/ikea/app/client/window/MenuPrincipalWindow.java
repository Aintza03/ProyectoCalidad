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

import com.ikea.app.client.window.ClientRegistration;
import com.ikea.app.client.window.ClientLogin;
import com.ikea.app.client.window.AdminIniciarSesionWindow;

public class MenuPrincipalWindow extends JFrame{

    /** Boton para ir a la ventana ClientRegistration y ClientLogin. */
    protected JButton cliente = new JButton("Cliente");
    /** Boton para ir a la ventana AdminIniciarSesionWindow. */
    protected JButton vendedor = new JButton("Vendedor");

    /** Ventana que se usa para logear el cliente. */
	protected ClientLogin window2;
    /** Ventana que se usa para registrar el cliente. */
    protected ClientRegistration window4;
    /** Ventana que se usa para logear el vendedor. */
    protected AdminIniciarSesionWindow window3;
	
    /** Constructor que crea toda la parte de interfaz grafica de esta ventana. */
    public MenuPrincipalWindow(WebTarget webTargets){

    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1)); 

    cp.add(cliente);
    cp.add(vendedor);

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Menu Principal");
	this.setLocationRelativeTo(null);

    cliente.addActionListener(new ActionListener() {
			
        @Override
        public void actionPerformed(ActionEvent e) {
            window4 = new ClientRegistration(webTargets);
            window2 = new ClientLogin(webTargets);
            dispose();
        }
    });	

    vendedor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window3 = new AdminIniciarSesionWindow(webTargets);
                dispose();
            }
        });	
    }
}
