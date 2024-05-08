package Presentacion.Vista.Restaurante.Empleados;

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


public class GUIEmpleados extends JFrame implements IGUI {
	
private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel panel2;
	//Botones
	private JButton altaEmpleado;
	private JButton listarCocinero;
	private JButton mostrarEmpleadoTurno;
	//private JButton mostrarEmpleadosPorMesa;
	
	private JButton listarEmpleados;
	private JButton consultar;
	private JButton modificarEmpleado;
	private JButton bajaEmpleado;
	
	private JButton listarCamarero;

	private JLabel platLabel;
	private JLabel iconLabel;

	public GUIEmpleados(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}
	
	public GUIEmpleados() {
		super("Empleados");
		initGUI();
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public void initGUI() {
		
		this.setTitle("Empleados");
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		panel = new JPanel();
        platLabel = new JLabel();
        iconLabel = new JLabel();
        panel2 = new JPanel();
        
        
        altaEmpleado = new JButton();
        listarCocinero = new JButton();
        mostrarEmpleadoTurno = new JButton();
        listarEmpleados = new JButton();
        consultar = new JButton();
        bajaEmpleado = new JButton();
        modificarEmpleado = new JButton();
        listarCamarero = new JButton();
        //mostrarEmpleadosPorMesa = new JButton();
        
        setPreferredSize(new Dimension(900, 500));

    //PANEL DE LA IZQUIERDA
        
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
    //LABEL CON TEXTO Empleados
        
        platLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        platLabel.setForeground(new Color(255, 255, 255));
        platLabel.setHorizontalAlignment(SwingConstants.CENTER);
        platLabel.setText("Empleados");
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

	//ALTA
        altaEmpleado.setBackground(new Color(255, 204, 102));
        altaEmpleado.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        altaEmpleado.setForeground(new Color(255, 255, 255));
        altaEmpleado.setText("ALTA");
        altaEmpleado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaEmpleados,null));
            }
        });
        panel2.add(altaEmpleado, new AbsoluteConstraints(70, 80, 180, 65));
        
    //LISTAR COCINERO
        listarCocinero.setBackground(new Color(255, 204, 102));
        listarCocinero.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarCocinero.setForeground(new Color(255, 255, 255));
        listarCocinero.setText("LISTAR COCINEROS");
        listarCocinero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarCocineros,null));
            }
        });
        panel2.add(listarCocinero, new AbsoluteConstraints(70, 165, 180, 65));
        
    //MOSTRAR EMPLEADOS POR TURNO
        mostrarEmpleadoTurno.setBackground(new Color(255, 204, 102));
        mostrarEmpleadoTurno.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        mostrarEmpleadoTurno.setForeground(new Color(255, 255, 255));
        mostrarEmpleadoTurno.setText("<html><center>MOSTRAR EMPLEADOS<br>POR TURNO</center></html>");
        mostrarEmpleadoTurno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.MostrarEmpleadosPorTurno,null));
            }
        });
        panel2.add(mostrarEmpleadoTurno, new AbsoluteConstraints(165, 250, 180, 65));
        
    //BAJA
        bajaEmpleado.setBackground(new Color(255, 204, 102));
        bajaEmpleado.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        bajaEmpleado.setForeground(new Color(255, 255, 255));
        bajaEmpleado.setText("BAJA");
        bajaEmpleado.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		ViewerFactory.getInstance().generarVista(new Context(Evento.BajaEmpleados,null));
        	}
        });
        panel2.add(bajaEmpleado, new AbsoluteConstraints(260, 80, 180, 65));

    //LISTAR EMPLEADOS
        listarEmpleados.setBackground(new Color(255, 204, 102));
        listarEmpleados.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarEmpleados.setForeground(new Color(255, 255, 255));
        listarEmpleados.setText("LISTAR EMPLEADOS");
        listarEmpleados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarEmpleados,null));
            }
        });
        panel2.add(listarEmpleados, new AbsoluteConstraints(260, 165, 180, 65));
        
    //CONSULTAR
        consultar.setBackground(new Color(255, 204, 102));
        consultar.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        consultar.setForeground(new Color(255, 255, 255));
        consultar.setText("CONSULTAR");
        consultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarEmpleados,null));
            }
        });
        panel2.add(consultar, new AbsoluteConstraints(355, 250, 180, 65));
        
    //MODIFICAR
        modificarEmpleado.setBackground(new Color(255, 204, 102));
        modificarEmpleado.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modificarEmpleado.setForeground(new Color(255, 255, 255));
        modificarEmpleado.setText("MODIFICAR");
        modificarEmpleado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarEmpleados,null));
            }
        });
        panel2.add(modificarEmpleado, new AbsoluteConstraints(450, 80, 180, 65));
        
    //LISTAR CAMARERO
        listarCamarero.setBackground(new Color(255, 204, 102));
        listarCamarero.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarCamarero.setForeground(new Color(255, 255, 255));
        listarCamarero.setText("LISTAR CAMAREROS");
        listarCamarero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarCamareros,null));
            }
        });
        panel2.add(listarCamarero, new AbsoluteConstraints(450, 165, 180, 65)); 
            
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
	public void actualizar(Context context) {	}

}
