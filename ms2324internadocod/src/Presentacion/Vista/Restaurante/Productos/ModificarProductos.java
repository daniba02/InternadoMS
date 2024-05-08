package Presentacion.Vista.Restaurante.Productos;

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

import Negocio.Restaurante.Producto.TProducto;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class ModificarProductos extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	public ModificarProductos() {
		super("MODIFICAR PRODUCTO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void initGUI() {
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		panelPrincipal = new JPanel(new BorderLayout());
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.CENTER);

		JPanel panel3_1 = new JPanel(new GridLayout(3, 1));
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel id = new JLabel(" ID Producto: ");
		final JTextField tId = new JTextField(20);

		JLabel nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel caracteristicas = new JLabel(" Caracteristicas: ");
		final JTextField tCaracteristicas = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(id, BorderLayout.PAGE_START);
		panel3_1.add(tId, BorderLayout.PAGE_START);
		panel3_2.add(nombre, BorderLayout.CENTER);
		panel3_1.add(tNombre, BorderLayout.CENTER);
		panel3_2.add(caracteristicas, BorderLayout.SOUTH);
		panel3_1.add(tCaracteristicas, BorderLayout.SOUTH);

		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre, caracteristicas;
				int id;
				try {
					try {
						id = Integer.parseInt(tId.getText());
					} catch (Exception ex2) {
						throw new Exception("El ID debe ser un numero entero");
					}
					if (id <= 0)
						throw new Exception("El ID debe ser un numero entero positivo");

					nombre = tNombre.getText();
					if (nombre.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");
					
					if(nombre.length() == 0)
						throw new Exception("El nombre no puede estar vacio");

					caracteristicas = tCaracteristicas.getText();
					if (caracteristicas.matches(".*\\d+.*"))
						throw new Exception("No se han introducido correctamente las caracteristicas");
					
					if(caracteristicas.length() == 0)
						throw new Exception("Las caracteristicas no pueden ser nulas");

					TProducto p = new TProducto(id, nombre, caracteristicas, true);
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarProductos, p));

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
		panel4.add(aceptar);
		panel4.add(cancelar);

		add(panelPrincipal);

		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(350, 350, 600, 120);

	}

	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ModificarProductos_OK)
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		this.setVisible(false);
	}

//	public static void main(String[] args) {
//
//		SwingUtilities.invokeLater(new Runnable() {
//
//			public void run() {
//				new ModificarProductos();
//			}
//		});
//	}

}
