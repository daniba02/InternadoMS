package TestDAO.Matricula_matriculable;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
public class DAOMatriculaMatriculableTest {
	@Test
	public void A_vincularMatriculaMatriculable() throws Exception {
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
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps7.executeUpdate();
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
		int id = daoMatriculaMatriculable.vincularMatriculaMatriculable(idMa, idM);
		if(id < 0){
			fail("No se han vinculado correctamnete");
		}
		
		t.commit();
	}
	@Test
	public void B_desvincularMatriculaMatriculable() throws Exception {
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
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps7.executeUpdate();
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
		int id = daoMatriculaMatriculable.desvincularMatriculaMatriculable(idMa, idM);
		if(id < 0){
			fail("No se han desvinculado correctamnete");
		}
		
		t.commit();
	}
	@Test
	public void D_comprobarVinculacion() throws Exception {
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
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps7.executeUpdate();
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
		int id = daoMatriculaMatriculable.comprobarVinculacion(idM, idMa);
		if(id < 0){
			fail("No se ha comprobado correctamnete");
		}
		daoMatriculaMatriculable.desvincularMatriculaMatriculable(idMa, idM);
		id = daoMatriculaMatriculable.comprobarVinculacion(idM, idMa);
		if(id >= 0){
			fail("No se han desvinculado correctamnete");
		}
		t.commit();
	}
	@Test
	public void E_Calificar() throws Exception{
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
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps7.executeUpdate();
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
		Integer id = daoMatriculaMatriculable.calificar(idMa, idM, 7);
		if(id<0)
			fail("No se califica correctamente");
		t.commit();
	}
	@Test
	public void F_MuestraNota(){
		DAOMatriculaMatriculable daoMatriculaMatriculable = DAOFactory.getInstance().getDAOMatriculaMatriculable();
		Transaction t;

		t = crearTransaccion();
		t.begin();
		Integer id = daoMatriculaMatriculable.muestraNota(1,1);
		if(id<0)
			fail("No se muestra correctamente");
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
