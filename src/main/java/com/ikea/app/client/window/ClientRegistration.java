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
/**Ventana que se usa para que el cliente se registre, la ventana pide el email del cliente
 * y su contrasena para poder registrarse.
 */
public class ClientRegistration extends JFrame{
    /** Label que muestra donde introducir el email del cliente. */
    protected JLabel labelEmail = new JLabel("Email: ");
    /** Label que muestra donde introducir la contrasena del cliente. */
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    /** Label que muestra donde introducir el nombre del cliente. */
    protected JLabel labelNombre = new JLabel("Nombre: ");
    /** JTextField en el que se introduce el email del cliente. */
    protected JTextField email = new JTextField();
    /** JPasswordField en el que se introduce la contrasena del cliente. */
    protected JPasswordField contrasena = new JPasswordField();
    /** JTextField en el que se introduce el nombre del cliente. */
    protected JTextField nombre = new JTextField();
    /** Boton que llama a la funcion del controller para registrar el cliente. */
    protected JButton registrar = new JButton("Registrar");
    /** Controller de esta ventana que guarda toda la funcionalidad. */
    protected ClientRegistrationController controller = new ClientRegistrationController();
    /** Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
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
