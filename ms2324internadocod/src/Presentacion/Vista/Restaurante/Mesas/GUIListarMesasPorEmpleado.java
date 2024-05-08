package Presentacion.Vista.Restaurante.Mesas;

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

import Negocio.Restaurante.Mesas.TMesas;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIListarMesasPorEmpleado extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel _dataTableModel;
	private String[] headers = { "Id Mesa", "Numero Mesa", "Capacidad", "ID Empleado", "Activo" };
	private Collection<TMesas> lista;
	JPanel panelPrincipal;
	JButton aceptar, cancelar;

	public GUIListarMesasPorEmpleado() {
		super("LISTAR MESAS POR EMPLEADO");
		initGUI();
		setLocationRelativeTo(null);
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

		JLabel id = new JLabel(" ID Empleado: ");
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
					Integer idp = null;

					if (tId.getText().equals(""))
						throw new Exception("Campo Id mesa no puede ser nulo");

					idp = Integer.parseInt(tId.getText());

					ApplicationController.getInstancia().handleRequest(new Context(Evento.MesasPorEmpleado, idp));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
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
		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"MESAS ASOCIADAS A ESE EMPLEADO: ");
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(450, 450, 700, 450);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.MesasPorEmpleado_OK) {
			int n = 0;
			lista = (Collection<TMesas>) context.getDato();
			if (lista != null) {
				_dataTableModel.setNumRows(lista.size());
				for (TMesas Me : lista) {

					_dataTableModel.setValueAt(Me.getID(), n, 0);
					_dataTableModel.setValueAt(Me.getNum(), n, 1);
					_dataTableModel.setValueAt(Me.getCapacidad(), n, 2);
					_dataTableModel.setValueAt(Me.getIDEmpleado(), n, 3);
					_dataTableModel.setValueAt(Me.getActivo(), n, 4);

					n++;
				}
			}
			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}

	}
}
