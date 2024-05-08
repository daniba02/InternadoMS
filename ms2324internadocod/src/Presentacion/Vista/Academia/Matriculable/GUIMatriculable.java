package Presentacion.Vista.Academia.Matriculable;

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


public class GUIMatriculable extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel panel2;
	//Botones
	private JButton altaMatriculable;
	private JButton bajaMatriculable;
	private JButton consultarMatriculable;
	
	private JButton listarMatriculables;
	private JButton listarMatriculablesPorProfesor;
	private JButton listarMatriculablesPorAnio;
	private JButton listarMatriculablesPorGrupo;
	private JButton listarMatriculablesPorAsignatura;
	
	private JButton modificarMatriculable;

	private JLabel matLabel;
	private JLabel iconLabel;

	public GUIMatriculable(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}
	
	public GUIMatriculable() {
		super("Matriculable");
		initGUI();
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public void initGUI() {
		
		this.setTitle("MATRICULABLE");
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		panel = new JPanel();
        matLabel = new JLabel();
        iconLabel = new JLabel();
        panel2 = new JPanel();
        
        
        altaMatriculable = new JButton();
        bajaMatriculable = new JButton();
        consultarMatriculable = new JButton();
        listarMatriculables = new JButton();
        listarMatriculablesPorProfesor = new JButton();
        listarMatriculablesPorAnio = new JButton();
        listarMatriculablesPorGrupo = new JButton();
        listarMatriculablesPorAsignatura = new JButton();
        modificarMatriculable = new JButton();
        
        setPreferredSize(new Dimension(900, 500));

        //PANEL DE LA IZQUIERDA
        
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO MATRICULABLE
        
        matLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        matLabel.setForeground(new Color(255, 255, 255));
        matLabel.setHorizontalAlignment(SwingConstants.CENTER);
        matLabel.setText("Matriculable");
        matLabel.setAlignmentX(90.0F);
        matLabel.setAlignmentY(90.0F);
        matLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        matLabel.setVerticalTextPosition(SwingConstants.TOP);
        panel.add(matLabel, new AbsoluteConstraints(0, 30, 210, 80));

        //IMAGEN DEL ICONO
        
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/Logo.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(140, 150, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledImage2));
		
        panel.add(iconLabel, new AbsoluteConstraints(40, 140, 140, 300));

        //PANEL DE LA DERECHA CON LOS BOTONES
        
        panel2.setBackground(new Color(255, 204, 153));
        panel2.setLayout(new AbsoluteLayout());

		
        altaMatriculable.setBackground(new Color(255, 204, 102));
        altaMatriculable.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        altaMatriculable.setForeground(new Color(255, 255, 255));
        altaMatriculable.setText("ALTA");
        altaMatriculable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaMatriculable,null));
            }
        });
        panel2.add(altaMatriculable, new AbsoluteConstraints(70, 115, 180, 65));
        
        
        bajaMatriculable.setBackground(new Color(255, 204, 102));
        bajaMatriculable.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        bajaMatriculable.setForeground(new Color(255, 255, 255));
        bajaMatriculable.setText("BAJA");
        bajaMatriculable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ViewerFactory.getInstance().generarVista(new Context(Evento.BajaMatriculable,null));
            }
        });
        panel2.add(bajaMatriculable, new AbsoluteConstraints(70, 200, 180, 65));
        
        
        consultarMatriculable.setBackground(new Color(255, 204, 102));
        consultarMatriculable.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        consultarMatriculable.setForeground(new Color(255, 255, 255));
        consultarMatriculable.setText("CONSULTAR");
        consultarMatriculable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarInformacionMatriculable,null));
            }
        });
        panel2.add(consultarMatriculable, new AbsoluteConstraints(70, 285, 180, 65));
        
        
        listarMatriculables.setBackground(new Color(255, 204, 102));
        listarMatriculables.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarMatriculables.setForeground(new Color(255, 255, 255));
        listarMatriculables.setText("LISTAR MATRICULABLES");
        listarMatriculables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarMatriculables,null));
            }
        });
        panel2.add(listarMatriculables, new AbsoluteConstraints(260, 200, 180, 65));
        
        
        listarMatriculablesPorProfesor.setBackground(new Color(255, 204, 102));
        listarMatriculablesPorProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarMatriculablesPorProfesor.setForeground(new Color(255, 255, 255));
        listarMatriculablesPorProfesor.setText("LISTAR POR PROFESOR");
        listarMatriculablesPorProfesor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarMatriculablePorProfesor,null));
            }
        });
        panel2.add(listarMatriculablesPorProfesor, new AbsoluteConstraints(260, 115, 180, 65));
        
        
        listarMatriculablesPorAnio.setBackground(new Color(255, 204, 102));
        listarMatriculablesPorAnio.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarMatriculablesPorAnio.setForeground(new Color(255, 255, 255));
        listarMatriculablesPorAnio.setText("LISTAR POR ANIO");
        listarMatriculablesPorAnio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarMatriculablesPorAnio,null));
            }
        });
        panel2.add(listarMatriculablesPorAnio, new AbsoluteConstraints(450, 285, 180, 65));
        
        
        listarMatriculablesPorGrupo.setBackground(new Color(255, 204, 102));
        listarMatriculablesPorGrupo.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarMatriculablesPorGrupo.setForeground(new Color(255, 255, 255));
        listarMatriculablesPorGrupo.setText("LISTAR POR GRUPO");
        listarMatriculablesPorGrupo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarMatriculablePorGrupo,null));
            }
        });
        panel2.add(listarMatriculablesPorGrupo, new AbsoluteConstraints(450, 115, 180, 65));
        
        
        listarMatriculablesPorAsignatura.setBackground(new Color(255, 204, 102));
        listarMatriculablesPorAsignatura.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        listarMatriculablesPorAsignatura.setForeground(new Color(255, 255, 255));
        listarMatriculablesPorAsignatura.setText("LISTAR POR ASIGNATURA");
        listarMatriculablesPorAsignatura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarMatriculablesPorAsignatura,null));
            }
        });
        panel2.add(listarMatriculablesPorAsignatura, new AbsoluteConstraints(260, 285, 180, 65));
        
        
        
        modificarMatriculable.setBackground(new Color(255, 204, 102));
        modificarMatriculable.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modificarMatriculable.setForeground(new Color(255, 255, 255));
        modificarMatriculable.setText("MODIFICAR");
        modificarMatriculable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarMatriculable,null));
            }
        });
        panel2.add(modificarMatriculable, new AbsoluteConstraints(450, 200, 180, 65)); 
        
        
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