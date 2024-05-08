package TestDAO.Asignaturas;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Asignaturas.DAOAsignaturas;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Asignaturas.TOptativa;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOAsignaturasTest {

	private boolean equals(TAsignaturas a, TAsignaturas b) {
		return a.getID().equals(b.getID()) && a.getNombre().equals(b.getNombre())
				&& a.getActivo().equals(b.getActivo());
	}

	private boolean equals(TObligatoria a, TObligatoria b) {
		return equals((TAsignaturas) a, (TAsignaturas) b) && a.getItinerario().equals(b.getItinerario());
	}

	private boolean equals(TOptativa a, TOptativa b) {
		return equals((TAsignaturas) a, (TAsignaturas) b) && a.getNivel() == b.getNivel();
	}

	@Test
	public void A_create() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TOptativa o1 = new TOptativa(1, true, "Mates", 2);
		TObligatoria o2 = new TObligatoria(2, true, "Lengua", "Mola");

		Integer id = dao.create(o1);
		Integer idObl = dao.create(o2);

		if (id < 0) {
			fail("No se ha creado la asignatura optativa");
		}
		if (idObl < 0) {
			fail("No se ha creado la asignatura obligatoria");
		}

		t.commit();
	}

	@Test
	public void B_read() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TOptativa opt1 = new TOptativa(1, true, "Mates", 2);
		TObligatoria obl1 = new TObligatoria(2, true, "Lengua", "Mola");

		TOptativa opt2 = (TOptativa) dao.read(opt1.getID());
		TObligatoria obl2 = (TObligatoria) dao.read(obl1.getID());

		if (!equals(opt1, opt2))
			fail("No se ha leido bien la optativa");
		if (!equals(obl1, obl2))
			fail("No se ha leido bien la obligatoria");

		t.commit();
	}
	
	@Test
	public void C_readByName() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TOptativa opt1 = new TOptativa(1, true, "Mates", 2);
		TObligatoria obl1 = new TObligatoria(2, true, "Lengua", "Mola");

		TOptativa opt2 = (TOptativa) dao.readByName(opt1.getNombre());
		TObligatoria obl2 = (TObligatoria) dao.readByName(obl1.getNombre());

		if (!equals(opt1, opt2))
			fail("No se ha leido bien la optativa");
		if (!equals(obl1, obl2))
			fail("No se ha leido bien la obligatoria");

		t.commit();
	}

	@Test
	public void D_readAll() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TOptativa opt1 = new TOptativa(1, true, "Mates", 2);
		TObligatoria obl1 = new TObligatoria(2, true, "Lengua", "Mola");

		ArrayList<TAsignaturas> list = dao.readAll();

		for (TAsignaturas a : list) {

			if (!equals(a, opt1) && !equals(a, obl1))
				fail("Fallo en la lista");
		}
		t.commit();
	}

	@Test
	public void E_readOptativa() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TOptativa opt1 = new TOptativa(1, true, "Mates", 2);
		TOptativa opt2 = new TOptativa(3, true, "Ingles", 4);

		dao.create(opt2);
		ArrayList<TOptativa> list = dao.readAllOptativas();

		int cont = 2;

		for (TAsignaturas a : list) {

			if (!equals(a, opt1) && !equals(a, opt2))
				fail("Fallo en la lista de optativas");
		}

		if (cont != list.size())
			fail("Fallo en el tamanio de la lista");

		t.commit();
	}

	@Test
	public void F_readObligatorias() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TObligatoria obl1 = new TObligatoria(2, true, "Lengua", "Mola");
		TObligatoria obl2 = new TObligatoria(4, true, "Frances", "Aburrimiento");

		dao.create(obl2);
		ArrayList<TObligatoria> list = dao.readAllObligatorias();

		int cont = 2;

		for (TAsignaturas a : list) {

			if (!equals(a, obl1) && !equals(a, obl2))
				fail("Fallo en la lista de optativas");
		}

		if (cont != list.size())
			fail("Fallo en el tamanio de la lista");

		t.commit();
	}

	@Test
	public void G_update() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TOptativa opt1 = new TOptativa(1, true, "EF", 5);
		TObligatoria obl1 = new TObligatoria(2, true, "Biologia", "Bueno");

		dao.update(opt1);
		dao.update(obl1);

		TOptativa optMod = (TOptativa) dao.read(opt1.getID());
		TObligatoria oblMod = (TObligatoria) dao.read(obl1.getID());

		if (!equals(opt1, optMod))
			fail("No se ha modificado la optativa");
		if (!equals(obl1, oblMod))
			fail("No se ha modificado la obligatoria");
		t.commit();
	}

	@Test
	public void H_delete() {

		DAOAsignaturas dao = DAOFactory.getInstance().getDAOAsignaturas();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TOptativa opt1 = new TOptativa(1, true, "EF", 5);
		TObligatoria obl1 = new TObligatoria(2, true, "Biologia", "Bueno");

		dao.delete(opt1.getID());
		dao.delete(obl1.getID());

		TOptativa optMod = (TOptativa) dao.read(opt1.getID());
		TObligatoria oblMod = (TObligatoria) dao.read(obl1.getID());

		if (optMod.getActivo())
			fail("No se ha eliminado la optativa");
		if (oblMod.getActivo())
			fail("No se ha eliminado la obligatoria");
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
			System.out.println(e.getMessage());
			return null;
		}
	}
}
