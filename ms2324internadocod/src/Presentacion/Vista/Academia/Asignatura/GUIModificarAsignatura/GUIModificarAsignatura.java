package Presentacion.Vista.Academia.Asignatura.GUIModificarAsignatura;

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

import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Asignaturas.TOptativa;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIModificarAsignatura extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	JButton aceptar, cancelar;
	JComboBox<String> combo;
	JPanel panelPrincipal, card;
	enum Tipo{NORM, OBLIGATORIA, OPTATIVA};
	Tipo tipo = Tipo.NORM;
	
	public GUIModificarAsignatura() {
		super("MODIFICAR ASIGNATURAS");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {

		this.setResizable(false);
		panelPrincipal = new JPanel(new BorderLayout());
		this.add(panelPrincipal);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		JPanel panelArriba = new JPanel(new GridLayout(6, 2));
		panelPrincipal.add(panelArriba, BorderLayout.CENTER);
		
		JLabel id = new JLabel(" ID: ");
		final JTextField tId = new JTextField(20);
		
		JLabel Nombre = new JLabel(" Nombre: ");
		final JTextField tNombre = new JTextField(20);
		
		panelArriba.add(id);
		panelArriba.add(tId);
		panelArriba.add(Nombre);
		panelArriba.add(tNombre);
		
		
		card = new JPanel();
	    card.setLayout( new CardLayout() );

	    JPanel noth = new JPanel();
	    JPanel panelOpt = new JPanel();
	    
	    JLabel opt = new JLabel(" Nivel: ");
		JTextField tOpt = new JTextField(20);
	    panelOpt.add(opt);
	    panelOpt.add(tOpt);
	    
	    
	    JPanel panelOblig = new JPanel();
	  
	    JLabel oblig = new JLabel(" Itinerario: ");
		JTextField tOblig = new JTextField(20);
		panelOblig.add(oblig);
		panelOblig.add(tOblig);

	    card.add( panelOpt );
	    card.add( panelOblig );
	    card.add(noth);
	    noth.setVisible(true);
	    panelOblig.setVisible(false);
		panelOpt.setVisible(false);
	    
	    combo = new JComboBox<String>();
		combo.setModel(new DefaultComboBoxModel<String>(new String[]{ " Elija un tipo: ", "Optativa", "Obligatoria"}));
        panelArriba.add(combo);
        panelArriba.add(card, BorderLayout.SOUTH);
        combo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int selection = combo.getSelectedIndex();
				switch(selection){
				case 0:
					tipo = Tipo.NORM;
					panelOblig.setVisible(false);
					panelOpt.setVisible(false);
					noth.setVisible(true);
					break;
				case 1:
					tipo = Tipo.OPTATIVA;
					panelOblig.setVisible(false);
					panelOpt.setVisible(true);
					noth.setVisible(false);
					break;
				case 2:
					tipo =Tipo.OBLIGATORIA;
					panelOblig.setVisible(true);
					panelOpt.setVisible(false);
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

				int nivel;
				
				try {
					
					if(tNombre.getText().equals(""))
						throw new Exception("Campo nombre no puede ser nulo");
					
					if(tipo == Tipo.OBLIGATORIA){
						if(tOblig.getText().equals(""))
							throw new Exception("Campo itinerario no puede ser nulo");
							
						
						ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarAsignaturas,
								new TObligatoria(Integer.parseInt(tId.getText()), true, tNombre.getText(), tOblig.getText())));
					}else if(tipo == Tipo.OPTATIVA){
						if(tOpt.getText().equals(""))
							throw new Exception("Campo nivel no puede ser nulo");
						try{
							nivel = Integer.parseInt(tOpt.getText());
						}catch(Exception ex){
							throw new Exception("Nivel tiene que ser un numero eg : 1, 2, 3...");
						}
						
						if(nivel <= 0)
							throw new Exception("Nivel debe ser un numero mayor que cero");
						
						
						ApplicationController.getInstancia().handleRequest(new Context(Evento.ModificarAsignaturas,
								new TOptativa(Integer.parseInt(tId.getText()), true, tNombre.getText(), nivel)));
					}else{
						throw new Exception("Se tiene que elegir uno de los tipos");
					}
					
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
		this.setBounds(600, 600, 400, 250);
		this.setVisible(true);
	}

	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ModificarAsignaturas_OK) {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.INFORMATION_MESSAGE);

			}else {
				JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			}
		this.setVisible(false);
	}

}