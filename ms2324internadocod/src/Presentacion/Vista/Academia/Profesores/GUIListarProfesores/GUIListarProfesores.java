package Presentacion.Vista.Academia.Profesores.GUIListarProfesores;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;



public class GUIListarProfesores extends JFrame implements IGUI {
	
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Profesor", "DNI", "Nombre Completo","Edad", "Activo", "Duracion"};
	private Collection<TProfesor> listaprofesores;
	JPanel panelPrincipal;
	
	public GUIListarProfesores() {
		super("LISTAR PROFESORES");
		initGUI();
		setLocationRelativeTo(null);
	}
	
	public void initGUI() {
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaPanel=new JPanel(new BorderLayout());
		panelPrincipal.add(listaPanel,BorderLayout.CENTER);

		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE PROFESORES");						
			listaPanel.setBorder(borde);
			
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
			listaPanel.add(ult,BorderLayout.CENTER);
			listaPanel.setVisible(true);
			
		} catch (Exception e) {
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
		if(context.getEvento()==Evento.ListarProfesores_OK) {
		int n=0;
		listaprofesores = (Collection<TProfesor>)context.getDato();
			if(listaprofesores!=null){
				_dataTableModel.setNumRows(listaprofesores.size());
				for(TProfesor p : listaprofesores){
					
					_dataTableModel.setValueAt(p.getId(),n,0);
				  	_dataTableModel.setValueAt(p.getDNI(),n,1);
					_dataTableModel.setValueAt(p.getNombreCompleto(),n,2);
					_dataTableModel.setValueAt(p.getEdad(),n,3);
					_dataTableModel.setValueAt(p.getActivo(), n, 4);
					
					if(p instanceof TInterino){
						_dataTableModel.setValueAt(((TInterino) p).getDuracion(), n, 5);
					}
					else{
						_dataTableModel.setValueAt("-", n, 5);

					}
					
					n++;
				 }
			 }
			panelPrincipal.revalidate();
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}
		
	}
	
}