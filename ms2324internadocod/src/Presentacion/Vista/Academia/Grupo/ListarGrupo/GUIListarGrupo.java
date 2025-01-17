package Presentacion.Vista.Academia.Grupo.ListarGrupo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Negocio.Academia.Grupo.TGrupo;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIListarGrupo extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID", "Letra", "Activo"};
	private JPanel panelPrincipal;
	private Collection<TGrupo> lista;

	public GUIListarGrupo() {
		super("LISTAR GRUPO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaGrupos=new JPanel(new BorderLayout());
		panelPrincipal.add(listaGrupos,BorderLayout.CENTER);

		try {
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE GRUPOS");						
			listaGrupos.setBorder(borde);
			
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
			listaGrupos.add(ult,BorderLayout.CENTER);
			listaGrupos.setVisible(true);
			
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
		if(context.getEvento() == Evento.ListarGrupo_OK) {
			int it = 0;
			lista = (Collection<TGrupo>) context.getDato();
			
			if(lista != null) {
				_dataTableModel.setNumRows(lista.size());
				
				for(TGrupo g : lista) {
					_dataTableModel.setValueAt(g.getId(),it,0);
					_dataTableModel.setValueAt(g.getLetra(),it,1);
					_dataTableModel.setValueAt(g.getActivo(),it,2);
					it++;
				}
			}
			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);
		}
		

	}

}