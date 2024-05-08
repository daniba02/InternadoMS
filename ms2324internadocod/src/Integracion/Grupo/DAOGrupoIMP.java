package Integracion.Grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Grupo.TGrupo;

public class DAOGrupoIMP implements DAOGrupo {
	
	public Integer create(TGrupo grupo) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
				
			 Connection conn = (Connection) t.getResource();
				
		     PreparedStatement ps = conn.prepareStatement ("INSERT INTO grupo (letra,activo) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
		     ps.setString(1, grupo.getLetra());
		     ps.setBoolean(2, grupo.getActivo());
		     ps.executeUpdate();
		     ResultSet rs = ps.getGeneratedKeys();
		     
		     if(rs.next()) 
		    	 id=rs.getInt(1);
		     rs.close();
		     ps.close();
		    
		 }catch (Exception e) {}
		return id;
	}

	public Integer delete(Integer id) {
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
			
			PreparedStatement ps = conn.prepareStatement("UPDATE grupo SET activo=false where idgrupo=?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
			ps.close();
			return id;
			
		}catch(Exception e){
			return -1;
		}		
	}

	public Integer update(TGrupo leido) {
		int id=-1; 
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		     PreparedStatement ps = conn.prepareStatement ("UPDATE grupo SET letra = ?, activo = ? WHERE idgrupo = ?", Statement.RETURN_GENERATED_KEYS);
		     ps.setString(1, leido.getLetra());
		     ps.setBoolean(2, leido.getActivo());
		     ps.setInt(3, leido.getId());		   
		     ps.executeUpdate();
		     ps.close();
		     id=leido.getId();
		     ps.close();
		 }catch (Exception e) {}
		return id;
	}

	public TGrupo read(Integer id) {
		TGrupo gr=null;
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM grupo WHERE idgrupo = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		    ps.setInt(1,id);
		    ResultSet rs = ps.executeQuery();
		    if(rs.next()){
		    	String letra=rs.getString("letra");
		    	Boolean activo = rs.getBoolean("activo");
		    	gr = new TGrupo(id, letra, activo);
	    }
	    rs.close();
	    ps.close();
	  
		}catch(Exception e){
			
		}
	    
		return gr;
	}
	
	public TGrupo readByLetra(String letra) {
		TGrupo gr=null;
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM grupo WHERE letra = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		    ps.setString(1,letra);
		    ResultSet rs = ps.executeQuery();
		    if(rs.next()){
		    	Integer id = rs.getInt("idgrupo");
		    	Boolean activo = rs.getBoolean("activo");
		    	gr = new TGrupo(id, letra, activo);
	    }
	    rs.close();
	    ps.close();
	  
		}catch(Exception e){
			
		}
	    
		return gr;
	}

	@Override
	public Collection<TGrupo> readAll() throws Exception {
		Collection<TGrupo> grupos = new ArrayList<TGrupo>();
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM grupo FOR UPDATE");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id;
				String letra;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idgrupo");
				letra = rs.getString("letra");
				activo = rs.getBoolean("activo");
				// A�ado el matriculable a la collection
				grupos.add(new TGrupo(id, letra, activo));
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return grupos;

	}
}