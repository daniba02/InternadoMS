package Presentacion.Vista.Academia.Matricula.GUIAbrirMatricula;

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

import Negocio.Academia.Matricula.TMatricula;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIAbrirMatricula extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	public GUIAbrirMatricula() {
		super("ABRIR MATRICULA");
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
		
		JLabel id = new JLabel(" ID Matricula (solo para abrir una ya existente): ");
		final JTextField tid = new JTextField(20);
		
		JLabel idalumno = new JLabel(" ID Alumno: ");
		final JTextField tIdalumno = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);

		JLabel descuento = new JLabel(" Descuento: ");
		final JTextField tDescuento = new JTextField(20);

		panel3_2.add(id,BorderLayout.PAGE_START);
		panel3_1.add(tid,BorderLayout.PAGE_START);
		panel3_2.add(idalumno,BorderLayout.CENTER);
		panel3_1.add(tIdalumno,BorderLayout.CENTER);
		panel3_2.add(descuento,BorderLayout.PAGE_END);
		panel3_1.add(tDescuento,BorderLayout.PAGE_END);
		
		
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
				Integer idAlumno=null,idm;
				Double descuento=null;
				try{		
					try{
						idm = Integer.parseInt(tid.getText());
					}catch(Exception ex2){idm=-1;}
					if(idm==-1){
						try{
							idAlumno = Integer.parseInt(tIdalumno.getText());
						}catch(Exception ex2){throw new Exception("El id debe ser un numero");}
						
						try{
							descuento = Double.parseDouble(tDescuento.getText());	
						}catch(Exception ex3){throw new Exception("El descuento debe ser un numero");}
						
						if(idAlumno<=0) throw new Exception("El id debe ser mayor que 0");
					}
					
					TMatricula t = new TMatricula(idm,idAlumno,0,descuento);
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.AbrirMatricula,t));
					
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
		this.setBounds(500,500,500,135);
		this.setVisible(true);
		
	}
	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.AbrirMatricula_OK)
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.ERROR_MESSAGE);
		this.setVisible(false);
	}

}