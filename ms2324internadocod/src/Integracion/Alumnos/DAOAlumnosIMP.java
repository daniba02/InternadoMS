
package Integracion.Alumnos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Alumnos.TAlumnos;

public class DAOAlumnosIMP implements DAOAlumnos {
		
	public Integer create(TAlumnos alumno) {
		int id=-1;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			
			Connection conn=(Connection) t.getResource();
			
			PreparedStatement ps=conn.prepareStatement("INSERT INTO alumno (DNI, nombre, apellido, edad, telefono, activo) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, alumno.getDNI());
			ps.setString(2, alumno.getNombre());
			ps.setString(3, alumno.getApellido());
			ps.setInt(4, alumno.getEdad());
			ps.setInt(5, alumno.getTelefono());
			ps.setBoolean(6, alumno.getActivo());
			ps.executeUpdate();
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next())
				id=rs.getInt(1);
			ps.close();
		}catch (Exception e) {}
		
		return id;
	}

	public Integer delete(Integer id) {
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			
			Connection conn = (Connection) t.getResource();
			
			PreparedStatement ps=conn.prepareStatement("UPDATE alumno SET activo=false WHERE idalumno=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			return id;
			
		}catch(Exception e) {
			return -1;
		}
	}

	/** 
	* (non-Javadoc)
	* @see DAOAlumnos#update(TAlumnos alumno)
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Integer update(TAlumnos alumno) {
		int id=-1;
		try {
		Transaction t = TransactionManager.getInstance().getTransaction();
		
		Connection conn=(Connection) t.getResource();
		
		PreparedStatement ps=conn.prepareStatement("UPDATE alumno SET dni = ?, nombre = ?, apellido = ?, edad = ?, telefono = ?, activo = ? WHERE idalumno = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, alumno.getDNI());
		ps.setString(2, alumno.getNombre());
		ps.setString(3, alumno.getApellido());
		ps.setInt(4, alumno.getEdad());
		ps.setInt(5, alumno.getTelefono());
		ps.setBoolean(6, alumno.getActivo());
		ps.setInt(7, alumno.getID());
		ps.executeUpdate();
		
	     ps.executeUpdate();
	     if(ps!=null)ps.close();
	     id=alumno.getID();
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	return id;
	}

	public TAlumnos read(Integer id) {
		TAlumnos alumno=null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			
			Connection conn = (Connection) t.getResource();
			
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM alumno WHERE idalumno = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,  id);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				String DNI, nombre, apellido;
				Integer edad, telefono;
				boolean activo;
				DNI=rs.getString("DNI");
				nombre=rs.getString("nombre");
				apellido=rs.getString("apellido");
				edad=rs.getInt("edad");
				telefono=rs.getInt("telefono");
				activo=rs.getBoolean("activo");
				alumno= new TAlumnos(id, DNI, nombre, apellido, edad, telefono, activo);
				
			}
			ps.close();
		} catch(Exception e) {}
		
		
		return alumno;
	}
	
	public TAlumnos readByDNI(String DNI) {
		TAlumnos alumno=null;
		try {
			Transaction t = TransactionManager.getInstance().getTransaction();
			
			Connection conn = (Connection) t.getResource();
			
			PreparedStatement ps=conn.prepareStatement("SELECT * FROM alumno WHERE dni = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, DNI);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				String nombre, apellido;
				int edad, telefono,id;
				boolean activo;
				id=rs.getInt("idalumno");
				nombre=rs.getString("nombre");
				apellido=rs.getString("apellido");
				edad=rs.getInt("edad");
				telefono=rs.getInt("telefono");
				activo=rs.getBoolean("activo");
				alumno= new TAlumnos(id,DNI, nombre, apellido, telefono, edad, activo);
			}
			ps.close();
		}catch (Exception e) {}
		
		return alumno;
	}

	public Collection<TAlumnos> readAll() {
		Collection<TAlumnos> alumnos=new LinkedList<TAlumnos>();
		
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM alumno FOR UPDATE");
		    ResultSet rs=ps.executeQuery();
		    while(rs.next()){
		    	String DNI, nombre, apellido;
				Integer id, edad, telefono;
				boolean activo;
				id=rs.getInt("idalumno");
				DNI=rs.getString("DNI");
				nombre=rs.getString("nombre");
				apellido=rs.getString("apellido");
				edad=rs.getInt("edad");
				telefono=rs.getInt("telefono");
				activo=rs.getBoolean("activo");
				alumnos.add(new TAlumnos(id, DNI, nombre, apellido, telefono, edad, activo));
		    }
		    ps.close();
		    rs.close();
		    
		}catch(Exception e){
		}
		return alumnos;
		
	}
}