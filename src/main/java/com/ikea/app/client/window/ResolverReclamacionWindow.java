package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.List;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Admin;
import com.ikea.app.pojo.Reclamacion;
import com.ikea.app.client.controller.HacerReclamacionWindowController;

public class ResolverReclamacionWindow extends JFrame{
    protected DefaultListModel<Reclamacion> modeloReclamaciones;
	protected JLabel labelReclamacion = new JLabel("Reclamaciones:");
    protected JList<Reclamacion> listaReclamaciones;
    protected JButton resolver = new JButton("Resolver reclamacion");
	protected HacerReclamacionWindowController controller = new HacerReclamacionWindowController();

    protected JFrame jFrame = new JFrame();
	
    public ResolverReclamacionWindow(WebTarget webTargets, Admin admin){
    List<Reclamacion> reclamacionesRecibidas = controller.sendReclamation(webTargets, admin);

    modeloReclamaciones = new DefaultListModel<Reclamacion>();
    for(Reclamacion reclamacion : reclamacionesRecibidas){
        modeloReclamaciones.addElement(reclamacion);
    }

    listaReclamaciones = new JList<Reclamacion>(modeloReclamaciones);
    listaReclamaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scrollHistorial = new JScrollPane(listaReclamaciones);
    if(modeloReclamaciones.isEmpty()){
        JOptionPane.showMessageDialog(jFrame, "No tiene reclamaciones pendientes");
    } else {
        Container cp = this.getContentPane();
	    cp.setLayout(new GridLayout(3, 1));
        cp.add(labelReclamacion);
        cp.add(scrollHistorial);
        cp.add(resolver);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    this.setVisible(true);
	    this.setSize(300,150);
	    this.setTitle("Resolver Reclamacion");
	    this.setLocationRelativeTo(null);   
    }

    resolver.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
            Reclamacion reclamacion = listaHistorial.getSelectedValue();
            JFrame jFrame = new JFrame();
            if(reclamacion != null){
                controller.resolverReclamacion(webTargets, reclamacion);
                JOptionPane.showMessageDialog(jFrame, "Se ha resuelto la reclamacion correctamente");
            } else {
                JOptionPane.showMessageDialog(jFrame, "Seleccione una reclamacion");
            }
        }	
    });

    }
}
