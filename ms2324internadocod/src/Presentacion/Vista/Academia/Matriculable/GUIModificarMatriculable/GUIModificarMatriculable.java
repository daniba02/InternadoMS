package Presentacion.Vista.Academia.Matriculable.GUIModificarMatriculable;

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

import Negocio.Academia.Matriculable.TMatriculable;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIModificarMatriculable extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel panelPrincipal;
	
	public GUIModificarMatriculable() {
		super("MODIFICAR MATRICULABLE");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(7, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel id = new JLabel(" id: ");
		final JTextField tid = new JTextField(20);
		
		JLabel Precio = new JLabel(" Precio: ");
		final JTextField tPrecio = new JTextField(20);

		JLabel Plazas = new JLabel(" Plazas: ");
		final JTextField tPlazas = new JTextField(20);
		
		JLabel Hora = new JLabel(" Hora: ");
		final JTextField tHora = new JTextField(20);

		JLabel IDAnio = new JLabel(" IDAnio: ");
		final JTextField tIDAnio = new JTextField(20);

		JLabel IDAsignatura = new JLabel(" IDAsignatura: ");
		final JTextField tIDAsignatura = new JTextField(20);

		JLabel IDGrupo = new JLabel(" IDGrupo: ");
		final JTextField tIDGrupo = new JTextField(20);

		panelArriba.add(id);
		panelArriba.add(tid);
		panelArriba.add(Precio);
		panelArriba.add(tPrecio);
		panelArriba.add(Plazas);
		panelArriba.add(tPlazas);
		panelArriba.add(Hora);
		panelArriba.add(tHora);
		panelArriba.add(IDAnio);
		panelArriba.add(tIDAnio);
		panelArriba.add(IDAsignatura);
		panelArriba.add(tIDAsignatura);
		panelArriba.add(IDGrupo);
		panelArriba.add(tIDGrupo);

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
				Integer id;
				Integer Precio;
				Integer Plazas;
				String  Hora;
				Integer Anio;
				Integer Asignatura;
				Integer Grupo;

				try {
					
					try {
						
						id = Integer.parseInt(tid.getText());
					} catch (Exception ex2) {
						throw new Exception("El id debe ser un numero entero");
					}
					if (id <= 0)
						throw new Exception("El precio debe ser un numero entero positivo");
					try {
						
						Precio = Integer.parseInt(tPrecio.getText());
					} catch (Exception ex2) {
						throw new Exception("El precio debe ser un numero entero");
					}
					if (Precio <= 0)
						throw new Exception("El precio debe ser un numero entero positivo");
	
					
					try {
						Plazas = Integer.parseInt(tPlazas.getText());
					} catch (Exception ex2) {
						throw new Exception("Las plazas deben ser un numero entero");
					}
					if (Plazas <= 0)
						throw new Exception("Las plazas deben ser un numero entero positivo");
	
					Hora = tHora.getText();
					if(Hora.length() != 5)
						throw new Exception("La hora tiene que seguir el siguiente formato: 'XX:YY'");
					// Verificamos que la hora este entre las 0 y las 24
					char c = Hora.charAt(0);
					if(c < '0' || c > '2')
						throw new Exception ("Primer digito de la hora no sigue reglas.");
					c = Hora.charAt(1);
					if(c < '0' || c > '9')
						throw new Exception ("Segundo digito de la hora no sigue reglas.");
					c = Hora.charAt(2);
					if(c != ':')
						throw new Exception("No se encuentran puntos separadores");
					c = Hora.charAt(3);
					if(c < '0' || c > '6')
						throw new Exception ("Primer digito de los minutos no sigue reglas.");
					c = Hora.charAt(4);
					if(c < '0' || c > '9')
						throw new Exception ("Segundo digito de los minutos no sigue reglas.");
					
					try {
						Anio = Integer.parseInt(tIDAnio.getText());
					} catch (Exception ex2) {
						throw new Exception("El anio debe ser un numero entero");
					}
	
					if (Anio <= 0)
						throw new Exception("El anio debe ser un numero entero positivo");
					
					try {
						Asignatura = Integer.parseInt(tIDAsignatura.getText());
					} catch (Exception ex2) {
						throw new Exception("La asignatura debe ser un numero entero");
					}
	
					if (Asignatura <= 0)
						throw new Exception("La asignatura debe ser un numero entero positivo");
					
					try {
						Grupo = Integer.parseInt(tIDGrupo.getText());
					} catch (Exception ex2) {
						throw new Exception("El grupo debe ser un numero entero");
					}
	
					if (Grupo <= 0)
						throw new Exception("El grupo debe ser un numero entero positivo");
					
					TMatriculable m = new TMatriculable(id,Precio, Plazas, Hora, Anio, Asignatura, Grupo, true);
	
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarMatriculable, m));
					
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

	public void actualizar(Context context) {
	if(context.getEvento()==Evento.ModificarMatriculable_OK) {
		JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
	setVisible(false);
	}
}