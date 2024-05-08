package Presentacion.Vista.Restaurante.Empleados;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Restaurante.Empleados.TCocinero;
import Negocio.Restaurante.Empleados.TCamarero;
import Negocio.Restaurante.Empleados.TEmpleado;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIModificarEmpleados extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal, card;
	JComboBox<String> combo;
	
	public enum Tipo {
		NORM, COCINERO, CAMARERO
	};

	Tipo tipo = Tipo.NORM;
	
	public GUIModificarEmpleados() {
		super("MODIFICAR EMPLEADOS");
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
		panelArriba.setSize(new Dimension(600,250));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel id = new JLabel(" ID empleado: ");
		final JTextField tid = new JTextField(20);
		
		JLabel Nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Sueldo = new JLabel(" Sueldo por hora: ");
		final JTextField tSueldo = new JTextField(20);
		
		JLabel Horas = new JLabel(" Horas: ");
		final JTextField tHoras = new JTextField(20);
		
		JLabel Turno = new JLabel(" ID turno: ");
		final JTextField tTurno = new JTextField(20);
		
		

		panelArriba.add(id);
		panelArriba.add(tid);
		panelArriba.add(Turno);
		panelArriba.add(tTurno);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		panelArriba.add(Sueldo);
		panelArriba.add(tSueldo);
		panelArriba.add(Horas);
		panelArriba.add(tHoras);
		panelArriba.add(Turno);
		panelArriba.add(tTurno);
		
		
		card = new JPanel();
		card.setLayout(new CardLayout ());
		card.setSize(new Dimension(400,400));

		JPanel noth = new JPanel();
		JPanel panelCocinero = new JPanel();

		JLabel coci = new JLabel(" Especialidad: ");
		JTextField tCoci = new JTextField(20);
		
		JLabel cociF = new JLabel(" Factor: ");
		JTextField tCociF = new JTextField(20);
		
		panelCocinero.add(coci);
		panelCocinero.add(tCoci);
		panelCocinero.add(cociF);
		panelCocinero.add(tCociF);

		JPanel panelCamarero = new JPanel();

		JLabel cama = new JLabel(" Propinas: ");
		JTextField tCama = new JTextField(20);
		panelCamarero.add(cama);
		panelCamarero.add(tCama);

		card.add(panelCocinero, BorderLayout.SOUTH);
		card.add(panelCamarero, BorderLayout.SOUTH);
		card.add(noth);
		noth.setVisible(true);
		panelCamarero.setVisible(false);
		panelCocinero.setVisible(false);

		combo = new JComboBox<String>();
		combo.setModel(new DefaultComboBoxModel<String>(new String[] { " Elija un tipo: ", "Cocinero", "Camarero" }));
		panelArriba.add(combo);
		panelArriba.add(card, BorderLayout.SOUTH);
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = combo.getSelectedIndex();
				switch (selection) {
				case 0:
					tipo = Tipo.NORM;
					panelCamarero.setVisible(false);
					panelCocinero.setVisible(false);
					noth.setVisible(true);
					break;
				case 1:
					tipo = Tipo.COCINERO;
					panelCamarero.setVisible(false);
					panelCocinero.setVisible(true);
					noth.setVisible(false);
					break;
				case 2:
					tipo = Tipo.CAMARERO;
					panelCamarero.setVisible(true);
					panelCocinero.setVisible(false);
					noth.setVisible(false);
					break;
				}
			}

		});
		
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
				Integer id, idTurno;
				Double horas, propinas, varFactor, sueldo;
				String nombre, especialidad;

				try {
					try {
						id = Integer.parseInt(tid.getText());
					} catch (Exception ex2) {
						throw new Exception("El id debe ser un numero entero");
					}
					if (id <= 0) {
						throw new Exception("El id debe ser un numero entero positivo");
					}
					
					try {
						idTurno = Integer.parseInt(tTurno.getText());
					} catch (Exception ex2) {
						throw new Exception("El id turno debe ser un numero entero");
					}
					if (idTurno <= 0) {
						throw new Exception("El id turno debe ser un numero entero positivo");
					}
	
					try {
						sueldo = Double.parseDouble(tSueldo.getText());
					} catch (Exception ex2) {
						throw new Exception("El sueldo debe ser un numero entero");
					}
					if (sueldo <= 0) {
						throw new Exception("La nomina debe ser un numero entero positivo");
					}
					
					try {
						horas = Double.parseDouble(tHoras.getText());
					} catch (Exception ex2) {
						throw new Exception("Las horas deben ser un numero");
					}
					if (horas <= 0)
						throw new Exception("Las horas deben ser un numero positivo");
	
	
					nombre = tNombre.getText();
					
					TEmpleado emp;
					if(tipo==Tipo.COCINERO) {
						especialidad = tCoci.getText();
                		if(especialidad==null||especialidad.equals(""))
							throw new Exception("La especialidad no puede ser nulo");
						try {
	                		varFactor = Double.parseDouble(tCociF.getText());
	                	} catch (Exception ex2) {
	                		throw new Exception("El factor debe ser un numero decimal");
	                	}
                		emp = new TCocinero(id,idTurno,nombre,sueldo,horas,true,especialidad, varFactor);
					} else if (tipo==Tipo.CAMARERO) {
						try {
							propinas = Double.parseDouble(tCama.getText());
						} catch (Exception ex2) {
							throw new Exception("Las propinas deben ser un entero");
						}
						if (propinas <= 0)
							throw new Exception("Las propinas deben ser un numero positivo");
						emp = new TCamarero(id,idTurno,nombre,sueldo,horas,true,propinas);
					} else {
						throw new Exception("Se tiene que elegir uno de los dos tipos");
					}
					
					setVisible(false);
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarEmpleados, emp));
					
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
		panelAbajo.setSize(500, 100);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(500, 250);
		this.setBounds(600, 600, 700, 420);
		this.setVisible(true);
		
	}


	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ModificarEmpleados_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			}
		setVisible(false);
	}

}
