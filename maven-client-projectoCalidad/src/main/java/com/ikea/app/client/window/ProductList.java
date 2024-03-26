package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.client.ClientMain;

public class ProductList extends JFrame{

    protected ArrayList<Producto> productoList;
    protected JTable tablaProductos;
    protected DefaultTableModel modeloTablaProductos;


    public ProductList(WebTarget webTargets){
        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(2,1));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        cp.add(panel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(300,150);
        this.setTitle("Lista de Productos");
        this.setLocationRelativeTo(null);
    }
    
}
