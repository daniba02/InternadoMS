package TestDAO.Profesores;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Alumnos.SAAlumnos;
import Negocio.Academia.Alumnos.TAlumnos;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Anio.TAnio;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.Academia.Asignaturas.TOptativa;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.Academia.Profesores.TFijo;
import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAProfesoresTest {

	private boolean equals(TProfesor a, TProfesor b) {
		return a.getId().equals(b.getId()) && a.getDNI().equals(b.getDNI()) && a.getEdad().equals(b.getEdad())
				&& a.getNombreCompleto().equals(b.getNombreCompleto()) && a.getActivo().equals(b.getActivo());
	}

	@SuppressWarnings("unused")
	private boolean equals(TFijo a, TFijo b) {
		return equals((TProfesor) a, (TProfesor) b);
	}

	@SuppressWarnings("unused")
	private boolean equals(TInterino a, TInterino b) {
		return equals((TProfesor) a, (TProfesor) b) && a.getDuracion().equals(b.getDuracion());
	}
	
	private void borrarBBDD(){
		Transaction t = null;

		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps2.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("TRUNCATE alumno");
			ps3.executeUpdate();
			PreparedStatement ps4 = conn.prepareStatement("TRUNCATE anio");
			ps4.executeUpdate();
			PreparedStatement ps5 = conn.prepareStatement("TRUNCATE grupo");
			ps5.executeUpdate();
			PreparedStatement ps6 = conn.prepareStatement("TRUNCATE matricula");
			ps6.executeUpdate();
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE profesor_matriculable");
			ps7.executeUpdate();
			PreparedStatement ps8 = conn.prepareStatement("TRUNCATE profesores");
			ps8.executeUpdate();
			PreparedStatement ps9 = conn.prepareStatement("TRUNCATE profesoresfijos");
			ps9.executeUpdate();
			PreparedStatement ps10 = conn.prepareStatement("TRUNCATE profesoresinterinos");
			ps10.executeUpdate();
			PreparedStatement ps11 = conn.prepareStatement("TRUNCATE matriculable");
			ps11.executeUpdate();
			PreparedStatement ps12 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps12.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void A_create() throws Exception {
		// alta

		Transaction t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			throw new Exception("Error transaccional");
		t.begin();

		borrarBBDD();

		SAProfesores sa = SAFactory.getInstance().getSAProfesores();
		try {
			Evento a = sa.alta(null).getEvento();
			if (a != Evento.AltaProfesor_KO)
				fail("No se puede crear un profesor nulo");

			TFijo fijo = new TFijo(1, "12345678A", 22, "Maria B", true);
			a = sa.alta(fijo).getEvento();
			if (a != Evento.AltaProfesor_OK)
				fail("No creado un profesor fijo no existente previamente");

			TInterino interino = new TInterino(2, "87654321B", 24, "Marco A", true, 12);
			a = sa.alta(interino).getEvento();
			if (a != Evento.AltaProfesor_OK)
				fail("No creado un profesor interino no existente previamente");

			a = sa.alta(fijo).getEvento();
			if (a != Evento.AltaProfesor_KO)
				fail("No se puede dar de alta un profesor que ya existe en la base de datos");

		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
	}

	@Test
	public void B_delete() throws Exception {
		// baja
		Transaction t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			throw new Exception("Error transaccional");
		t.begin();

		borrarBBDD();

		SAProfesores sa = SAFactory.getInstance().getSAProfesores();
		try {

			TFijo fijo = new TFijo(1, "12345678A", 22, "Maria B", true);
			sa.alta(fijo);

			Evento a = sa.baja(null).getEvento();
			if (a != Evento.BajaProfesor_KO)
				fail("Fallo al intentar dar de baja a un id nulo");

			a = sa.baja(12).getEvento();
			if (a != Evento.BajaProfesor_KO)
				fail("Fallo al intentar de baja un id que no existe en la bbdd");

			a = sa.baja(fijo.getId()).getEvento();
			if (a != Evento.BajaProfesor_OK)
				fail("Fallo al dar de baja un profesor correctamente");

			TFijo fijo2 = new TFijo(2, "87654321B", 24, "Marco A", true);
			sa.alta(fijo2);

			sa.baja(fijo2.getId());
			a = sa.baja(fijo2.getId()).getEvento();
			if (a != Evento.BajaProfesor_KO)
				fail("Fallo al haber dado de baja un profesor que estaba inactivo");

		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
	}

	@Test
	public void C_update() throws Exception {
		// modificar
		Transaction t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			throw new Exception("Error transaccional");
		t.begin();

		borrarBBDD();

		SAProfesores sa = SAFactory.getInstance().getSAProfesores();

		try {
			TFijo fijo = new TFijo(1, "12345678A", 22, "Maria B", true);
			sa.alta(fijo);
			fijo.setEdad(23);
			//sa.baja(fijo.getId()); // si lo doy de baja no debería poder modificarlo
			Evento a = sa.modificar(fijo).getEvento();
			if (a != Evento.ModificarProfesor_OK)
				fail("No se ha modificado un profesor con datos correctos y existente en la base de datos: " + a);

			a = sa.modificar(null).getEvento();
			if (a != Evento.ModificarProfesor_KO)
				fail("Profesor nulo no se puede modificar");

			sa.baja(fijo.getId());
			a = sa.modificar(fijo).getEvento();
			if (a != Evento.ModificarProfesor_KO)
				fail("No se puede modificar un profesor inactivo");

//			TFijo fijo2 = new TFijo(fijo.getId(), "11223344S", 13, "Taylor Swift", true);
//			sa.alta(fijo2);
//			a = sa.modificar(fijo2).getEvento();
//			if (a != Evento.ModificarProfesor_KO)
//				fail("No se puede modificar un profesor abierto");

		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
	}

	@Test
	public void D_read() throws Exception {
		// consulta(id)
		borrarBBDD();

		SAProfesores sa = SAFactory.getInstance().getSAProfesores();

		TFijo fijo = new TFijo(1, "12345678A", 22, "Ana", true);
		TFijo fijo2 = new TFijo(2, "00000000A", 24, "Bea", true);
		TInterino interino = new TInterino(3, "87654321B", 24, "Cia", true, 12);

		sa.alta(fijo);
		sa.alta(fijo2);
		sa.alta(interino);

		Evento a = sa.consulta(4).getEvento(); // consulto un profesor que no existe
		Evento b = sa.consulta(fijo.getId()).getEvento();
		Evento c = sa.consulta(interino.getId()).getEvento();

		
		try {
			if (a != Evento.ConsultarProfesor_KO)
				fail("ha devuelto un profesor que no existe");

			if (b != Evento.ConsultarProfesor_OK || c != Evento.ConsultarProfesor_OK)
				fail("no muestra una asignatura que sí existe en la bbdd" + a + " , "+ b);

			if (!sa.consulta(fijo.getId()).getEvento().equals(Evento.ConsultarProfesor_OK))
				fail("devuelve datos incorrectos en una búsqueda correcta");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void E_readAll() throws Exception {
		// listar todos

		borrarBBDD();

		SAProfesores sa = SAFactory.getInstance().getSAProfesores();

		TFijo fijo = new TFijo(1, "12345678A", 22, "Ana", true);
		TFijo fijo2 = new TFijo(2, "00000000A", 24, "Bea", true);
		TInterino interino = new TInterino(3, "87654321B", 24, "Cia", true, 12);

		sa.alta(fijo);
		sa.alta(fijo2);
		sa.alta(interino);

		Collection<TProfesor> profesores = (Collection<TProfesor>) sa.listar().getDato();

		try {
			if (profesores == null) {
				fail("no recibe bien una lista: ha devuelto null");
			}

			if (profesores.size() != 3) {
				fail("fallo de tamaño, debería devolver 3 pero devuelve " + profesores.size());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void F_ListarProfesorInterino() throws Exception {
		// listar interinos

		borrarBBDD();

		SAProfesores sa = SAFactory.getInstance().getSAProfesores();

		TInterino interino = new TInterino(1, "23232323S", 24, "Cia", true, 12);
		sa.alta(interino);
		TInterino interino2 = new TInterino(2, "12121212M", 24, "Viana", true, 12);
		sa.alta(interino2);
		TInterino interino3 = new TInterino(3, "13131313T", 24, "Jodie", true, 12);
		sa.alta(interino3);

		Collection<TInterino> interinos = (Collection<TInterino>) sa.listarProfesorInterino().getDato();

		try {
			if (interinos == null) {
				fail("no recibe bien una lista: ha devuelto null");
			}

			if (interinos.size() != 3) {
				fail("fallo de tamaño, debería devolver 3 pero devuelve " + interinos.size());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void G_ListarProfesorFijo() throws Exception {
		// listar fijos

		borrarBBDD();

		try {
			SAProfesores sa = SAFactory.getInstance().getSAProfesores();

			TFijo fijo = new TFijo(1, "87654321B", 24, "Cia", true);
			sa.alta(fijo);
			TFijo fijo2 = new TFijo(2, "12132448", 23, "Sena", true);
			sa.alta(fijo2);

			Collection<TFijo> fijos = (Collection<TFijo>) sa.listarProfesorFijo().getDato();
			
			if (fijos == null) {
				fail("no recibe bien una lista: ha devuelto null");
			}

			if (fijos.size() != 2) {
				fail("fallo de tamaño, debería devolver 2 pero devuelve " + fijos.size());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void H_ListarProfesorPorMatriculable() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			throw new Exception("Error transaccional");
		t.begin();

		borrarBBDD();
		
		try{
			SAProfesores sa_p = SAFactory.getInstance().getSAProfesores();
			SAMatriculable sa_mat =SAFactory.getInstance().getSAMatriculable();
			
			// linkedlist o arraylist
			Collection<TProfesor> profesores = new ArrayList<TProfesor>();
			
			TInterino interino = new TInterino(1, "23232323S", 24, "Cia", true, 12);
			sa_p.alta(interino);
			TInterino interino2 = new TInterino(2, "12121212M", 24, "Viana", true, 12);
			sa_p.alta(interino2);
			
			profesores.add(interino);
			profesores.add(interino2);
			 
			
			
			Evento profesoresNulo =  sa_p.listarProfesorPorMatriculable(null).getEvento();
			
			if(profesoresNulo != Evento.ListarProfesoresPorMatriculable_KO)
				fail("Error al intentar profesores pasando un id de matriculable nulo");
			TMatriculable matriculable = new TMatriculable(1,12,13,"12:12",1,1,1,true);
			sa_mat.alta(matriculable);
			
			profesores = (Collection<TProfesor>) sa_p.listarProfesorPorMatriculable(matriculable.getId()).getDato();
			if(profesores.size() != 0)
				fail("Error al intentar mostrar profesor(es) cuando aun no se ha(n) vinculado con el matriculable");
//			if(sa_p.listarProfesorPorMatriculable(matriculable.getId()).getEvento() != Evento.ListarProfesoresPorMatriculable_KO)
//				fail("Error al intentar mostrar profesor(es) cuando aun no se ha(n) vinculado con el matriculable");
			// vinculo a matriculable los profesores interino e interino2
			
			
			sa_p.vincularProfesorMatriculable(interino.getId(), matriculable.getId());
			sa_p.vincularProfesorMatriculable(interino2.getId(), matriculable.getId());
			
			profesores = (Collection<TProfesor>) sa_p.listarProfesorPorMatriculable(matriculable.getId()).getDato();
			
			Iterator<TProfesor> itProfesor = profesores.iterator();
			Iterator<TProfesor> itLista = profesores.iterator();
			
			while(itProfesor.hasNext()){
				if(!equals(itProfesor.next(), itLista.next()))
					fail("Fallo en la lista de profesores devuelta por la bbdd");
			}
			t.commit();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void I_VincularProfesorMatriculable() throws Exception {
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t = tm.newTransaction();
		t = tm.getTransaction();
		if (t == null)
			throw new Exception("Error en la transaccion");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE profesores");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE asignaturas");
			ps2.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("TRUNCATE anio");
			ps3.executeUpdate();
			PreparedStatement ps4 = conn.prepareStatement("TRUNCATE grupo");
			ps4.executeUpdate();
			PreparedStatement ps5 = conn.prepareStatement("TRUNCATE profesor_matriculable");
			ps5.executeUpdate();
			PreparedStatement ps6 = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps6.executeUpdate();
			PreparedStatement ps7 = conn.prepareStatement("TRUNCATE profesoresfijos");
			ps7.executeUpdate();
			PreparedStatement ps8 = conn.prepareStatement("TRUNCATE profesoresinterinos");
			ps8.executeUpdate();
			PreparedStatement ps9 = conn.prepareStatement("TRUNCATE matricula");
			ps9.executeUpdate();
			PreparedStatement ps10 = conn.prepareStatement("TRUNCATE matriculable");
			ps10.executeUpdate();
			ps.close();
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
			ps5.close();
			ps6.close();
			ps7.close();
			ps8.close();
			ps9.close();
			ps10.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try{
			SAProfesores saprofesores = SAFactory.getInstance().getSAProfesores();
			SAMatriculable samatriculable = SAFactory.getInstance().getSAMatriculable();
			SAAsignatura saasignatura = SAFactory.getInstance().getSAAsignatura();
			SAAnio saanio = SAFactory.getInstance().getSAAnio();
			SAGrupo sagrupo = SAFactory.getInstance().getSAGrupo();
			SAAlumnos salumno = SAFactory.getInstance().getSAAlumnos();

			TAnio tanio = new TAnio(1, "2023");
			TOptativa tasignatura = new TOptativa(1, true, "Lengua", 3);
			TGrupo tgrupo = new TGrupo(1, "A");
			
			saanio.alta(tanio);
			sagrupo.alta(tgrupo);
			saasignatura.Alta(tasignatura);
			
			TAlumnos a1= new TAlumnos(1,"12345678A","Jodie","King",28,612244896);
			salumno.Alta(a1);
			
			TMatriculable tmatriculable = new TMatriculable(1,123,10,"12:12",tanio.getId(),tasignatura.getID(),tgrupo.getId(),true);
			samatriculable.alta(tmatriculable);
			
			TFijo tprofesor = new TFijo(1, "11223344A", 24, "Paloma", true);
			saprofesores.alta(tprofesor);
			
			Evento a = saprofesores.vincularProfesorMatriculable(tprofesor.getId(), tmatriculable.getId()).getEvento();
			
			if(a != Evento.VincularProfesorMatriculable_OK)
				fail("Fallo al haber intentado vincular profesor con matriculable correctamente");

			a = saprofesores.vincularProfesorMatriculable(null, null).getEvento();
			if (a != Evento.VincularProfesorMatriculable_KO)
				fail("Fallo al vincular con ids nulos");
			
			saprofesores.baja(tprofesor.getId());
			a = saprofesores.vincularProfesorMatriculable(tprofesor.getId(), tmatriculable.getId()).getEvento();
			if(a!=Evento.VincularProfesorMatriculable_KO)
				fail("fallo al vincular un profesor cerrado");	
			
			TMatriculable tmatriculable2 = new TMatriculable(2,123,10,"12:12",1,1,1,true);
			samatriculable.alta(tmatriculable2);
			samatriculable.baja(tmatriculable2.getId());
			a = saprofesores.vincularProfesorMatriculable(tprofesor.getId(), tmatriculable2.getId()).getEvento();
			if(a!=Evento.VincularProfesorMatriculable_KO)
				fail("fallo al vincular un profesor cerrado");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}

	@Test
	public void J_DesvincularProfesorMatriculable() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			throw new Exception("Error de transaccion");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE profesores");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE profesoresfijos");
			ps.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE profesoresinterinos");
			ps.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			PreparedStatement ps4 = conn.prepareStatement("TRUNCATE profesor_matriculable");
			ps2.executeUpdate();
			ps.close();
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try{
			SAProfesores saprofesores = SAFactory.getInstance().getSAProfesores();
			SAMatriculable samatriculable = SAFactory.getInstance().getSAMatriculable();
			
			Evento a = saprofesores.desvincularProfesorMatriculable(null, null).getEvento();
			if(a != Evento.DesvincularProfesorMatriculable_KO)
				fail("Fallo al desvincular un profesor y un matriculable nulos");
			
			a = saprofesores.desvincularProfesorMatriculable(212, 123).getEvento();
			if(a!=Evento.DesvincularProfesorMatriculable_KO)
				fail("Fallo al desvincular un profesor y un matriculable que no existen");
			
			TFijo tprofesor = new TFijo(1, "11223344A", 24, "Paloma", true);
			saprofesores.alta(tprofesor);
			
			TMatriculable tmatriculable = new TMatriculable(1,123,10,"12:12",1,1,1,true);
			samatriculable.alta(tmatriculable);
			tprofesor = new TFijo(26, "11223344A", 24, "Paloma", true);
			saprofesores.alta(tprofesor);
			a = saprofesores.desvincularProfesorMatriculable(tprofesor.getId(), tmatriculable.getId()).getEvento();
			if(a!=Evento.DesvincularProfesorMatriculable_KO)
				fail("Fallo al desvincular un profesor y un matriculable que no estbaan vinculados");
			tprofesor = new TFijo(1, "11223344A", 24, "Paloma", true);
			saprofesores.alta(tprofesor);
			saprofesores.vincularProfesorMatriculable(tprofesor.getId(), tmatriculable.getId()).getEvento();
			a = saprofesores.desvincularProfesorMatriculable(tprofesor.getId(), tmatriculable.getId()).getEvento();
			if(a!= Evento.DesvincularProfesorMatriculable_OK)
				fail("Fallo al desvincular de forma correcta un profesor y un matriculable");
			t.commit();	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}