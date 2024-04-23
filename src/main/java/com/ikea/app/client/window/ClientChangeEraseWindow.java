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
import com.ikea.app.client.controller.ModificarUsuarioController;
public class ClientChangeEraseWindow extends JFrame{
	protected JLabel labelNombre = new JLabel("Nombre: ");
    protected JTextField nombre;
    protected JLabel labelEmail = new JLabel("Email: ");
    protected JLabel labelEmailAnt;
    protected JLabel labelContrasena = new JLabel("Contraseña: ");
    protected JPasswordField contrasena;
    protected JLabel labelContrasenaRep = new JLabel("Contraseña Repetida: ");
    protected JPasswordField contrasenaRep;
    protected JButton cambiar = new JButton("Cambiar");
    protected JButton eliminar = new JButton("Eliminar Usuario");
    protected ClientChangeEraseWindow window3;
    protected ModificarUsuarioController controller = new ModificarUsuarioController();
    protected Cliente cliente;

	public ClientChangeEraseWindow(WebTarget webTargets,String mail, String contra, String nom) {
        nombre = new JTextField(nom);
        labelEmailAnt = new JLabel(mail);
        contrasena = new JPasswordField(contra);
        contrasenaRep = new JPasswordField();
       
        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(2, 1));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(labelNombre);
        panel.add(nombre);
        panel.add(labelEmail);
        panel.add(labelEmailAnt);
        panel.add(labelContrasena);
        panel.add(contrasena);
        panel.add(labelContrasenaRep);
        panel.add(contrasenaRep);
        cp.add(panel);
        JPanel panelButton = new JPanel();
        panelButton.add(cambiar);
        panelButton.add(eliminar);
        cp.add(panelButton);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500,200);
        this.setTitle("Datos del Cliente");
        this.setLocationRelativeTo(null);

        cambiar.addActionListener(new ActionListener() {
                
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] pass1 = contrasena.getPassword();
                String pass = "";
                for (char c : pass1) {
					pass = pass + c;
				}  
                char[] pass2 = contrasenaRep.getPassword();
                String passRep = "";
                for (char c : pass2) {
					passRep = passRep + c;
				}  
                if (pass.equals(passRep)) {
                    cliente = controller.modificarUsuario(webTargets, mail, pass, nombre.getText());
                    dispose();
                    ClientChangeEraseWindow window4 = new ClientChangeEraseWindow(webTargets, mail,pass, nombre.getText());
                } else {
                    ClientMain.getLogger().error("Las contraseñas no coinciden");
                }
            }
        });	

        eliminar.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmacionEliminarWindow cew = new ConfirmacionEliminarWindow(webTargets, mail, contra, nom); 
            }
        });
    }
}
