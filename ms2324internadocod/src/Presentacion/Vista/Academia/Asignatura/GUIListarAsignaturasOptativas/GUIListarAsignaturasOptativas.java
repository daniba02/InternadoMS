package Presentacion.Vista.Academia.Asignatura.GUIListarAsignaturasOptativas;

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

import Negocio.Academia.Asignaturas.TOptativa;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIListarAsignaturasOptativas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers = { "ID Asignatura", "Nombre", "Nivel", "Activo" };
	private Collection<TOptativa> listaAsig;
	JPanel panelPrincipal;

	public GUIListarAsignaturasOptativas() {
		super("LISTAR ASIGNATURAS OPTATIVAS");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);
		JPanel listaPanel = new JPanel(new BorderLayout());
		panelPrincipal.add(listaPanel, BorderLayout.CENTER);

		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"LISTA DE ASIGNATURAS OPTATIVAS");
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


	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ListarAsignaturasOptativas_OK) {
			int n = 0;
			listaAsig = (Collection<TOptativa>) context.getDato();
			if (listaAsig != null) {
				_dataTableModel.setNumRows(listaAsig.size());
				for (TOptativa p : listaAsig) {

					_dataTableModel.setValueAt(p.getID(), n, 0);
					_dataTableModel.setValueAt(p.getNombre(), n, 1);
					_dataTableModel.setValueAt(p.getNivel(), n, 2);
					_dataTableModel.setValueAt(p.getActivo(), n, 3);

					n++;
				}
			}
			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
	}

}