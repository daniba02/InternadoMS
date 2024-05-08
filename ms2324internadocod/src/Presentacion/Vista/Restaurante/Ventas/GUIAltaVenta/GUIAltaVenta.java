package Presentacion.Vista.Restaurante.Ventas.GUIAltaVenta;

import java.awt.BorderLayout;
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

import Negocio.Restaurante.Ventas.TVentas;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIAltaVenta extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	public GUIAltaVenta() {
		super("ALTA VENTA");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		
		this.setResizable(false);
		
		panelPrincipal = new JPanel(new BorderLayout());
		
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		

		JPanel panelArriba = new JPanel(new GridLayout(4, 2));
		this.add(panelArriba, BorderLayout.CENTER);
		
		JPanel renovar = new JPanel(new GridLayout(1,1));
		renovar.setBorder(BorderFactory.createTitledBorder(" Solo para abrir ventas ya existentes : "));
		this.add(renovar, BorderLayout.NORTH);
		
		JLabel id = new JLabel(" ID Venta: ");
		final JTextField tid = new JTextField(20);
		
		JLabel cliente = new JLabel(" Cliente: ");
		final JTextField tCliente = new JTextField(20);

		JLabel fecha = new JLabel(" Fecha: ");
		final JTextField tFecha = new JTextField(20);

        JLabel IDMesa = new JLabel(" ID-Mesa: ");
		final JTextField tIDMesa = new JTextField(20);

		renovar.add(id);
		renovar.add(tid);
		panelArriba.add(cliente);
		panelArriba.add(tCliente);
		panelArriba.add(fecha);
		panelArriba.add(tFecha);
		panelArriba.add(IDMesa);
		panelArriba.add(tIDMesa);
		
		
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
				Integer id;
				String  Cliente;
				String  Fecha;
				Integer Mesa;

				try {
					
					try {
						
						id = Integer.parseInt(tid.getText());
					} catch (Exception ex2) {
						//throw new Exception("El id debe ser un numero entero");
					id=-1;
					}
				//	if (id <= 0)
				//		throw new Exception("El precio debe ser un numero entero positivo");
					
					Cliente = tCliente.getText();
					if (Cliente.matches(".*\\d+.*"))
						throw new Exception("No se ha introducido correctamente el nombre");
					
					Fecha = tFecha.getText();
					if(Fecha.length() != 10)
						throw new Exception("La hora tiene que seguir el siguiente formato: 'XX/YY/ZZZZ'");
					
					if(Fecha.charAt(2) != '/' || Fecha.charAt(5) != '/')
						throw new Exception("No se encuentran barras separadoras '/'");
					
					try {
							String[] partes = Fecha.split("/");
							int dia = Integer.parseInt(partes[0]);
							int mes = Integer.parseInt(partes[1]);
							int anio = Integer.parseInt(partes[2]);
							
							if(!fechaValida(dia, mes, anio))
								throw new Exception("Dia - Mes - Anio invalidos");
						
					} catch (Exception ex2) {
						throw new Exception("Problema en la fecha");
					}
					try {
						Mesa = Integer.parseInt(tIDMesa.getText());
					} catch (Exception ex2) {
						throw new Exception("El anio debe ser un numero entero");
					}
	
					if (Mesa <= 0)
						throw new Exception("El anio debe ser un numero entero positivo");
					
					TVentas v = new TVentas(id, Cliente, Fecha, Mesa, true);	
	
					//ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaVenta, v));
					//ViewerFactory.getInstance().generarVista(new Context(Evento.Carrito,v));
				CarritoDialog dialogoCarrito=new CarritoDialog(GUIAltaVenta.this,v);
				dialogoCarrito.open();
				} catch (Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				} finally {
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
		this.setBounds(615, 615, 615, 205);
		this.setVisible(true);
	}
	
	private boolean fechaValida(int dia, int mes, int anio) {
        if (anio < 2000 || anio > 2050)	 return false;
        if( mes < 1 || mes > 12)	return false;
        
        int[] diasEnMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(dia < 1 || dia > diasEnMes[mes])	return false;
        
        return true;
	}
	
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.AltaVenta_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
	}
}
