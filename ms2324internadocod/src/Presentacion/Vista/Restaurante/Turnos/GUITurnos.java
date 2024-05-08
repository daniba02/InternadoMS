package Presentacion.Vista.Restaurante.Turnos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.ApplicationController.ViewerFactory;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;
import Presentacion.Vista.GUIMain;

public class GUITurnos extends JFrame implements IGUI{
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panel2;
	
	//funcion botones
	private JButton alta;
	private JButton baja;
	private JButton consulta;
	private JButton listar;
	private JButton modificar;
	
	private JLabel asigLabel;
	private JLabel iconLabel;
	
	public GUITurnos(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}
	
	public GUITurnos() {
		super("turnos");
		initGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	private void initGUI() {
		this.setTitle("Turnos");
		panel=new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		
		panel2=new JPanel();
		asigLabel=new JLabel();
		iconLabel=new JLabel();
		alta=new JButton();
		baja=new JButton();
		consulta=new JButton();
		listar=new JButton();
		modificar=new JButton();
		
		
		 setPreferredSize(new Dimension(900, 500));
	        
	        //PANEL IZQUIERDA
	        panel.setBackground(new Color(255, 153, 102));
	        panel.setLayout(new AbsoluteLayout());
	        
	        //LABEL CON TEXTO ALUMNO
	        asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
	        asigLabel.setForeground(new Color(255, 255, 255));
	        asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        asigLabel.setText("Turnos");
	        asigLabel.setAlignmentX(90.0F);
	        asigLabel.setAlignmentY(90.0F);
	        asigLabel.setHorizontalTextPosition(SwingConstants.LEFT);
	        asigLabel.setVerticalTextPosition(SwingConstants.TOP);
	        panel.add(asigLabel, new AbsoluteConstraints(0, 30, 210, 80));
	        
	      //imagen
	        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/LogoRestaurante.png"));
			Image image2 = imageIcon2.getImage();
			Image scaledImage2 = image2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			iconLabel.setIcon(new ImageIcon(scaledImage2));
	        panel.add(iconLabel, new AbsoluteConstraints(10, 140, 220, 300));
	        
	        //panel derecha con botones
	        panel2.setBackground(new Color(255, 204, 153));
	        panel2.setLayout(new AbsoluteLayout());
	        
	      //BOTÓN ALTA
	        alta.setBackground(new Color(255, 204, 102));
	        alta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
	        alta.setForeground(new Color(255, 255, 255));
	        alta.setText("ALTA");
	        alta.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
					ViewerFactory.getInstance().generarVista(new Context(Evento.AltaTurno,null));
	            }
	        });
	        panel2.add(alta, new AbsoluteConstraints(122, 56, 150, 75));

	      //BOTÓN BAJA
	        baja.setBackground(new Color(255, 204, 102));
	        baja.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
	        baja.setForeground(new Color(255, 255, 255));
	        baja.setText("BAJA");
	        baja.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	            	//new GUIBajaAsignatura();
					ViewerFactory.getInstance().generarVista(new Context(Evento.BajaTurno,null));
	            }
	        });
	        panel2.add(baja, new AbsoluteConstraints(420, 56, 150, 75));
	        
	      //BOTÓN MODIFICAR
	        modificar.setBackground(new Color(255, 204, 102));
	        modificar.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
	        modificar.setForeground(new Color(255, 255, 255));
	        modificar.setText("MODIFICAR");
	        modificar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
					ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarTurno,null));
	            }
	        });
	        panel2.add(modificar, new AbsoluteConstraints(122, 161, 150, 75));
	        
	      //BOTON MOSTRAR
	        consulta.setBackground(new Color(255, 204, 102));
	        consulta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
	        consulta.setForeground(new Color(255, 255, 255));
	        consulta.setText("CONSULTAR");
	        consulta.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
					ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarTurno,null));
	            }
	        });
	        panel2.add(consulta, new AbsoluteConstraints(420, 161, 150, 75));
	        
	        
	      //BOTÓN LISTAR
	        listar.setBackground(new Color(255, 204, 102));
	        listar.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
	        listar.setForeground(new Color(255, 255, 255));
	        listar.setText("LISTAR");
	        listar.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarTurnos,null));
	            }
	        });
	        panel2.add(listar, new AbsoluteConstraints(271, 264, 150, 75));
	        
	        GroupLayout layout = new GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );

	        pack();	
	        
	}

	@Override
	public void actualizar(Context context) {		
	}
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new GUIMain();
			}
		});
	}
}
