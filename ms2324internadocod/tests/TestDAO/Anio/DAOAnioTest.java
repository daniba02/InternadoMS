package TestDAO.Anio;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Anio.DAOAnio;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Anio.TAnio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOAnioTest {
	
	private boolean equals(TAnio a, TAnio b) {
		return a.getId().equals(b.getId()) && a.getAnio().equals(b.getAnio()) && a.getActivo().equals(b.getActivo());
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
	public void A_create() {
		
		DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();

		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TAnio a = new TAnio(1, "23");
		Integer id = dao.create(a);

		if (id < 0) {
			fail("No se ha creado el año");
		}
		t.commit();
	}
	
	@Test
	public void B_delete() {
		
		DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		dao.delete(1);
		
		TAnio a = dao.read(1);
		
		if(a.getActivo()){
			fail("No se ha eliminado correctamente el año");
		}
		t.commit();
	}
	
	@Test
	public void C_update() {
		
		DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TAnio a_o = new TAnio(1, "23");
		dao.create(a_o);
		Integer idA_m = dao.update(a_o);
		TAnio a_m = dao.read(idA_m);
		
		if(!equals(a_o,a_m)){
			fail("No se ha modificado correctamente el año");
		}
		t.commit();
	}
	
	@Test
	public void D_read() {

		DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		TAnio a1 = new TAnio(1, "23");
		Integer id = dao.create(a1);
		TAnio a2= dao.read(id);

		if (!equals(a1, a2))
			fail("No se ha leido bien el año");
		t.commit();
	}
	
}
