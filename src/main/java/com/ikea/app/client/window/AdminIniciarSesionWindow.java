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
import com.ikea.app.client.window.ProductListAdmin;
/**Ventana que se usa para que el administrador inicie sesion, la ventana pide el nombre del administrador
 * y su contrasena para verificar.
 */
public class AdminIniciarSesionWindow extends JFrame{
	/**Label que se utiliza para mostrar donde incluir el nombre del administrador:*/
	protected JLabel labelUsuario = new JLabel("Usuario: ");
	/**Label que se utiliza para mostrar donde incluir la contrasena del administrador:*/
    protected JLabel labelContrasena = new JLabel("Contrasena: ");
	/**JTextField en el que se escribe el nombre del administrador.*/
    protected JTextField usuario = new JTextField();
    /**Password field utilizado para introducir la contrasena de modo que lo que el usuario escribe no pueda ser
	 * directamente leido por pantalla.*/
	protected JPasswordField contrasena = new JPasswordField();
	/**Boton que activa el inicio de sesion. Al ser pulsado el boton llama a la funcion de inicio de sesion en el controller. */
    protected JButton login = new JButton("Iniciar sesion");
	/**La siguiente ventana despues de iniciar Sesion, en este caso la lista de productos del administrador */
	protected ProductListAdmin window2;
	/**Controller de esta ventana que guarda toda la funcionalidad.*/
	protected AdminLoginController controller = new AdminLoginController();
	/**Constructor que crea toda la interfaz de usuario y se encarga de llamar al controller correspondiente
	 * a la ventana que tiene toda la funcionalidad.
	 */
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
                Admin result = controller.loginAdmin(webTargets,usuario.getText(),stringC); 
				if (result != null){
					window2 = new ProductListAdmin(webTargets, result);
				}
            }
        });	
    }
}
