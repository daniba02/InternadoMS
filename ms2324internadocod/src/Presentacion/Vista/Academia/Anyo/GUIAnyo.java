package Presentacion.Vista.Academia.Anyo;

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

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.ApplicationController.ViewerFactory;
import Presentacion.Evento.Evento;


public class GUIAnyo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel panel2;
	
	//Function Buttons
	private JButton create;
	private JButton delete;
	private JButton search;
	private JButton modify;
	private JButton listar;
	
	private JLabel labelTitle;
	private JLabel logo;

	public GUIAnyo(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}
	
	public GUIAnyo() {
		super("Anio");
		initGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		this.setTitle("Anio");
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		panel = new JPanel();
        labelTitle = new JLabel();
        logo = new JLabel();
        panel2 = new JPanel();
        create = new JButton();
        delete = new JButton();
        search = new JButton();
        modify = new JButton();
        listar = new JButton();
        
        setPreferredSize(new Dimension(900, 500));
        
        //panel izquierda
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
      //title
        labelTitle.setFont(new Font("Stencil", 0, 26)); // NOI18N
        labelTitle.setForeground(new Color(255, 255, 255));
        labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitle.setText("Anio");
        labelTitle.setAlignmentX(90.0F);
        labelTitle.setAlignmentY(90.0F);
        labelTitle.setHorizontalTextPosition(SwingConstants.LEFT);
        labelTitle.setVerticalTextPosition(SwingConstants.TOP);
        panel.add(labelTitle, new AbsoluteConstraints(0, 30, 210, 80));
        
      //imagen
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/Logo.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(140, 150, Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(scaledImage2));
        panel.add(logo, new AbsoluteConstraints(40, 140, 140, 300));
        
      //panel derecha 
        panel2.setBackground(new Color(255, 204, 153));
        panel2.setLayout(new AbsoluteLayout());
        
      //boton ALTA
        create.setBackground(new Color(255, 204, 102));
        create.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        create.setForeground(new Color(255, 255, 255));
        create.setText("ALTA");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaAnio,null));
            }
        });
        panel2.add(create, new AbsoluteConstraints(120, 100, 150, 75));

      //boton baja
        delete.setBackground(new Color(255, 204, 102));
        delete.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        delete.setForeground(new Color(255, 255, 255));
        delete.setText("BAJA");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.BajaAnio,null));
            }
        });
        panel2.add(delete, new AbsoluteConstraints(420, 100, 150, 75));
        
      //boton consulta
        search.setBackground(new Color(255, 204, 102));
        search.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        search.setForeground(new Color(255, 255, 255));
        search.setText("CONSULTA");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultaAnio,null));
            }
        });
        panel2.add(search, new AbsoluteConstraints(120, 215, 150, 75));
        
      //boton modificar
        modify.setBackground(new Color(255, 204, 102));
        modify.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modify.setForeground(new Color(255, 255, 255));
        modify.setText("MODIFICAR");
        modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificacionAnio,null));
            }
        });
        panel2.add(modify, new AbsoluteConstraints(420, 215, 150, 75));
        
      //boton listar
        listar.setBackground(new Color(255, 204, 102));
        listar.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listar.setForeground(new Color(255, 255, 255));
        listar.setText("LISTAR");
        listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarAnio,null));
            }
        });
        panel2.add(listar, new AbsoluteConstraints(270, 330, 150, 75));
        
        
        
        
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
	

	public void actualizar(Context context) {}
	
}