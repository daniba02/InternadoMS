package Integracion.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;

public class NotaMediaAsignatura implements Query {

	public Object execute(Object parametro) {
		
		Integer notamedia=null;
		Integer sum=0;
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t=tm.getTransaction();
		Connection connection = (Connection) t.getResource();
		try{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM asignaturas NATURAL JOIN matriculable NATURAL JOIN matricula_matriculable WHERE idasignatura = ?", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, (Integer)parametro);
			ResultSet rs=ps.executeQuery();
			int cont=0;
			int aux;
			boolean hayNota=false;
			while(rs.next()) {
				aux=rs.getInt("nota");
				if(aux>-1){
					sum+=aux;
					hayNota=true;
				}
				cont++;
			}
			sum/=cont;
			if(cont>0&&hayNota){
				notamedia=sum;
			}
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}catch(Exception e){
		}
		
		return notamedia;
	}

}