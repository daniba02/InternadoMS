package Presentacion.Vista.Academia.Alumnos.AlumnosAnio;

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

import Negocio.Academia.Alumnos.TAlumnos;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIAlumnosAnio extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;

	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	private DefaultTableModel _dataTableModel;
	private String[] headers = { "ID", "Nombre", "Apellido", "DNI", "Edad", "Telefono", "Estado" };
	private Collection<TAlumnos> lista;
	
	public GUIAlumnosAnio() {
		super("ALUMNOS ANIO");
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
		
		JLabel idAlumno = new JLabel(" ID ANIO: ");
		final JTextField tAnio= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idAlumno,BorderLayout.PAGE_START);
		panel3_1.add(tAnio,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					ApplicationController.getInstancia().handleRequest(new Context(Evento.AlumnoANIO, Integer.parseInt(tAnio.getText())));
				}
				catch(Exception ex1)
				{
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
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

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.AlumnoANIO_OK) {
			int it = 0;
			lista = (Collection<TAlumnos>) context.getDato();
			
			if(lista != null) {
				_dataTableModel.setNumRows(lista.size());
				
				for(TAlumnos a : lista) {
					_dataTableModel.setValueAt(a.getID(),it,0);
					_dataTableModel.setValueAt(a.getDNI(),it,1);
					_dataTableModel.setValueAt(a.getNombre(),it,2);
					_dataTableModel.setValueAt(a.getApellido(),it,3);
					_dataTableModel.setValueAt(a.getEdad(),it,4);
					_dataTableModel.setValueAt(a.getTelefono(),it,5);
					_dataTableModel.setValueAt(a.getActivo(),it,6);
					
					it++;
				}
			}
			panelPrincipal.revalidate();	
		
		}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
				this.setVisible(false);
		}
		
	}
	
}
