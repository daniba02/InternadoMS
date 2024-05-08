package Presentacion.Vista.Restaurante.Empleados;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

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

public class GUIMostrarEmpleadosPorTurno extends JFrame implements IGUI,VisitanteEmpleados{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Empleado","Nombre","SueldoPorHora","Horas","ID Turno","Activo","Tipo","Especialidad","Factor","Propinas","NominaTotal"};
	private Collection<TEmpleado> lista;
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	private int it;
	
	public GUIMostrarEmpleadosPorTurno() {
		super("MOSTRAR EMPLEADOS POR TURNO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initGUI() {
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);

		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel id = new JLabel(" ID Turno: ");
		final JTextField tId = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(id, BorderLayout.PAGE_START);
		panel3_1.add(tId, BorderLayout.PAGE_START);

		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4, BorderLayout.PAGE_END);

		JPanel listaPaquetes = new JPanel(new BorderLayout());
		panelPrincipal.add(listaPaquetes, BorderLayout.CENTER);

		_dataTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		_dataTableModel.setColumnIdentifiers(headers);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		JPanel panelBotones = new JPanel();
		panelBotones.add(aceptar);
		panelBotones.add(cancelar);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setVisible(false);
					ApplicationController.getInstancia().handleRequest(
							new Context(Evento.MostrarEmpleadosPorTurno, Integer.parseInt(tId.getText())));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar id correto", "",
							JOptionPane.ERROR_MESSAGE);
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
		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)), "EMPLEADOS QUE TIENEN ESE TURNO: ");
			listaPaquetes.setBorder(borde);
			panel4.add(aceptar, BorderLayout.CENTER);
			panel4.add(cancelar, BorderLayout.CENTER);

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675, 200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
			JPanel ult = new JPanel();
			ult.add(tableScroll, BorderLayout.CENTER);
			listaPaquetes.add(ult, BorderLayout.CENTER);
			listaPaquetes.add(panelBotones, BorderLayout.PAGE_END);
			listaPaquetes.setVisible(true);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(450, 450, 705, 450);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.MostrarEmpleadosPorTurno_OK) {
			 it = 0;
			lista = (Collection<TEmpleado>) context.getDato();
			if (lista != null) {
				_dataTableModel.setNumRows(lista.size());
				for (TEmpleado emp : lista) {
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
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);

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
