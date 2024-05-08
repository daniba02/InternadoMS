package Presentacion.Vista.Academia.Alumnos.ListarAlumnos;

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

import Negocio.Academia.Alumnos.TAlumnos;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIListarAlumnos extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers= {"ID", "DNI", "Nombre", "Apellidos", "Telefono", "Edad", "Activo"};
	private Collection<TAlumnos> lista;
	JPanel panelPrincipal;

	
	public GUIListarAlumnos() {
		super("LISTAR Alumnos");
		initGUI();
		setLocationRelativeTo(null);
	}

	
	public void initGUI() {
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaAlumnos=new JPanel(new BorderLayout());
		panelPrincipal.add(listaAlumnos,BorderLayout.CENTER);

		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE ALUMNOS");						
			listaAlumnos.setBorder(borde);
			
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
			
			listaAlumnos.add(ult,BorderLayout.CENTER);
			listaAlumnos.setVisible(true);
			
			
			
		}catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(450,450,700,450);
		
	}

	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ListarAlumnos_OK) {
			int it=0;
			lista=(Collection<TAlumnos>) context.getDato();
			
			if(lista!=null) {
				_dataTableModel.setNumRows(lista.size());
				
				for(TAlumnos a : lista) {
					_dataTableModel.setValueAt(a.getID(),it, 0);
					_dataTableModel.setValueAt(a.getDNI(), it, 1);
					_dataTableModel.setValueAt(a.getNombre(), it, 2);
					_dataTableModel.setValueAt(a.getApellido(), it, 3);
					_dataTableModel.setValueAt(a.getEdad(), it, 4);
					_dataTableModel.setValueAt(a.getTelefono(), it, 5);
					_dataTableModel.setValueAt(a.getActivo(), it, 6);
					it++;
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
		}
	}
	
}