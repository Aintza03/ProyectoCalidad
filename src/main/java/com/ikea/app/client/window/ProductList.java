package com.ikea.app.client.window;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.TitledBorder;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.core.config.builder.api.Component;

import com.ikea.app.pojo.Cliente;
import com.ikea.app.pojo.Producto;
import com.ikea.app.pojo.Cesta;
import com.ikea.app.client.ClientMain;
import com.ikea.app.client.window.CestaWindow;
import com.ikea.app.client.controller.ProductListController;
public class ProductList extends JFrame{

    protected List<Producto> productoList = new ArrayList<Producto>();
    protected JTable tablaProductos;
    protected DefaultTableModel modeloTablaProductos;

	protected Cesta cesta;

    protected int mouseRow = -1;
	protected int mouseCol = -1;

	protected WebTarget webTargets;
	protected CestaWindow cestaWindow;
	protected JLabel imagenProducto;

	protected ProductListController controller = new ProductListController();

	protected JLabel idProducto = new JLabel();
	protected JLabel nombre = new JLabel();
	protected JLabel tipo = new JLabel();
	protected JLabel precio = new JLabel();

	protected JComboBox combo;

    public ProductList(WebTarget webTargets, String email){
        Container cp = this.getContentPane();
        cp.setLayout(new GridBagLayout());
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(4,1));
		container.add(idProducto);
		container.add(nombre);
		container.add(tipo);
		container.add(precio);

		GridBagConstraints constraints = new GridBagConstraints();
		cesta = controller.getCesta(webTargets,email);
		this.cestaWindow = new CestaWindow(webTargets,cesta);
        this.webTargets = webTargets;
		this.initTable();
        this.loadProducto(webTargets);

		combo = new JComboBox();
		combo.addItem("Salon");
		combo.addItem("Baño");
		combo.addItem("Habitacion");
		combo.addItem("Comedor");
		combo.addItem("Jardin");
		combo.addItem("Entrada");
		combo.addItem("Productos ordenados por Tipo:");

