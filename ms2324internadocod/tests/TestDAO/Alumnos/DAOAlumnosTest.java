package TestDAO.Alumnos;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Alumnos.DAOAlumnos;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Alumnos.TAlumnos;

@FixMethodOrder(MethodSorters.JVM)
public class DAOAlumnosTest {
	
	private boolean equals(TAlumnos a, TAlumnos b) {
		return  a.getID().equals(b.getID()) && a.getDNI().equals(b.getDNI()) && a.getNombre().equals(b.getNombre()) &&
				a.getApellido().equals(b.getApellido()) && a.getTelefono().equals(b.getTelefono()) &&
				a.getEdad().equals(b.getEdad()) && a.getActivo().equals(b.getActivo());
	}
	
	private Transaction crearTransaccion() {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			Transaction t = tm.getTransaction();
			return t;
		} catch (Exception e) {
			fail("Error transaccional");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Test
	public void Create() {
		
		DAOAlumnos dao = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		 
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TAlumnos a = new TAlumnos("12345678X", "Jose", "Tortosa", 20, 616616616);
		Integer id = dao.create(a);
		
		if(id < 0)
			fail("No se ha creado el grupo");
		
		t.commit();			
	}

	@Test
	public void Delete() {
		DAOAlumnos dao = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		 
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		TAlumnos a = new TAlumnos("12345678X", "Jose", "Tortosa", 20, 616616616);
		Integer id = dao.create(a);
		
		dao.delete(id);
		
		a = dao.read(id);
		
		if(a.getActivo())
			fail("No se ha eliminado correctamente el dao");
		
		t.commit();
	}

	@Test
	public void Update() {
		
		DAOAlumnos dao = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		 
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String nombre_anterior = "jose";
		
		TAlumnos a = new TAlumnos("12345678X", nombre_anterior, "Tortosa", 20, 616616616);
		
		Integer id = dao.create(a);
		
		a.setID(id);
		a.setNombre("Juan");
		
		dao.update(a);
		
		if(a.getNombre() == nombre_anterior)
		{
			fail("No se ha modificado correctamente la asignatura");
		}
		
		t.commit();
	}
	
	@Test
	public void Read() {
		DAOAlumnos dao = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		 
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TAlumnos a = new TAlumnos("12345678X", "Jose", "Tortosa", 20, 616616616);
		Integer id = dao.create(a);
		a.setID(id);
		
		TAlumnos b = dao.read(id);
		b.setID(id);
		
		if(!equals(a,b))
		{
			fail("No se ha leido correctamente la asignatura por su ID");
		}
		
		t.commit();
	}
	
	
	@Test
	public void ReadByDNI() {
		DAOAlumnos dao = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		 
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String DNI = "12345678X";
		TAlumnos a = new TAlumnos(DNI, "Jose", "Tortosa", 616616616, 20);
		dao.create(a);
		
		TAlumnos b = dao.readByDNI(DNI);
		
		if(a.equals(b))
			fail("No se ha leido correctamente la asignatura por su DNI");
		
		t.commit();
	}
	
	@Test
	public void ReadAll() {
		DAOAlumnos dao = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		 
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TAlumnos a = new TAlumnos("12345678X", "Jose", "Tortosa", 20, 616616616);
		TAlumnos b = new TAlumnos("12345678B", "Juan", "Perez",21, 616616617);
		dao.create(a);
		dao.create(b);
		
		Collection<TAlumnos> Alumnos = dao.readAll();
		
		if(Alumnos.size() != 2)
			fail("No se ha podido hacer una lista de los alumnos");
		
		t.commit();
	}
	
	
}
