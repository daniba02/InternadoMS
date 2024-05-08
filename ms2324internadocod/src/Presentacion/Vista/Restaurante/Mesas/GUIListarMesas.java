package Presentacion.Vista.Restaurante.Mesas;

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

import Negocio.Restaurante.Mesas.TMesas;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIListarMesas extends JFrame implements IGUI{
private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers = {"Id Mesa", "Numero Mesa", "Capacidad", "ID Empleado", "Activo"};
	private Collection<TMesas> lista;
	JPanel panelPrincipal;
	
	public GUIListarMesas() {
		super("LISTAR MESAS");
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
					"LISTA DE MESAS");
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
	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.ListarMesas_OK) {
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
