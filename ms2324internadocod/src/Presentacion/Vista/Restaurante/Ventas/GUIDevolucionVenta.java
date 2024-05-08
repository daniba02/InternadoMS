package Presentacion.Vista.Restaurante.Ventas;

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
import javax.swing.SwingUtilities;

import Negocio.Restaurante.PlatosVentas.TPlatoVenta;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIDevolucionVenta extends JFrame implements IGUI{

private static final long serialVersionUID = 1L;
	JButton aceptar,cancelar;
	JPanel panelPrincipal;

	public GUIDevolucionVenta() {
		super("DEVOLUCION VENTA");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		
		this.setResizable(false);
		
		panelPrincipal = new JPanel(new BorderLayout());
		
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		
		JPanel panelArriba = new JPanel(new GridLayout(3, 2));
		this.add(panelArriba, BorderLayout.CENTER);
		
		JLabel idVenta = new JLabel(" ID Venta: ");
		final JTextField tidVenta = new JTextField(20);
		
		JLabel idPlato = new JLabel(" ID plato: ");
		final JTextField tidPlato = new JTextField(20);
		
		JLabel cantidad = new JLabel("Cantidad a devolver: ");
		final JTextField tCantidad = new JTextField(20);
	
		panelArriba.add(idVenta);
		panelArriba.add(tidVenta);
		panelArriba.add(idPlato);
		panelArriba.add(tidPlato);
		panelArriba.add(cantidad);
		panelArriba.add(tCantidad);
		
		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panel4,BorderLayout.PAGE_END);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer idVenta,idProducto,cantidad;
				try{
					try{
						idVenta = Integer.parseInt(tidVenta.getText());
					}catch(Exception ex2){
						throw new Exception("El id debe ser un numero");
					}
					
					if(idVenta<=0) 
						throw new Exception("El id debe ser un numero entero positivo");
					
                    try{
						idProducto = Integer.parseInt(tidPlato.getText());
					}catch(Exception ex2){
						throw new Exception("El id debe ser un numero");
					}
					
					if(idProducto<=0) 
						throw new Exception("El id debe ser un numero entero positivo");
					
                     try{
						cantidad = Integer.parseInt(tCantidad.getText());
					}catch(Exception ex2){
						throw new Exception("la cantidad debe ser un numero");
					}
					
					if(cantidad<=0) 
						throw new Exception("La cantidad debe ser positiva");
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.DevolucionVenta,new TPlatoVenta(idVenta,idProducto,cantidad)));
					setVisible(false);
					
				}catch(Exception ex1){
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
					
				}
				finally {
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
		
		//add(panelPrincipal,BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(615, 615, 515, 145);
		this.setVisible(true);
		
	}

	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.DevolucionVenta_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new GUIDevolucionVenta();
			}
		});
	}

}
