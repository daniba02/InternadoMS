package Presentacion.Vista.Restaurante.Ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Restaurante.Ventas.TVentas;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIListarVentas extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID", "Cliente","Fecha", "ID-Mesa", "Activo"};
	private Collection<TVentas> lista;
	JPanel panelPrincipal;
	
	public GUIListarVentas() {
		super("LISTAR VENTAS");
		initGUI();
		setLocationRelativeTo(null);	
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaVentas=new JPanel(new BorderLayout());
		panelPrincipal.add(listaVentas,BorderLayout.CENTER);

		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE VENTAS");						
			listaVentas.setBorder(borde);
			
			_dataTableModel = new DefaultTableModel(){
				private static final long serialVersionUID = 1L;

				@Override
		        public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModel.setColumnIdentifiers(headers);
			

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675,200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
	        JPanel ult=new JPanel(new BorderLayout());
			ult.add(tableScroll,BorderLayout.CENTER);
			listaVentas.add(ult,BorderLayout.CENTER);
			listaVentas.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(450,450,700,450);
	}

	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ListarVentas_OK) {
			int it = 0;
			lista = (Collection<TVentas>) context.getDato();
			
			if(lista != null) {
				_dataTableModel.setNumRows(lista.size());
				
				for(TVentas m : lista) {
					_dataTableModel.setValueAt(m.getId(),it,0);
					_dataTableModel.setValueAt(m.getCliente(),it,1);
					_dataTableModel.setValueAt(m.getFecha(),it,2);
					_dataTableModel.setValueAt(m.getIdMesa(),it,3);
					_dataTableModel.setValueAt(m.getActivo(),it,4);
					
					it++;
				}
			}
			panelPrincipal.revalidate();
			}else {
				setVisible(false);
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			}
	}
}