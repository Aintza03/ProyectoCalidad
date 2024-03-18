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

import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.ClientMain;
public class ClientRegistration extends JFrame{
    protected JLabel labelEmail = new JLabel("Email: ");
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    protected JLabel labelNombre = new JLabel("Nombre: ");
    protected JTextField email = new JTextField();
    protected JPasswordField contrasena = new JPasswordField();
    protected JTextField nombre = new JTextField();
    protected JButton registrar = new JButton("Registrar");

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

    registrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				char[] contrasenas = contrasena.getPassword();
				String stringC = "";
				for (char c : contrasenas) {
					stringC = stringC + c;
				}  
                registrarCliente(webTargets,email.getText(),stringC,nombre.getText());          
            }
        });
    }

    public void registrarCliente(WebTarget webTarget,String email, String contrasena, String nombre) {
		WebTarget WebTargetRegistrarUsuario = webTarget.path("register");
		Invocation.Builder invocationBuilder = WebTargetRegistrarUsuario.request(MediaType.APPLICATION_JSON);
		
		Cliente cliente = new Cliente();
		cliente.setEmail(email);
		cliente.setContrasena(contrasena);
        cliente.setNombre(nombre);
		Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			ClientMain.getLogger().info("Cliente registrado correctamente");
		}
	}
}
