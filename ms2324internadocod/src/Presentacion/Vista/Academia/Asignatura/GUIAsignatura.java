package Presentacion.Vista.Academia.Asignatura;

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

public class GUIAsignatura extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JPanel panel2;
	//Function Buttons
	private JButton create;
	private JButton delete;
	private JButton modify;
	private JButton showAsig;
	private JButton showList;
	private JButton showObList;
	private JButton showOpList;
	private JButton mediaButton;
	
	private JLabel asigLabel;
	private JLabel iconLabel;
	
	public GUIAsignatura(JFrame parent) {
		initGUI();
		this.setVisible(true);
		
	}
	
	public GUIAsignatura() {
		super("Asignatura");
		initGUI();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	
	public void initGUI() {
		// begin-user-code
		// TODO Auto-generated method stub
		this.setTitle("Asignatura");
		
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
        showAsig = new JButton();
        showList = new JButton();
        showObList = new JButton();
        showOpList = new JButton();
        mediaButton = new JButton();
        
        setPreferredSize(new Dimension(900, 500));

        //PANEL DE LA IZQUIERDA
        
        panel.setBackground(new Color(255, 153, 102));
        panel.setLayout(new AbsoluteLayout());
        
        //LABEL CON TEXTO ASIGNATURA
        
        asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
        asigLabel.setForeground(new Color(255, 255, 255));
        asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
        asigLabel.setText("Asignatura");
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
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaAsignaturas,null));
            }
        });
        panel2.add(create, new AbsoluteConstraints(122, 56, 150, 75));
        
        modify.setBackground(new Color(255, 204, 102));
        modify.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        modify.setForeground(new Color(255, 255, 255));
        modify.setText("MODIFICAR");
        modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarAsignaturas,null));
            }
        });
        panel2.add(modify, new AbsoluteConstraints(122, 161, 150, 75));

        showAsig.setBackground(new Color(255, 204, 102));
        showAsig.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showAsig.setForeground(new Color(255, 255, 255));
        showAsig.setText("MOSTRAR");
        showAsig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.MostrarAsignatura,null));
            }
        });
        panel2.add(showAsig, new AbsoluteConstraints(420, 161, 150, 75));

        delete.setBackground(new Color(255, 204, 102));
        delete.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        delete.setForeground(new Color(255, 255, 255));
        delete.setText("BAJA");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.BajaAsignaturas,null));
            }
        });
        panel2.add(delete, new AbsoluteConstraints(420, 56, 150, 75));

        showObList.setBackground(new Color(255, 204, 102));
        showObList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showObList.setForeground(new Color(255, 255, 255));
        showObList.setText("LISTAR OBLIGATORIAS");
        showObList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarAsignaturasObligatorias,null));
            }
        });
        panel2.add(showObList, new AbsoluteConstraints(122, 369, 150, 75));

        mediaButton.setBackground(new Color(255, 204, 102));
        mediaButton.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        mediaButton.setForeground(new Color(255, 255, 255));
        mediaButton.setText("MEDIA ASIGNATURA");
        mediaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.NotaMediaAsignatura,null));
            }
        });
        panel2.add(mediaButton, new AbsoluteConstraints(420, 367, 150, 75));

        showList.setBackground(new Color(255, 204, 102));
        showList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showList.setForeground(new Color(255, 255, 255));
        showList.setText("LISTAR");
        showList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//new GUIListarAsignaturas();
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarAsignaturas,null));
            }
        });
        panel2.add(showList, new AbsoluteConstraints(122, 264, 150, 75));

        showOpList.setBackground(new Color(255, 204, 102));
        showOpList.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        showOpList.setForeground(new Color(255, 255, 255));
        showOpList.setText("LISTAR OPTATIVAS");
        showOpList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//new GUIListarAsignaturasOptativas();
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarAsignaturasOptativas,null));
            }
        });
        panel2.add(showOpList, new AbsoluteConstraints(420, 264, 150, 75));

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
		// end-user-code
	}

	public void actualizar(Context context) {}
}