/**
 * 
 */
package Presentacion.Vista.Academia.Profesores.GUIModificarProfesores;

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

public class GUIModificarProfesores extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel mainPanel;
	JTextField tDuracion;

	enum Tipo {
		FIJO, INTERINO
	};

	Tipo tipo = null;

	public GUIModificarProfesores() {
		super("MODIFICAR PROFESOR");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initGUI() {

		this.setResizable(false);
		mainPanel = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(6, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel ID = new JLabel("  ID Profesor:  ");
		final JTextField tID = new JTextField(20);

		JLabel DNI = new JLabel(" DNI: ");
		final JTextField tDNI = new JTextField(20);

		JLabel Nombre = new JLabel(" Nombre Completo: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Edad = new JLabel(" Edad: ");
		final JTextField tEdad = new JTextField(20);

		panelArriba.add(ID);
		panelArriba.add(tID);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		panelArriba.add(DNI);
		panelArriba.add(tDNI);
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
		cancelar = new JButton("Volver");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreCompleto, dni;
				Integer id, edad;
				try {

					try{
						id = Integer.parseInt(tID.getText());
					} catch (Exception e1) {
						throw new Exception("El id debe ser un nº");
					}

					if (id <= 0)
						throw new Exception("El id debe ser positivo");

					nombreCompleto = tNombre.getText();
					if (nombreCompleto.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");
					
					if (nombreCompleto.length() == 0)
						throw new Exception("El nombre no puede estar vacio");
					
					dni = tDNI.getText();
					
					// Verificar longitud del dni (9)
					if (dni.length() != 9)
						throw new Exception("El DNI debe tener 9 caracteres");

					// Verificar que los primeros 8 caracteres son numeros
					for (int i = 0; i < 8; i++) {
						char c = dni.charAt(i);
						if (c < '0' || c > '9')
							throw new Exception("Los 8 primeros caracteres tienen que ser numeros");
					}

					// Verificar que el ultimo caracter es una letra
					// mayuscula
					char letra = dni.charAt(8);
					if (letra < 'A' || letra > 'Z')
						throw new Exception("El ultimo caracter tiene que ser una letra en mayuscula");

					try {
						edad = Integer.parseInt(tEdad.getText());
					} catch (Exception ex2) {
						throw new Exception("La edad debe ser un numero entero");
					}
					
					if (tipo == Tipo.FIJO) {
						TFijo fijo = new TFijo(id, dni, edad, nombreCompleto, true);
						ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarProfesor, fijo));

					} else if (tipo == Tipo.INTERINO) {
						try{
							int duracion = Integer.parseInt(tDuracion.getText());
							TInterino interino = new TInterino(id, dni, edad, nombreCompleto, true, duracion);
							ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarProfesor, interino));
						}
						catch(Exception ex3){
							throw new Exception("La duración debe ser un nº");

						}
						
					} else {
						throw new Exception("Se tiene que elegir un tipo");
					}
					
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

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(600, 600, 400, 185);
		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ModificarProfesor_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);

	}

}