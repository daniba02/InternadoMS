package Presentacion.Vista.Academia.Matriculable.GUIConsultarMatriculable;

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

import Negocio.Academia.Matriculable.TOAMatriculable;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIConsultarMatriculable extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID", "Precio","Plazas","Hora", "Anio", "Grupo", "Asignatura"};
	private JPanel panelPrincipal;

	public GUIConsultarMatriculable() {
		super("CONSULTAR MATRICULABLE");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

public void initGUI() {
		
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaMatriculables=new JPanel(new BorderLayout());
		panelPrincipal.add(listaMatriculables,BorderLayout.CENTER);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idMatriculable = new JLabel(" ID Matriculable: ");
		final JTextField tidMatriculable= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idMatriculable,BorderLayout.PAGE_START);
		panel3_1.add(tidMatriculable,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Habria que comentar esta linea del controller
				try {
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarInformacionMatriculable, Integer.parseInt(tidMatriculable.getText())));					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar ID correto", "", JOptionPane.ERROR_MESSAGE);
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
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "MATRICULABLE CONSULTADO: ");						
			listaMatriculables.setBorder(borde);
			
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
			listaMatriculables.add(ult,BorderLayout.CENTER);
			listaMatriculables.add(panelBotones,BorderLayout.PAGE_END);
			listaMatriculables.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200,200,600,200);
	}

	public void actualizar(Context context) {
	if(context.getEvento()==Evento.ConsultarInformacionMatriculable_OK) {
		
		TOAMatriculable toam = (TOAMatriculable)context.getDato();
		if(toam!=null)_dataTableModel.setNumRows(1);
	    _dataTableModel.setValueAt(toam.getMatriculable().getId(),0,0);
	    _dataTableModel.setValueAt(toam.getMatriculable().getPrecio(),0,1);
	    _dataTableModel.setValueAt(toam.getMatriculable().getPlazas(),0,2);
	    _dataTableModel.setValueAt(toam.getMatriculable().getHora(),0,3);
	    _dataTableModel.setValueAt(toam.getAnio().getAnio(),0,4);
	    _dataTableModel.setValueAt(toam.getGrupo().getLetra(),0,5);
	    _dataTableModel.setValueAt(toam.getAsignatura().getNombre(),0,6);
	    panelPrincipal.revalidate();
	
	}else {
		setVisible(false);
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			
	}
	}

}