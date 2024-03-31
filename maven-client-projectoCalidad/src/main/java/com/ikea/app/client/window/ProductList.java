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
public class ProductList extends JFrame{

    protected List<Producto> productoList = new ArrayList<Producto>();
    protected JTable tablaProductos;
    protected DefaultTableModel modeloTablaProductos;
	protected Cesta cesta;
    protected int mouseRow = -1;
	protected int mouseCol = -1;
	protected WebTarget webTargets;
	protected CestaWindow cestaWindow;
    public ProductList(WebTarget webTargets, String email){
        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(1,1));
		cesta = getCesta(webTargets,email);
		this.cestaWindow = new CestaWindow(webTargets,cesta);
        this.webTargets = webTargets;
		this.initTable();
        this.loadProducto(webTargets);
        JScrollPane scrollPaneProductos = new JScrollPane(tablaProductos);
        scrollPaneProductos.setBorder(new TitledBorder("Productos"));
        cp.add(scrollPaneProductos);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(300,150);
        this.setTitle("Lista de Productos");
        this.setLocationRelativeTo(null);


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
				
				if(col == 4) {
					try {
						int d = 0; 
						d = Integer.parseInt(modeloTablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0).toString());
						for (Producto producto : productoList) {
							if(producto.getId() == d) {
							modificarCesta(webTargets,producto);
							modeloTablaProductos.removeRow(row);
							}
							}
					} catch (Exception e2) {
						System.err.println("No se ha escogido animal");
					}	
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
		for(Producto a : datosDeProductos(webTarget)){
			if(!(idProductos.contains(a.getId()))) {
				this.productoList.add(a);
			}
		}
		for (Producto a : this.productoList) {
			this.modeloTablaProductos.addRow( new Object[] {a.getId(),a.getNombre(), a.getTipo(), a.getPrecio(), new JButton("->")} );
		}		
	}

    public List<Producto> datosDeProductos(WebTarget webTarget) {
        // issuing a GET request to the users endpoint with some query parameters
        try {
            Response response = webTarget.path("listProducts")
                .request(MediaType.APPLICATION_JSON)
                .get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                // the response is a generic type (a List<User>)
                GenericType<List<Producto>> listType = new GenericType<List<Producto>>(){};
                List<Producto> product = response.readEntity(listType);
                //System.out.println(product);
				return product;
            } else {
				System.out.format("Error obtaining product list. %s%n", response);
				return new ArrayList<Producto>();
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining product list. %s%n", e.getMessage());
			return new ArrayList<Producto>();
        }

	}
	public Cesta getCesta(WebTarget webTarget,String email){
		try {
            Response response = webTarget.path("cesta")
                .queryParam("email", email)
				.request(MediaType.APPLICATION_JSON)
				.get();

            // check that the response was HTTP OK
            if (response.getStatusInfo().toEnum() == Status.OK) {
                Cesta cesta = response.readEntity(Cesta.class);
				System.out.println(cesta);
				return cesta;		
            } else {
				System.out.format("Error obtaining cesta. %s%n", response);
				return null;
            }
        } catch (ProcessingException e) {
            System.out.format("Error obtaining cesta. %s%n", e.getMessage());
			return null;
        }	
	}
	public void modificarCesta(WebTarget webTarget,Producto producto) {
		WebTarget WebTargetLogin = webTarget.path("modifyCesta");
		Invocation.Builder invocationBuilder = WebTargetLogin.request(MediaType.APPLICATION_JSON);
		this.cesta.anadirCesta(producto);
		Response response = invocationBuilder.post(Entity.entity(this.cesta, MediaType.APPLICATION_JSON));
		if (response.getStatus() != Status.OK.getStatusCode()) {
			ClientMain.getLogger().error("Error connecting with the server. Code: {}", response.getStatus());
		} else {	
			ClientMain.getLogger().info("Cesta modificada correctamente");
		}
	}
}
    

