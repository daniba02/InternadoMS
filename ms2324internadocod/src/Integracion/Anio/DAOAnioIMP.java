package Integracion.Anio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Anio.TAnio;

public class DAOAnioIMP implements DAOAnio {
	
	public Integer create(TAnio anio) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
				
			 Connection conn = (Connection) t.getResource();
				
		     PreparedStatement ps = conn.prepareStatement ("INSERT INTO anio (anio,activo) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
		     ps.setString(1, anio.getAnio());
		     ps.setBoolean(2, anio.getActivo());
		     ps.executeUpdate();
		     ResultSet rs = ps.getGeneratedKeys();
		     if(rs.next()) 
		    	 id=rs.getInt(1);
		     if(ps!=null)ps.close();
			    
		    if(rs!=null)rs.close();
		 }catch (Exception e) {}
		return id;
	}

	public Integer delete(Integer id) {
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
			
			PreparedStatement ps = conn.prepareStatement("UPDATE anio SET activo=false where idanio = ?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			return id;
			
		}catch(Exception e){
			return -1;
		}		
	}

	public Integer update(TAnio leido) {
		int id=-1; 
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		     PreparedStatement ps = conn.prepareStatement ("UPDATE anio SET anio = ?, activo = ? WHERE idanio = ?", Statement.RETURN_GENERATED_KEYS);
		     ps.setString(1, leido.getAnio());
		     ps.setBoolean(2, leido.getActivo());
		     ps.setInt(3, leido.getId());		   
		     ps.executeUpdate();
		     ps.close();
		     id=leido.getId();
		 }catch (Exception e) {}
		return id;
	}

	public TAnio read(Integer id) {
		TAnio an = null;
		try{
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t = tm.getTransaction();
		
		Connection conn = (Connection) t.getResource();
	    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM anio WHERE idanio = ?  FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1,id);
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()){
	    	String anio =rs.getString("anio");
	    	Boolean activo=rs.getBoolean("activo");
	    	an = new TAnio(id,anio,activo);
	    }
	    if(ps!=null)ps.close();
	    if(rs!=null)rs.close();
	    
		}catch(Exception e){}
	    
		return an;
	}
	
	public TAnio readByAnio(String anio) {
		TAnio an = null;
		try{
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t = tm.getTransaction();
		
		Connection conn = (Connection) t.getResource();
	    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM anio WHERE anio = ?  FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
	    ps.setString(1,anio);
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()){
	    	Integer id =rs.getInt("idanio");
	    	Boolean activo=rs.getBoolean("activo");
	    	an = new TAnio(id,anio,activo);
	    }
	    if(ps!=null)ps.close();
	    if(rs!=null)rs.close();
	    
		}catch(Exception e){}
	    
		return an;
	}

	@Override
	public Collection<TAnio> readAll() throws Exception {
		Collection<TAnio> anios = new ArrayList<TAnio>();
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM anio FOR UPDATE");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id;
				String anio;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idanio");
				anio = rs.getString("anio");
				activo = rs.getBoolean("activo");
				// A�ado el matriculable a la collection
				anios.add(new TAnio(id, anio, activo));
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return anios;
	}
}