package Presentacion.Vista.Restaurante.Platos;

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

import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Platos.TComida;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Negocio.Restaurante.Platos.VisitantePlatos;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;
import Presentacion.Vista.Restaurante.GUIutilities;

public class ListarPlatosporProductos extends JFrame implements IGUI,VisitantePlatos {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers = {"Id Plato", "Nombre", "Stock","Precio", "Activo","Tipo","Temperatura"};
	private Collection<TPlatoCompleto> lista;
	private int n=0;
	JButton aceptar, cancelar;
	JPanel panelPrincipal;

	public ListarPlatosporProductos() {
		super("MOSTRAR PRODUCTOS POR PLATO:");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);

		panelPrincipal = new JPanel(new BorderLayout());
		add(panelPrincipal, BorderLayout.CENTER);

		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3, BorderLayout.PAGE_START);

		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1, BorderLayout.LINE_END);

		JLabel id = new JLabel(" ID Producto: ");
		final JTextField tId = new JTextField(20);
		GUIutilities.onlyIntegers(tId);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2, BorderLayout.LINE_START);

		panel3_2.add(id, BorderLayout.PAGE_START);
		panel3_1.add(tId, BorderLayout.PAGE_START);

		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4, BorderLayout.PAGE_END);

		JPanel listaPaquetes = new JPanel(new BorderLayout());
		panelPrincipal.add(listaPaquetes, BorderLayout.CENTER);

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
		cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		JPanel panelBotones = new JPanel();
		panelBotones.add(aceptar);
		panelBotones.add(cancelar);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer idp;
					try{
						idp = Integer.parseInt(tId.getText());
					}catch(Exception e1){
						throw new Exception("El id no puede ser nulo");
					}
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ListarPlatosPorProducto,idp));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "",JOptionPane.ERROR_MESSAGE);
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
					"PLATOS QUE TIENEN ESE PRODUCTO: ");
			listaPaquetes.setBorder(borde);
			panel4.add(aceptar, BorderLayout.CENTER);
			panel4.add(cancelar, BorderLayout.CENTER);

			JTable table = new JTable(_dataTableModel);
			table.getTableHeader().setReorderingAllowed(false);
			table.setPreferredScrollableViewportSize(new Dimension(675, 200));
			JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
			JPanel ult = new JPanel();
			ult.add(tableScroll, BorderLayout.CENTER);
			listaPaquetes.add(ult, BorderLayout.CENTER);
			listaPaquetes.add(panelBotones, BorderLayout.PAGE_END);
			listaPaquetes.setVisible(true);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
		}
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(450, 450, 700, 450);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if (context.getEvento() == Evento.ListarPlatosPorProducto_OK) {
			lista = (Collection<TPlatoCompleto>) context.getDato();
			if (lista != null) {
				_dataTableModel.setNumRows(lista.size());
				for (TPlatoCompleto pl : lista) {
					_dataTableModel.setValueAt(pl.getPlato().getId(), n, 0);
					_dataTableModel.setValueAt(pl.getPlato().getNombre(), n, 1);
					_dataTableModel.setValueAt(pl.getPlato().getStock(), n, 2);
					_dataTableModel.setValueAt(pl.getPlato().getPrecio(), n, 3);
					_dataTableModel.setValueAt(pl.getPlato().getActivo(), n, 4);
					pl.aceptar(this);
					n++;
				}
			}
			panelPrincipal.revalidate();
		} else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);

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
