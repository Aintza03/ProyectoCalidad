package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

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

import org.apache.logging.log4j.core.config.builder.api.Component;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;

import com.ikea.app.client.ClientMain;

public class ProductList extends JFrame{

    protected ArrayList<Producto> productoList;
    protected JTable tablaProductos;
    protected DefaultTableModel modeloTablaProductos;

    protected int mouseRow = -1;
	protected int mouseCol = -1;


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

        cp.add(new JScrollPane(this.tablaProductos));

        this.initTable();
        this.loadProducto();


    }

    private void initTable() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraProducto = new Vector<String>(Arrays.asList( "Nombre", "Tipo", "Precio", "Cantidad"));				
		//Se crea el modelo de datos para la tabla de comics sÃ³lo con la cabecera	
		
		this.modeloTablaProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProducto) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		//Se crea la tabla de comics con el modelo de datos		
		this.tablaProductos = new JTable(this.modeloTablaProductos);
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Render para las celdas de la Editorial se define como un Label un logo
		DefaultTableCellRenderer a = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public javax.swing.JComponent getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				
				if(!(value instanceof JButton)) {
				JLabel label = new JLabel(value.toString());
				
				//El label se alinea a la izquierda
				label.setHorizontalAlignment(JLabel.CENTER);
						
				//Se diferencia el color de fondo en filas pares e impares
				if (row % 2 == 0) {
					label.setForeground(Color.BLACK);
					label.setBackground(Color.CYAN);
				} else {
					label.setForeground(Color.BLACK);
					label.setBackground(Color.WHITE);
				}
				
				//Si la celda estÃ¡ seleccionada se asocia un color de fondo y letra
				if (mouseRow == row ) {
					label.setBackground(Color.GREEN);
					label.setForeground(Color.BLACK);
				}
				
				//Si la celda estÃ¡ seleccionada se asocia un color de fondo y letra
				if (isSelected) {
					label.setBackground(table.getSelectionBackground().BLUE);
					label.setForeground(table.getSelectionForeground().WHITE);
				}

				//Es necesaria esta sentencia para pintar correctamente el color de fondo
				label.setOpaque(true);
				
				return label;
                
			}else {
                JButton boton1 = (JButton) value;
                    return boton1;
            }
        }
		};
		
		for(int i = 0; i < this.tablaProductos.getColumnModel().getColumnCount(); i++ ) {
			this.tablaProductos.getColumnModel().getColumn(i).setCellRenderer(a);
			
		}
					
			
		this.tablaProductos.addMouseListener(new MouseAdapter() {						
			@Override
			public void mousePressed(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
			}
			
			
			/*@Override
            public void mouseClicked(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				
				if(col == 5) {
					try {
						int d = 0; 
						d = Integer.parseInt(modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0).toString());
						for (Animal animal : animales) {
							if(animal.getId() == d) {
						v.actualizarAnimal(animal, dni, null);
						modeloDatosAnimales.removeRow(row);
							}
							}
					} catch (Exception e2) {
						System.err.println("No se ha escogido animal");
					}	
				}
			}*/
			
			@Override
			public void mouseEntered(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				//Cuando el ratÃ³n sale de la tabla, se resetea la columna/fila sobre la que estÃ¡ el ratÃ³n				
				mouseRow = -1;
				mouseCol = -1;
			}
			
		});
		
		tablaProductos.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				mouseRow = row;
				mouseCol = col;
				
				repaint();

			}
		});
		
		
	}
    
    private void loadProducto() {
		//Se borran los datos del modelo de datos Animales
		this.modeloTablaProductos.setRowCount(0);

        Producto b = new Producto();
        b.setNombre("Mesa");
        b.setPrecio(100.0);
        b.setTipo("Mueble");
        b.setCantidad(1);
        productoList.add(b);
		
		//Se aÃ±ade al modelo una fila de datos por cada comic
		for (Producto a : this.productoList) {
			this.modeloTablaProductos.addRow( new Object[] {a.getNombre(), a.getTipo(), a.getPrecio(),  a.getCantidad(), new JButton("->")} );
		}		
	}
}
    

