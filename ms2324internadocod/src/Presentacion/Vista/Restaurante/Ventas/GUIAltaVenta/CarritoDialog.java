package Presentacion.Vista.Restaurante.Ventas.GUIAltaVenta;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Negocio.Restaurante.Ventas.TCarrito;
import Negocio.Restaurante.Ventas.TVentas;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class CarritoDialog extends Dialog {
	String[] header = { "ID PLATO","CANTIDAD"};
	private DefaultTableModel _dataTableModel;
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,Integer> carro;
	private boolean status;
	private TCarrito prodDeVenta;
	private TVentas venta;
	
	public CarritoDialog (Frame parent,TVentas venta){
		super(parent,true);
		carro=new HashMap<Integer,Integer>();
	//	prodDeVenta=new TCarrito();
		this.venta=venta;
	    this.initGUI() ;

	}
	private void initGUI() {
		
		setTitle("Carrito de de platos");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JLabel descripcion=new JLabel("<html><p>Elige un plato por su id y selecciona una cantidad</p></html>");
		descripcion.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(descripcion);
		this.add(mainPanel);

		JPanel panel1=new JPanel();

		JLabel lprod=new JLabel("ID Platos:");
		final JTextField tprod= new JTextField(20);

		JPanel panel2=new JPanel();

		JLabel lcanti=new JLabel("Cantidad:");
		final JTextField tcant= new JTextField(20);

		panel1.add(lprod);
		panel1.add(tprod);
		panel2.add(lcanti);
		panel2.add(tcant);
		mainPanel.add(panel1);
		mainPanel.add(panel2);

		JButton aniadir=new JButton("Añadir");
		aniadir.setToolTipText("Aniade N unidades de un plato");
		JButton elim=new JButton("Eliminar");
		elim.setToolTipText("Elimina N unidades de un plato");
		JButton aceptar=new JButton("Aceptar");
		
		//añado la lista
				this._dataTableModel = new DefaultTableModel(){
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				};
				_dataTableModel.setColumnIdentifiers(header);
				this.updateTableModel();
				JTable dataTable = new JTable(_dataTableModel) {
					private static final long serialVersionUID = 1L;

					// we override prepareRenderer to resize columns to fit to content
					@Override
					public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
						Component component = super.prepareRenderer(renderer, row, column);
						int rendererWidth = component.getPreferredSize().width;
						TableColumn tableColumn = getColumnModel().getColumn(column);
						tableColumn.setPreferredWidth(
								Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
						return component;
					}
				};
				JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		mainPanel.add(tabelScroll);

		
		
		JPanel botones=new JPanel();
		botones.add(aniadir);
		botones.add(elim);
		botones.add(aceptar);
		
		mainPanel.add(botones);
		aniadir.addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent e)
			{	
			try{
				Integer id=Integer.parseInt(tprod.getText());
				Integer cantidad=Integer.parseInt(tcant.getText());
				if(!carro.containsKey(id))
					carro.put(id,cantidad );
				else{
					carro.put(id, carro.get(id)+cantidad);
				}
				updateTableModel();
			}
			catch(Exception e1){
				JOptionPane.showMessageDialog(null, "Los datos introducidos no son validos intentelo de nuevo", "", JOptionPane.ERROR_MESSAGE);
			}
			
			tprod.setText(null);
			tcant.setText(null);
				
			}
	});
		elim.addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent e)
			{		try{
				int actual=carro.get(Integer.parseInt(tprod.getText()));
				int nuevo=Integer.parseInt(tcant.getText());
				if(carro.containsKey(Integer.parseInt(tprod.getText()))&&actual>=nuevo){
					
					carro.replace((Integer.parseInt(tprod.getText())), actual-nuevo);
					
					if(carro.get(Integer.parseInt(tprod.getText()))==0){
						carro.remove(Integer.parseInt(tprod.getText()));
					}
					updateTableModel();
				}
			}catch(Exception e1){
				JOptionPane.showMessageDialog(null, "Los datos introducidos no son validos intentelo de nuevo", "", JOptionPane.ERROR_MESSAGE);
			}
				tprod.setText(null);
				tcant.setText(null);
			}
	});
		aceptar.addActionListener(new ActionListener()
		{ public void actionPerformed(ActionEvent e)
			{		
				setVisible(false);
				status=true;
				tprod.setText(null);
				tcant.setText(null);
				prodDeVenta= new TCarrito(venta,carro);
				ApplicationController.getInstancia().handleRequest(new Context(Evento.AltaVenta,prodDeVenta ));
			}
	});
		
		setPreferredSize(new Dimension(400, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	private void updateTableModel() {
			_dataTableModel.setNumRows(carro.keySet().size());
			int i=0;
			for (int k :carro.keySet()) {
				_dataTableModel.setValueAt(k, i, 0);
				_dataTableModel.setValueAt(carro.get(k), i, 1);
				i++;
			}
		
	}
	public boolean  open() {
		//prodDeVenta.setVenta(datos);//paso el id de la venta
		if (getParent() != null)
			setLocation(//
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return status;
	}
}