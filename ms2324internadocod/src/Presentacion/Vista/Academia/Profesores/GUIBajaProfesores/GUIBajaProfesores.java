/**
 * 
 */
package Presentacion.Vista.Academia.Profesores.GUIBajaProfesores;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
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


public class GUIBajaProfesores extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	JButton aceptar,atras;
	JPanel panel;
	
	public void initGUI() {
		
		
		panel = new JPanel(new BorderLayout());
		this.setResizable(false);
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panel.add(panel3,BorderLayout.CENTER);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idProfesor = new JLabel("ID Profesor: ");
		final JTextField tidProfesor = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idProfesor,BorderLayout.PAGE_START);
		panel3_1.add(tidProfesor,BorderLayout.PAGE_START);
		
		
		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panel.add(panel4,BorderLayout.PAGE_END);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		atras = new JButton("Volver");
		atras.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer id;
				try{
					try{
						id= Integer.parseInt(tidProfesor.getText());
					}catch(Exception ex2){throw new Exception("El id debe ser un numero");}
					
					if(id<=0) throw new Exception("El id debe ser mayor que 0");
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.BajaProfesor,id));
					
				}catch(Exception ex1){
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
				finally{
					setVisible(false);
				}
			}
		});
		
		atras.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		panel4.add(aceptar);
		panel4.add(atras);
		
		add(panel,BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(350,350,400,120);
		this.setVisible(true);
	}

	
	public GUIBajaProfesores() {
		super("BAJA PROFESORES");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.BajaProfesor_OK) {
		JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

	} else {
		JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
	}
		this.setVisible(false);
	
	}

}