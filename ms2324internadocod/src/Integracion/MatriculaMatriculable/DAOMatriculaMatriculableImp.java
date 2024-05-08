/**
 * 
 */
package Integracion.MatriculaMatriculable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;

public class DAOMatriculaMatriculableImp implements DAOMatriculaMatriculable {
	@Override
	public Integer vincularMatriculaMatriculable(Integer idMatricula, Integer idMatriculable) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
				
			 Connection conn = (Connection) t.getResource();
				
		     PreparedStatement ps = conn.prepareStatement ("INSERT INTO matricula_matriculable (idmatricula,idmatriculable,nota) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
		     ps.setInt(1, idMatricula);
		     ps.setInt(2, idMatriculable);
		     ps.setInt(3, -1);
		     ps.executeUpdate();
			 id=idMatricula;
			 if(ps!=null)ps.close();
		 }catch (Exception e) {}
		return id;
	}
	@Override
	public Integer desvincularMatriculaMatriculable(Integer idMatricula, Integer idMatriculable) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
				
			 Connection conn = (Connection) t.getResource();
				
		     PreparedStatement ps = conn.prepareStatement ("DELETE FROM matricula_matriculable WHERE idmatricula=? AND idmatriculable=?", Statement.RETURN_GENERATED_KEYS);
		     ps.setInt(1, idMatricula);
		     ps.setInt(2, idMatriculable);
		     ps.executeUpdate();
			 id=idMatricula;
			 if(ps!=null)ps.close();
		 }catch (Exception e) {}
		return id;
	}
	@Override
	public Integer comprobarVinculacion(Integer idMatricula, Integer idMatriculable) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
				
			 Connection conn = (Connection) t.getResource();
				
		     PreparedStatement ps = conn.prepareStatement ("SELECT * FROM matricula_matriculable WHERE idmatricula=? AND idmatriculable=? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		     ps.setInt(1, idMatricula);
		     ps.setInt(2, idMatriculable);
		     ResultSet res=ps.executeQuery();
			 if(res.next())
				 id=0;
			 if(ps!=null)ps.close();
			 if(res!=null)res.close();
			 
		 }catch (Exception e) {}
		return id;
	}
	@Override
	public Integer muestraNota(Integer idMatricula, Integer idMatriculable) {
		Integer nota = null;
		try{
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t = tm.getTransaction();
		
		Connection conn = (Connection) t.getResource();
	    PreparedStatement ps = conn.prepareStatement ("SELECT nota FROM matricula_matriculable WHERE idmatricula = ? AND idmatriculable = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1,idMatricula);
	    ps.setInt(2,idMatriculable);
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()){
	    	nota=rs.getInt("nota");
	    }
	    if(ps!=null)ps.close();
	    if(rs!=null)rs.close();
		}catch(Exception e){}
	    
		return nota;
	}
	@Override
	public Integer calificar(Integer idMatricula, Integer idMatriculable, Integer nota) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
				
			 Connection conn = (Connection) t.getResource();
				
		     PreparedStatement ps = conn.prepareStatement ("UPDATE matricula_matriculable SET nota=? where idmatricula=? AND idmatriculable =?", Statement.RETURN_GENERATED_KEYS);
		     ps.setInt(1, nota);
		     ps.setInt(2, idMatricula);
		     ps.setInt(3, idMatriculable);
		     ps.executeUpdate();
			 id=idMatricula;
			 if(ps!=null)ps.close();
		 }catch (Exception e) {}
		return id;
	}
	
}