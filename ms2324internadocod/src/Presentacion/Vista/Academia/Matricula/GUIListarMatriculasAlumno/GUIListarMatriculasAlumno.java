package Presentacion.Vista.Academia.Matricula.GUIListarMatriculasAlumno;

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

import Negocio.Academia.Matricula.TMatricula;
import Presentacion.IGUI.IGUI;
import Presentacion.ApplicationController.ApplicationController;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class GUIListarMatriculasAlumno extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel _dataTableModel;
	private String[] headers={"ID", "ID Alumno","PrecioTotal","Descuento","Abierto","Activo"};
	private Collection<TMatricula> lista;
	
	JButton aceptar,cancelar;
	JPanel panelPrincipal;
	
	public GUIListarMatriculasAlumno() {
		super("MOSTRAR MATRICULAS POR ALUMNO");
		initGUI();
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void initGUI() {
		Color colorAzul=new Color(121, 192, 238);
		Color colorRojo=new Color(240, 92, 92);
		
		panelPrincipal=new JPanel(new BorderLayout());
		add(panelPrincipal,BorderLayout.CENTER);
		
		JPanel panel3 = new JPanel(new BorderLayout());
		panelPrincipal.add(panel3,BorderLayout.PAGE_START);
		
		JPanel panel3_1 = new JPanel(new BorderLayout());
		panel3.add(panel3_1,BorderLayout.LINE_END);
		
		JLabel id = new JLabel(" ID Alumno: ");
		final JTextField tId = new JTextField(20);
		
		JPanel panel3_2 = new JPanel(new BorderLayout());
		panel3.add(panel3_2,BorderLayout.LINE_START);
	
		panel3_2.add(id,BorderLayout.PAGE_START);
		panel3_1.add(tId,BorderLayout.PAGE_START);
		
		JPanel panel4 = new JPanel();
		panel4.setAlignmentX(RIGHT_ALIGNMENT);
		panelPrincipal.add(panel4,BorderLayout.PAGE_END);
		
		JPanel listaPaquetes=new JPanel(new BorderLayout());
		panelPrincipal.add(listaPaquetes,BorderLayout.CENTER);

		_dataTableModel = new DefaultTableModel(){
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
		
		aceptar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					
					ApplicationController.getInstancia().handleRequest(new Context(Evento.MostrarMatriculasporAlumno,Integer.parseInt(tId.getText())));
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
				finally
				{
					setVisible(false);
				}
				
			}
			
		});
		
		cancelar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		try{
		TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "LISTA DE MATRICULAS QUE ESTAN VINCULADAS AL ALUMNO CON ID: ");						
		listaPaquetes.setBorder(borde);
			
		JTable table = new JTable(_dataTableModel);
		table.getTableHeader().setReorderingAllowed(false);
		table.setPreferredScrollableViewportSize(new Dimension(675,200));
		JScrollPane tableScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScroll.setPreferredSize(new Dimension(tableScroll.getPreferredSize().width, 375));
        JPanel ult=new JPanel(new BorderLayout());
		ult.add(tableScroll,BorderLayout.CENTER);
		listaPaquetes.add(ult,BorderLayout.CENTER);
	    listaPaquetes.setVisible(true);
		panel4.add(aceptar,BorderLayout.CENTER);
		panel4.add(cancelar,BorderLayout.CENTER);
		}catch(Exception e){
			
		}
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(450,450,700,450);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(Context context) {
		if(context.getEvento()==Evento.MostrarMatriculasporAlumno_OK){
			int n=0;
			lista = (Collection<TMatricula>)context.getDato();
			if(lista!=null){
				_dataTableModel.setNumRows(lista.size());
				for(TMatricula ac : lista){
					
					_dataTableModel.setValueAt(ac.getId(),n,0);
				  	_dataTableModel.setValueAt(ac.getIdAlumno(),n,1);
					_dataTableModel.setValueAt(ac.getPreciototal(),n,2);
					_dataTableModel.setValueAt(ac.getDescuento(),n,3);
					_dataTableModel.setValueAt(ac.getAbierto(), n,4);
					_dataTableModel.setValueAt(ac.getActivo(), n,5);
					n++;
				 }
			 }
			panelPrincipal.revalidate();
		}
		else
		{
			JOptionPane.showMessageDialog(null, context.getDato(), "",JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
		}
		
	}

}