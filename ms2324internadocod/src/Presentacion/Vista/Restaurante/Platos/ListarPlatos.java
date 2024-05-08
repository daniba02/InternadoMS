package Presentacion.Vista.Restaurante.Platos;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Platos.TComida;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Negocio.Restaurante.Platos.VisitantePlatos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class ListarPlatos extends JFrame implements IGUI,VisitantePlatos {
	
	private static final long serialVersionUID = 1L;
	
	private DefaultTableModel _dataTableModel;
	private String[] headers={"Id Plato", "Nombre", "Stock","Precio", "Activo","Tipo","Temperatura"};
	private Collection<TPlatoCompleto> listaplatos;
	JPanel panelPrincipal;
	private int n=0;
	
	public ListarPlatos() {
		super("LISTAR PLATOS");
		initGUI();
		setLocationRelativeTo(null);
	}
	
	public void initGUI() {
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaPanel=new JPanel(new BorderLayout());
		panelPrincipal.add(listaPanel,BorderLayout.CENTER);

		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE PLATOS");						
			listaPanel.setBorder(borde);
			
			_dataTableModel = new DefaultTableModel(){
	
				private static final long serialVersionUID = 1L;

				@Override
		        public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			_dataTableModel.setColumnIdentifiers(headers);

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675,200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
	        JPanel ult=new JPanel(new BorderLayout());
			ult.add(tableScroll,BorderLayout.CENTER);
			listaPanel.add(ult,BorderLayout.CENTER);
			listaPanel.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(450,450,700,450);
	}
	
	@SuppressWarnings("unchecked")
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.ListarPlatos_OK) {
		
		listaplatos = (Collection<TPlatoCompleto>)context.getDato();
			if(listaplatos!=null){
				_dataTableModel.setNumRows(listaplatos.size());
				for(TPlatoCompleto pl : listaplatos){
					
					_dataTableModel.setValueAt(pl.getPlato().getId(),n,0);
				  	_dataTableModel.setValueAt(pl.getPlato().getNombre(),n,1);
					_dataTableModel.setValueAt(pl.getPlato().getStock(),n,2);
					_dataTableModel.setValueAt(pl.getPlato().getPrecio(),n,3);
					_dataTableModel.setValueAt(pl.getPlato().getActivo(), n, 4);
					
					pl.aceptar(this);
					
					n++;
				 }
			 }
			panelPrincipal.revalidate();
		}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);

		}
		
	}

	@Override
	public void visitaComida(TComida tComida) {
		// TODO Auto-generated method stub
		_dataTableModel.setValueAt(tComida.getTipo(), n, 5);
		_dataTableModel.setValueAt("-", n, 6);
	}

	@Override
	public void visitaBebida(TBebida tBebida) {
		// TODO Auto-generated method stub
		_dataTableModel.setValueAt("-", n, 5);
		_dataTableModel.setValueAt(tBebida.getTemperatura(), n, 6);
	}
	
}