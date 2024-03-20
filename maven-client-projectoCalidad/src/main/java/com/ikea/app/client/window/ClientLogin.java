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
public class ClientLogin extends JFrame{
    protected JLabel labelEmail = new JLabel("Email: ");
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    protected JTextField email = new JTextField();
    protected JPasswordField contrasena = new JPasswordField();
    protected JButton login = new JButton("Iniciar sesion");

	public ClientLogin(WebTarget webTargets){
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 2));
    panel.add(labelEmail);
    panel.add(email);
    panel.add(labelContrasena);
    panel.add(contrasena);
    cp.add(panel);
    cp.add(login);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Iniciar Sesion Cliente");
	this.setLocationRelativeTo(null);

    login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				char[] contrasenas = contrasena.getPassword();
				String stringC = "";
				for (char c : contrasenas) {
					stringC = stringC + c;
				}  
                loginCliente(webTargets,email.getText(),stringC);          
            }
        });	
    }

    public void loginCliente(WebTarget webTarget,String email, String contrasena) {
		WebTarget WebTargetRegistrarUsuario = webTarget.path("login");
		Invocation.Builder invocationBuilder = WebTargetRegistrarUsuario.request(MediaType.APPLICATION_JSON);
		
		Cliente cliente = new Cliente();
		cliente.setEmail(email);
		cliente.setContrasena(contrasena);
        cliente.setNombre("");
		Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {
			ClientMain.getLogger().info("Cliente registrado correctamente");
		}
	}
}
