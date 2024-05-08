package Presentacion.Vista.Academia.Profesores.GUIListarProfesoresInterinos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Academia.Profesores.TInterino;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIListarProfesoresInterinos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers = { "ID Profesor", "Nombre Completo", "DNI", "Edad", "Duracion", "Activo" };
	private Collection<TInterino> listaprofesores;
	JPanel panelPrincipal;

	public void initGUI() {

		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);
		JPanel listaPanel = new JPanel(new BorderLayout());
		panelPrincipal.add(listaPanel, BorderLayout.CENTER);

		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"LISTA DE PROFESORES INTERINOS");
			listaPanel.setBorder(borde);

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
			listaPanel.add(ult, BorderLayout.CENTER);
			listaPanel.setVisible(true);

		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(450, 450, 700, 450);
	}

	public GUIListarProfesoresInterinos() {
		super("LISTAR PROFESORES INTERINOS");
		initGUI();
		setLocationRelativeTo(null);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.ListarProfesoresInterinos_OK) {
			int n = 0;
			listaprofesores = (Collection<TInterino>) context.getDato();
			if (listaprofesores != null) {
				_dataTableModel.setNumRows(listaprofesores.size());
				for (TInterino p : listaprofesores) {

					_dataTableModel.setValueAt(p.getId(), n, 0);
					_dataTableModel.setValueAt(p.getNombreCompleto(), n, 1);
					_dataTableModel.setValueAt(p.getDNI(), n, 2);
					_dataTableModel.setValueAt(p.getEdad(), n, 3);
					_dataTableModel.setValueAt(p.getDuracion(), n, 4);
					_dataTableModel.setValueAt(p.getActivo(), n, 5);

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
