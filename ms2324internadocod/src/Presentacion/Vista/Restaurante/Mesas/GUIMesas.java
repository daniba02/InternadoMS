package Presentacion.Vista.Restaurante.Mesas;

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

public class GUIMesas extends JFrame implements IGUI{
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panel2;
	
	private JButton create;
	private JButton delete;
	private JButton modify;
	private JButton consult;
	private JButton list;
	private JButton mesasEmpl;
	
	private JLabel asigLabel;
	private JLabel iconLabel;
	

	public GUIMesas(){
		super("Mesas");
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
        create = new JButton();
        delete = new JButton();
        modify = new JButton();
        consult = new JButton();
        list = new JButton();
        mesasEmpl = new JButton();
		
		setPreferredSize(new Dimension(900, 500));
        
        //PANEL IZQUIERDA
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO MESAS
        asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        asigLabel.setForeground(new Color(255, 255, 255));
        asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
        asigLabel.setText("MESAS");
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
        create.setBackground(new Color(255, 204, 102));
        create.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        create.setForeground(new Color(255, 255, 255));
        create.setText("ALTA");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaMesas, null));
            }
        });
        panel2.add(create, new AbsoluteConstraints(122, 56, 150, 75));

        //BOTÓN BAJA
        delete.setBackground(new Color(255, 204, 102));
        delete.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        delete.setForeground(new Color(255, 255, 255));
        delete.setText("BAJA");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.BajaMesas, null));
            }
        });
        panel2.add(delete, new AbsoluteConstraints(420, 56, 150, 75));
        
        //BOTÓN MODIFICAR
        modify.setBackground(new Color(255, 204, 102));
        modify.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modify.setForeground(new Color(255, 255, 255));
        modify.setText("MODIFICAR");
        modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarMesas, null));
            }
        });
        panel2.add(modify, new AbsoluteConstraints(122, 206, 150, 75));
        
        //BOTÓN CONSULTAR
        consult.setBackground(new Color(255, 204, 102));
        consult.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        consult.setForeground(new Color(255, 255, 255));
        consult.setText("CONSULTAR");
        consult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarMesa, null));
            }
        });
        panel2.add(consult, new AbsoluteConstraints(420, 206, 150, 75));
        
        //BOTÓN LISTAR
        list.setBackground(new Color(255, 204, 102));
        list.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        list.setForeground(new Color(255, 255, 255));
        list.setText("LISTAR");
        list.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarMesas, null));
            }
        });
        panel2.add(list, new AbsoluteConstraints(122, 358, 150, 75));
        
        //BOTÓN MESAS POR EMPLEADO
        mesasEmpl.setBackground(new Color(255, 204, 102));
        mesasEmpl.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        mesasEmpl.setForeground(new Color(255, 255, 255));
        mesasEmpl.setText("MESAS POR EMPLEADO");
        mesasEmpl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ViewerFactory.getInstance().generarVista(new Context(Evento.MesasPorEmpleado, null));
            }
        });
        panel2.add(mesasEmpl, new AbsoluteConstraints(420, 358, 150, 75));
        
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
