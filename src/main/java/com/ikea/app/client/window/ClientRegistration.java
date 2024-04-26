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

import com.ikea.app.client.controller.ClientRegistrationController;

public class ClientRegistration extends JFrame{
    protected JLabel labelEmail = new JLabel("Email: ");
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    protected JLabel labelNombre = new JLabel("Nombre: ");
    protected JTextField email = new JTextField();
    protected JPasswordField contrasena = new JPasswordField();
    protected JTextField nombre = new JTextField();
    protected JButton registrar = new JButton("Registrar");
    protected ClientRegistrationController controller = new ClientRegistrationController();
	public ClientRegistration(WebTarget webTargets){
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2,1));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3,3));
    panel.add(labelEmail);
    panel.add(email);
    panel.add(labelContrasena);
    panel.add(contrasena);
    panel.add(labelNombre);
    panel.add(nombre);
    cp.add(panel);
    cp.add(registrar);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Registrar Cliente");
	this.setLocationRelativeTo(null);
    this.setLocation(450,400);

    registrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				char[] contrasenas = contrasena.getPassword();
				String stringC = "";
				for (char c : contrasenas) {
					stringC = stringC + c;
				}  
                controller.registrarCliente(webTargets,email.getText(),stringC,nombre.getText());          
            }
        });
    }

}
