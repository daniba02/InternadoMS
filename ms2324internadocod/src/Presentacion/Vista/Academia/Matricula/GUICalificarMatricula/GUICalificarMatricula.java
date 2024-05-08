package Presentacion.Vista.Academia.Matricula.GUICalificarMatricula;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Academia.MatriculaMatriculable.TMatriculaMatriculable;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUICalificarMatricula extends JFrame implements IGUI{

private static final long serialVersionUID = 1L;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	public GUICalificarMatricula() {
		super("CALIFICAR MATRICULA");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		
		this.setResizable(false);
		
		panelPrincipal = new JPanel(new BorderLayout());
		
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.CENTER);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
		
		JLabel idMatricula = new JLabel(" ID Matricula: ");
		final JTextField tidMatricula = new JTextField(20);

		JLabel idMatriculable = new JLabel(" ID Matriculable: ");
		final JTextField tidMatriculable = new JTextField(20);
		
		JLabel nota = new JLabel(" Nota: ");
		final JTextField tnota = new JTextField(20);
	
		panel3_2.add(idMatricula,BorderLayout.PAGE_START);
		panel3_1.add(tidMatricula,BorderLayout.PAGE_START);
		panel3_2.add(idMatriculable,BorderLayout.CENTER);
		panel3_1.add(tidMatriculable,BorderLayout.CENTER);
		panel3_2.add(nota,BorderLayout.PAGE_END);
		panel3_1.add(tnota,BorderLayout.PAGE_END);
		
		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4,BorderLayout.PAGE_END);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer idMatricula,idMatriculable,nota;
				try{					
					try{
						idMatricula = Integer.parseInt(tidMatricula.getText());
					}catch(Exception ex2){throw new Exception("El id debe ser un numero");}
					try{
						idMatriculable = Integer.parseInt(tidMatriculable.getText());	
					}catch(Exception ex3){throw new Exception("El descuento debe ser un numero");}
					try{
						nota = Integer.parseInt(tnota.getText());
					}catch(Exception ex1){throw new Exception("La nota debe ser un numero");}
					
					if(idMatriculable<=0||idMatricula<=0) throw new Exception("El id debe ser mayor que 0");
					if(nota<0||nota>10)throw new Exception("La nota debe estar entre 0 y 10 (incluidos)");
					
					TMatriculaMatriculable tmm = new TMatriculaMatriculable(idMatricula,idMatriculable,nota);
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.CalificarMatriculableMatricula,tmm));
					
				}catch(Exception ex1){
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
				finally{
					setVisible(false);
				}
			}
		}); 
		
		cancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		panel4.add(aceptar);
		panel4.add(cancelar);
		
		add(panelPrincipal,BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(350,350,450,138);
		this.setVisible(true);
		
	}
	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.CalificarMatriculableMatricula_OK)
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.ERROR_MESSAGE);
		setVisible(false);

	}


}
