package Presentacion.Vista.Academia;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIAcademia extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;

	private JButton Alumnos;
	private JButton Anyo;
	private JButton Asignatura;
	private JButton Grupo;
	private JButton Matricula;
	private JButton Matriculable;
	private JButton Profesores;
	private JButton deleteBBDDbutton;
	

	private JPanel main;

	public GUIAcademia() {

		super("FLORES Academy");
		initGUI();
		setLocationRelativeTo(null);

	}

	public void initGUI() {
		
		ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/LogoAF.jpg"));
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
											// iteraci�n TODO 50
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
		        Grupo = new JButton();
		        Alumnos = new JButton();
		        Anyo = new JButton();
		        Asignatura = new JButton();
		        Profesores = new JButton();
		        Matriculable = new JButton();
		        Matricula = new JButton();
		        deleteBBDDbutton = new JButton();

		        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		        ImageIcon icon2 = new ImageIcon(getClass().getResource("/resources/banner.png"));
				Image im2 = icon2.getImage();
				Image sc2 = im2.getScaledInstance(900, 200, Image.SCALE_SMOOTH);
				jLabel1.setIcon(new ImageIcon(sc2));
				
		        jPanel1.setBackground(new Color(142, 211, 183));
		        jPanel1.setLayout(new AbsoluteLayout());
		        jPanel1.add(jLabel1, new AbsoluteConstraints(0, 0, 900, 130));

		        jPanel2.setBackground(new Color(62, 85, 70));
		        jPanel2.setLayout(new AbsoluteLayout());

		        Grupo.setBackground(new Color(153, 255, 255));
		        Grupo.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Grupo.setForeground(new Color(51, 51, 51));
		        Grupo.setText("GRUPO");
		        jPanel2.add(Grupo, new AbsoluteConstraints(620, 70, 140, 80));
		        Grupo.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIGRUPO, null));
		            }
		        });
		        
		        Alumnos.setBackground(new Color(153, 255, 255));
		        Alumnos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Alumnos.setForeground(new Color(51, 51, 51));
		        Alumnos.setText("ALUMNOS");
		        Alumnos.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIALUMNOS, null));
		            }
		        });
		        jPanel2.add(Alumnos, new AbsoluteConstraints(140, 70, 140, 80));

		        Anyo.setBackground(new Color(153, 255, 255));
		        Anyo.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Anyo.setForeground(new Color(51, 51, 51));
		        Anyo.setText("ANIO");
		        Anyo.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIANIO, null));
		            }
		        });
		        jPanel2.add(Anyo, new AbsoluteConstraints(300, 70, 140, 80));

		        Asignatura.setBackground(new Color(153, 255, 255));
		        Asignatura.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Asignatura.setForeground(new Color(51, 51, 51));
		        Asignatura.setText("ASIGNATURA");
		        Asignatura.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIAsignatura, null));
		            }
		        });
		        jPanel2.add(Asignatura, new AbsoluteConstraints(460, 70, 140, 80));

		        Profesores.setBackground(new Color(153, 255, 255));
		        Profesores.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Profesores.setForeground(new Color(51, 51, 51));
		        Profesores.setText("PROFESORES");
		        Profesores.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIProfesores, null));
		            }
		        });
		        jPanel2.add(Profesores, new AbsoluteConstraints(540, 210, 140, 80));

		        Matriculable.setBackground(new Color(153, 255, 255));
		        Matriculable.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Matriculable.setForeground(new Color(51, 51, 51));
		        Matriculable.setText("MATRICULABLE");
		        Matriculable.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIMATRICULABLE, null));
		            }
		        });
		        jPanel2.add(Matriculable, new AbsoluteConstraints(380, 210, 140, 80));

		        Matricula.setBackground(new Color(153, 255, 255));
		        Matricula.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		        Matricula.setForeground(new Color(51, 51, 51));
		        Matricula.setText("MATRICULA");
		        Matricula.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIMATRICULA, null));
		            }
		        });
		        jPanel2.add(Matricula, new AbsoluteConstraints(220, 210, 140, 80));
		        
		        deleteBBDDbutton.setOpaque(false);
		        deleteBBDDbutton.setBorder(null);
		        Color c = UIManager.getLookAndFeel().getDefaults().getColor( "Panel.background");
		        deleteBBDDbutton.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue()));
		        icon2 = new ImageIcon(getClass().getResource("/resources/delete.png"));
				im2 = icon2.getImage();
				sc2 = im2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				deleteBBDDbutton.setIcon(new ImageIcon(sc2));
				deleteBBDDbutton.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent evt) {
		           
		            	deleteBBDD();
		            }
		        });
		        
		        jPanel2.add(deleteBBDDbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 290, 50, 50));


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
	
	private void deleteBBDD(){
		Transaction t = null;

		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps2.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("TRUNCATE alumno");
			ps3.executeUpdate();
			PreparedStatement ps4 = conn.prepareStatement("TRUNCATE anio");
			ps4.executeUpdate();
			PreparedStatement ps5 = conn.prepareStatement("TRUNCATE grupo");
			ps5.executeUpdate();
			PreparedStatement ps6 = conn.prepareStatement("TRUNCATE matricula");
			ps6.executeUpdate();
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE profesor_matriculable");
			ps7.executeUpdate();
			PreparedStatement ps8 = conn.prepareStatement("TRUNCATE profesores");
			ps8.executeUpdate();
			PreparedStatement ps9 = conn.prepareStatement("TRUNCATE profesoresfijos");
			ps9.executeUpdate();
			PreparedStatement ps10 = conn.prepareStatement("TRUNCATE profesoresinterinos");
			ps10.executeUpdate();
			PreparedStatement ps11 = conn.prepareStatement("TRUNCATE matriculable");
			ps11.executeUpdate();
			PreparedStatement ps12 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps12.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actualizar(Context context) {}

}
