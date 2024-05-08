package Presentacion.Vista.Academia.Alumnos.ModificarAlumnos;

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

import Negocio.Academia.Alumnos.TAlumnos;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIModificarAlumnos extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel panelPrincipal;
	
	public GUIModificarAlumnos() {
		super("MODIFICAR ALUMNOS");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		
		
		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(6, 2));
		this.add(panelArriba, BorderLayout.CENTER);
		
		JLabel ID=new JLabel("  ID:  ");
		final JTextField tID=new JTextField(20);

		JLabel Nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Apellido = new JLabel(" Apellido: ");
		final JTextField tApellido = new JTextField(20);

		JLabel DNI = new JLabel(" DNI: ");
		final JTextField tDNI = new JTextField(20);

		JLabel Telefono = new JLabel(" Telefono: ");
		final JTextField tTelefono = new JTextField(20);

		JLabel Edad = new JLabel(" Edad: ");
		final JTextField tEdad = new JTextField(20);

		panelArriba.add(ID);
		panelArriba.add(tID);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		panelArriba.add(Apellido);
		panelArriba.add(tApellido);
		panelArriba.add(DNI);
		panelArriba.add(tDNI);
		panelArriba.add(Telefono);
		panelArriba.add(tTelefono);
		panelArriba.add(Edad);
		panelArriba.add(tEdad);

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
				String Nombre, Apellido, DNI;
				Integer ID, Edad, Telefono;
				try {
					
					Nombre = tNombre.getText();
					if (Nombre.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");
					Apellido = tApellido.getText();
					if (Apellido.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el apellido");
					DNI = tDNI.getText();
					// Verificar que el DNI tiene 9 caracteres
					if (DNI.length() != 9)
						throw new Exception("El DNI debe tener 9 caracteres");

					// Verificar que los primeros 8 caracteres son números
					for (int i = 0; i < 8; i++) {
						char c = DNI.charAt(i);
						if (c < '0' || c > '9')
							throw new Exception("Los 8 primeros caracteres tienen que ser numeros");
					}

					// Verificar que el último carácter es una letra
					// mayúscula
					char letra = DNI.charAt(8);
					if (letra < 'A' || letra > 'Z')
						throw new Exception("El ultimo caracter tiene que ser una letra en mayuscula");
					
					
					try {
						ID=Integer.parseInt(tID.getText());
					}catch (Exception ex2) {
						throw new Exception("El ID debe ser un numero entero");
					}
					if(ID<=0)
						throw new Exception("El ID debe ser un número entero positivo");
					
					
					
					try {
						Edad = Integer.parseInt(tEdad.getText());
					} catch (Exception ex2) {
						throw new Exception("La edad debe ser un numero entero");
					}
					if (Edad <= 0)
						throw new Exception("La edad debe ser un numero entero positivo");

					
					
					try {
						Telefono = Integer.parseInt(tTelefono.getText());
					} catch (Exception ex2) {
						throw new Exception("El telefono debe ser un numero entero");
					}
					if (Telefono / 100000000 < 0)
						throw new Exception("El telefono debe tener 9 digitos o mas");

					TAlumnos a = new TAlumnos(ID, DNI, Nombre, Apellido, Edad, Telefono);
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarAlumnos, a));
					
				}catch(Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}finally {
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
		this.setBounds(600, 600, 400, 185);
		this.setVisible(true);
		
	}

	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ModificarAlumnos_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			}
		this.setVisible(false);
	}

}