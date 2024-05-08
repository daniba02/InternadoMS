package Presentacion.Vista.Restaurante.Productos;

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


import Negocio.Restaurante.Producto.TProducto;

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;


public class ConsultarProductos extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Producto","Nombre","Caracteristicas"};
	private JPanel lista;
	JButton aceptar, cancelar;
	JPanel mainPanel;
	

	public ConsultarProductos() {
		super("CONSULTAR PRODUCTO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public void initGUI() {

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		mainPanel = new JPanel(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		lista = new JPanel(new BorderLayout());
		mainPanel.add(lista, BorderLayout.CENTER);
		JPanel panel3 = new JPanel(new BorderLayout());
		mainPanel.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);
		JLabel id = new JLabel(" ID Producto: ");
		final JTextField tId = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(id, BorderLayout.PAGE_START);
		panel3_1.add(tId, BorderLayout.PAGE_START);

		aceptar = new JButton("Consultar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Volver");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer id = 0;

					id = Integer.parseInt(tId.getText());

					if (tId.getText().isEmpty()) {
						id = 0;
					} else {
						id = Integer.valueOf(tId.getText());
					}

					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarProductos, id));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "el id debe ser un nº ", "ERROR", JOptionPane.ERROR_MESSAGE);

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
		JPanel panel4 = new JPanel();
		panel4.add(aceptar);
		panel4.add(cancelar);

		try {
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"DATOS DEL PRODUCTO CONSULTADO: ");
			lista.setBorder(borde);
			

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
			table.setPreferredScrollableViewportSize(new Dimension(450, 180));

			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 180));

			JPanel ult = new JPanel(new BorderLayout());
			
			ult.add(tableScroll, BorderLayout.CENTER);
			lista.add(ult, BorderLayout.CENTER);
			lista.add(panel4, BorderLayout.PAGE_END);
			lista.setVisible(true);

		} catch (Exception ex1) {
			setVisible(false);
			ex1.printStackTrace();
		}

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200, 200, 600, 200);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ConsultarProductos_OK) {
			_dataTableModel.setNumRows(1);
			TProducto tm = (TProducto) context.getDato();
			_dataTableModel.setValueAt(tm.getId(), 0, 0);
			_dataTableModel.setValueAt(tm.getNombre(), 0, 1);
			_dataTableModel.setValueAt(tm.getCaracteristicas(), 0, 2);		

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}
		}

}
