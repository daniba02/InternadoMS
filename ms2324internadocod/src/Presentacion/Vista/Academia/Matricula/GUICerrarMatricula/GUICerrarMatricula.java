package Presentacion.Vista.Academia.Matricula.GUICerrarMatricula;

import Presentacion.IGUI.IGUI;

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

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUICerrarMatricula extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	public GUICerrarMatricula() {
		super("CERRAR MATRICULA");
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
		
		JLabel idMatricula = new JLabel(" ID Matricula: ");
		final JTextField tidMatricula = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idMatricula,BorderLayout.PAGE_START);
		panel3_1.add(tidMatricula,BorderLayout.PAGE_START);
		
		
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
				Integer idMatricula;
				try{
					try{
						idMatricula = Integer.parseInt(tidMatricula.getText());
					}catch(Exception ex2){throw new Exception("El id debe ser un numero");}
					
					if(idMatricula<=0) throw new Exception("El id debe ser mayor que 0");
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.CerrarMatricula,idMatricula));
					
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
		this.setBounds(350,350,450,120);
		this.setVisible(true);
		
	}
	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.CerrarMatricula_OK)
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.ERROR_MESSAGE);
		setVisible(false);

	}

}