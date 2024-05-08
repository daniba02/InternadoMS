package Presentacion.Vista.Academia.Alumnos.BajaAlumnos;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIBajaAlumnos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	public GUIBajaAlumnos() {
		super("BAJA ALUMNOS");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(1, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel ID = new JLabel(" ID del alumno: ");
		final JTextField tID = new JTextField(20);

		panelArriba.add(ID);
		panelArriba.add(tID);

		JPanel panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panelAbajo, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer ID;

				try {
					try {
						ID = Integer.parseInt(tID.getText());
					} catch (Exception e1) {
						throw new Exception("El ID debe ser un numero entero");
					}

					if (ID <= 0)
						throw new Exception("El ID debe ser un numero entero positivo");

					ApplicationController.getInstancia().handleRequest(new Context(Evento.BajaAlumnos, ID));

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
		panelAbajo.add(aceptar);
		panelAbajo.add(cancelar);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 600, 400, 100);
		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.BajaAlumnos_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			}
		
		this.setVisible(false);
	}
}