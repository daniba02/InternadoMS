package Presentacion.Vista.Restaurante.Turnos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIBajaTurno extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	public GUIBajaTurno() {
		super("BAJA TURNO");
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

		JLabel ID = new JLabel(" ID del turno: ");
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
						ID = Integer.parseInt(tID.getText().trim());
					} catch (Exception e1) {
						throw new Exception("El ID debe ser un numero entero");
					}

					if (ID <= 0)
						throw new Exception("El ID debe ser un numero entero positivo");

					ApplicationController.getInstancia().handleRequest(new Context(Evento.BajaTurno, ID));

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
		if(context.getEvento()==Evento.BajaTurno_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
			}
	}

}
