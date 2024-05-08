package Presentacion.Vista.Academia.Alumnos.ConsultarAlumnos;

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

import Negocio.Academia.Alumnos.TAlumnos;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIConsultarAlumnos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	private DefaultTableModel _dataTableModel;
	private String[] headers = { "ID", "Nombre", "Apellido", "DNI", "Edad", "Telefono"};

	public GUIConsultarAlumnos() {
		super("CONSULTAR ALUMNOS");
		initGUI();
		setLocationRelativeTo(null);
	}

public void initGUI() {
		
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaAlumnos=new JPanel(new BorderLayout());
		panelPrincipal.add(listaAlumnos,BorderLayout.CENTER);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idAlumno = new JLabel(" ID Alumno: ");
		final JTextField tID= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idAlumno,BorderLayout.PAGE_START);
		panel3_1.add(tID,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Habria que comentar esta linea del controller
				try {
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarAlumnos, Integer.parseInt(tID.getText())));					
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar numero correcto", "", JOptionPane.ERROR_MESSAGE);
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
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "ALUMNO CONSULTADO: ");						
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
			listaAlumnos.add(panelBotones,BorderLayout.PAGE_END);
			listaAlumnos.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(200,200,600,200);
	}

	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ConsultarAlumnos_OK) {
			
			_dataTableModel.setNumRows(1);
			
			TAlumnos t = (TAlumnos) context.getDato();
			_dataTableModel.setValueAt(t.getID(), 0, 0);
			_dataTableModel.setValueAt(t.getNombre(), 0, 1);
			_dataTableModel.setValueAt(t.getApellido(), 0, 2);
			_dataTableModel.setValueAt(t.getDNI(), 0, 3);
			_dataTableModel.setValueAt(t.getEdad(), 0, 4);
			_dataTableModel.setValueAt(t.getTelefono(), 0, 5);
		}
		else
		{
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
		}
	}
}