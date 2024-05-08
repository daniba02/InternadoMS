package Presentacion.Vista.Restaurante.Platos;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Platos.TComida;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Negocio.Restaurante.Platos.VisitantePlatos;
import Negocio.Restaurante.Producto.TProducto;
import Presentacion.IGUI.IGUI;
import Presentacion.Vista.Restaurante.GUIutilities;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class ConsultarPlatos extends JFrame implements IGUI,VisitantePlatos {

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Plato","Nombre","Stock","Precio","Productos","Temperatura", "Tipo"};
	private JPanel panelPrincipal;

	public ConsultarPlatos() {
		super("CONSULTAR PLATOS");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

public void initGUI() {
		
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaPlatos=new JPanel(new BorderLayout());
		panelPrincipal.add(listaPlatos,BorderLayout.CENTER);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idPlato = new JLabel(" ID Plato: ");
		final JTextField tidPlato= new JTextField(20);
		GUIutilities.onlyIntegers(tidPlato);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idPlato,BorderLayout.PAGE_START);
		panel3_1.add(tidPlato,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer id;
					try{
						id=Integer.parseInt(tidPlato.getText());
					}catch(Exception ex){
						throw new Exception("El id no puede ser nulo");
					}
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarPlatos, id));					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					setVisible(false);
				}
			}
		});

		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		JPanel panelBotones=new JPanel();
		panelBotones.add(aceptar);
		panelBotones.add(cancelar);
		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "PLATO CONSULTADO: ");						
			listaPlatos.setBorder(borde);
			
			_dataTableModel = new DefaultTableModel(){
				private static final long serialVersionUID = 1L;

				@Override
		        public boolean isCellEditable(int row, int column) {
					if(column ==4){
						return true;
					}else{
						return false;
					}
				}
			};
			_dataTableModel.setColumnIdentifiers(headers);
			JTable table = new JTable(_dataTableModel){
				private static final long serialVersionUID = 1L;

				@Override
	            public TableCellEditor getCellEditor(int row, int column) {
	                if (column == 4) {
	                    return new LongTextCellEditor();
	                }
	                return super.getCellEditor(row, column);
	            }
			};
			int columnIndex = 4;  // El índice de la columna que contiene información extensa

			table.getColumnModel().getColumn(columnIndex).setCellEditor(new LongTextCellEditor());

			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675,200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
	        JPanel ult=new JPanel(new BorderLayout());
			ult.add(tableScroll,BorderLayout.CENTER);
			listaPlatos.add(ult,BorderLayout.CENTER);
			listaPlatos.add(panelBotones,BorderLayout.PAGE_END);
			listaPlatos.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200,200,800,200);
	}

	public void actualizar(Context context) {
	if(context.getEvento()==Evento.ConsultarPlatos_OK) {
		int n=1;
		TPlatoCompleto toapl = (TPlatoCompleto)context.getDato();
		if(toapl!=null)_dataTableModel.setNumRows(1);
	 	_dataTableModel.setValueAt(toapl.getPlato().getId(),0,0);
        _dataTableModel.setValueAt(toapl.getPlato().getNombre(),0,1);
	    _dataTableModel.setValueAt(toapl.getPlato().getStock(),0,2);
	    _dataTableModel.setValueAt(toapl.getPlato().getPrecio(),0,3);
	    Collection<TProducto> productos = toapl.getProductos();
	    String productos_nombre="";
	    if(productos.size()>0){
	    Iterator<TProducto> it=productos.iterator();
	    productos_nombre = it.next().getNombre();
	    while(n < toapl.getProductos().size()){
	    	productos_nombre += ", " +it.next().getNombre();
	    n++;
	    }
	    }
	    _dataTableModel.setValueAt(productos_nombre,0,4);
	    toapl.aceptar(this);
	}else {
		setVisible(false);
		JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
	}
	}
	
	static class LongTextCellEditor extends DefaultCellEditor {
	    private static final long serialVersionUID = 1L;
	    private JButton button;
	    private String value;

	    public LongTextCellEditor() {
	        super(new JTextField());

	        button = new JButton("Ver Todo");
	        button.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JOptionPane.showMessageDialog(button, value, "Contenido Completo", JOptionPane.PLAIN_MESSAGE);
	            }
	        });

	        editorComponent = new JPanel(new BorderLayout());
	        ((JPanel) editorComponent).add(button, BorderLayout.EAST);
	        clickCountToStart = 2; // Hacer clic dos veces para activar el editor
	    }

	    @Override
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	        this.value = (String) value;
	        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	    }

	    @Override
	    public Object getCellEditorValue() {
	        return value;
	    }
	}

	@Override
	public void visitaComida(TComida tComida) {
		// TODO Auto-generated method stub
		_dataTableModel.setValueAt("-",0,5);
		_dataTableModel.setValueAt(tComida.getTipo(),0,6);
	}

	@Override
	public void visitaBebida(TBebida tBebida) {
		// TODO Auto-generated method stub
		_dataTableModel.setValueAt(tBebida.getTemperatura(),0,5);
		_dataTableModel.setValueAt("-",0,6);
	}

}