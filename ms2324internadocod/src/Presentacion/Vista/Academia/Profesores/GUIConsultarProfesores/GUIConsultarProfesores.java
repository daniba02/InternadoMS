/**
 * 
 */
package Presentacion.Vista.Academia.Profesores.GUIConsultarProfesores;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;

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

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIConsultarProfesores extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private JButton aceptar, cancelar;
	private DefaultTableModel _dataTableModel;
	private String[] headers = { "ID", "DNI", "Nombre Completo", "Edad", "Duracion" };
	private JPanel panelPrincipal;
	@SuppressWarnings("unused")
	private Integer idProfesor;
	// private TitledBorder borde;
	private JPanel lista;

	public void initGUI() {

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);
		lista = new JPanel(new BorderLayout());
		panelPrincipal.add(lista, BorderLayout.CENTER);
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);
		JLabel id = new JLabel(" ID Profesor: ");
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

					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarProfesor, id));
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
					"DATOS DEL PROFESOR CONSULTADO: ");
			lista.setBorder(borde);
			// ult.setVisible(true);

			_dataTableModel = new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModel.setColumnIdentifiers(headers);
			_dataTableModel.setNumRows(1);

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(450, 180));

			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 180));

			JPanel ult = new JPanel(new BorderLayout());
			// ult.setVisible(false);
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

	public GUIConsultarProfesores() {
		super("CONSULTAR PROFESORES");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void actualizar(Context context) {

		if (context.getEvento() == Evento.ConsultarProfesor_OK) {
			TProfesor tm = (TProfesor) context.getDato();
			_dataTableModel.setValueAt(tm.getId(), 0, 0);
			_dataTableModel.setValueAt(tm.getDNI(), 0, 1);
			_dataTableModel.setValueAt(tm.getNombreCompleto(), 0, 2);
			_dataTableModel.setValueAt(tm.getEdad(), 0, 3);
			
			if (tm instanceof TInterino){
				_dataTableModel.setValueAt(((TInterino) tm).getDuracion(), 0, 4);	
			}
			else{
				_dataTableModel.setValueAt("-", 0, 4);

			}

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}
		
	}

}