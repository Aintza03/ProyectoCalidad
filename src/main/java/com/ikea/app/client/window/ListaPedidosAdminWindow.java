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
import com.ikea.app.client.controller.ListaPedidosAdminController;
import com.ikea.app.pojo.Admin;
public class ListaPedidosAdminWindow extends JFrame{

    protected List<Producto> productoList = new ArrayList<Producto>();
    protected JTable tablaPedidos;
    protected DefaultTableModel modeloTablaProductos;
    protected int mouseRow = -1;
	protected int mouseCol = -1;
	protected WebTarget webTargets;
	protected ListaPedidosAdminController controller = new ListaPedidosAdminController();

    public ListaPedidosAdminWindow(WebTarget webTargets, Admin usuario){
        Container cp = this.getContentPane();
        cp.setLayout(new GridLayout(1,2));
		this.webTargets = webTargets;
		this.initTable();
        this.loadPedidos(webTargets,usuario.getUsuario());
        JScrollPane scrollPaneProductos = new JScrollPane(tablaPedidos);
        scrollPaneProductos.setBorder(new TitledBorder("Pedidos"));
        cp.add(scrollPaneProductos);
		
		JButton eliminarProducto=new JButton("Eliminar pedido");
		eliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource()==eliminarProducto) {
					int row = tablaPedidos.getSelectedRow();
					if (row != -1) {
						Producto producto= productoList.get(row);
						controller.eliminarPedido(webTargets, producto);
						JFrame jFrame = new JFrame();
						JOptionPane.showMessageDialog(jFrame, "Se ha eliminado el pedido "+ producto.getNombre() + " Notificando al comprador y la entidad bancaria para la devolución del dinero");
						loadPedidos(webTargets, usuario.getUsuario());
					}
				}
			}
		});
		
		
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(2,1));
		cp.add(jpanel);
		jpanel.add(eliminarProducto);

    	
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(400,200);
        this.setTitle("Lista de Pedidos de Administrador");
        this.setLocationRelativeTo(null);
    }

    private void initTable() {
		//Cabecera del modelo de datos
		Vector<String> cabeceraProducto = new Vector<String>(Arrays.asList( "ID","Nombre", "Tipo", "Precio"));				
		//Se crea el modelo de datos para la tabla de comics sÃ³lo con la cabecera	
		
		this.modeloTablaProductos = new DefaultTableModel(new Vector<Vector<Object>>(), cabeceraProducto) {
			
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		//Se crea la tabla de comics con el modelo de datos		
		this.tablaPedidos = new JTable(this.modeloTablaProductos);
		tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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
		
		for(int i = 0; i < this.tablaPedidos.getColumnModel().getColumnCount(); i++ ) {
			this.tablaPedidos.getColumnModel().getColumn(i).setCellRenderer(a);
			
		}
					
			
		this.tablaPedidos.addMouseListener(new MouseAdapter() {						
			@Override
			public void mousePressed(MouseEvent e) {
				int row = tablaPedidos.rowAtPoint(e.getPoint());
				int col = tablaPedidos.columnAtPoint(e.getPoint());
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = tablaPedidos.rowAtPoint(e.getPoint());
				int col = tablaPedidos.columnAtPoint(e.getPoint());
			}
			
			
			@Override
            public void mouseClicked(MouseEvent e) {
				int row = tablaPedidos.rowAtPoint(e.getPoint());
				int col = tablaPedidos.columnAtPoint(e.getPoint());
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				int row = tablaPedidos.rowAtPoint(e.getPoint());
				int col = tablaPedidos.columnAtPoint(e.getPoint());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				int row = tablaPedidos.rowAtPoint(e.getPoint());
				int col = tablaPedidos.columnAtPoint(e.getPoint());
				//Cuando el ratÃ³n sale de la tabla, se resetea la columna/fila sobre la que estÃ¡ el ratÃ³n				
				mouseRow = -1;
				mouseCol = -1;
			}
			
		});
		
		tablaPedidos.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = tablaPedidos.rowAtPoint(e.getPoint());
				int col = tablaPedidos.columnAtPoint(e.getPoint());
				mouseRow = row;
				mouseCol = col;
				
				repaint();

			}
		});
		
		
	}
    
    protected void loadPedidos(WebTarget webTarget, String usuario) {
		this.modeloTablaProductos.setRowCount(0);
		this.productoList = controller.verPedidos(webTarget,usuario);
		for (Producto a : this.productoList) {
			this.modeloTablaProductos.addRow( new Object[] {a.getId(),a.getNombre(), a.getTipo(), a.getPrecio()} );
		}		
	}
    }
    


