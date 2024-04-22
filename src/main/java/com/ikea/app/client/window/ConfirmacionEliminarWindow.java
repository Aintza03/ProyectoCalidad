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
import com.ikea.app.client.controller.ConfirmacionEliminarWindowController;

public class ConfirmacionEliminarWindow extends JFrame{
    protected ClientRegistration registrar;
    protected ClientLogin login;
    protected ClientChangeEraseWindow changeErase;
    protected ProductList listaProductos;
    protected CestaWindow cestaWindow;
    protected ConfirmacionEliminarWindowController controller = new ConfirmacionEliminarWindowController();
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
                controller.borrarCliente(webTargets, cliente);
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
}