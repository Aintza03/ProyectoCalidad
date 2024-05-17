package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import javax.ws.rs.ProcessingException;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.controller.HacerReclamacionWindowController;

public class HacerReclamacionWindow extends JFrame{
    protected JButton reclamacion = new JButton("Enviar Reclamacion");
    protected JLabel labelText = new JLabel("Escriba su reclamacion");
    protected JTextField reclamacionText = new JTextField();
    protected HacerReclamacionWindowController controller = new HacerReclamacionWindowController();
    
    public HacerReclamacionWindow(WebTarget webTargets, Producto producto, Cliente cliente) {
        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(3, 1));
        cp.add(labelText);
        cp.add(reclamacionText);
        cp.add(reclamacion);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500,200);
        this.setTitle("Datos del Cliente");
        this.setLocationRelativeTo(null);

        reclamacion.addActionListener(new ActionListener() {
                
            @Override
            public void actionPerformed(ActionEvent e) {
                String reclamacion = reclamacionText.getText();
                controller.hacerReclamacion(webTargets, reclamacion, cliente, producto);
                JFrame jFrame = new JFrame();
                JOptionPane.showMessageDialog(jFrame, "Se ha enviado la reclamacion correctamente");
                dispose();
            }
        });
    }
}