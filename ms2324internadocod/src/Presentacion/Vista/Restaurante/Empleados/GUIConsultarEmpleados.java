package Presentacion.Vista.Restaurante.Empleados;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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

import Negocio.Restaurante.Empleados.TCamarero;
import Negocio.Restaurante.Empleados.TCocinero;
import Negocio.Restaurante.Empleados.TEmpleado;
import Negocio.Restaurante.Empleados.VisitanteEmpleados;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIConsultarEmpleados extends JFrame implements IGUI, VisitanteEmpleados{

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Empleado","Nombre","SueldoPorHora","Horas","ID Turno","Tipo","Especialidad","Factor","Propinas","NominaTotal"};
	private JPanel panelPrincipal;
	
	public GUIConsultarEmpleados() {
		super("CONSULTAR EMPLEADOS");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initGUI() {
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaEmpleados=new JPanel(new BorderLayout());
		panelPrincipal.add(listaEmpleados,BorderLayout.CENTER);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idEmpleado = new JLabel(" ID Empleado: ");
		final JTextField tidEmpleado= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idEmpleado,BorderLayout.PAGE_START);
		panel3_1.add(tidEmpleado,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarEmpleados, Integer.parseInt(tidEmpleado.getText())));					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar ID correto", "", JOptionPane.ERROR_MESSAGE);
				} finally {
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
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "EMPLEADO CONSULTADO: ");						
			listaEmpleados.setBorder(borde);
			
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
			listaEmpleados.add(ult,BorderLayout.CENTER);
			listaEmpleados.add(panelBotones,BorderLayout.PAGE_END);
			listaEmpleados.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200,200,750,200);
	}


	@Override
	public void actualizar(Context context) {
		if(context.getEvento() == Evento.ConsultarEmpleados_OK) {
			_dataTableModel.setNumRows(1);
			
			TEmpleado e = (TEmpleado) context.getDato();
			
			_dataTableModel.setValueAt(e.getId(), 0, 0);
			_dataTableModel.setValueAt(e.getNombre(), 0, 1);
			_dataTableModel.setValueAt(e.getSueldoPorHora(), 0, 2);
			_dataTableModel.setValueAt(e.getHoras(), 0, 3);
			_dataTableModel.setValueAt(e.getTurno(), 0, 4);
			e.aceptar(this);
				
		}else {
			setVisible(false);
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
				
		}
	}

	@Override
	public void visitarCamarero(TCamarero tCamarero) {
		_dataTableModel.setValueAt("Camarero", 0, 5);
		_dataTableModel.setValueAt("-", 0, 6);
		_dataTableModel.setValueAt("-", 0, 7);
		_dataTableModel.setValueAt(tCamarero.getPropinas(), 0, 8);  
		_dataTableModel.setValueAt(tCamarero.calcularNominatotal(), 0, 9);  
	}

	@Override
	public void visitarCocinero(TCocinero tCocinero) {
		_dataTableModel.setValueAt("Cocinero", 0, 5);
		_dataTableModel.setValueAt(tCocinero.getEspecialidad(), 0, 6);
		_dataTableModel.setValueAt(tCocinero.getFactor(), 0, 7);
		_dataTableModel.setValueAt("-", 0, 8);  
		_dataTableModel.setValueAt(tCocinero.calcularNominatotal(), 0, 9);  
	}

}
