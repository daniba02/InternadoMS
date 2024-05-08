package Presentacion.Vista.Academia.Anyo.ModificarAnyo;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Academia.Anio.TAnio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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


public class GUIModificarAnyo extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	JButton aceptar, cancelar;
	JPanel panelPrincipal;
	
	public GUIModificarAnyo() {
		super("MODIFICAR ANIO");
		initGUI();
		setLocationRelativeTo(null);
	}

	public void initGUI() {
		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panelArriba = new JPanel(new GridLayout(1, 2));
		this.add(panelArriba, BorderLayout.CENTER);
		
		JLabel id = new JLabel(" Id: ");
		JTextField tId = new JTextField(20);
		JLabel anio = new JLabel(" Anio: ");
		JTextField tAnio = new JTextField(20);

		panelArriba.add(id);
		panelArriba.add(tId);
		panelArriba.add(anio);
		panelArriba.add(tAnio);
		
		JPanel panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panelAbajo, BorderLayout.PAGE_END);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Integer id;
				String anioS, auxId;
				try {
					auxId = tId.getText();
					anioS = tAnio.getText();
					if (tId.getText().equals("")) {
						throw new Exception("No puedes modificar un anio si no proporcionas un id.");
					}
					if (tAnio.getText().equals("")) {
						throw new Exception("No puedes modificar un anio si no proporcionas la letra.");
					}
					if (!auxId.matches("^[0-9]*$"))
						throw new Exception("El id debe ser un numero entero positivo");
					
					id = Integer.parseInt(auxId);
					TAnio t = new TAnio(id, anioS);

					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificacionAnio, t));
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
		this.setBounds(600, 600, 400, 100);
		this.setVisible(true);
	
	}


	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ModificacionAnio_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);
	}
	
}