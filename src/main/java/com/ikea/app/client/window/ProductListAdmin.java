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
import com.ikea.app.client.window.EditarProducto;
import com.ikea.app.client.controller.ProductListAdminController;
import com.ikea.app.client.window.ResolverReclamacionWindow;
import com.ikea.app.pojo.Admin;
/**Ventana que se usa para mostrar la lista de productos del cliente.
 */
public class ProductListAdmin extends JFrame{

	/**Lista de productos que se van a mostrar en la tabla. */
    protected List<Producto> productoList = new ArrayList<Producto>();
	/**Tabla que muestra los productos. */
    protected JTable tablaProductos;
	/**Modelo de la tabla que se usa para mostrar los productos. */
    protected DefaultTableModel modeloTablaProductos;
	/**Fila en la que se encuentra el raton. */
    protected int mouseRow = -1;
	/**Columna en la que se encuentra el raton. */
	protected int mouseCol = -1;
	/** WebTarget que se usa para llamar a los servicios REST. */
	protected WebTarget webTargets;
	/** Controller de esta ventana que guarda toda la funcionalidad. */
	protected ProductListAdminController controller = new ProductListAdminController();
	/** Ventana que se usa para editar un producto. */
	protected EditarProducto editarProducto;

	/** Constructor que crea toda la parte de interfaz grafica de esta ventana y gestiona los eventos llamando a la funcionalidad del controller. */
    public ProductListAdmin(WebTarget webTargets, Admin usuario){
        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(1,2));
		this.webTargets = webTargets;
		this.initTable();
        this.loadProducto(webTargets,usuario.getUsuario());
        JScrollPane scrollPaneProductos = new JScrollPane(tablaProductos);
        scrollPaneProductos.setBorder(new TitledBorder("Productos"));
        cp.add(scrollPaneProductos);

		JButton editarProducto = new JButton("Editar producto");

		JButton anadirProducto=new JButton("Añadir producto");
		
		JButton eliminarProducto=new JButton("Eliminar producto");

		editarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==editarProducto) {
					int row = tablaProductos.getSelectedRow();
					if (row != -1) {
						Producto producto= productoList.get(row);
						new EditarProducto(webTargets, producto, usuario);
						dispose();
					}
				}
			}
		});

		eliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==eliminarProducto) {
					int row = tablaProductos.getSelectedRow();
					if (row != -1) {
						Producto producto= productoList.get(row);
						controller.eliminarProducto(webTargets, producto);
						loadProducto(webTargets, usuario.getUsuario());
					}
				}
			}
		});
		
		JButton verListaPedidos = new JButton("Ver pedidos");
		verListaPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==verListaPedidos) {
					new ListaPedidosAdminWindow(webTargets, usuario);
				}
			}
		});

		JButton verReclamaciones = new JButton("Ver reclamaciones");
		verReclamaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==verReclamaciones) {
					new ResolverReclamacionWindow(webTargets, usuario);
				}
			}
		});

		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(2,1));
		cp.add(jpanel);
		jpanel.add(eliminarProducto);
		jpanel.add(editarProducto);
		jpanel.add(verListaPedidos);
    	jpanel.add(anadirProducto);
		jpanel.add(verReclamaciones);
    	anadirProducto.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==anadirProducto) {	
				new AnadirProducto(webTargets, usuario, ProductListAdmin.this);
			}
                
                }
            });
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(800,400);
        this.setTitle("Lista de Productos de Administrador");
        this.setLocationRelativeTo(null);
    }

    private void initTable() {
		Vector<String> cabeceraProducto = new Vector<String>(Arrays.asList( "ID","Nombre", "Tipo", "Precio"));				
		
		this.modeloTablaProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProducto) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};	
		this.tablaProductos = new JTable(this.modeloTablaProductos);
		tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		DefaultTableCellRenderer a = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public javax.swing.JComponent getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				
				if(!(value instanceof JButton)) {
				JLabel label = new JLabel(value.toString());
				
				label.setHorizontalAlignment(JLabel.CENTER);
						
				if (row % 2 == 0) {
					label.setForeground(Color.BLACK);
					label.setBackground(Color.CYAN);
				} else {
					label.setForeground(Color.BLACK);
					label.setBackground(Color.WHITE);
				}
				
				if (mouseRow == row ) {
					label.setBackground(Color.GREEN);
					label.setForeground(Color.BLACK);
				}

				if (isSelected) {
					label.setBackground(table.getSelectionBackground().BLUE);
					label.setForeground(table.getSelectionForeground().WHITE);
				}

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
    
	/** Metodo que se usa para cargar los productos en la tabla.*/
    protected void loadProducto(WebTarget webTarget, String usuario) {
		this.modeloTablaProductos.setRowCount(0);
		this.productoList = controller.datosDeProductos(webTarget,usuario);
		for (Producto a : this.productoList) {
			this.modeloTablaProductos.addRow( new Object[] {a.getId(),a.getNombre(), a.getTipo(), a.getPrecio()} );
		}		
	}
    }
    

