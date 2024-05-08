package Presentacion.Vista.Academia.Matricula;

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


public class GUIMatricula extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panel2;
	//Function Buttons
	private JButton create;
	private JButton delete;
	private JButton close;
	private JButton modify;
	private JButton showMatri;
	private JButton showList;
	private JButton showAlumnoList;
	private JButton showMatriculableList;
	private JButton CalificarButton;
	private JButton showNote;
	private JButton unlink;
	private JButton link;
	
	private JLabel asigLabel;
	private JLabel iconLabel;

	public GUIMatricula(JFrame parent) {
		initGUI();
		this.setVisible(true);
		
	}
	
	public GUIMatricula() {
		super("MATRICULA");
		initGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		this.setTitle("MATRICULA");
		
		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);
		
		panel = new JPanel();
        asigLabel = new JLabel();
        iconLabel = new JLabel();
        panel2 = new JPanel();
        
        
        create = new JButton();
    	delete = new JButton();
    	close = new JButton();
    	modify = new JButton();
    	showMatri = new JButton();
    	showList = new JButton();
    	showAlumnoList = new JButton();
    	showMatriculableList = new JButton();
    	CalificarButton = new JButton();
    	showNote = new JButton();
    	unlink = new JButton();
    	link = new JButton();
        
        setPreferredSize(new Dimension(900, 500));

        //PANEL DE LA IZQUIERDA
        
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO MATRICULA
        
        asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        asigLabel.setForeground(new Color(255, 255, 255));
        asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
        asigLabel.setText("Matricula");
        asigLabel.setAlignmentX(90.0F);
        asigLabel.setAlignmentY(90.0F);
        asigLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        asigLabel.setVerticalTextPosition(SwingConstants.TOP);
        panel.add(asigLabel, new AbsoluteConstraints(0, 30, 210, 80));

        //IMAGEN DEL ICONO
        
        ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/Logo.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(140, 150, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledImage2));
		
        panel.add(iconLabel, new AbsoluteConstraints(40, 140, 140, 300));

        //PANEL DE LA DERECHA CON LOS BOTONES
        
        panel2.setBackground(new Color(255, 204, 153));
        panel2.setLayout(new AbsoluteLayout());

        
        //TODOS LOS BOTONES
        
        create.setBackground(new Color(255, 204, 102));
        create.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        create.setForeground(new Color(255, 255, 255));
        create.setText("ALTA");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AbrirMatricula,null));
            }
        });
        panel2.add(create, new AbsoluteConstraints(70, 96, 170, 55));
       
        delete.setBackground(new Color(255, 204, 102));
        delete.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        delete.setForeground(new Color(255, 255, 255));
        delete.setText("BAJA");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ViewerFactory.getInstance().generarVista(new Context(Evento.BajaMatricula,null));
            }
        });
        panel2.add(delete, new AbsoluteConstraints(70, 161, 170, 55));
        
        showMatri.setBackground(new Color(255, 204, 102));
        showMatri.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showMatri.setForeground(new Color(255, 255, 255));
        showMatri.setText("CONSULTAR");
        showMatri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarMatricula,null));
            }
        });
        panel2.add(showMatri, new AbsoluteConstraints(70, 291, 170, 55));
        
        showAlumnoList.setBackground(new Color(255, 204, 102));
        showAlumnoList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showAlumnoList.setForeground(new Color(255, 255, 255));
        showAlumnoList.setText("MOSTRAR_ALUMNOS");
        showAlumnoList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.MostrarMatriculasporAlumno,null));
            }
        });
        panel2.add(showAlumnoList, new AbsoluteConstraints(70, 226, 170, 55));
        
        close.setBackground(new Color(255, 204, 102));
        close.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        close.setForeground(new Color(255, 255, 255));
        close.setText("CERRAR");
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.CerrarMatricula,null));
            }
        });
        panel2.add(close, new AbsoluteConstraints(260, 161, 170, 55));
        
        modify.setBackground(new Color(255, 204, 102));
        modify.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modify.setForeground(new Color(255, 255, 255));
        modify.setText("MODIFICAR");
        modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarMatricula,null));
            }
        });
        panel2.add(modify, new AbsoluteConstraints(260, 96, 170, 55));
        
        showList.setBackground(new Color(255, 204, 102));
        showList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showList.setForeground(new Color(255, 255, 255));
        showList.setText("LISTAR");
        showList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarMatricula,null));
            }
        });
        panel2.add(showList, new AbsoluteConstraints(260, 291, 170, 55));
        
        showMatriculableList.setBackground(new Color(255, 204, 102));
        showMatriculableList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showMatriculableList.setForeground(new Color(255, 255, 255));
        showMatriculableList.setText("MOSTRAR_MATRICULABLES");
        showMatriculableList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.MostrarMatriculasporMatriculable,null));
            }
        });
        panel2.add(showMatriculableList, new AbsoluteConstraints(260, 226, 170, 55));
        
        
        
        CalificarButton.setBackground(new Color(255, 204, 102));
        CalificarButton.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        CalificarButton.setForeground(new Color(255, 255, 255));
        CalificarButton.setText("CALIFICAR");
        CalificarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.CalificarMatriculableMatricula,null));
            }
        });
        panel2.add(CalificarButton, new AbsoluteConstraints(450, 96, 170, 55));

        showNote.setBackground(new Color(255, 204, 102));
        showNote.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showNote.setForeground(new Color(255, 255, 255));
        showNote.setText("CONSULTAR_NOTA");
        showNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarNota,null));
            }
        });
        panel2.add(showNote, new AbsoluteConstraints(450, 291, 170, 55));
    	
        unlink.setBackground(new Color(255, 204, 102));
        unlink.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        unlink.setForeground(new Color(255, 255, 255));
        unlink.setText("DESVINCULAR");
        unlink.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.DesvincularMatriculaMatriculable,null));
            }
        });
        panel2.add(unlink, new AbsoluteConstraints(450, 226, 170, 55));
    	
        link.setBackground(new Color(255, 204, 102));
        link.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        link.setForeground(new Color(255, 255, 255));
        link.setText("VINCULAR");
        link.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.VincularMatriculaMatriculable,null));
            }
        });
        panel2.add(link, new AbsoluteConstraints(450, 161, 170, 55));
        
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

	
	public void actualizar(Context context) {
		// begin-user-code
		// TODO Auto-generated method stub
		// end-user-code
	}
}