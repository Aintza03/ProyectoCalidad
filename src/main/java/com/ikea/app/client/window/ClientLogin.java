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
import com.ikea.app.client.window.MenuClienteWindow;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.client.controller.ClientLoginController;
/**Ventana que se usa para que el cliente inicie sesion, la ventana pide el email del cliente
 * y su contrasena para verificar.
 */
public class ClientLogin extends JFrame{
	/**Label que se utiliza para mostrar donde incluir el email del cliente. */
	protected JLabel labelEmail = new JLabel("Email: ");
	/**Label que se utiliza para mostrar donde incluir la contrasena del cliente. */
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
	/**JTextField en el que se escribe el email del cliente.*/
    protected JTextField email = new JTextField();
	/**Password field utilizado para introducir la contrasena de modo que lo que el usuario escribe no pueda ser. */
    protected JPasswordField contrasena = new JPasswordField();
	/**Boton que activa el inicio de sesion. Al ser pulsado el boton llama a la funcion de inicio de sesion en el controller. */
    protected JButton login = new JButton("Iniciar sesion");
	/**La siguiente ventana despues de iniciar Sesion, en este caso la lista de productos del cliente. */
	protected MenuClienteWindow window;
	/**Controller de esta ventana que guarda toda la funcionalidad. */
	protected ClientLoginController controller = new ClientLoginController();
	/**Constructor que crea toda la interfaz de usuario y se encarga de llamar al controller correspondiente
	 * a la ventana que tiene toda la funcionalidad.
	 */
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
	this.setLocation(750,400);


    login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				char[] contrasenas = contrasena.getPassword();
				String stringC = "";
				for (char c : contrasenas) {
					stringC = stringC + c;
				}  
        	String result = controller.loginCliente(webTargets,email.getText(),stringC); 
				if (result != ""){
					window = new MenuClienteWindow(webTargets, email.getText(), stringC, result);
				}
            }
        });	
    }
}
