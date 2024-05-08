package Integracion.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;

public class ListarNotasAlumno implements Query {
	
	@Override
	public Object execute(Object parametro) {
		
		Map<String,Integer> notas = new HashMap<String,Integer>();
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t=tm.getTransaction();
		Connection connection = (Connection) t.getResource();
		try{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM matricula_matriculable mm JOIN matricula m ON mm.idmatricula=m.idmatricula WHERE idalumno = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, (Integer)parametro);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Integer nota=rs.getInt("nota");
				ps = connection.prepareStatement("SELECT nombre FROM asignaturas a JOIN matriculable m ON a.idasignatura=m.idAsignatura WHERE idmatriculable = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, rs.getInt("idmatriculable"));
				ResultSet rs1=ps.executeQuery();
				if(rs1.next()){
					String nombre=rs1.getString("nombre");
					if(nota>-1)
						notas.put(nombre, nota);
				}
				if(rs1!=null) rs1.close();
			}
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}
		
		return notas;
	}
}