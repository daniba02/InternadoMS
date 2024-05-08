/**
 * 
 */
package Presentacion.Vista.Academia.Profesores.GUIVincularMatriculable;

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

import Negocio.Academia.ProfesorMatriculable.TProfesorMatriculable;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIVincularMatriculable extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel panel;

	public void initGUI() {
		this.setResizable(false);

		panel = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panel3 = new JPanel(new BorderLayout());
		panel.add(panel3, BorderLayout.CENTER);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel idProfesor = new JLabel(" ID Profesor: ");
		final JTextField tidProfesor = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		JLabel idmatriclable = new JLabel(" ID Matriculable: ");
		final JTextField tidmatriculable = new JTextField(20);

		panel3_2.add(idProfesor, BorderLayout.PAGE_START);
		panel3_1.add(tidProfesor, BorderLayout.PAGE_START);
		panel3_2.add(idmatriclable, BorderLayout.PAGE_END);
		panel3_1.add(tidmatriculable, BorderLayout.PAGE_END);

		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(panel4, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Volver");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer idProfesor, idmatriculable;
				try {
					try {
						idProfesor = Integer.parseInt(tidProfesor.getText());
					} catch (Exception ex2) {
						throw new Exception("El ID de profesor debe ser un numero");
					}
					try {
						idmatriculable = Integer.parseInt(tidmatriculable.getText());
					} catch (Exception ex3) {
						throw new Exception("El ID Matriculable debe ser un numero");
					}

					if (idProfesor <= 0 || idmatriculable <= 0)
						throw new Exception("El id debe ser mayor que 0");

					TProfesorMatriculable tmm = new TProfesorMatriculable(idProfesor, idmatriculable);

					ApplicationController.getInstancia()
							.handleRequest(new Context(Evento.VincularProfesorMatriculable, tmm));

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

		add(panel, BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(350, 350, 600, 120);
		this.setVisible(true);

	}

	public GUIVincularMatriculable() {
		super("VINCULAR PROFESOR A MATRICULABLE");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actualizar(Context context) {

		if (context.getEvento() == Evento.VincularProfesorMatriculable_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);

	}

}