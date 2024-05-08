package Integracion.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Matricula.TMatricula;

public class DAOMatriculaImp implements DAOMatricula {

	public Integer create(TMatricula matricula) {
		int id =-1;
		 try {
			 TransactionManager tm = TransactionManager.getInstance();
			 Transaction t = tm.getTransaction();
			 	
			 Connection conn = (Connection) t.getResource();
			 
		     PreparedStatement ps = conn.prepareStatement ("INSERT INTO matricula (descuento,precio,activo,abierto,idalumno) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		     ps.setDouble(1, matricula.getDescuento());
		     ps.setInt(2, matricula.getPreciototal());
		     ps.setBoolean(3, matricula.getActivo());
		     ps.setBoolean(4, matricula.getAbierto());
		     ps.setInt(5, matricula.getIdAlumno());
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
			
			PreparedStatement ps = conn.prepareStatement("UPDATE matricula SET activo=false where idmatricula=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			return id;
			
		}catch(Exception e){
			return -1;
		}
	}

	public Integer close(Integer id) {
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
			
			PreparedStatement ps = conn.prepareStatement("UPDATE matricula SET abierto=false where idmatricula=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			return id;
			
		}catch(Exception e){
			return -1;
		}
	}

	public Integer update(TMatricula leido) {
		int id=-1; 
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		     PreparedStatement ps = conn.prepareStatement ("UPDATE matricula SET descuento = ?, precio = ?, activo = ?, abierto = ? WHERE idmatricula = ?", Statement.RETURN_GENERATED_KEYS);
		     ps.setDouble(1, leido.getDescuento());
		     ps.setInt(2, leido.getPreciototal());
		     ps.setBoolean(3, leido.getActivo());
		     ps.setBoolean(4, leido.getAbierto());
		     ps.setInt(5, leido.getId());
		     ps.executeUpdate();
		     if(ps!=null)ps.close();
		     id=leido.getId();
		 }catch (Exception e) {}
		return id;
	}

	public TMatricula read(Integer id) {
		TMatricula mt=null;
		try{
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t = tm.getTransaction();
		
		Connection conn = (Connection) t.getResource();
	    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM matricula WHERE idmatricula = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1,id);
	    ResultSet rs = ps.executeQuery();
	    if(rs.next()){
	    	double descuento;
	    	int precioTotal,idalumno;
	    	boolean abierto,activo;
	    	descuento=rs.getDouble("descuento");
	    	precioTotal=rs.getInt("precio");
	    	abierto=rs.getBoolean("abierto");
	    	activo=rs.getBoolean("activo");
	    	idalumno=rs.getInt("idalumno");
	    	mt = new TMatricula(id,idalumno,precioTotal,descuento,abierto,activo);
	    }
	    if(ps!=null)ps.close();
	    if(rs!=null)rs.close();
		}catch(Exception e){}
	    
		return mt;
	}

	public Collection<TMatricula> readAll() {
		Collection<TMatricula>mts=new ArrayList<TMatricula>();
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM matricula FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		    ResultSet rs=ps.executeQuery();
		    while(rs.next()){
		    	int id,preciototal,idalumno;
		    	double descuento;
		    	boolean abierto,activo;
		    	id=rs.getInt("idmatricula");
		    	preciototal=rs.getInt("precio");
		    	descuento=rs.getDouble("descuento");
		    	abierto=rs.getBoolean("abierto");
		    	activo=rs.getBoolean("activo");
		    	idalumno=rs.getInt("idalumno");
		    	mts.add(new TMatricula(id,idalumno,preciototal,descuento,abierto,activo));
		    }
		    if(ps!=null)ps.close();
		    if(rs!=null)rs.close();
		}catch(Exception e){}
		return mts;
	}

	public Collection<TMatricula> readByAlumno(Integer idAlumno) {
		Collection<TMatricula>mts=new ArrayList<TMatricula>();
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM matricula WHERE idalumno=? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		    ps.setInt(1, idAlumno);
		    ResultSet rs=ps.executeQuery();
		    while(rs.next()){
			    	int id,preciototal,idalumno;
			    	double descuento;
			    	boolean abierto,activo;
			    	id=rs.getInt("idmatricula");
			    	preciototal=rs.getInt("precio");
			    	descuento=rs.getDouble("descuento");
			    	abierto=rs.getBoolean("abierto");
			    	activo=rs.getBoolean("activo");
			    	idalumno=rs.getInt("idalumno");
			    	mts.add(new TMatricula(id,idalumno,preciototal,descuento,abierto,activo));
		    }
		    if(ps!=null)ps.close();
		    if(rs!=null)rs.close();
		}catch(Exception e){}
		return mts;
	}

	public Collection<TMatricula> readByMatriculable(Integer matriculable) {
		Collection<TMatricula>mts=new ArrayList<TMatricula>();
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM matricula_matriculable WHERE idmatriculable=?", Statement.RETURN_GENERATED_KEYS);
		    ps.setInt(1, matriculable);
		    ResultSet rs=ps.executeQuery();
		    while(rs.next()){
		    	PreparedStatement ps1=conn.prepareStatement("SELECT * FROM matricula WHERE idmatricula=? FOR UPDATE",Statement.RETURN_GENERATED_KEYS);
		    	ps1.setInt(1, rs.getInt("idmatricula"));
		    	ResultSet rs1=ps1.executeQuery();
		    	if(rs1.next()){
			    	int id,preciototal,idalumno;
			    	double descuento;
			    	boolean abierto,activo;
			    	id=rs1.getInt("idmatricula");
			    	preciototal=rs1.getInt("precio");
			    	descuento=rs1.getDouble("descuento");
			    	abierto=rs1.getBoolean("abierto");
			    	activo=rs1.getBoolean("activo");
			    	idalumno=rs1.getInt("idalumno");
			    	mts.add(new TMatricula(id,idalumno,preciototal,descuento,abierto,activo));
		    	}
		    	if(ps1!=null)ps1.close();
		    	if(rs1!=null)rs1.close();
		    }
		    if(ps!=null)ps.close();
		    if(rs!=null)rs.close();
		}catch(Exception e){}
		return mts;
	}

	
}