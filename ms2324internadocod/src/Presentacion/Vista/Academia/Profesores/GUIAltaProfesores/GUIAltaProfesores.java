/**
 * 
 */
package Presentacion.Vista.Academia.Profesores.GUIAltaProfesores;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Academia.Profesores.TFijo;
import Negocio.Academia.Profesores.TInterino;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIAltaProfesores extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	JButton aceptar, atras;
	JPanel mainPanel;
	JTextField tDuracion;

	enum Tipo {
		FIJO, INTERINO
	};

	Tipo tipo = null;

	public GUIAltaProfesores() {
		super("ALTA PROFESOR");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initGUI() {

		this.setResizable(false);
		mainPanel = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(5, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel dni = new JLabel(" DNI: ");
		final JTextField tDni = new JTextField(20);

		JLabel nombre = new JLabel(" Nombre completo: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Edad = new JLabel(" Edad: ");
		final JTextField tEdad = new JTextField(20);

		panelArriba.add(dni);
		panelArriba.add(tDni);
		panelArriba.add(nombre);
		panelArriba.add(tNombre);
		panelArriba.add(Edad);
		panelArriba.add(tEdad);

		JComboBox combo = new JComboBox();
		combo.setModel(new DefaultComboBoxModel(new String[] { " Elija un tipo: ", "Fijo", "Interino" }));
		panelArriba.add(combo);
		panelArriba.add(new JPanel());

		JPanel panelMedio = new JPanel(new GridLayout(1, 2));
		panelArriba.add(panelMedio);
		JLabel duracion = new JLabel(" Duracion: ");
		tDuracion = new JTextField(20);
		panelMedio.add(duracion, BorderLayout.CENTER);
		panelMedio.add(tDuracion, BorderLayout.SOUTH);
		panelMedio.setVisible(false);

		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = combo.getSelectedIndex();
				switch (selection) {
				case 0:
					panelMedio.setVisible(false);
					break;
				case 1:
					tipo = Tipo.FIJO;
					panelMedio.setVisible(false);

					break;
				case 2:
					tipo = Tipo.INTERINO;
					panelMedio.setVisible(true);
					break;
				}
			}

		});

		JPanel panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panelAbajo, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		atras = new JButton("Volver");
		atras.setBackground(colorRojo);

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String dni;
				String nombreCompleto;
				Integer edad;

				try {
					nombreCompleto = tNombre.getText();
					if (nombreCompleto.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");

					dni = tDni.getText();

					// Verificar longitud del dni (9)
					if (dni.length() != 9)
						throw new Exception("El DNI debe tener 9 caracteres");

					// Verificar que los primeros 8 caracteres son numeros
					for (int i = 0; i < 8; i++) {
						char c = dni.charAt(i);
						if (c < '0' || c > '9')
							throw new Exception("Los 8 primeros caracteres tienen que ser numeros");
					}

					// Verificar que el ultimo caracter es una letra y mayúscula
					char letra = dni.charAt(8);
					if (letra < 'A' || letra > 'Z')
						throw new Exception("El ultimo caracter tiene que ser una letra en mayuscula");

					try {
						edad = Integer.parseInt(tEdad.getText());
					} catch (Exception ex2) {
						throw new Exception("La edad debe ser un numero entero");
					}

					if (nombreCompleto.length() == 0)
						throw new Exception("El nombre no puede estar vacio");

					if (edad <= 0)
						throw new Exception("La edad debe ser un numero entero positivo");
					if (edad < 18)
						throw new Exception("El profesor debe ser mayor de edad");

					if (tipo == Tipo.FIJO) {
						TFijo fijo = new TFijo(dni, edad, nombreCompleto, true);
						ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaProfesor, fijo));

					} else if (tipo == Tipo.INTERINO) {
						try {
							int duracion = Integer.parseInt(tDuracion.getText());
							TInterino interino = new TInterino(dni, edad, nombreCompleto, true, duracion);
							ApplicationController.getInstancia()
									.handleRequest(new Context(Evento.AltaProfesor, interino));

						} catch (Exception ex3) {
							throw new Exception("La duracion debe ser un numero");
						}

					} else {
						throw new Exception("Se tiene que elegir un tipo");
					}

				} catch (Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				} finally {
					setVisible(false);
				}
			}
		});

		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		panelAbajo.add(aceptar);
		panelAbajo.add(atras);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 600, 400, 185);
		this.setVisible(true);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.AltaProfesor_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);

	}

}