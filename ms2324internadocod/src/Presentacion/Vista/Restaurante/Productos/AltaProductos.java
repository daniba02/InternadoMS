package Presentacion.Vista.Restaurante.Productos;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import Presentacion.Vista.Restaurante.GUIutilities;

public class AltaProductos extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel mainPanel,card;
	

	public AltaProductos() {
		super("ALTA PRODUCTO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public void initGUI() {

		this.setResizable(false);
		mainPanel = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(5, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JPanel renovar = new JPanel(new GridLayout(1,1));
		renovar.setBorder(BorderFactory.createTitledBorder("Para productos existentes no activos"));
		this.add(renovar, BorderLayout.NORTH);
		
		JLabel id = new JLabel(" ID: ");
		final JTextField tid = new JTextField(20);
		GUIutilities.onlyIntegers(tid);
		
		JLabel nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel caracteriticas = new JLabel(" Caracteristicas: ");
		final JTextField tCaracteristicas = new JTextField(50);

		renovar.add(id);
		renovar.add(tid);
		panelArriba.add(nombre);
		panelArriba.add(tNombre);
		panelArriba.add(caracteriticas);
		panelArriba.add(tCaracteristicas);

		card = new JPanel();
		card.setLayout(new CardLayout());
		
		JPanel noth = new JPanel();
		
		noth.setVisible(true);
		JPanel panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panelAbajo, BorderLayout.PAGE_END);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Volver");
		cancelar.setBackground(colorRojo);

		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre,caract;
				Integer id;
				try {
					id = Integer.parseInt(tid.getText());
				} catch (Exception ex2) {
					id=-1;
				}
				try {
										
					if(id==-1){
					nombre = tNombre.getText();
					if (nombre.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");

					caract = tCaracteristicas.getText();
					if (caract.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente las caracteristicas");

					if (nombre.length() == 0)
						throw new Exception("El nombre no puede estar vacio");
					
					if (caract.length() == 0)
						throw new Exception("Las caracteristicas no pueden estar vacias");
				
					ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaProductos,new TProducto(id,nombre,caract,true)));
					}else {
						
					ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaProductos,new TProducto(id)));
						
					}
				} catch (Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
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

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(700, 700, 500, 205);
		this.setVisible(true);
	}

	public void actualizar(Context context) {
		if (context.getEvento() == Evento.AltaProductos_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(false);
	}

}
