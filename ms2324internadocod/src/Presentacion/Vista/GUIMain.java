package Presentacion.Vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIMain extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;

	private JButton DAO;
	private JButton JPA;
	
	private JPanel main;

	
	public GUIMain() {

		super("Proyecto");
		initGUI();
		setLocationRelativeTo(null);

	}

	public void initGUI() {

		main = new JPanel(new GridLayout(2, 1, 10, 10));
        DAO = new JButton();
        JPA = new JButton();

        DAO.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        DAO.setToolTipText("DAO");
        DAO.setBackground(new Color(142, 211, 183));
		ImageIcon imageIcon1 = new ImageIcon(getClass().getResource("/resources/banner.png"));
		Image im1 = imageIcon1.getImage();
		Image sc1 = im1.getScaledInstance(1000, 270, Image.SCALE_SMOOTH);
        DAO.setIcon(new ImageIcon(sc1));
        
        DAO.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	setVisible(false);
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIAcademia,null));
            }
        });
        
        JPA.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
        JPA.setToolTipText("JPA");
        
		ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/BannerRestaurante.png"));
		Image im2 = imageIcon2.getImage();
		Image sc2 = im2.getScaledInstance(1000, 270, Image.SCALE_SMOOTH);
		JPA.setIcon(new ImageIcon(sc2));
        
        JPA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	setVisible(false);
            	ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIRestaurante,null));
            }
        });

        main.add(DAO);
        main.add(JPA);
        
        this.add(main);

        this.setSize(1000, 600);
        
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				ApplicationController.getInstancia().handleRequest(new Context(Evento.GUIMain,null));
			}
		});
	}

	@Override
	public void actualizar(Context context) {}
}
