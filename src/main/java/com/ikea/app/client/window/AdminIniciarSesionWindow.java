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
import com.ikea.app.pojo.Admin;
import com.ikea.app.client.controller.AdminLoginController;
public class AdminIniciarSesionWindow extends JFrame{
	protected JLabel labelUsuario = new JLabel("Usuario: ");
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    protected JTextField usuario = new JTextField();
    protected JPasswordField contrasena = new JPasswordField();
    protected JButton login = new JButton("Iniciar sesion");
	protected ProductList window2;
	protected AdminLoginController controller = new AdminLoginController();
	public AdminIniciarSesionWindow(WebTarget webTargets){
    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1));
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 2));
    panel.add(labelUsuario);
    panel.add(usuario);
    panel.add(labelContrasena);
    panel.add(contrasena);
    cp.add(panel);
    cp.add(login);
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Iniciar Sesion Administrador");
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
                Boolean result = controller.loginAdmin(webTargets,usuario.getText(),stringC); 
				if (result == true){
					JFrame jFrame = new JFrame();
					JOptionPane.showMessageDialog(jFrame, "Inicio de sesion correcto");
				}
            }
        });	
    }
}
