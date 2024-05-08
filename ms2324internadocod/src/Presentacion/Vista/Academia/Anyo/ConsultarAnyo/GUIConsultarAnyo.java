package Presentacion.Vista.Academia.Anyo.ConsultarAnyo;

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

import Negocio.Academia.Anio.TAnio;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;


public class GUIConsultarAnyo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel _dataTableModel;
	private String[] headers={"Id", "Anio"};
	private JPanel panelPrincipal;

	public GUIConsultarAnyo() {
		super("CONSULTAR ANIO");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel anios = new JPanel(new BorderLayout());
		panelPrincipal.add(anios,BorderLayout.CENTER);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel id = new JLabel(" Id anio: ");
		final JTextField tId= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(id,BorderLayout.PAGE_START);
		panel3_1.add(tId,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Habria que comentar esta linea del controller
				try {
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultaAnio, Integer.parseInt(tId.getText())));
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(null, "No puedes consultar sin introducir bien el id", "", JOptionPane.ERROR_MESSAGE);
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
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "Anio: ");						
			anios.setBorder(borde);
			
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
			anios.add(ult,BorderLayout.CENTER);
			anios.add(panelBotones,BorderLayout.PAGE_END);
			anios.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(200,200,600,200);
		
	}

	public void actualizar(Context context) {
		if(context.getEvento() == Evento.ConsultaAnio_OK) {
			TAnio g = (TAnio)context.getDato();
			if(g!=null)_dataTableModel.setNumRows(1);
			_dataTableModel.setValueAt(g.getId(),0,0);
			_dataTableModel.setValueAt(g.getAnio(),0,1);
			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
		}
		
	}
	
}