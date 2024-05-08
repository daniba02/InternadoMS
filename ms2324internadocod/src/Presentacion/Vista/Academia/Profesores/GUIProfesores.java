/**
 * 
 */
package Presentacion.Vista.Academia.Profesores;


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



public class GUIProfesores extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel panel2;
	
	//buttons
	 private JButton altaProfesor;
	 private JButton bajaProfesor;
	 private JButton modificarProfesor;
	 private JButton consultarProfesor;
	 private JButton listarProfesores;
	 private JButton listarProfesoresFijos;
	 private JButton listarProfesoresInterinos;
	 private JButton vincularProfesor;
	 private JButton desvincularProfesor;
	 private JButton listarProfesorMatriculable;
	
	 private JLabel asigLabel;
	 private JLabel iconLabel;
	
	public void initGUI() {
		
		this.setTitle("Profesores");
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		panel = new JPanel();
        asigLabel = new JLabel();
        iconLabel = new JLabel();
        panel2 = new JPanel();
        
        altaProfesor = new JButton();
   	bajaProfesor = new JButton();
   	modificarProfesor= new JButton();
   	consultarProfesor= new JButton();
  listarProfesores= new JButton();
    listarProfesoresFijos= new JButton();
   	  listarProfesoresInterinos= new JButton();
   	 vincularProfesor= new JButton();
   	  desvincularProfesor= new JButton();
   	listarProfesorMatriculable=new JButton();
   	  
   	setPreferredSize(new Dimension(900, 500));
   	
   	
   	panel.setBackground(new Color(255, 153, 102));
    panel.setLayout(new AbsoluteLayout());
    
    
    asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
    asigLabel.setForeground(new Color(255, 255, 255));
    asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
    asigLabel.setText("Profesor");
    asigLabel.setAlignmentX(90.0F);
    asigLabel.setAlignmentY(90.0F);
    asigLabel.setHorizontalTextPosition(SwingConstants.LEFT);
    asigLabel.setVerticalTextPosition(SwingConstants.TOP);
    panel.add(asigLabel, new AbsoluteConstraints(0, 30, 210, 80));
		
    panel2.setBackground(new Color(255, 204, 153));
    panel2.setLayout(new AbsoluteLayout());
        
    ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/Logo.png"));
	Image image2 = imageIcon2.getImage();
	Image scaledImage2 = image2.getScaledInstance(140, 150, Image.SCALE_SMOOTH);
	iconLabel.setIcon(new ImageIcon(scaledImage2));
	
    panel.add(iconLabel, new AbsoluteConstraints(40, 140, 140, 300));
    
  //PANEL DE LA DERECHA CON LOS BOTONES
    
    panel2.setBackground(new Color(255, 204, 153));
    panel2.setLayout(new AbsoluteLayout());

    
    //TODOS LOS BOTONES
    
   altaProfesor.setBackground(new Color(255, 204, 102));
   altaProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
   altaProfesor.setForeground(new Color(255, 255, 255));
   altaProfesor.setText("ALTA");
   altaProfesor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
			ViewerFactory.getInstance().generarVista(new Context(Evento.AltaProfesor,null));
        }
    });
    panel2.add(altaProfesor, new AbsoluteConstraints(70, 96, 170, 55));
   
    bajaProfesor.setBackground(new Color(255, 204, 102));
    bajaProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    bajaProfesor.setForeground(new Color(255, 255, 255));
    bajaProfesor.setText("BAJA");
    bajaProfesor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	ViewerFactory.getInstance().generarVista(new Context(Evento.BajaProfesor,null));
        }
    });
    panel2.add(bajaProfesor, new AbsoluteConstraints(70, 161, 170, 55));
    
    consultarProfesor.setBackground(new Color(255, 204, 102));
    consultarProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    consultarProfesor.setForeground(new Color(255, 255, 255));
    consultarProfesor.setText("CONSULTAR");
    consultarProfesor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
			ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarProfesor,null));
        }
    });
    panel2.add(consultarProfesor, new AbsoluteConstraints(70, 226, 170, 55));
    
    listarProfesoresFijos.setBackground(new Color(255, 204, 102));
    listarProfesoresFijos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    listarProfesoresFijos.setForeground(new Color(255, 255, 255));
    listarProfesoresFijos.setText("MOSTRAR P.FIJOS");
    listarProfesoresFijos.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarProfesoresFijos,null));
        }
    });
    panel2.add(listarProfesoresFijos, new AbsoluteConstraints(260, 161, 170, 55));
    listarProfesoresInterinos.setBackground(new Color(255, 204, 102));
    listarProfesoresInterinos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    listarProfesoresInterinos.setForeground(new Color(255, 255, 255));
    listarProfesoresInterinos.setText("MOSTRAR P.INTERINOS");
    listarProfesoresInterinos.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarProfesoresInterinos,null));
        }
    });
    panel2.add(listarProfesoresInterinos, new AbsoluteConstraints(260, 96, 170, 55));
  
    
    modificarProfesor.setBackground(new Color(255, 204, 102));
    modificarProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    modificarProfesor.setForeground(new Color(255, 255, 255));
    modificarProfesor.setText("MODIFICAR");
    modificarProfesor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
			ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarProfesor,null));
        }
    });
    panel2.add(modificarProfesor, new AbsoluteConstraints(260, 291, 170, 55));
    
    listarProfesores.setBackground(new Color(255, 204, 102));
    listarProfesores.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    listarProfesores.setForeground(new Color(255, 255, 255));
    listarProfesores.setText("LISTAR");
    listarProfesores.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
        	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarProfesores,null));
        }
    });
    panel2.add(listarProfesores, new AbsoluteConstraints(260, 226, 170, 55));  
    
    listarProfesorMatriculable.setBackground(new Color(255, 204, 102));
    listarProfesorMatriculable.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    listarProfesorMatriculable.setForeground(new Color(255, 255, 255));
    listarProfesorMatriculable.setText("MOSTRAR MATRICULABLES");
    listarProfesorMatriculable.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
			ViewerFactory.getInstance().generarVista(new Context(Evento.ListarProfesoresPorMatriculable,null));
        }
    });
    panel2.add(listarProfesorMatriculable, new AbsoluteConstraints(450, 96, 170, 55));
	
    desvincularProfesor.setBackground(new Color(255, 204, 102));
    desvincularProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    desvincularProfesor.setForeground(new Color(255, 255, 255));
    desvincularProfesor.setText("DESVINCULAR");
    desvincularProfesor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
			ViewerFactory.getInstance().generarVista(new Context(Evento.DesvincularProfesorMatriculable,null));
        }
    });
    panel2.add(desvincularProfesor, new AbsoluteConstraints(450, 161, 170, 55));
	
    vincularProfesor.setBackground(new Color(255, 204, 102));
    vincularProfesor.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
    vincularProfesor.setForeground(new Color(255, 255, 255));
    vincularProfesor.setText("VINCULAR");
    vincularProfesor.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
			ViewerFactory.getInstance().generarVista(new Context(Evento.VincularProfesorMatriculable,null));
        }
    });
    panel2.add(vincularProfesor, new AbsoluteConstraints(450, 226, 170, 55));
    
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
	public GUIProfesores() {
		super("Profesores");
		initGUI();
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public GUIProfesores(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}

	@Override
	public void actualizar(Context context) {}
		
		
	
}