/**
 * 
 */
package Presentacion.Vista.Academia.Profesores.GUIListarProfesoresPorMatriculable;

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

import Negocio.Academia.Profesores.TProfesor;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIListarProfesoresPorMatriculable extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers = { "ID Profesor", "Nombre Completo", "DNI", "Edad" };
	private Collection<TProfesor> lista;
	JPanel panelPrincipal;

	JButton aceptar, cancelar;

	public void initGUI() {

		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);
		JPanel lista = new JPanel(new BorderLayout());
		panelPrincipal.add(lista, BorderLayout.CENTER);
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel id = new JLabel(" ID Matriculable: ");
		final JTextField tId = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(id, BorderLayout.PAGE_START);
		panel3_1.add(tId, BorderLayout.PAGE_START);
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"LISTA DE  PROFESORES POR MATRICULABLES");
			lista.setBorder(borde);

			_dataTableModel = new DefaultTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModel.setColumnIdentifiers(headers);

			JButton aceptar = new JButton("Aceptar");
			aceptar.setBackground(colorAzul);
			JButton cancelar = new JButton("Volver");
			cancelar.setBackground(colorRojo);
			aceptar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						setVisible(false);
						Integer id = 0;

						id = Integer.parseInt(tId.getText());

						if (tId.getText().isEmpty()) {
							id = 0;
						} else {
							id = Integer.valueOf(tId.getText());
						}

						ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarProfesoresPorMatriculable, id));
					} catch (NumberFormatException e1) {
						JOptionPane.showMessageDialog(null, "el id matriculable debe ser un nº", "ERROR", JOptionPane.ERROR_MESSAGE);

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

			JPanel panelBotones = new JPanel();
			panelBotones.add(aceptar);
			panelBotones.add(cancelar);

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675, 200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
			JPanel ult = new JPanel(new BorderLayout());
			ult.add(tableScroll, BorderLayout.CENTER);
			lista.add(ult, BorderLayout.CENTER);
			lista.add(panelBotones, BorderLayout.PAGE_END);
			lista.setVisible(true);

		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(500, 500, 700, 500);
	}

	public GUIListarProfesoresPorMatriculable() {
		super("LISTAR PROFESORES POR MATRICULABLE");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ListarProfesoresPorMatriculable_OK) {
			int it = 0;
			lista = (Collection<TProfesor>) context.getDato();

			if (lista != null) {
				_dataTableModel.setNumRows(lista.size());

				for (TProfesor m : lista) {
					_dataTableModel.setValueAt(m.getId(), it, 0);
					_dataTableModel.setValueAt(m.getNombreCompleto(), it, 1);
					_dataTableModel.setValueAt(m.getDNI(), it, 2);
					_dataTableModel.setValueAt(m.getEdad(), it, 3);
					it++;
				}
			}
			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}

	}

}