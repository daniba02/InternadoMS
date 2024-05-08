package Presentacion.Vista.Restaurante.Platos;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Platos.TComida;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;
import Presentacion.Vista.Restaurante.GUIutilities;

public class ModificarPlatos extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JPanel panelPrincipal,card;
	JComboBox<String> combo;

	public enum Tipo {
		NORM, COMIDA, BEBIDA
	};

	Tipo tipo = Tipo.NORM;
	
	public ModificarPlatos() {
		super("MODIFICAR PLATO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());

		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panelArriba = new JPanel(new GridLayout(7, 2));
		this.add(panelArriba, BorderLayout.CENTER);

		JLabel id = new JLabel(" ID: ");
		final JTextField tid = new JTextField(20);
		GUIutilities.onlyIntegers(tid);
		
		JLabel Nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);

		JLabel Stock = new JLabel(" Stock: ");
		final JTextField tStock = new JTextField(20);
		GUIutilities.onlyIntegers(tStock);
		
		JLabel Precio = new JLabel(" Precio: ");
		final JTextField tPrecio = new JTextField(20);

		panelArriba.add(id);
		panelArriba.add(tid);
		panelArriba.add(Precio);
		panelArriba.add(tPrecio);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		panelArriba.add(Stock);
		panelArriba.add(tStock);
		panelArriba.add(Precio);
		panelArriba.add(tPrecio);
		
		card = new JPanel();
		card.setLayout(new CardLayout());

		JPanel noth = new JPanel();
		JPanel panelCom = new JPanel();

		JLabel com = new JLabel(" Tipo: ");
		JTextField tCom = new JTextField(20);
		panelCom.add(com);
		panelCom.add(tCom);

		JPanel panelBeb = new JPanel();

		JLabel temp = new JLabel(" Temperatura: ");
		JTextField tTemp = new JTextField(20);
		panelBeb.add(temp);
		panelBeb.add(tTemp);

		card.add(panelCom, BorderLayout.SOUTH);
		card.add(panelBeb, BorderLayout.SOUTH);
		card.add(noth);
		noth.setVisible(true);
		panelBeb.setVisible(false);
		panelCom.setVisible(false);

		combo = new JComboBox<String>();
		combo.setModel(new DefaultComboBoxModel<String>(new String[] { " Elija un tipo: ", "Comida", "Bebida" }));
		panelArriba.add(combo);
		panelArriba.add(card, BorderLayout.SOUTH);
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = combo.getSelectedIndex();
				switch (selection) {
				case 0:
					tipo = Tipo.NORM;
					panelBeb.setVisible(false);
					panelCom.setVisible(false);
					noth.setVisible(true);
					break;
				case 1:
					tipo = Tipo.COMIDA;
					panelBeb.setVisible(false);
					panelCom.setVisible(true);
					noth.setVisible(false);
					break;
				case 2:
					tipo = Tipo.BEBIDA;
					panelBeb.setVisible(true);
					panelCom.setVisible(false);
					noth.setVisible(false);
					break;
				}
			}

		});
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
				Integer id;
				Double Precio = null,Temperatura;
				Integer Stock = null;
				String  Nombre = null, Type;

				try {
					
					try {
						id = Integer.parseInt(tid.getText());
					} catch (Exception ex2) {
						throw new Exception("El id debe ser un numero entero");
					}
					if (id <= 0)
						throw new Exception("El id debe ser un numero entero positivo");
					try {
						Precio = Double.parseDouble(tPrecio.getText());
					} catch (Exception ex2) {
						throw new Exception("El precio debe ser un numero entero");
					}
					if (Precio <= 0)
						throw new Exception("El precio debe ser un numero entero positivo");
					try {
						Stock = Integer.parseInt(tStock.getText());
					} catch (Exception ex2) {
						throw new Exception("El stock debe ser un numero entero");
					}
					if (Stock <= 0)
						throw new Exception("El stock debe ser un numero entero positivo");
						try{
						Nombre = tNombre.getText();
						}catch(Exception ex2){
							throw new Exception("El nombre tiene que ser un string");
						}
				
					TPlatoCompleto toap;
					if(tipo==Tipo.BEBIDA){
						try{
						Temperatura = Double.parseDouble(tTemp.getText());
						}catch(Exception ex3){
							throw new Exception("La temperatura debe ser un numero");
						}
						toap = new TBebida(id,Nombre,Stock,Precio,Temperatura);
					}else if(tipo==Tipo.COMIDA){
						Type = tCom.getText();
						toap = new TComida(id,Nombre,Stock,Precio,Type);
					}else{
						throw new Exception("Se tiene que elegir uno de los dos tipos");
					}
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarPlatos, toap));
					
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

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(600, 600, 700, 250);
		this.setVisible(true);
	}

	public void actualizar(Context context) {
	if(context.getEvento()==Evento.ModificarPlatos_OK) {
		JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
		}
	setVisible(false);
	}
	
}
