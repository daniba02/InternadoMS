package Presentacion.Vista.Restaurante.Productos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Restaurante.Producto.TProducto;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class ListarProductosPorPlato  extends JFrame implements IGUI{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID Producto","Nombre","Caracteristicas","Activo"};
	private Collection<TProducto> listaproductos;
	JPanel panelPrincipal;
	JButton aceptar, cancelar;
	
	public ListarProductosPorPlato() {
		super("LISTAR PRODUCTOS POR PLATO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void initGUI() {
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel id = new JLabel(" ID plato: ");
		final JTextField tId = new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);
		
		panel3_2.add(id, BorderLayout.PAGE_START);
		panel3_1.add(tId, BorderLayout.PAGE_START);
		
		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4, BorderLayout.PAGE_END);
		
		JPanel listaPanel=new JPanel(new BorderLayout());
		panelPrincipal.add(listaPanel,BorderLayout.CENTER);

		_dataTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		_dataTableModel.setColumnIdentifiers(headers);
		
		aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		cancelar = new JButton("Volver");
		cancelar.setBackground(colorRojo);

		JPanel panelBotones = new JPanel();
		panelBotones.add(aceptar,BorderLayout.CENTER);
		panelBotones.add(cancelar,BorderLayout.CENTER);
		
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer id ;
				try {
					setVisible(false);
					 id = Integer.parseInt(tId.getText());

					if (tId.getText().isEmpty()) {
						id = 0;
					} else {
						id = Integer.valueOf(tId.getText());
					}

					ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarProductosPorPlato, id));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "el id de plato debe ser un nº", "ERROR", JOptionPane.ERROR_MESSAGE);

				}
				finally
				{
					setVisible(false);

				}
			}
		});
		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});
		try {

			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
					"LISTA DE PRODUCTOS POR PLATOS");
			listaPanel.setBorder(borde);
			
			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675,200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
	        
	        JPanel ult=new JPanel();
			ult.add(tableScroll,BorderLayout.CENTER);
			listaPanel.add(ult,BorderLayout.CENTER);
			listaPanel.add(panelBotones,BorderLayout.PAGE_END);
			
			listaPanel.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(450,450,700,450);
	}

	
	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ListarProductosPorPlato_OK) {
		int n=0;
		listaproductos = (Collection<TProducto>)context.getDato();
			if(listaproductos!=null){
				_dataTableModel.setNumRows(listaproductos.size());
				for(TProducto p : listaproductos){
					
					_dataTableModel.setValueAt(p.getId(),n,0);
				  	_dataTableModel.setValueAt(p.getNombre(),n,1);
				  	_dataTableModel.setValueAt(p.getCaracteristicas(),n,2);
					_dataTableModel.setValueAt(p.getActivo(), n, 3);
					
					
					n++;
				 }
			 }
			panelPrincipal.revalidate();
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}
		
	

	}
}
