package Presentacion.Vista.Restaurante.Platos;

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

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.ApplicationController.ViewerFactory;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;


public class GUIPlatos extends JFrame implements IGUI{
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel panel2;
	//Botones
	private JButton altaPlatos;
	private JButton bajaPlatos;
	private JButton consultarPlatos;
	
	private JButton listarPlatos;
	private JButton listarPlatosPorProductos;
	private JButton listarBebidas;
	private JButton listarComida;
	
	private JButton modificarPlatos;
	private JButton vincularPlatos;
	private JButton desvincularPlatos;

	private JLabel platLabel;
	private JLabel iconLabel;

	public GUIPlatos(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}
	
	public GUIPlatos() {
		super("Platos");
		initGUI();
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public void initGUI() {
		
		this.setTitle("Platos");
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		panel = new JPanel();
        platLabel = new JLabel();
        iconLabel = new JLabel();
        panel2 = new JPanel();
        
        
        altaPlatos = new JButton();
        bajaPlatos = new JButton();
        consultarPlatos = new JButton();
        listarPlatos = new JButton();
        listarPlatosPorProductos = new JButton();
        listarBebidas = new JButton();
        listarComida = new JButton();
        modificarPlatos = new JButton();
        vincularPlatos =  new JButton();
        desvincularPlatos =  new JButton();
        
        setPreferredSize(new Dimension(900, 500));

        //PANEL DE LA IZQUIERDA
        
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO Platos
        
        platLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        platLabel.setForeground(new Color(255, 255, 255));
        platLabel.setHorizontalAlignment(SwingConstants.CENTER);
        platLabel.setText("Platos");
        platLabel.setAlignmentX(90.0F);
        platLabel.setAlignmentY(90.0F);
        platLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        platLabel.setVerticalTextPosition(SwingConstants.TOP);
        panel.add(platLabel, new AbsoluteConstraints(0, 30, 210, 80));

        //IMAGEN DEL ICONO
        
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/LogoRestaurante.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledImage2));
        panel.add(iconLabel, new AbsoluteConstraints(10, 140, 220, 300));

        //PANEL DE LA DERECHA CON LOS BOTONES
        
        panel2.setBackground(new Color(255, 204, 153));
        panel2.setLayout(new AbsoluteLayout());

		
        altaPlatos.setBackground(new Color(255, 204, 102));
        altaPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        altaPlatos.setForeground(new Color(255, 255, 255));
        altaPlatos.setText("ALTA");
        altaPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaPlatos,null));
            }
        });
        panel2.add(altaPlatos, new AbsoluteConstraints(70, 75, 180, 65));
        
        
        bajaPlatos.setBackground(new Color(255, 204, 102));
        bajaPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        bajaPlatos.setForeground(new Color(255, 255, 255));
        bajaPlatos.setText("BAJA");
        bajaPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ViewerFactory.getInstance().generarVista(new Context(Evento.BajaPlatos,null));
            }
        });
        panel2.add(bajaPlatos, new AbsoluteConstraints(70, 155, 180, 65));
        
        
        consultarPlatos.setBackground(new Color(255, 204, 102));
        consultarPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        consultarPlatos.setForeground(new Color(255, 255, 255));
        consultarPlatos.setText("CONSULTAR");
        consultarPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarPlatos,null));
            }
        });
        panel2.add(consultarPlatos, new AbsoluteConstraints(70, 235, 180, 65));
        
        
        listarPlatos.setBackground(new Color(255, 204, 102));
        listarPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarPlatos.setForeground(new Color(255, 255, 255));
        listarPlatos.setText("LISTAR PLATOS");
        listarPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarPlatos,null));
            }
        });
        panel2.add(listarPlatos, new AbsoluteConstraints(260, 155, 180, 65));
        
        
        listarPlatosPorProductos.setBackground(new Color(255, 204, 102));
        listarPlatosPorProductos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarPlatosPorProductos.setForeground(new Color(255, 255, 255));
        listarPlatosPorProductos.setText("LISTAR PLATOS POR PRODUCTO");
        listarPlatosPorProductos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarPlatosPorProducto,null));
            }
        });
        panel2.add(listarPlatosPorProductos, new AbsoluteConstraints(260, 75, 180, 65));
        
        
        listarBebidas.setBackground(new Color(255, 204, 102));
        listarBebidas.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarBebidas.setForeground(new Color(255, 255, 255));
        listarBebidas.setText("LISTAR BEBIDAS");
        listarBebidas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarBebidas,null));
            }
        });
        panel2.add(listarBebidas, new AbsoluteConstraints(450, 235, 180, 65));
        
        
        listarComida.setBackground(new Color(255, 204, 102));
        listarComida.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarComida.setForeground(new Color(255, 255, 255));
        listarComida.setText("LISTAR COMIDAS");
        listarComida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarComida,null));
            }
        });
        panel2.add(listarComida, new AbsoluteConstraints(450, 75, 180, 65));
        
        modificarPlatos.setBackground(new Color(255, 204, 102));
        modificarPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modificarPlatos.setForeground(new Color(255, 255, 255));
        modificarPlatos.setText("MODIFICAR");
        modificarPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarPlatos,null));
            }
        });
        panel2.add(modificarPlatos, new AbsoluteConstraints(450, 155, 180, 65)); 
        
        vincularPlatos.setBackground(new Color(255, 204, 102));
        vincularPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        vincularPlatos.setForeground(new Color(255, 255, 255));
        vincularPlatos.setText("VINCULAR");
        vincularPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.VincularPlatoProducto,null));
            }
        });
        panel2.add(vincularPlatos, new AbsoluteConstraints(260, 235, 180, 65)); 
        
        desvincularPlatos.setBackground(new Color(255, 204, 102));
        desvincularPlatos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        desvincularPlatos.setForeground(new Color(255, 255, 255));
        desvincularPlatos.setText("DESVINCULAR");
        desvincularPlatos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.DesvincularPlatoProducto,null));
            }
        });
        panel2.add(desvincularPlatos, new AbsoluteConstraints(260, 315, 180, 65)); 
        
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
	public void actualizar(Context context) {}

}
