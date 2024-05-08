package Presentacion.Vista.Restaurante.Mesas;

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

import Negocio.Restaurante.Mesas.TMesas;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIConsultarMesa extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers = { "Id", "Numero", "Capacidad", "Id Empleado" };
	private JPanel panelPrincipal;

	public GUIConsultarMesa() {
		super("CONSULTAR MESA");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);
		JPanel consultarMesaPanel = new JPanel(new BorderLayout());
		panelPrincipal.add(consultarMesaPanel, BorderLayout.CENTER);

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel idMesa = new JLabel(" ID Mesa: ");
		final JTextField tidMesa = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(idMesa, BorderLayout.PAGE_START);
		panel3_1.add(tidMesa, BorderLayout.PAGE_START);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Habria que comentar esta linea del controller

				try {

					if (tidMesa.getText().equals(""))
						throw new Exception("Campo Id mesa no puede ser nulo");
					
					Integer num = Integer.parseInt(tidMesa.getText());
					
					if (num <= 0)
						throw new Exception("id Empleado no puede ser negativo");

					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarMesa, num));
				} catch (Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
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
		JPanel panelBotones = new JPanel();
		panelBotones.add(aceptar);
		panelBotones.add(cancelar);
		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"MESA CONSULTADA: ");
			consultarMesaPanel.setBorder(borde);

			_dataTableModel = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModel.setColumnIdentifiers(headers);

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675, 200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
			JPanel ult = new JPanel(new BorderLayout());
			ult.add(tableScroll, BorderLayout.CENTER);
			consultarMesaPanel.add(ult, BorderLayout.CENTER);
			consultarMesaPanel.add(panelBotones, BorderLayout.PAGE_END);
			consultarMesaPanel.setVisible(true);

		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(200, 200, 600, 200);
	}

	public void actualizar(Context context) {

		if (context.getEvento() == Evento.ConsultarMesa_OK) {
			TMesas p = (TMesas) context.getDato();
			_dataTableModel.setNumRows(1);
			_dataTableModel.setValueAt(p.getID(), 0, 0);
			_dataTableModel.setValueAt(p.getNum(), 0, 1);
			_dataTableModel.setValueAt(p.getCapacidad(), 0, 2);
			_dataTableModel.setValueAt(p.getIDEmpleado(), 0, 3);

			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);

		}

	}
}
