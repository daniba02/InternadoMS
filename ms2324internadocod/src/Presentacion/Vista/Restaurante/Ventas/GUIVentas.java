package Presentacion.Vista.Restaurante.Ventas;

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

public class GUIVentas extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panel2;
	
	private JButton altaVenta;
	private JButton bajaVenta;
	private JButton consultaVenta;
	private JButton devolucionVenta;
	private JButton listarVentas;
	private JButton listarVentasPorCliente;
	private JButton listarVentasPorMesa;
	private JButton modificarVenta;
	
	private JLabel asigLabel;
	private JLabel iconLabel;
	
	public GUIVentas(){
		super("VENTAS");
		initGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void initGUI() {
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		
		panel = new JPanel();
        asigLabel = new JLabel();
        iconLabel = new JLabel();
        panel2 = new JPanel();
        
        altaVenta = new JButton();
        bajaVenta = new JButton();
        consultaVenta = new JButton();
        devolucionVenta = new JButton();
        listarVentas = new JButton();
        listarVentasPorCliente = new JButton();
        listarVentasPorMesa = new JButton();
        modificarVenta = new JButton();
		
		setPreferredSize(new Dimension(900, 500));
        
        //PANEL IZQUIERDA
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO VENTAS
        asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        asigLabel.setForeground(new Color(255, 255, 255));
        asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
        asigLabel.setText("VENTAS");
        asigLabel.setAlignmentX(90.0F);
        asigLabel.setAlignmentY(90.0F);
        asigLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        asigLabel.setVerticalTextPosition(SwingConstants.TOP);
        panel.add(asigLabel, new AbsoluteConstraints(0, 30, 210, 80));
        
        //IMAGEN
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/LogoRestaurante.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledImage2));
        panel.add(iconLabel, new AbsoluteConstraints(10, 140, 220, 300));
        
        //PANEL DERECHA 
        panel2.setBackground(new Color(255, 204, 153));
        panel2.setLayout(new AbsoluteLayout());
       
        
        //BOTON ALTA VENTA
        altaVenta.setBackground(new Color(255, 204, 102));
        altaVenta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        altaVenta.setForeground(new Color(255, 255, 255));
        altaVenta.setText("ALTA VENTA");
        altaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaVenta, null));
            }
        });
        panel2.add(altaVenta, new AbsoluteConstraints(122, 56, 150, 75));

        
        //BOTON BAJA VENTA
        bajaVenta.setBackground(new Color(255, 204, 102));
        bajaVenta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        bajaVenta.setForeground(new Color(255, 255, 255));
        bajaVenta.setText("BAJA VENTA");
        bajaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.BajaVenta, null));
            }
        });
        panel2.add(bajaVenta, new AbsoluteConstraints(122, 161, 150, 75));
        
        
        //BOTON MODIFICAR VENTA
        modificarVenta.setBackground(new Color(255, 204, 102));
        modificarVenta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modificarVenta.setForeground(new Color(255, 255, 255));
        modificarVenta.setText("MODIFICAR VENTA");
        modificarVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarVenta, null));
            }
        });
        panel2.add(modificarVenta, new AbsoluteConstraints(420, 161, 150, 75));
        
        
        //BOTON CONSULTAR VENTA
        consultaVenta.setBackground(new Color(255, 204, 102));
        consultaVenta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        consultaVenta.setForeground(new Color(255, 255, 255));
        consultaVenta.setText("CONSULTAR VENTA");
        consultaVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultaVenta, null));
            }
        });
        panel2.add(consultaVenta, new AbsoluteConstraints(420, 56, 150, 75));
        
        
        //BOTON LISTAR VENTAS
        listarVentas.setBackground(new Color(255, 204, 102));
        listarVentas.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarVentas.setForeground(new Color(255, 255, 255));
        listarVentas.setText("LISTAR VENTAS");
        listarVentas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarVentas, null));
            }
        });
        panel2.add(listarVentas, new AbsoluteConstraints(122, 369, 150, 75));
        
        
        //BOTON LISTAR VENTAS POR CLIENTE
        listarVentasPorCliente.setBackground(new Color(255, 204, 102));
        listarVentasPorCliente.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarVentasPorCliente.setForeground(new Color(255, 255, 255));
        listarVentasPorCliente.setText("VENTAS POR CLIENTE");
        listarVentasPorCliente.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		ViewerFactory.getInstance().generarVista(new Context(Evento.ListarVentasPorCliente, null));
        	}
        });
        panel2.add(listarVentasPorCliente, new AbsoluteConstraints(420, 367, 150, 75));
        
        
        //BOTON LISTAR VENTAS POR MESAS
        listarVentasPorMesa.setBackground(new Color(255, 204, 102));
        listarVentasPorMesa.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarVentasPorMesa.setForeground(new Color(255, 255, 255));
        listarVentasPorMesa.setText("VENTAS POR MESA");
        listarVentasPorMesa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		ViewerFactory.getInstance().generarVista(new Context(Evento.ListarVentasPorMesa, null));
        	}
        });
        panel2.add(listarVentasPorMesa, new AbsoluteConstraints(122, 264, 150, 75));
        
        
        //BOTON DEVOLUCION VENTA
        devolucionVenta.setBackground(new Color(255, 204, 102));
        devolucionVenta.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        devolucionVenta.setForeground(new Color(255, 255, 255));
        devolucionVenta.setText("DEVOLUCION VENTA");
        devolucionVenta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.DevolucionVenta, null));
            }
        });
        panel2.add(devolucionVenta, new AbsoluteConstraints(420, 264, 150, 75));
    
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
