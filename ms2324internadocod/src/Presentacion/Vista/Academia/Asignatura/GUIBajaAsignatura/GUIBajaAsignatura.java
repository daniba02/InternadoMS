package Presentacion.Vista.Academia.Asignatura.GUIBajaAsignatura;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIBajaAsignatura extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	public GUIBajaAsignatura() {
		super("BAJA ASIGNATURAS");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		this.setResizable(false);

		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.CENTER);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel idAsig = new JLabel(" ID Asignatura: ");
		final JTextField tidAsignatura = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(idAsig, BorderLayout.PAGE_START);
		panel3_1.add(tidAsignatura, BorderLayout.PAGE_START);

		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer idAsigInt;
				try {
					try {
						idAsigInt = Integer.parseInt(tidAsignatura.getText());
					} catch (Exception ex2) {
						throw new Exception("El id debe ser un numero");
					}

					if (idAsigInt <= 0)
						throw new Exception("El id debe ser un numero entero positivo");

					ApplicationController.getInstancia().handleRequest(new Context(Evento.BajaAsignaturas, idAsigInt));
					setVisible(false);

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
		panel4.add(aceptar);
		panel4.add(cancelar);

		add(panelPrincipal, BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(350, 350, 400, 120);
		this.setVisible(true);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.BajaAsignaturas_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);
	}

}