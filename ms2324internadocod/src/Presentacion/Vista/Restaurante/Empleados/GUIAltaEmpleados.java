package Presentacion.Vista.Restaurante.Empleados;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Restaurante.Empleados.TCamarero;
import Negocio.Restaurante.Empleados.TCocinero;
import Negocio.Restaurante.Empleados.TEmpleado;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIAltaEmpleados extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	public GUIAltaEmpleados() {
		super("ALTA EMPLEADOS");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initGUI() {

		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(8, 2));
		this.add(panelArriba, BorderLayout.CENTER);
		
		JPanel renovar = new JPanel(new GridLayout(1,1));
		renovar.setBorder(BorderFactory.createTitledBorder("Para empleados existentes no activos"));
		this.add(renovar, BorderLayout.NORTH);

		JLabel id = new JLabel(" ID: ");
		final JTextField tid = new JTextField(20);
		
		JLabel Nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Sueldo = new JLabel(" Sueldo por Hora: ");
		final JTextField tSueldo = new JTextField(20);
		
		JLabel Horas = new JLabel(" Horas: ");
		final JTextField tHoras = new JTextField(20);
		
		JLabel Turno = new JLabel(" ID Turno: ");
		final JTextField tTurno = new JTextField(20);
		
		JLabel Especialidad = new JLabel(" Especialidad (cocinero): ");
		final JTextField tEspecialidad = new JTextField(20);
		
		JLabel factor = new JLabel(" Factor (cocinero): ");
		final JTextField tfactor = new JTextField(20);

		JLabel Propinas = new JLabel(" Propinas (camarero): ");
		final JTextField tPropinas = new JTextField(20);
		
		JLabel Opciones = new JLabel(" Opciones: ");
		String[] opciones = {"Elige una opcion", "Cocinero", "Camarero"};
		JComboBox<String> comboBox = new JComboBox<>(opciones);
		
		renovar.add(id);
		renovar.add(tid);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		panelArriba.add(Sueldo);
		panelArriba.add(tSueldo);
		panelArriba.add(Horas);
		panelArriba.add(tHoras);
		panelArriba.add(Turno);
		panelArriba.add(tTurno);
		
		panelArriba.add(Opciones);
		panelArriba.add(comboBox);
		panelArriba.add(Especialidad);
		tEspecialidad.setEnabled(false);
		panelArriba.add(tEspecialidad);

		panelArriba.add(factor);
		tfactor.setEnabled(false);
		panelArriba.add(tfactor);
		
		panelArriba.add(Propinas);
		 tPropinas.setEnabled(false);
		panelArriba.add(tPropinas);
		
		tPropinas.setBackground(new Color(211, 211, 211));
		tEspecialidad.setBackground(new Color(211, 211, 211));
		tfactor.setBackground(new Color(211, 211, 211));
		
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBox.getSelectedItem();
                
                // Activa los campos de texto según la selección
                if ("Cocinero".equals(seleccion)) {
                	tPropinas.setEnabled(false);
                	tPropinas.setBackground(new Color(211, 211, 211));
                	tPropinas.setText("");
                	
                	tEspecialidad.setBackground(Color.WHITE);
                	tEspecialidad.setEnabled(true);
                	tfactor.setBackground(Color.WHITE);
                	tfactor.setEnabled(true);
                	
                } else if ("Camarero".equals(seleccion)) {
                	tPropinas.setBackground(Color.WHITE);
                	tPropinas.setEnabled(true);
                	
                	tEspecialidad.setEnabled(false);
                	tEspecialidad.setBackground(new Color(211, 211, 211));
                	tEspecialidad.setText("");
                	tfactor.setEnabled(false);
                	tfactor.setBackground(new Color(211, 211, 211));
                	tfactor.setText("");
                	
                }else if ("Elige una opcion".equals(seleccion)) {
                    tPropinas.setEnabled(false);
                    tPropinas.setBackground(new Color(211, 211, 211));
                    tPropinas.setText("");
                    
                    tEspecialidad.setEnabled(false);
                    tEspecialidad.setBackground(new Color(211, 211, 211));
                    tEspecialidad.setText("");
                	tfactor.setEnabled(false);
                	tfactor.setBackground(new Color(211, 211, 211));
                	tfactor.setText("");
					
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
				Integer ID, idTurno = null;
				String nombre,especialidad = null;
				Double horas, propinas, varFactor, sueldo;

				try {
					try {
						ID = Integer.parseInt(tid.getText());
					} catch (Exception ex2) {
						ID=-1;
					}
					TEmpleado emp;
					if(ID==-1){
						nombre = tNombre.getText();
						if(nombre==null||nombre.equals(""))
							throw new Exception("El nombre no puede ser nulo");
						
						try {
							sueldo = Double.parseDouble(tSueldo.getText());
						} catch (Exception ex2) {
							throw new Exception("La nomina debe ser un numero entero");
						}
						if (sueldo <= 0)
							throw new Exception("La nomina debe ser un numero entero positivo");
		
						try{
							horas = Double.parseDouble(tHoras.getText());
						}catch(Exception ex3){
							throw new Exception("Las horas trabajadas deben ser un numero");
						}
						if(horas <= 0){
							throw new Exception("Las horas trabajadas no pueden ser 0 o negativo");
						}
						try {
							idTurno = Integer.parseInt(tTurno.getText());
						} catch (Exception ex2) {
							throw new Exception("El ID de turno debe ser un numero entero");
						}
						//comprobar exite turno
						if (sueldo <= 0)
							throw new Exception("El ID de turno debe ser un numero entero positivo");
						
						String seleccion = (String) comboBox.getSelectedItem();
						
		                // Activa los campos de texto según la selección
		                if (seleccion.equals("Cocinero")) {
		                	especialidad = tEspecialidad.getText();
	                		if(especialidad==null||especialidad.equals(""))
								throw new Exception("La especialidad no puede ser nulo");
							try {
		                		varFactor = Double.parseDouble(tfactor.getText());
		                	} catch (Exception ex2) {
		                		throw new Exception("El factor debe ser un numero decimal");
		                	}
	                		emp = new TCocinero(ID,idTurno,nombre,sueldo,horas,true,especialidad, varFactor);
	                		ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaEmpleados, emp));
		                } else if (seleccion.equals("Camarero")) {
		                	try {
		                		propinas = Double.parseDouble(tPropinas.getText());
		                	} catch (Exception ex2) {
		                		throw new Exception("Las propinas deben ser un número decimal");
		                	}
		                	emp = new TCamarero(ID,idTurno,nombre,sueldo,horas,true,propinas);
	                		ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaEmpleados, emp));
		                } else {
		                	throw new Exception("Elige una opción (cocinero o camarero)");
		                }
					}
					else{
						emp = new TCamarero(ID,null,null,null,null,true,null);
						ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaEmpleados, emp));
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
		this.setBounds(700, 700, 500, 285);
		this.setVisible(true);
	}


	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.AltaEmpleados_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
			}
		
	}

}
