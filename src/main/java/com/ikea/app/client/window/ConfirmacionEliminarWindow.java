package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.ws.rs.ProcessingException;
 
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
import com.ikea.app.pojo.Cesta;
public class ConfirmacionEliminarWindow extends JFrame{
    protected ClientRegistration registrar;
    protected ClientLogin login;
    protected ClientChangeEraseWindow changeErase;
    protected ProductList listaProductos;
    protected CestaWindow cestaWindow;

	protected JLabel labelWarning = new JLabel("  ¿Seguro que quieres borrar la cuenta?");
    protected JButton si = new JButton("Si");
    protected JButton no = new JButton("No");
    protected Cliente cliente = new Cliente();
    public ConfirmacionEliminarWindow(WebTarget webTargets, String mail, String contra, String nom) {
        cliente.setNombre(nom);
        cliente.setEmail(mail);
        cliente.setContrasena(contra);

        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(2, 1));
        JPanel panel = new JPanel();
        cp.add(labelWarning);
        JPanel panelButton = new JPanel();
        panelButton.add(si);
        panelButton.add(no);
        cp.add(panelButton);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(250,150);
        this.setTitle("Confirmacion para Elminar Cliente");
        this.setLocationRelativeTo(null);

        si.addActionListener(new ActionListener() {
                
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarCliente(webTargets, cliente);
                System.exit(0);
            }
        });	

        no.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void borrarCliente( WebTarget webTarget, Cliente cliente) {
        try {
            Response responseEncontrar = webTarget.path("cesta")
                .queryParam("email", cliente.getEmail())
				.request(MediaType.APPLICATION_JSON)
				.get();
            // check that the response was HTTP OK
            if (responseEncontrar.getStatusInfo().toEnum() == Status.OK) {
                Cesta cesta = responseEncontrar.readEntity(Cesta.class);
				System.out.println(cesta);
				WebTarget WebTargetCesta = webTarget.path("vaciarCesta");
                Invocation.Builder invocationBuilderCesta = WebTargetCesta.request(MediaType.APPLICATION_JSON);
                Response responseCesta = invocationBuilderCesta.post(Entity.entity(cesta, MediaType.APPLICATION_JSON));
                if (responseCesta.getStatus() != Status.OK.getStatusCode()) {
                    ClientMain.getLogger().error("Error connecting with the server. Code: {}", responseCesta.getStatus());
                } else {	
                    ClientMain.getLogger().info("Producto borrado correctamente");
                }
                WebTarget WebTargetLogin = webTarget.path("borrarCliente");
                Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
                Response response = invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
                if (response.getStatus() != Status.OK.getStatusCode()) {
                    ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
                } else {	
                    ClientMain.getLogger().info("Producto borrado correctamente");
                }
            } else {
				System.out.format("Error obtaining cesta. %s%n", responseEncontrar);
			}
        } catch (ProcessingException e) {
            System.out.format("Error obtaining cesta. %s%n", e.getMessage());
        }
        
    }
}