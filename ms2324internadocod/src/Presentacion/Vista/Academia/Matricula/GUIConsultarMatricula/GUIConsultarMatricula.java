package Presentacion.Vista.Academia.Matricula.GUIConsultarMatricula;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

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

import Negocio.Academia.Matricula.TOAMatricula;
import Negocio.Academia.Matriculable.TOAMatriculable;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;
import Presentacion.IGUI.IGUI;

public class GUIConsultarMatricula extends JFrame implements IGUI{
	private static final long serialVersionUID = 1L;
	
	private JButton aceptar,cancelar;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID","Nombre","Hora","Precio","Plazas","Asignaturas","Grupo","Precio"};
	private JPanel panelPrincipal;
	private JPanel precioTotal;

	public GUIConsultarMatricula() {
		super("CONSULTAR MATRICULA");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

public void initGUI() {
		
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		JPanel listaMatriculables=new JPanel(new BorderLayout());
		panelPrincipal.add(listaMatriculables,BorderLayout.CENTER);
		precioTotal=new JPanel(new BorderLayout());
		precioTotal.setVisible(false);
		
		Color colorAzul = new Color(121, 192, 238);
		Color colorRojo = new Color(240, 92, 92);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel idMatricula = new JLabel(" ID Matricula: ");
		final JTextField tidMatricula= new JTextField(20);

		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
		
		panel3_2.add(idMatricula,BorderLayout.PAGE_START);
		panel3_1.add(tidMatricula,BorderLayout.PAGE_START);
		
		JButton aceptar = new JButton("Aceptar");
		aceptar.setBackground(colorAzul);
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBackground(colorRojo);
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Habria que comentar esta linea del controller
				try {
					setVisible(false);
					ApplicationController.getInstancia().handleRequest(new Context(Evento.ConsultarMatricula, Integer.parseInt(tidMatricula.getText())));					
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Hay que especificar número correto", "", JOptionPane.ERROR_MESSAGE);
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
			
			TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "MATRICULA CONSULTADA: ");						
			listaMatriculables.setBorder(borde);
			
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
			ult.add(precioTotal,BorderLayout.AFTER_LAST_LINE);
			listaMatriculables.add(ult,BorderLayout.CENTER);
			listaMatriculables.add(panelBotones,BorderLayout.PAGE_END);
			listaMatriculables.setVisible(true);
			
		} catch (Exception e) {
			setVisible(false);
			e.printStackTrace();
		}
		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200,200,600,200);
       }

	public void actualizar(Context context) {
	if(context.getEvento()==Evento.ConsultarMatricula_OK) {
		int n=0;
		TOAMatricula toama = (TOAMatricula)context.getDato();
		if(toama!=null)_dataTableModel.setNumRows(toama.getMatriculables().size());
	    Collection<TOAMatriculable> tmatriculables = toama.getMatriculables();
	    Iterator<TOAMatriculable> it=tmatriculables.iterator();
	    TOAMatriculable tm=it.next();
	    while(n<toama.getMatriculables().size()){
	   	_dataTableModel.setValueAt(toama.getMatricula().getId(),n,0);
        _dataTableModel.setValueAt(toama.getAlumno().getNombre(),n,1);
	    _dataTableModel.setValueAt(tm.getMatriculable().getHora(),n,2);
	    _dataTableModel.setValueAt(tm.getMatriculable().getPrecio(),n,3);
	    _dataTableModel.setValueAt(tm.getMatriculable().getPlazas(),n,4);
	    _dataTableModel.setValueAt(tm.getAsignatura().getNombre(),n,5);
	    _dataTableModel.setValueAt(tm.getGrupo().getLetra(),n,6);
	    _dataTableModel.setValueAt(tm.getMatriculable().getPrecio(),n,7);
	    n++;
	    if(it.hasNext())
	    	tm=it.next();
	    }
	    JLabel labelPrecio = new JLabel("Precio total matricula (descuento aplicado sin decimales): ");
		precioTotal.add(labelPrecio,BorderLayout.LINE_START);
		final JTextField tprecio = new JTextField(10);
		tprecio.setText(toama.getMatricula().getPreciototal().toString());
		tprecio.setEditable(false);
		precioTotal.add(tprecio);
		precioTotal.setVisible(true);
	    panelPrincipal.revalidate();
	    aceptar.setVisible(false);
	    cancelar.setVisible(false);
	
	}else {
			JOptionPane.showMessageDialog(null, context.getDato(), "", JOptionPane.ERROR_MESSAGE);
			setVisible(false);

	}
	}
	
}

