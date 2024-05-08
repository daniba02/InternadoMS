package Presentacion.Vista.Restaurante.Productos;

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

public class GUIProductos extends JFrame implements IGUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel panel2;

	private JButton altaProducto;
	private JButton bajaProducto;
	private JButton consultarProducto;
	private JButton modificarProducto;
	private JButton listarProductos;
	private JButton listarProductosPorPlato;

	private JLabel asigLabel;
	private JLabel iconLabel;

	public GUIProductos(JFrame parent) {
		initGUI();
		this.setVisible(true);
	}

	public GUIProductos() {
		super("Productos");
		initGUI();
		setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		this.setTitle("Productos");

		panel = new JPanel();
		this.add(panel);
		this.setResizable(false);

		panel = new JPanel();
		asigLabel = new JLabel();
		iconLabel = new JLabel();
		panel2 = new JPanel();

		altaProducto = new JButton();
		bajaProducto = new JButton();
		modificarProducto = new JButton();
		consultarProducto = new JButton();
		listarProductos = new JButton();
		listarProductosPorPlato = new JButton();

		setPreferredSize(new Dimension(900, 500));

		panel.setBackground(new Color(255, 153, 102));
		panel.setLayout(new AbsoluteLayout());

		asigLabel.setFont(new Font("Stencil", 0, 26)); // NOI18N
		asigLabel.setForeground(new Color(255, 255, 255));
		asigLabel.setHorizontalAlignment(SwingConstants.CENTER);
		asigLabel.setText("Productos");
		asigLabel.setAlignmentX(90.0F);
		asigLabel.setAlignmentY(90.0F);
		asigLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		asigLabel.setVerticalTextPosition(SwingConstants.TOP);
		panel.add(asigLabel, new AbsoluteConstraints(0, 30, 210, 80));

		panel2.setBackground(new Color(255, 204, 153));
		panel2.setLayout(new AbsoluteLayout());

		ImageIcon imageIcon2 = new ImageIcon(getClass().getResource("/resources/LogoRestaurante.png"));
		Image image2 = imageIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		iconLabel.setIcon(new ImageIcon(scaledImage2));
        panel.add(iconLabel, new AbsoluteConstraints(10, 140, 220, 300));

		// PANEL DE LA DERECHA CON LOS BOTONES

		panel2.setBackground(new Color(255, 204, 153));
		panel2.setLayout(new AbsoluteLayout());

		// TODOS LOS BOTONES

		altaProducto.setBackground(new Color(255, 204, 102));
		altaProducto.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		altaProducto.setForeground(new Color(255, 255, 255));
		altaProducto.setText("ALTA");
		altaProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.AltaProductos, null));
			}
		});
		panel2.add(altaProducto, new AbsoluteConstraints(122, 56, 150, 75));

		bajaProducto.setBackground(new Color(255, 204, 102));
		bajaProducto.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		bajaProducto.setForeground(new Color(255, 255, 255));
		bajaProducto.setText("BAJA");
		bajaProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.BajaProductos, null));
			}
		});
		panel2.add(bajaProducto, new AbsoluteConstraints(420, 56, 150, 75));

		consultarProducto.setBackground(new Color(255, 204, 102));
		consultarProducto.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		consultarProducto.setForeground(new Color(255, 255, 255));
		consultarProducto.setText("CONSULTAR");
		consultarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ViewerFactory.getInstance().generarVista(new Context(Evento.ConsultarProductos, null));
			}
		});
		panel2.add(consultarProducto, new AbsoluteConstraints(420, 206, 150, 75));

		listarProductos.setBackground(new Color(255, 204, 102));
		listarProductos.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		listarProductos.setForeground(new Color(255, 255, 255));
		listarProductos.setText("LISTAR");
		listarProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
			 	ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarProductos,null));
			}
		});
		panel2.add(listarProductos, new AbsoluteConstraints(122, 358, 150, 75));
		listarProductosPorPlato.setBackground(new Color(255, 204, 102));
		listarProductosPorPlato.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		listarProductosPorPlato.setForeground(new Color(255, 255, 255));
		listarProductosPorPlato.setText("LISTAR PRODUCTOS POR PLATO");
		listarProductosPorPlato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				ViewerFactory.getInstance().generarVista(new Context(Evento.ListarProductosPorPlato,null));
		          }
		});
		panel2.add(listarProductosPorPlato, new AbsoluteConstraints(420, 358, 150, 75));

		modificarProducto.setBackground(new Color(255, 204, 102));
		modificarProducto.setFont(new Font("Segoe UI Black", 0, 12)); // NOI18N
		modificarProducto.setForeground(new Color(255, 255, 255));
		modificarProducto.setText("MODIFICAR");
		modificarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
			 	ViewerFactory.getInstance().generarVista(new Context(Evento.ModificarProductos, null));	}
		});
		panel2.add(modificarProducto, new AbsoluteConstraints(122, 206, 150, 75));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(panel2, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}

	@Override
	public void actualizar(Context context) {
	}
}
