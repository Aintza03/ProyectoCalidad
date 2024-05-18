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
/**Ventana que se usa para resolver la reclamacion. */
public class ResolverReclamacionWindow extends JFrame{
    /**Modelo de la lista de reclamaciones. */
    protected DefaultListModel<Reclamacion> modeloReclamaciones;
    /**Label que muestra un mensaje para indicar donde hay que seleccionar la reclamacion. */
	protected JLabel labelReclamacion = new JLabel("Reclamaciones:");
    /**Lista de reclamaciones que se van a mostrar en la tabla. */
    protected JList<Reclamacion> listaReclamaciones;
    /**Boton que llama a la funcion del controller para resolver la reclamacion. */
    protected JButton resolver = new JButton("Resolver reclamacion");
    /**Controller de esta ventana que guarda toda la funcionalidad. */
	protected HacerReclamacionWindowController controller = new HacerReclamacionWindowController();
    /**Ventana que se usa para mostrar mensajes. */
    protected JFrame jFrame = new JFrame();
    /** Constructor que crea la interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
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
            Reclamacion reclamacion = listaReclamaciones.getSelectedValue();
            JFrame jFrame = new JFrame();
            if(reclamacion != null){
                controller.resolverReclamacion(webTargets, reclamacion);
                JOptionPane.showMessageDialog(jFrame, "Se ha resuelto la reclamacion correctamente");
                modeloReclamaciones.removeElement(reclamacion);
            } else {
                JOptionPane.showMessageDialog(jFrame, "Seleccione una reclamacion");
            }
        }	
    });

    }
}
