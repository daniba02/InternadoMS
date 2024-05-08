package TestDAO.Matriculable;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.DAOFactory.DAOFactory;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.ProfesorMatriculable.DAOProfesorMatriculable;
import Integracion.Profesores.DAOProfesor;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.Academia.Profesores.TProfesor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOMatriculableTests {
	
	private boolean equals(TMatriculable m1, TMatriculable m2) {
		return m1.getId().equals(m2.getId()) && m1.getActivo().equals(m2.getActivo()) && m1.getHora().equals(m2.getHora()) &&
				m1.getPlazas().equals(m2.getPlazas()) && m1.getPrecio().equals(m2.getPrecio());
	}

	@Test
	public void A_create() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		
		Integer id = d_mat.create(m);
		if(id < 0)
			fail("No se ha creado el matriculable");

		t.commit();
	}
	
	@Test
	public void B_read() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		TMatriculable m_aux = d_mat.read(m.getId());
		
		if(!equals(m, m_aux)) 
			fail("No se lee correctamente el matriculable");
		
		t.commit();
	}
	
	@Test
	public void C_update() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		d_mat.update(m);
		
		TMatriculable m_mod = d_mat.read(m.getId());
		
		if(!equals(m, m_mod)) 
			fail("No se modifica correctamente el matriculable");
		
		t.commit();
	}
	
	
	@Test
	public void E_readAll() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		TMatriculable m2 = new TMatriculable(2, 35, 20, "18:30", 2, 2, 2, true);
		
		d_mat.create(m2);
		TMatriculable m3 = new TMatriculable(3, 37, 21, "19:30", 2, 2, 2, true);
		d_mat.create(m3);
		
		Collection<TMatriculable> list = d_mat.readAll();
		
		for(TMatriculable i : list) {
			
			if(i instanceof TMatriculable)
				if(!equals(i, m) && !equals(i, m2) && !equals(i, m3))
					fail("Fallo en la lista de readAll - Matriculable");
		}
		if(list.size() <= 0)
			fail("Fallo");
		
		t.commit();
	}
	
	// No se si profesor es aqui o no (DAO y SA no concuerda)
	
	
	@Test
	public void G_readByGrupo() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m2 = new TMatriculable(2, 35, 20, "18:30", 2, 2, 2, true);
		TMatriculable m3 = new TMatriculable(3, 37, 21, "19:30", 2, 2, 2, true);
		
		Collection<TMatriculable> list = d_mat.listarMatriculablesPorGrupo(2);
		
		for(TMatriculable i : list) {

				if(!equals(i, m2) && !equals(i, m3))
					fail("Fallo en la lista de readAllGrupo - Matriculable");
		}
		
		if(list.size() != 2)
			fail("No se a�aden las listas por grupo necesarias");
		
		t.commit();
	}
	
	@Test
	public void H_readByAsignatura() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m2 = new TMatriculable(2, 35, 20, "18:30", 2, 2, 2, true);
		TMatriculable m3 = new TMatriculable(3, 37, 21, "19:30", 2, 2, 2, true);

		Collection<TMatriculable> list = d_mat.listarMatriculablesPorAsignatura(2);
		
		for(TMatriculable i : list) {
				if(!equals(i, m2) && !equals(i, m3))
					fail("Fallo en la lista de readAllAsignatura - Matriculable");
		}
		  
		if(list.size() != 2)
			fail("No se a�aden las listas por grupo necesarias");
		
		t.commit();
	}
	
	@Test
	public void I_readByAnio() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m2 = new TMatriculable(2, 35, 20, "18:30", 2, 2, 2, true);
		TMatriculable m3 = new TMatriculable(3, 37, 21, "19:30", 2, 2, 2, true);

		Collection<TMatriculable> list = d_mat.listarMatriculablesPorAnio(2);
		
		for(TMatriculable i : list) {
			if(i instanceof TMatriculable)
				if(!equals(i, m2) && !equals(i, m3))
					fail("Fallo en la lista de readAllAnio - Matriculable");
		}
		
		if(list.size() != 2)
			fail("No se a�aden las listas por grupo necesarias");
		
		t.commit();
	}
	
	@Test 
	public void J_delete() throws Exception {
	
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		
		d_mat.delete(m.getId());
		
		TMatriculable m_aux = d_mat.read(m.getId());
		
		if(m_aux.getActivo())
			fail("Matriculable no se ha borrado correctamente");
		
		t.commit();
	}
	
	@Test
	public void K_readByProfesor() throws Exception {
		
		DAOMatriculable d_mat = DAOFactory.getInstance().getDAOMatriculable();
		DAOProfesor d_prof = DAOFactory.getInstance().getDAOProfesor();
		DAOProfesorMatriculable d_profmat = DAOFactory.getInstance().getDAOProfesorMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		TMatriculable m2 = new TMatriculable(2, 35, 20, "18:30", 2, 2, 2, true);
		TMatriculable m3 = new TMatriculable(3, 37, 21, "19:30", 2, 2, 2, true);
		
		TProfesor prof = new TProfesor(1, "12345678X", 20, "Jose", true);
		
		d_mat.create(m);
		d_mat.create(m2);
		d_mat.create(m3);
		
		d_prof.create(prof);
		d_profmat.vincularProfesorMatriculable(prof.getId(), m.getId());
		d_profmat.vincularProfesorMatriculable(prof.getId(), m2.getId());
		d_profmat.vincularProfesorMatriculable(prof.getId(), m3.getId());

		Collection<TMatriculable> list = d_mat.listarmatriculablesporProfesor(prof.getId());

		if(list.size() != 3)
			fail("No se anaden las listas por grupo necesarias");
		
		t.commit();
		
	}
	
	private Transaction crearTransaccion() {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			Transaction t = tm.getTransaction();
			return t;
		} catch (Exception e) {
			fail("Error transaccional");
			return null;
		}
	}

}
