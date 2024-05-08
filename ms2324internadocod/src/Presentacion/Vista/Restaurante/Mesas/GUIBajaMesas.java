package Presentacion.Vista.Restaurante.Mesas;

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

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;
import Presentacion.Vista.Restaurante.GUIutilities;

public class GUIBajaMesas extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	private JButton aceptar, cancelar;
	private JPanel panelPrincipal;
	
	public GUIBajaMesas(){
		super("Baja MESAS");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void initGUI(){
		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());
		this.add(panelPrincipal);

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panelArriba = new JPanel(new GridLayout(3, 2));
		panelPrincipal.add(panelArriba, BorderLayout.CENTER);
		
		JLabel numero = new JLabel("Id Mesa: ");
		JTextField numeroT= new JTextField();
		GUIutilities.onlyIntegers(numeroT);
		
		panelArriba.add(numero);
		panelArriba.add(numeroT);
		
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
				try{
					//Comprobacion de datos nulos
					if (numeroT.getText().equals(""))
						throw new Exception("Campo Id mesa no puede ser nulo");
					
				ApplicationController.getInstancia().handleRequest(new Context(Evento.BajaMesas, Integer.parseInt(numeroT.getText())));
					
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

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(600, 600, 400, 185);
		this.setVisible(true);
	}
	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.BajaMesas_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
			}
	}
}
