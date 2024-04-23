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
import com.ikea.app.client.window.ProductList;
import com.ikea.app.client.controller.AdminLoginController;
import com.ikea.app.client.window.CestaWindow;
import com.ikea.app.client.window.ClientChangeEraseWindow;

public class MenuClienteWindow extends JFrame{

    protected JButton producto = new JButton("Comprar Productos");
    protected JButton cuenta = new JButton("Personalizar Cuenta");

	protected ProductList window2;
    protected CestaWindow window3;
    protected ClientChangeEraseWindow window4;

	protected AdminLoginController controller = new AdminLoginController();
	
    public MenuClienteWindow(WebTarget webTargets){

    Container cp = this.getContentPane();
	cp.setLayout(new GridLayout(2, 1)); 

    cp.add(producto);
    cp.add(cuenta);

    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setVisible(true);
	this.setSize(300,150);
	this.setTitle("Menu Cliente");
	this.setLocationRelativeTo(null);

    producto.addActionListener(new ActionListener() {
			
        @Override
        public void actionPerformed(ActionEvent e) {
            //Admin res = controller.loginAdmin(webTargets,usuario.getText());
            //window2 = new ProductList(webTargets);
            dispose();
        }
    });	

    cuenta.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
            //Admin res = controller.loginAdmin(webTargets,usuario.getText());
			//window3 = new ProductListAdmin(webTargets, res);
            //window4 = new ClientChangeEraseWindow(webTargets, res);
            dispose();
        }
    });	
}
}
