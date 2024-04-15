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
import com.ikea.app.client.window.ProductList;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.ClientMain;

public class ClientChangeEraseWindow extends JFrame{
	protected JLabel labelEmail = new JLabel("Email: ");
    protected JLabel labelEmailAnt;
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    protected JLabel labelContrasenaAnt;
    protected JLabel labelNombre = new JLabel("Nombre: ");
    protected JLabel labelNombreAnt;
    protected JTextField email = new JTextField();
    protected JPasswordField contrasena = new JPasswordField();
    protected JTextField nombre = new JTextField();
    protected JButton cambiar = new JButton("Cambiar");
    protected JButton eliminar = new JButton("Eliminar");
	protected ProductList window2;

	public ClientChangeEraseWindow(WebTarget webTargets, String emailGive, String contrasenaGive){
    labelNombreAnt = new JLabel(contrasenaGive);
    labelEmailAnt = new JLabel(emailGive);
    labelContrasenaAnt = new JLabel(contrasenaGive);
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 3));
    panel.add(labelNombre);
    panel.add(labelNombreAnt);
    panel.add(nombre);
    panel.add(labelEmail);
    panel.add(labelEmailAnt);
    panel.add(email);
    panel.add(labelContrasena);
    panel.add(labelContrasenaAnt);
    panel.add(contrasena);
    cp.add(panel);
    JPanel panelButton = new JPanel();
    panelButton.add(cambiar);
    panelButton.add(eliminar);
    cp.add(panelButton);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(500,150);
	this.setTitle("Iniciar Sesion Cliente");
	this.setLocationRelativeTo(null);

    cambiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                
            }
        });	
    }


}
