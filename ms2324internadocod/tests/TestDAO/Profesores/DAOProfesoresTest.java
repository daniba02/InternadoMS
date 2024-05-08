package TestDAO.Profesores;

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
import Integracion.Profesores.DAOProfesor;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.Academia.Profesores.TFijo;
import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class DAOProfesoresTest {
	
	private boolean equals(TProfesor a, TProfesor b) {
		return a.getId().equals(b.getId()) && a.getNombreCompleto().equals(b.getNombreCompleto())
				&& a.getActivo().equals(b.getActivo()&& a.getDNI().equals(b.getDNI()));
	}

	private boolean equals(TFijo a, TFijo b) {
		return equals((TProfesor) a, (TProfesor) b);
	}

	private boolean equals( TInterino a,  TInterino b) {
		return equals((TProfesor) a, (TProfesor) b) && a.getDuracion()==b.getDuracion();
	}

	@Test
	public void A_create() throws Exception {
		Transaction t;
		t = crearTransaccion();
		t.begin();

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE profesores");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE profesoresfijos");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE profesoresinterinos");
			ps2.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TProfesor o1 = new TFijo(1, "00000000A", 33, "Fernando", true);
		TInterino o2 = new TInterino(2, "00000000B", 22, "Maria", true,60);

		Integer id = dao.create(o1);
		Integer idObl = dao.create(o2);

		if (id < 0) {
			fail("No se ha creado el profesor fijo");
		}
		if (idObl < 0) {
			fail("No se ha creado el profesor interino");
		}

		t.commit();
	}


	@Test
	public void B_read()throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TFijo fijo1 = new TFijo(1, "00000000A", 16, "Fernando", true);
		TInterino int1 = new TInterino(2, "00000000B", 22, "Maria", true,60);
		TFijo fijo2 = (TFijo) dao.read(fijo1.getId());
		TInterino int2 = (TInterino) dao.read(int1.getId());
		

		if (!equals(fijo1, fijo2))
			fail("No se ha leido bien el profesor fijo");
		if (!equals(int1, int2))
			fail("No se ha leido bien el profesor interino");

		t.commit();
	}
	@Test
	public void C_readByDNI()throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			Transaction t;

		t = crearTransaccion();
		t.begin();

		TFijo fijo1 = new TFijo(1, "00000000A", 16, "Fernando", true);
		TInterino int1 = new TInterino(2, "00000000B", 22, "Maria", true,60);
		TFijo fijo2 = (TFijo) dao.readByDNI(fijo1.getDNI());
		TInterino int2 = (TInterino) dao.readByDNI(int1.getDNI());
		
		if (!equals(fijo1, fijo2))
			fail("No se ha leido bien el profesor fijo");
		if (!equals(int1, int2))
			fail("No se ha leido bien el profesor interino");

		t.commit();
	}
	@Test
	public void D_readAll()throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		TFijo fijo1 = new TFijo(1, "00000000A", 33, "Fernando", true);
		TInterino int1 = new TInterino(2, "00000000B", 22, "Maria", true,60);
		Collection<TProfesor> list = dao.readAll();

		for (TProfesor a : list) {

			if (!equals(a, fijo1) && !equals(a, int1))
				fail("Fallo en la lista");
		}
		t.commit();
	}
	
	@Test
	public void E_readFijo()throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		TFijo fijo1 = new TFijo(1, "00000000A", 33, "Fernando", true);
		TFijo fijo2 = new TFijo(3, "00000000D", 56, "Jesus", true);

		

		dao.create(fijo2);
		Collection<TFijo> list = dao.readAllProfesorFijo();

		int cont = 2;

		for (TProfesor a : list) {

			if (!equals(a, fijo1) && !equals(a, fijo2))
				fail("Fallo en la lista de fijos");
		}

		if (cont != list.size())
			fail("Fallo en el tamanio de la lista");

		t.commit();
	}

	@Test
	public void F_readInterino()throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TInterino int1 = new TInterino (2, "00000000B", 22, "Maria", true,60);
		TInterino int2 = new TInterino (4, "00000000E", 44, "Raquel", true,50);
		dao.create(int2);
		Collection<TInterino> list = dao.readAllProfesorInterino();

		int cont = 2;

		for (TProfesor a : list) {

			if (!equals(a, int1) && !equals(a, int2))
				fail("Fallo en la lista de interinos");
		}

		if (cont != list.size())
			fail("Fallo en el tamanio de la lista");

		t.commit();
	}
	
	
	@Test
	public void C_readByMatriculable() throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		TMatriculable tmatriculable = new TMatriculable(1,100,50, "9:30",1,1,1,true);
		TFijo fijo1 = new TFijo(1, "00000000A", 16, "Fernando", true);
		TInterino int1 = new TInterino(2, "00000000B", 22, "Maria", true,60);
		TFijo fijo2 = (TFijo) dao.readByDNI(fijo1.getDNI());
		TInterino int2 = (TInterino) dao.readByDNI(int1.getDNI());
		Integer idM=daoMatriculable.create(tmatriculable);
		
		
		Collection<TProfesor> list=dao.readByMatriculable(idM);
		for(TProfesor tM: list){
			if(!equals(fijo2,tM)){
				fail("Fallo en la lista");
			}
			if(!equals(int2,tM)){
				fail("Fallo en la lista");
			}
		}
		

		t.commit();
	}
	
	
	@Test
	public void G_update()throws Exception {

		DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TFijo fijo1 = new TFijo(1, "00000000A", 16, "Fernando", true);
		TInterino int1 = new TInterino(2, "00000000B", 22, "Maria", true,20);


		dao.update(fijo1);
		dao.update(int1);

		TFijo optMod = (TFijo) dao.read(fijo1.getId());
		TInterino oblMod = (TInterino) dao.read(int1.getId());

		if (!equals(fijo1, optMod))
			fail("No se ha modificado el profesor fijo");
		if (!equals(int1, oblMod))
			fail("No se ha modificado el profesor interino");
		t.commit();
	}

	@Test
	public void H_delete()throws Exception {

		DAOProfesor daoProfesor = DAOFactory.getInstance().getDAOProfesor();
		Transaction t;

		t = crearTransaccion();
		t.begin();

		TFijo fijo1 = new TFijo(1, "00000000A", 16, "Fernando", true);
		TInterino int1 = new TInterino(2, "00000000B", 22, "Maria", true,20);
		

		daoProfesor.delete(fijo1.getId());
		daoProfesor.delete(int1.getId());

		TFijo fijoMod = (TFijo) daoProfesor.read(fijo1.getId());
		TInterino intMod = (TInterino) daoProfesor.read(int1.getId());

		if (fijoMod.getActivo())
			fail("No se ha eliminado el profesor fijo");
	if (intMod.getActivo())
			fail("No se ha eliminado el profesor interino");
		t.commit();
	}

	private Transaction crearTransaccion() {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			Transaction t = tm.getTransaction();
			return t;
		} catch (Exception e) {
			fail("Error transaccion");
			System.out.println(e.getMessage());
			return null;
		}
	}
}
