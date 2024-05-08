package Presentacion.Vista.Academia.Alumnos;

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


public class GUIAlumnos extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel panel2;
	
	//Function Buttons
	private JButton create;
	private JButton delete;
	private JButton modify;
	private JButton showAlumn;
	private JButton showList;
	private JButton showGrades;
	private JButton showAlumnYear;
	
	private JLabel asigLabel;
	private JLabel iconLabel;
	
	public GUIAlumnos(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}
	
	public GUIAlumnos() {
		super("Alumnos");
		initGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initGUI() {
		this.setTitle("Alumnos");
		
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
        showAlumn = new JButton();
        showList = new JButton();
        showGrades = new JButton();
        showAlumnYear = new JButton();

        
        setPreferredSize(new Dimension(900, 500));
        
        //PANEL IZQUIERDA
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO ALUMNO
        asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        asigLabel.setForeground(new Color(255, 255, 255));
        asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
        asigLabel.setText("Alumnos");
        asigLabel.setAlignmentX(90.0F);
        asigLabel.setAlignmentY(90.0F);
        asigLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        asigLabel.setVerticalTextPosition(SwingConstants.TOP);
        panel.add(asigLabel, new AbsoluteConstraints(0, 30, 210, 80));
        
        //imagen
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/Logo.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(140, 150, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledImage2));
        panel.add(iconLabel, new AbsoluteConstraints(40, 140, 140, 300));
        
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
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaAlumnos,null));
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
            	//new GUIBajaAsignatura();
				ViewerFactory.getInstance().generarVista(new Context(Evento.BajaAlumnos,null));
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
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarAlumnos,null));
            }
        });
        panel2.add(modify, new AbsoluteConstraints(122, 161, 150, 75));
        
        //BOTON MOSTRAR
        showAlumn.setBackground(new Color(255, 204, 102));
        showAlumn.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showAlumn.setForeground(new Color(255, 255, 255));
        showAlumn.setText("MOSTRAR");
        showAlumn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarAlumnos,null));
            }
        });
        panel2.add(showAlumn, new AbsoluteConstraints(420, 161, 150, 75));
        
        //BOTÓN LISTAR
        showList.setBackground(new Color(255, 204, 102));
        showList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showList.setForeground(new Color(255, 255, 255));
        showList.setText("LISTAR");
        showList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarAlumnos,null));
            }
        });
        panel2.add(showList, new AbsoluteConstraints(122, 264, 150, 75));
        
        //BOTÓN NOTAS ALUMNO
        showGrades.setBackground(new Color(255, 204, 102));
        showGrades.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showGrades.setForeground(new Color(255, 255, 255));
        showGrades.setText("NOTAS ALUMNO");
        showGrades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarNotasAlumnos,null));
            }
        });
        panel2.add(showGrades, new AbsoluteConstraints(420, 264, 150, 75));
        
        //BOTON ALUMNO ANIO
        showAlumnYear.setBackground(new Color(255, 204, 102));
        showAlumnYear.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showAlumnYear.setForeground(new Color(255, 255, 255));
        showAlumnYear.setText("ALUMNO POR ANIO");
        showAlumnYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AlumnoANIO,null));
            }
        });
        panel2.add(showAlumnYear, new AbsoluteConstraints(255, 367, 175, 75));
        
        
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

