package Presentacion.Vista.Academia.Matricula.GUIConsultarNotaMatricula;

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

import Negocio.Academia.MatriculaMatriculable.TMatriculaMatriculable;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIConsultarNotaMatricula extends JFrame implements IGUI{
	private static final long serialVersionUID = 1L;
	
	private JButton aceptar,cancelar;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"Nota"};
	private JPanel panelPrincipal;
	private Integer nota;

	public GUIConsultarNotaMatricula() {
		super("CONSULTAR NOTA MATRICULA");
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
		
		JLabel idMatricula = new JLabel(" ID Matricula: ");
		final JTextField tidMatricula = new JTextField(20);
		JLabel idMatriculable = new JLabel(" ID Matriculable: ");
		final JTextField tidMatriculable = new JTextField(20);
		
		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idMatricula,BorderLayout.PAGE_START);
		panel3_1.add(tidMatricula,BorderLayout.PAGE_START);
		panel3_2.add(idMatriculable,BorderLayout.AFTER_LAST_LINE);
		panel3_1.add(tidMatriculable,BorderLayout.AFTER_LAST_LINE);

		_dataTableModel = new DefaultTableModel(){
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
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Habria que comentar esta linea del controller
				try {
					Integer idMa = Integer.parseInt(tidMatricula.getText());
					Integer idM = Integer.parseInt(tidMatriculable.getText());
					setVisible(false);
					TMatriculaMatriculable tmm=new TMatriculaMatriculable(idMa,idM,0);
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarNota,tmm));
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar número correcto", "", JOptionPane.ERROR_MESSAGE);
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
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "MATRICULA CONSULTADA: ");						
			listaMatriculables.setBorder(borde);
			
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
	if(context.getEvento()==Evento.ConsultarNota_OK) {
	    nota = (Integer)context.getDato();
	    _dataTableModel.setNumRows(1);
	    if(nota != null){
	    	_dataTableModel.setValueAt(nota,0,0);
	    }
	    panelPrincipal.revalidate();
	
	}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);

	}
	}
	
}



