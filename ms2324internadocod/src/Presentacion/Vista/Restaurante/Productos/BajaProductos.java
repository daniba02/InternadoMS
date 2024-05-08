package Presentacion.Vista.Restaurante.Productos;

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
import Presentacion.Vista.Restaurante.GUIutilities;

public class BajaProductos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel mainPanel;


	public BajaProductos() {
		super("BAJA PRODUCTO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void initGUI() {
		this.setResizable(false);

		mainPanel = new JPanel(new BorderLayout());
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new BorderLayout());
		mainPanel.add(panelArriba, BorderLayout.CENTER);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panelArriba.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idProducto = new JLabel("ID producto: ");
		final JTextField tidProducto = new JTextField(20);
		GUIutilities.onlyIntegers(tidProducto);
		
		JPanel panel3_2 = new JPanel(new BorderLayout());
		panelArriba.add(panel3_2,BorderLayout.LINE_START);
		panel3_2.add(idProducto,BorderLayout.PAGE_START);
		panel3_1.add(tidProducto,BorderLayout.PAGE_START);
		
		JPanel panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(RIGHT_ALIGNMENT);
		mainPanel.add(panelAbajo, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer id;
				try {
					try {
						id = Integer.parseInt(tidProducto.getText());
					} catch (Exception ex2) {
						throw new Exception("El id debe ser un numero");
					}

					if (id <= 0)
						throw new Exception("El id debe ser mayor que 0");

					ApplicationController.getInstancia().handleRequest(new Context(Evento.BajaProductos, id));

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
		add(mainPanel,BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(400, 400, 400, 155);
		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.BajaProductos_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
	}
	
}
