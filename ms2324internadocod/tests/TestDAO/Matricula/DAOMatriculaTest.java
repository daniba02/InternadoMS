package TestDAO.Matricula;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Alumnos.DAOAlumnos;
import Integracion.Anio.DAOAnio;
import Integracion.Asignaturas.DAOAsignaturas;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Grupo.DAOGrupo;
import Integracion.Matricula.DAOMatricula;
import Integracion.MatriculaMatriculable.DAOMatriculaMatriculable;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Alumnos.TAlumnos;
import Negocio.Academia.Anio.TAnio;
import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.Academia.Matricula.TMatricula;
import Negocio.Academia.Matriculable.TMatriculable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOMatriculaTest {
	
	private boolean equals(TMatricula a, TMatricula b) {
		return a.getId().equals(b.getId()) && a.getDescuento().equals(b.getDescuento()) 
				&& a.getActivo().equals(b.getActivo() && a.getAbierto().equals(b.getAbierto())
						&& a.getPreciototal().equals(b.getPreciototal()) && a.getIdAlumno().equals(b.getIdAlumno()));
	}

	@Test
	public void A_create() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TAlumnos talumnos = new TAlumnos(1,"23689459Z","Laura","Diez",695789320, 15);
		talumnos.setID(daoAlumnos.create(talumnos));
		TMatricula tmatricula= new TMatricula(0.2,talumnos.getID());
		tmatricula.setId(daoMatricula.create(tmatricula));
		if(tmatricula.getId() < 0){
			fail("No se ha creado correctamente la matricula");
		}
		t.commit();
	}
	@Test
	public void B_delete() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		daoAlumnos.delete(1);
		daoMatricula.delete(1);
		
		TMatricula tM= daoMatricula.read(1);
		
		if(tM.getActivo()){
			fail("No se ha eliminado correctamente la matricula");
		}
		t.commit();
	}
	@Test
	public void C_close() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		TAlumnos talumnos = new TAlumnos("23689459Z","Laura","Diez",695789320, 15);
		talumnos.setID(daoAlumnos.create(talumnos));
		TMatricula tmatricula= new TMatricula(0.2,talumnos.getID());
		tmatricula.setId(daoMatricula.create(tmatricula));
		
		daoMatricula.close(tmatricula.getId());
		
		TMatricula tM= daoMatricula.read(tmatricula.getId());
		
		if(tM.getAbierto()){
			fail("No se ha cerrado correctamente la matricula");
		}
		t.commit();
	}
	@Test
	public void D_read() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TMatricula tmatricula= new TMatricula(1,1,0,0.2);
		TAlumnos talumnos = new TAlumnos(1,"23689459Z","Laura","Diez",695789320, 15);
		daoAlumnos.create(talumnos);
		Integer idM = daoMatricula.create(tmatricula);
		TMatricula tM= daoMatricula.read(idM);
		if(!equals(tmatricula,tM)){
			fail("No se ha leido correctamente la matricula");
		}
		t.commit();
	}
	@Test
	public void E_update() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TMatricula tmatricula= new TMatricula(1,1,0,0.2);
		TAlumnos talumnos = new TAlumnos(1,"23689459Z","Laura","Diez",695789320, 15);
		daoAlumnos.create(talumnos);
		daoMatricula.create(tmatricula);
		
		Integer idM = daoMatricula.update(tmatricula);
		TMatricula tM= daoMatricula.read(idM);
		
		if(!equals(tmatricula,tM)){
			fail("No se ha modificado correctamente la matricula");
		}
		t.commit();
	}
	@Test
	public void F_readAll() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TMatricula tmatricula= new TMatricula(1,1,0,0.2);
		TAlumnos talumnos = new TAlumnos(1,"23689459Z","Laura","Diez",695789320, 15);
		daoAlumnos.create(talumnos);
		daoMatricula.create(tmatricula);
		Collection<TMatricula> lista= daoMatricula.readAll();
		for(TMatricula tM:lista){
			if(!equals(tmatricula,tM)){
				fail("Fallo en la lista");
			}
		}
		t.commit();
	}
	@Test
	public void G_readByAlumno() {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TMatricula tmatricula= new TMatricula(1,1,0,0.2);
		TAlumnos talumnos = new TAlumnos(1,"23689459Z","Laura","Diez",695789320, 15);
		Integer idA=daoAlumnos.create(talumnos);
		daoMatricula.create(tmatricula);
		Collection<TMatricula> lista= daoMatricula.readByAlumno(idA);
		for(TMatricula tM:lista){
			if(!equals(tmatricula,tM)){
				fail("Fallo en la lista");
			}
		}
		t.commit();
	}
	@Test
	public void H_readByMatriculable() throws Exception {
		DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
		DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
		DAOAlumnos daoAlumnos = DAOFactory.getInstance().getDAOAlumnos();
		DAOAnio daoAnio = DAOFactory.getInstance().getDAOAnio();
		DAOAsignaturas daoAsignatura = DAOFactory.getInstance().getDAOAsignaturas();
		DAOGrupo daoGrupo = DAOFactory.getInstance().getDAOGrupo();
		DAOMatriculaMatriculable daoMatriculaMatriculable = DAOFactory.getInstance().getDAOMatriculaMatriculable();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE matriculable");
			ps2.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("TRUNCATE anio");
			ps3.executeUpdate();
			PreparedStatement ps4 = conn.prepareStatement("TRUNCATE grupo");
			ps4.executeUpdate();
			PreparedStatement ps5 = conn.prepareStatement("TRUNCATE asignaturas");
			ps5.executeUpdate();
			PreparedStatement ps6 = conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps6.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		TMatricula tmatricula= new TMatricula(1,1,0,0.2);
		TAnio tanio= new TAnio(1,"2023");
		TGrupo tgrupo = new TGrupo(1,"A",true);
		TObligatoria tasignatura = new TObligatoria(1,true,"Lengua","Humanidades");
		TMatriculable tmatriculable = new TMatriculable(1,100,50, "9:30",1,1,1,true);
		TAlumnos talumnos = new TAlumnos(1,"23689459Z","Laura","Diez",695789320, 15);
		daoAlumnos.create(talumnos);
		Integer idMa = daoMatricula.create(tmatricula);
		
		daoAnio.create(tanio);
		daoGrupo.create(tgrupo);
		daoAsignatura.create(tasignatura);
		Integer idM=daoMatriculable.create(tmatriculable);
		daoMatriculaMatriculable.vincularMatriculaMatriculable(idMa, idM);
		Collection<TMatricula> lista= daoMatricula.readByMatriculable(idM);
		for(TMatricula tM:lista){
			if(!equals(tmatricula,tM)){
				fail("Fallo en la lista");
			}
		}
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
