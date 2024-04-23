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

import com.ikea.app.pojo.Admin;
import com.ikea.app.client.controller.AdminLoginController;
import com.ikea.app.client.window.ProductListAdmin;

public class MenuAdminWindow extends JFrame{

    protected JButton producto = new JButton("Ver Productos");
    protected JButton cuenta = new JButton("Personalizar Cuenta");

	protected ProductListAdmin window2;
	protected AdminLoginController controller = new AdminLoginController();
	
    public MenuAdminWindow(WebTarget webTargets){

    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1)); 

    cp.add(producto);
    cp.add(cuenta);

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Menu Vendedor");
	this.setLocationRelativeTo(null);

    producto.addActionListener(new ActionListener() {
			
        @Override
        public void actionPerformed(ActionEvent e) {
            //Admin res = controller.loginAdmin(webTargets,usuario.getText());
            //window2 = new ProductListAdmin(webTargets, res);
            dispose();
        }
    });	

    cuenta.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
            //Admin res = controller.loginAdmin(webTargets,usuario.getText());
			//window2 = new ProductListAdmin(webTargets, res);
            dispose();
        }
    });	
}
}
