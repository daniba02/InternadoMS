package Presentacion.Vista.Restaurante;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIRestaurante extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;

	private JButton productoBtn;
	private JButton platoBtn;
	private JButton ventaBtn;
	private JButton mesasBtn;
	private JButton empleadosBtn;
	private JButton turnoBtn;

	private JPanel main;

	public GUIRestaurante() {

		super("Restaurante OnTheSushi");
		initGUI();
		setLocationRelativeTo(null);

	}

	public void initGUI() {
		
		ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/LogoOS.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
		ImageIcon scaledImageIcon2 = new ImageIcon(scaledImage2);
		
		this.setIconImage(scaledImageIcon2.getImage());
		this.setSize(700, 700);
		this.setResizable(false);
		main = new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;
			float opacity = 0.0f;
			
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Graphics2D g2d = (Graphics2D) g;
				AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
				g2d.setComposite(alphaComposite);

				Dimension size = this.getSize();

				int x = (size.width - scaledImageIcon2.getImage().getWidth(null)) / 2;
				int y = (size.height - scaledImageIcon2.getImage().getHeight(null)) / 2;

				g.drawImage(scaledImageIcon2.getImage(), x, y, null);

				if (opacity <= 0.9f) {
					opacity += 0.025f;

					try {
						Thread.sleep(50); // Esperar 1 segundo entre cada
						//TODO 50
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					repaint();
				}
			}
		};
		
		main.setBackground(new Color(255,255,255));
		this.setContentPane(main);
		main.revalidate();

		new Thread(() -> {
			try {	
				Thread.sleep(3000); // Pausar durante 3 segundos
				// TODO 3000

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SwingUtilities.invokeLater(() -> {
				
				JPanel jPanel1 = new JPanel();
		        JLabel jLabel1 = new JLabel();
		        JPanel jPanel2 = new JPanel();
		        mesasBtn = new JButton();
		        productoBtn = new JButton();
		        platoBtn = new JButton();
		        ventaBtn = new JButton();
		        turnoBtn = new JButton();
		        empleadosBtn = new JButton();

		        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		        ImageIcon icon2 = new ImageIcon(getClass().getResource("/resources/BannerRestaurante.png"));
				Image im2 = icon2.getImage();
				Image sc2 = im2.getScaledInstance(900, 130, Image.SCALE_SMOOTH);
				jLabel1.setIcon(new ImageIcon(sc2));
				
		        jPanel1.setBackground(new Color(142, 211, 183));
		        jPanel1.setLayout(new AbsoluteLayout());
		        jPanel1.add(jLabel1, new AbsoluteConstraints(0, 0, 900, 130));

		        jPanel2.setBackground(new Color(62, 85, 70));
		        jPanel2.setLayout(new AbsoluteLayout());

		        mesasBtn.setBackground(new Color(153, 255, 255));
		        mesasBtn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        mesasBtn.setForeground(new Color(51, 51, 51));
		        mesasBtn.setText("MESAS");
		        jPanel2.add(mesasBtn, new AbsoluteConstraints(220, 210, 140, 80));
		        mesasBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIMesas,null));
		            }
		        });
		        
		        productoBtn.setBackground(new Color(153, 255, 255));
		        productoBtn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        productoBtn.setForeground(new Color(51, 51, 51));
		        productoBtn.setText("PRODUCTOS");
		        productoBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIProductos, null));
		            }
		        });
		        jPanel2.add(productoBtn, new AbsoluteConstraints(220, 70, 140, 80));

		        platoBtn.setBackground(new Color(153, 255, 255));
		        platoBtn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        platoBtn.setForeground(new Color(51, 51, 51));
		        platoBtn.setText("PLATOS");
		        platoBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIPlatos, null));
		            }
		        });
		        jPanel2.add(platoBtn, new AbsoluteConstraints(380, 70, 140, 80));

		        ventaBtn.setBackground(new Color(153, 255, 255));
		        ventaBtn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        ventaBtn.setForeground(new Color(51, 51, 51));
		        ventaBtn.setText("VENTAS");
		        ventaBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIVentas, null));
		            }
		        });
		        jPanel2.add(ventaBtn, new AbsoluteConstraints(540, 70, 140, 80));


		        turnoBtn.setBackground(new Color(153, 255, 255));
		        turnoBtn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        turnoBtn.setForeground(new Color(51, 51, 51));
		        turnoBtn.setText("TURNOS");
		        turnoBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUITurnos, null));
		            }
		        });
		        jPanel2.add(turnoBtn, new AbsoluteConstraints(540, 210, 140, 80));

		        empleadosBtn.setBackground(new Color(153, 255, 255));
		        empleadosBtn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        empleadosBtn.setForeground(new Color(51, 51, 51));
		        empleadosBtn.setText("EMPLEADOS");
		        empleadosBtn.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIEmpleados, null));
		            }
		        });
		        jPanel2.add(empleadosBtn, new AbsoluteConstraints(380, 210, 140, 80));
		        
		        GroupLayout layout = new GroupLayout(getContentPane());
		        getContentPane().setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addComponent(jPanel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addComponent(jPanel2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
		                //.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE))
		        );

		        pack();
		        this.revalidate();

			});
		}).start();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	@Override
	public void actualizar(Context context) {}

}
