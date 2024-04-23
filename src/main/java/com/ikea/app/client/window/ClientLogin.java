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
import com.ikea.app.client.window.ClientChangeEraseWindow;
import com.ikea.app.client.window.MenuClienteWindow;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.controller.ClientLoginController;
public class ClientLogin extends JFrame{

	protected JLabel labelEmail = new JLabel("Email: ");
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
    protected JTextField email = new JTextField();
    protected JPasswordField contrasena = new JPasswordField();

    protected JButton login = new JButton("Iniciar sesion");
	
	protected ClientLoginController controller = new ClientLoginController();

	protected ProductList window2;
	protected ClientChangeEraseWindow window3;
	//protected MenuClienteWindow window4;

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
        	String result = controller.loginCliente(webTargets,email.getText(),stringC); 
				if (result != ""){
					window2 = new ProductList(webTargets, email.getText());
          			window3 = new ClientChangeEraseWindow(webTargets, email.getText(), stringC, result);
					//window4 = new MenuClienteWindow(webTargets);
				}
            }
        });	
    }
}
