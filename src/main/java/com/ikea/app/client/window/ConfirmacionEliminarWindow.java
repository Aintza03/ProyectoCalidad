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

public class ConfirmacionEliminarWindow extends JFrame{
	protected JLabel labelWarning = new JLabel("  ¿Seguro que quieres borrar la cuenta?");
    protected JButton si = new JButton("Si");
    protected JButton no = new JButton("No");

	public ConfirmacionEliminarWindow(WebTarget webTargets, String mail, String contra, String nom) {
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
                dispose();  
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
