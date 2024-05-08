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

import Negocio.Restaurante.Mesas.TMesas;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;
import Presentacion.Vista.Restaurante.GUIutilities;

public class GUIModificarMesas extends JFrame implements IGUI{
	private static final long serialVersionUID = 1L;
	private JButton aceptar, cancelar;
	private JPanel panelPrincipal;
	
	public GUIModificarMesas(){
		super("MODIFICAR MESAS");
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
		
		JPanel panelArriba = new JPanel(new GridLayout(4, 2));
		panelPrincipal.add(panelArriba, BorderLayout.CENTER);
		
		JLabel id = new JLabel("ID Mesa: ");
		JTextField idT= new JTextField();
		GUIutilities.onlyIntegers(idT);
		
		panelArriba.add(id);
		panelArriba.add(idT);
		
		JLabel numero = new JLabel("Numero Mesa: ");
		JTextField numeroT= new JTextField();
		GUIutilities.onlyIntegers(numeroT);
		
		panelArriba.add(numero);
		panelArriba.add(numeroT);
		
		
		JLabel cap = new JLabel("Capacidad Mesa: ");
		JTextField capT= new JTextField();
		GUIutilities.onlyIntegers(capT);
		
		panelArriba.add(cap);
		panelArriba.add(capT);
		
		JPanel panelAbajo = new JPanel();
		panelAbajo.setAlignmentX(RIGHT_ALIGNMENT);
		this.add(panelAbajo, BorderLayout.PAGE_END);
		
		JLabel empId = new JLabel(" Id Camarero: ");
		JTextField empT = new JTextField();
		GUIutilities.onlyIntegers(empT);
		
		panelArriba.add(empId);
		panelArriba.add(empT);

		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					//Comprobacion de datos nulos
					if(idT.getText().equals(""))
						throw new Exception("Campo Id Mesa no puede ser nulo");
					
					if (numeroT.getText().equals(""))
						throw new Exception("Campo numero mesa no puede ser nulo");
					
					if (capT.getText().equals(""))
						throw new Exception("Campo capacidad mesa no puede ser nulo");
					
					if(empT.getText().equals(""))
						throw new Exception("Campo Id camarero no puede ser nulo");
					
					TMesas mesa = new TMesas();
					mesa.setID(Integer.parseInt(idT.getText()));
					mesa.setNum(Integer.parseInt(numeroT.getText()));
					mesa.setCapacidad( Integer.parseInt(capT.getText()));
					mesa.setIDEmpleado(Integer.parseInt(empT.getText()));
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarMesas, mesa));
					
					
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
		if(context.getEvento()==Evento.ModificarMesa_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
			setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
				setVisible(false);
			}
	}
 
}
