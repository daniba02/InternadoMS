package Presentacion.Vista.Academia.Matricula.GUIModificarMatricula;

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

public class GUIModificarMatricula extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JButton aceptar,cancelar;
	
	public GUIModificarMatricula(){
		super("MODIFICAR MATRICULA");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initGUI(){
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		
		panelPrincipal=new JPanel(new BorderLayout());
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.CENTER);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel id = new JLabel(" ID Matricula: ");
		final JTextField tId = new JTextField(20);
		
		JLabel descuento = new JLabel(" Descuento: ");
		final JTextField tdescuento = new JTextField(20);
		
		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);

		panel3_2.add(id,BorderLayout.PAGE_START);
		panel3_1.add(tId,BorderLayout.PAGE_START);
		panel3_2.add(descuento,BorderLayout.AFTER_LAST_LINE);
		panel3_1.add(tdescuento,BorderLayout.AFTER_LAST_LINE);
		
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
				try{
					Double descuento;
					Integer id;
					try{
						descuento=Double.parseDouble(tdescuento.getText());
					}catch(Exception e4){
						throw new Exception("El descuento debe ser un numero valido");
					}
					try{
						id=Integer.parseInt(tId.getText());
					}catch(Exception e3){
						throw new Exception("El id debe ser un numero valido");
					}
					
					if(descuento<=0||descuento>1)
						throw new Exception("El descuento debe estar entre 0(no incluido) y 1");
					
					TMatricula t = new TMatricula(id,descuento);
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarMatricula,t));	
					setVisible(false);
				}catch(Exception ex1){
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
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
		
		add(panelPrincipal);
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(350,350,600,120);
	}
	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ModificarMatricula_OK)
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.ERROR_MESSAGE);
		this.setVisible(false);
	}

}