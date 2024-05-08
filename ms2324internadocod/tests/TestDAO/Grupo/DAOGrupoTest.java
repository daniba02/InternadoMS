package TestDAO.Grupo;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Grupo.DAOGrupo;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Grupo.TGrupo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOGrupoTest {

	private boolean equals(TGrupo a, TGrupo b) {
		return a.getId().equals(b.getId()) && a.getLetra().equals(b.getLetra()) && a.getActivo().equals(b.getActivo());
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
		
		DAOGrupo dao = DAOFactory.getInstance().getDAOGrupo();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();

		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TGrupo g = new TGrupo(1, "A",true);
		Integer id = dao.create(g);

		if (id < 0) {
			fail("No se ha creado el grupo");
		}
		t.commit();
	}
	
	@Test
	public void B_delete() {
		
		DAOGrupo dao = DAOFactory.getInstance().getDAOGrupo();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TGrupo g = new TGrupo(1, "A",true);
		Integer id = dao.create(g);

		if (id < 0) {
			fail("No se ha creado el grupo");
		}
		dao.delete(1);
		
		TGrupo gmod = dao.read(1);
		
		if(gmod.getActivo()){
			fail("No se ha eliminado correctamente el grupo");
		}
		t.commit();
	}
	
	@Test
	public void C_update() {
		
		DAOGrupo dao = DAOFactory.getInstance().getDAOGrupo();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TGrupo g_o = new TGrupo(1, "A",true);
		dao.create(g_o);
		Integer idG_m =  dao.update(g_o);
		TGrupo g_m= dao.read(idG_m);
		
		if(!equals(g_o,g_m)){
			fail("No se ha modificado correctamente el grupo");
		}
		t.commit();
	}
	
	@Test
	public void D_read() {

		DAOGrupo dao = DAOFactory.getInstance().getDAOGrupo();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		TGrupo g1 = new TGrupo(1, "A",true);
		Integer id = dao.create(g1);
		TGrupo g2= dao.read(id);

		if (!equals(g1, g2))
			fail("No se ha leido bien el grupo");
		TGrupo aux= dao.read(-1);
		if(aux!=null)
			fail(" se ha leido bien el grupo con id negativo");
		 aux= dao.read(2);
		if(aux!=null)
			fail(" se ha leido bien el grupo con id no registrado");
		t.commit();
	}
	
}