		 RowFilter<Object,Object> startsWithAFilter = new RowFilter<Object,Object>() {
			   public boolean include(Entry<? extends Object, ? extends Object> entry) {
			     
			       if (entry.getStringValue(1).equals("Salon")) {
			         // The value starts with "a", include it
			         return true;
			       }else {
			     
			     // None of the columns start with "a"; return false so that this
			     // entry is not shown
			     return false;
			       }
			   }
			 };
			 RowFilter<Object,Object> startsWithAFilter1 = new RowFilter<Object,Object>() {
				   public boolean include(Entry<? extends Object, ? extends Object> entry) {
				     
				       if (entry.getStringValue(1).equals("Baño")) {
				         // The value starts with "a", include it
				         return true;
				       }else {
				     
				     // None of the columns start with "a"; return false so that this
				     // entry is not shown
				     return false;
				       }
				   }
				 };
			RowFilter<Object,Object> startsWithAFilter2 = new RowFilter<Object,Object>() {
				   public boolean include(Entry<? extends Object, ? extends Object> entry) {
				     
				       if (entry.getStringValue(1).equals("Habitacion")) {
				         // The value starts with "a", include it
				         return true;
				       }else {
				     
				     // None of the columns start with "a"; return false so that this
				     // entry is not shown
				     return false;
				       }
				   }
				 };	
				 RowFilter<Object,Object> startsWithAFilter3 = new RowFilter<Object,Object>() {
				   public boolean include(Entry<? extends Object, ? extends Object> entry) {
				     
				       if (entry.getStringValue(1).equals("Comedor")) {
				         // The value starts with "a", include it
				         return true;
				       }else {
				     
				     // None of the columns start with "a"; return false so that this
				     // entry is not shown
				     return false;
				       }
				   }
				 }; 
				 RowFilter<Object,Object> startsWithAFilter4 = new RowFilter<Object,Object>() {
				   public boolean include(Entry<? extends Object, ? extends Object> entry) {
				     
				       if (entry.getStringValue(1).equals("Jardin")) {
				         // The value starts with "a", include it
				         return true;
				       }else {
				     
				     // None of the columns start with "a"; return false so that this
				     // entry is not shown
				     return false;
				       }
				   }
				 };
				 RowFilter<Object,Object> startsWithAFilter5 = new RowFilter<Object,Object>() {
				   public boolean include(Entry<? extends Object, ? extends Object> entry) {
				     
				       if (entry.getStringValue(1).equals("Entrada")) {
				         // The value starts with "a", include it
				         return true;
				       }else {
				     
				     // None of the columns start with "a"; return false so that this
				     // entry is not shown
				     return false;
				       }
				   }
				 };
			 combo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(combo.getSelectedIndex() == 0) {
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaProductos);
							sorter.setRowFilter(startsWithAFilter);
							tablaProductos.setRowSorter(sorter);
						}else if(combo.getSelectedIndex() == 1){
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaProductos);
							sorter.setRowFilter(startsWithAFilter1);
							tablaProductos.setRowSorter(sorter);}
						else if(combo.getSelectedIndex() == 2){
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaProductos);
							sorter.setRowFilter(startsWithAFilter2);
							tablaProductos.setRowSorter(sorter);}
						else if(combo.getSelectedIndex() == 3){
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaProductos);
							sorter.setRowFilter(startsWithAFilter3);
							tablaProductos.setRowSorter(sorter);}
						else if(combo.getSelectedIndex() == 4){
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaProductos);
							sorter.setRowFilter(startsWithAFilter4);
							tablaProductos.setRowSorter(sorter);}
						else if(combo.getSelectedIndex() == 5){
							TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modeloTablaProductos);
							sorter.setRowFilter(startsWithAFilter5);
							tablaProductos.setRowSorter(sorter);
						}else if(combo.getSelectedIndex() == 6) {
							tablaProductos.setRowSorter(null);
						}
						}	
					
				});

        JScrollPane scrollPaneProductos = new JScrollPane(tablaProductos);
        scrollPaneProductos.setBorder(new TitledBorder("Productos"));
        constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_START;
		cp.add(scrollPaneProductos, constraints);
		cp.add(combo, constraints);
		imagenProducto = new JLabel("");
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.ipady = 0;
		JPanel total = new JPanel();
		total.setLayout(new GridLayout(1,2));
		total.add(imagenProducto);
		total.add(container);
		cp.add(total, constraints);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(400,200);
        this.setTitle("Lista de Productos");
        this.setLocationRelativeTo(null);
    	this.setLocation(450,400);
    }

    private void initTable() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraProducto = new Vector<String>(Arrays.asList( "ID","Nombre", "Tipo", "Precio","Anadir"));				
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
			
			
			@Override
            public void mouseClicked(MouseEvent e) {
				int row = tablaProductos.rowAtPoint(e.getPoint());
				int col = tablaProductos.columnAtPoint(e.getPoint());
				int d = 0; 
				d = Integer.parseInt(modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0).toString());		
				if(col == 4) {
					try {
						for (Producto producto : productoList) {
							if(producto.getId() == d) {
							cesta.anadirCesta(producto);
							cestaWindow.addProducto(producto);
							controller.modificarCesta(webTargets, cesta);
							modeloTablaProductos.removeRow(row);
							}
							}
							imagenProducto.setIcon(null);
							idProducto.setText("");
							nombre.setText("");
							tipo.setText("");
							precio.setText("");
							setSize(400,200);
					} catch (Exception e2) {
						System.err.println("No se ha escogido animal");
					}	
				} else{
					idProducto.setText("ID: " + d);
					nombre.setText("Nombre: " + modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 1).toString());
					tipo.setText("Tipo: " + modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 2).toString());
					precio.setText("Precio: " + modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 3).toString());nombre.setText("Nombre: " + modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0).toString());//nombre
					Image image = new ImageIcon("src/main/resources/MuebleFotos/"+ d + ".jpg").getImage();
					ImageIcon image2 = new ImageIcon(image.getScaledInstance(78,124,Image.SCALE_SMOOTH));
					imagenProducto.setIcon(image2);
					setSize(400 + imagenProducto.getIcon().getIconWidth(),200+imagenProducto.getIcon().getIconHeight()); 
				
				}
			}
			
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
    
    private void loadProducto(WebTarget webTarget) {
		this.modeloTablaProductos.setRowCount(0);
		List<Integer> idProductos = new ArrayList<Integer>();
		for (Producto a: this.cesta.getCesta()){
			idProductos.add(a.getId());
		}
		
		for(Producto a : controller.datosDeProductos(webTarget)){
			if(!(idProductos.contains(a.getId()))) {
				this.productoList.add(a);
			}
		}
		for (Producto a : this.productoList) {
			this.modeloTablaProductos.addRow( new Object[] {a.getId(),a.getNombre(), a.getTipo(), a.getPrecio(), new JButton("->")} );
		}		
	}
}
    

