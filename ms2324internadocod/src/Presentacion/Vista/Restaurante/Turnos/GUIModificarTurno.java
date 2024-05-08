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

import Negocio.Restaurante.Turnos.TTurno;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIModificarTurno extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	public GUIModificarTurno() {
		super("MODIFICACION TURNO");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {

		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(4, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel ID = new JLabel(" ID: ");
		final JTextField tID = new JTextField(20);

		JLabel Nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Hora_Entrada = new JLabel(" Hora Entrada: ");
		final JTextField tHora_Entrada = new JTextField(20);

		JLabel Hora_Salida = new JLabel(" Hora Salida: ");
		final JTextField tHora_Salida = new JTextField(20);

		panelArriba.add(ID);
		panelArriba.add(tID);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		panelArriba.add(Hora_Entrada);
		panelArriba.add(tHora_Entrada);
		panelArriba.add(Hora_Salida);
		panelArriba.add(tHora_Salida);

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
				String Nombre, Hora_Entrada, Hora_Salida;

				try {

					try {
						ID = Integer.parseInt(tID.getText().trim());
					} catch (Exception e1) {
						throw new Exception("El ID debe ser un numero entero");
					}

					if (ID <= 0)
						throw new Exception("El ID debe ser un numero entero positivo");

					Nombre = tNombre.getText().trim();
					if (Nombre.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");

					Hora_Entrada = tHora_Entrada.getText().trim();
					if (!Hora_Entrada.matches("\\d{2}:\\d{2}") && !Hora_Entrada.matches("\\d{1}:\\d{2}"))
						throw new Exception("El patron de hora debe ser Hora:Minutos");

					String[] partes = Hora_Entrada.split(":");
					int horas = Integer.parseInt(partes[0]);
					int minutos = Integer.parseInt(partes[1]);

					if (horas < 0 || horas >= 24)
						throw new Exception("No se ha introducido correctamente la hora de entrada");
					else if (minutos < 0 || minutos >= 60)
						throw new Exception("No se han introducido correctamente los minutos de entrada");

					Hora_Salida = tHora_Salida.getText().trim();
					if (!Hora_Salida.matches("\\d{2}:\\d{2}") && !Hora_Salida.matches("\\d{1}:\\d{2}"))
						throw new Exception("El patron de hora debe ser Hora:Minutos");

					String[] partes2 = Hora_Salida.split(":");
					int horas2 = Integer.parseInt(partes2[0]);
					int minutos2 = Integer.parseInt(partes2[1]);

					if (horas2 < 0 || horas2 >= 24)
						throw new Exception("No se ha introducido correctamente la hora de salida");
					else if (minutos2 < 0 || minutos2 >= 60)
						throw new Exception("No se han introducido correctamente los minutos de salida");

					if (horas * 60 + minutos > horas2 * 60 + minutos2)
						throw new Exception("La hora de entrada debe ser antes que la hora de salida");

					if (Hora_Entrada.length() == 4)
						Hora_Entrada = "0" + Hora_Entrada;

					if (Hora_Salida.length() == 4)
						Hora_Salida = "0" + Hora_Salida;

					TTurno t = new TTurno(ID, Nombre, Hora_Entrada, Hora_Salida);

					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarTurno, t));

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
		this.setBounds(600, 600, 400, 200);
		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ModificarTurno_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
	}

}
