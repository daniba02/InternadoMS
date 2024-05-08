package Presentacion.Vista.Academia.Matricula.GUIListarMatriculas;

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

import Negocio.Academia.Matricula.TMatricula;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIListarMatriculas extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID", "ID Alumno","PrecioTotal","Descuento","Abierto","Activo"};
	private Collection<TMatricula> lista;
	JPanel panelPrincipal;
	
	public GUIListarMatriculas() {
		super("LISTAR MATRICULAS");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaMatriculas=new JPanel(new BorderLayout());
		panelPrincipal.add(listaMatriculas,BorderLayout.CENTER);
		
		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE MATRICULAS");						
			listaMatriculas.setBorder(borde);
			
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
			listaMatriculas.add(ult,BorderLayout.CENTER);
			listaMatriculas.setVisible(true);
			
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
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ListarMatricula_OK){
			int n=0;
			lista = (Collection<TMatricula>)context.getDato();
			if(lista!=null){
				_dataTableModel.setNumRows(lista.size());
				for(TMatricula ac : lista){
					
					_dataTableModel.setValueAt(ac.getId(),n,0);
				  	_dataTableModel.setValueAt(ac.getIdAlumno(),n,1);
					_dataTableModel.setValueAt(ac.getPreciototal(),n,2);
					_dataTableModel.setValueAt(ac.getDescuento(),n,3);
					_dataTableModel.setValueAt(ac.getAbierto(), n,4);
					_dataTableModel.setValueAt(ac.getActivo(), n,5);
					
					n++;
				 }
			 }
			panelPrincipal.revalidate();
			this.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.ERROR_MESSAGE);
			setVisible(false);

		}
		
	}

}