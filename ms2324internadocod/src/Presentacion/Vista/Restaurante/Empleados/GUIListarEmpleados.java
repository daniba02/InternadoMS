package Presentacion.Vista.Restaurante.Empleados;

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

import Negocio.Restaurante.Empleados.TCamarero;
import Negocio.Restaurante.Empleados.TCocinero;
import Negocio.Restaurante.Empleados.TEmpleado;
import Negocio.Restaurante.Empleados.VisitanteEmpleados;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIListarEmpleados extends JFrame implements IGUI,VisitanteEmpleados{

	private static final long serialVersionUID = 1L;
	

	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Empleado","Nombre","SueldoPorHora","Horas","ID Turno","Activo","Tipo","Especialidad","Factor","Propinas","NominaTotal"};
	private Collection<TEmpleado> lista;
	JPanel panelPrincipal;
	private int it;
	
	public GUIListarEmpleados() {
		super("LISTAR EMPLEADOS");
		it=0;
		initGUI();
		setLocationRelativeTo(null);
	}
	
	public void initGUI() {
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaPanel=new JPanel(new BorderLayout());
		panelPrincipal.add(listaPanel,BorderLayout.CENTER);

		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE EMPLEADOS");						
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
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ListarEmpleados_OK) {
		 this.it=0;
		lista = (Collection<TEmpleado>) context.getDato();
			if(lista!=null){
				_dataTableModel.setNumRows(lista.size());
				for(TEmpleado emp : lista){
					
					_dataTableModel.setValueAt(emp.getId(),it,0);
				  	_dataTableModel.setValueAt(emp.getNombre(),it,1);
					_dataTableModel.setValueAt(emp.getSueldoPorHora(),it,2);
					_dataTableModel.setValueAt(emp.getHoras(),it,3);
					_dataTableModel.setValueAt(emp.getTurno(), it, 4);
					_dataTableModel.setValueAt(emp.getActivo(), it, 5);
					emp.aceptar(this);
					
					it++;
				 }
			 }
			panelPrincipal.revalidate();
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}
		
	}
	
	@Override
	public void visitarCamarero(TCamarero tCamarero) {
		// TODO Auto-generated method stub
		_dataTableModel.setValueAt("Camarero", it, 6);
		_dataTableModel.setValueAt("-", it, 7);
		_dataTableModel.setValueAt("-", it, 8);
		_dataTableModel.setValueAt(tCamarero.getPropinas(), it, 9);  
		_dataTableModel.setValueAt(tCamarero.calcularNominatotal(), it, 10);  
	}
	
	@Override
	public void visitarCocinero(TCocinero tCocinero) {
		// TODO Auto-generated method stub
		_dataTableModel.setValueAt("Cocinero", it, 6);
		_dataTableModel.setValueAt(tCocinero.getEspecialidad(), it, 7);
		_dataTableModel.setValueAt(tCocinero.getFactor(), it, 8);
		_dataTableModel.setValueAt("-", it, 9);  
		_dataTableModel.setValueAt(tCocinero.calcularNominatotal(), it, 10);  
	}

}
