package Presentacion.Vista.Restaurante.Ventas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Platos.TComida;
import Negocio.Restaurante.Platos.VisitantePlatos;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;
import Negocio.Restaurante.Ventas.TFacturaVenta;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIConsultaVenta  extends JFrame implements IGUI,VisitantePlatos{

	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModelVenta;
	private DefaultTableModel _dataTableModelPlatos;
	private String[] headerVenta={"ID", "Fecha","Cliente","Mesa", "Capacidad"};
    private String[] headerPlatos={"Nombre","Cantidad","PrecioTotal","Tipo de plato","Tipo","Temperatura"};
	private JPanel panelPrincipal;
	private int rowPlatos;
	
	public GUIConsultaVenta() {
		super("CONSULTAR VENTA");
		rowPlatos=0;
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

public void initGUI() {
		
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaVentas=new JPanel(new BorderLayout());
		panelPrincipal.add(listaVentas,BorderLayout.CENTER);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idVenta = new JLabel(" ID Venta: ");
		final JTextField tidVenta= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(idVenta,BorderLayout.PAGE_START);
		panel3_1.add(tidVenta,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Habria que comentar esta linea del controller
				try {
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultaVenta, Integer.parseInt(tidVenta.getText())));					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar ID correto", "", JOptionPane.ERROR_MESSAGE);
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
		JPanel panelBotones=new JPanel();
		panelBotones.add(aceptar);
		panelBotones.add(cancelar);
		try {
			//dos tablas 
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "VENTA CONSULTADA: ");						
			listaVentas.setBorder(borde);
			
		
     		_dataTableModelVenta = new DefaultTableModel(){
				private static final long serialVersionUID = 1L;

				@Override
		        public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModelPlatos = new DefaultTableModel(){
				private static final long serialVersionUID = 1L;

				@Override
		        public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModelVenta.setColumnIdentifiers(headerVenta);
			_dataTableModelPlatos.setColumnIdentifiers(headerPlatos);

			JTable tableVenta= new JTable(_dataTableModelVenta);
			tableVenta.getTableHeader().setReorderingAllowed(false);
			tableVenta.setPreferredScrollableViewportSize(new Dimension(400,200));
			JScrollPane tableScroll = new JScrollPane(tableVenta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
	        JPanel ult=new JPanel(new BorderLayout());
			
	        ult.add(tableScroll,BorderLayout.CENTER);
			
			
			JTable tablePlatos= new JTable(_dataTableModelPlatos);
			tablePlatos.getTableHeader().setReorderingAllowed(false);
			tablePlatos.setPreferredScrollableViewportSize(new Dimension(400,200));
			JScrollPane tableScrollPlatos = new JScrollPane(tablePlatos, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        tableScrollPlatos.setPreferredSize(new Dimension(tableScrollPlatos.getPreferredSize().width, 375));

	        //ult.add(tableScrollPl,BorderLayout.CENTER);
	        JPanel platos=new JPanel(new BorderLayout());
	        platos.add(tableScrollPlatos,BorderLayout.CENTER);
	       
	        // Use a BoxLayout with Y_AXIS for vertical stacking
	        JPanel tablesPanel = new JPanel();
	        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

	        tablesPanel.add(tableScroll);
	        tablesPanel.add(tableScrollPlatos);

	        // Add the tablesPanel to listaVentas
	        listaVentas.add(tablesPanel, BorderLayout.CENTER);

			
			listaVentas.add(panelBotones,BorderLayout.PAGE_END);
			listaVentas.setVisible(true);
			
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(400,400,800,300);
	}
	@Override
	public void actualizar(Context context) {
		
	if(context.getEvento()==Evento.ConsultaVenta_OK) {
		
		TFacturaVenta tfv = (TFacturaVenta)context.getDato();
		if(tfv!=null)_dataTableModelVenta.setNumRows(1);
		_dataTableModelPlatos.setNumRows(tfv.getPlatos().size());
		
		//TABLA DE LA VENTA
		_dataTableModelVenta.setValueAt(tfv.getVenta().getId(),0,0);
		_dataTableModelVenta.setValueAt(tfv.getVenta().getFecha(),0,1);
		_dataTableModelVenta.setValueAt(tfv.getVenta().getCliente(),0,2);
		_dataTableModelVenta.setValueAt(tfv.getVenta().getIdMesa(),0,3);
		_dataTableModelVenta.setValueAt(tfv.getMesa().getCapacidad(),0,4);
	   
	   //TABLA CON LOS PLATOS
	    rowPlatos = 0;
	   for(TPlatoVenta pv : tfv.getPlatos()){
		   _dataTableModelPlatos.setValueAt(pv.getPlato().getPlato().getNombre(),rowPlatos, 0);
		   _dataTableModelPlatos.setValueAt(pv.getCantidad(), rowPlatos, 1);
		   _dataTableModelPlatos.setValueAt(pv.getPrecio(),rowPlatos, 2);
		   pv.getPlato().aceptar(this);
		   rowPlatos++;
	   }
	    panelPrincipal.revalidate();
	
	}else {
		setVisible(false);
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
	}
	}

	@Override
	public void visitaComida(TComida tComida) {
		// TODO Auto-generated method stub
		_dataTableModelPlatos.setValueAt("comida",rowPlatos, 3);
		 _dataTableModelPlatos.setValueAt(tComida.getTipo(),rowPlatos, 4);
		 _dataTableModelPlatos.setValueAt("-",rowPlatos, 5);
	}

	@Override
	public void visitaBebida(TBebida tBebida) {
		// TODO Auto-generated method stub
		_dataTableModelPlatos.setValueAt("bebida",rowPlatos, 3);
		_dataTableModelPlatos.setValueAt("-",rowPlatos, 4);
		_dataTableModelPlatos.setValueAt(tBebida.getTemperatura(),rowPlatos, 5);
		
	}

}
