package Presentacion.Vista.Academia.Asignatura.GUINotaMediaAsignatura;

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
import Presentacion.IGUI.IGUI;

public class GUINotaMediaAsignatura extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	private JLabel nota;
	
	public GUINotaMediaAsignatura(){
		super("NOTA MEDIA ASIGNATURA");
		initGUI();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(){
		
		nota = new JLabel();
		nota.setText("Nota media: ");
		panelPrincipal = new JPanel(new BorderLayout());
		
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.CENTER);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idasignatura = new JLabel(" ID Asignatura: ");
		final JTextField tidasignatura = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idasignatura,BorderLayout.PAGE_START);
		panel3_1.add(tidasignatura,BorderLayout.PAGE_START);
		panel3_2.add(nota, BorderLayout.PAGE_END);
		
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
				Integer IDasignatura;
				try{
					try{
						IDasignatura = Integer.parseInt(tidasignatura.getText());
					}catch(Exception ex2){throw new Exception("El id debe ser un numero");}
										
					ApplicationController.getInstancia().handleRequest(new Context(Evento.NotaMediaAsignatura,IDasignatura));
					
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
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(350,350,500,120);
		
	}
	
	@Override
	public void actualizar(Context context) {
		
		nota.setText("Nota media: " + (String)context.getDato());
		this.revalidate();
	}

}
