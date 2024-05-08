package TestDAO.Alumnos;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

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
import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.Academia.Matricula.SAMatricula;
import Negocio.Academia.Matricula.TMatricula;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAAlumnostest {

	private boolean equals(TAlumnos a, TAlumnos b) {
		return a.getID().equals(b.getID()) && a.getDNI().equals(b.getDNI()) && a.getNombre().equals(b.getNombre())
				&& a.getApellido().equals(b.getApellido()) && a.getTelefono().equals(b.getTelefono())
				&& a.getEdad().equals(b.getEdad()) && a.getActivo().equals(b.getActivo());
	}

	
	@Test
	public void A_alta() {
		
		Transaction t = null;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();
		
		TAlumnos a = new TAlumnos(1, "89742312A", "Pedro", "Romero", 13, 654789321);
		Integer id=a.getID();
		Evento id_evento;
		try {
			
			
			// creo el alumno en la base de datos
			id_evento = sa_alumn.Alta(a).getEvento();
			
			// compruebo si lo he añadido directamente
			if(id_evento!=Evento.AltaAlumnos_OK)
				fail("No se ha creado el nuevo alumno");

			

			//doy de baja a ver si se puede
			id_evento=sa_alumn.Baja(id).getEvento();
			if(id_evento!=Evento.BajaAlumnos_OK)
				fail("No se ha dado de baja correctamente el alumno");

			
			
			//Intento dar de alta un alumno dado de alta, pero inactivo
			id_evento=sa_alumn.Alta(a).getEvento();
			if(id_evento!=Evento.AltaAlumnos_OK)
				fail("El alumno no fue dado de alta correctamente anteriormente");
			
			//Pruebo a añadir un alumno ya existente y activo
			id_evento=sa_alumn.Alta(a).getEvento();
			if(id_evento!=Evento.AltaAlumnos_KO)
				fail("Se ha dado de alta un alumno ya existente y activo");
			 
			

			// Ahora diferentes casos para ver si falla
			TAlumnos a2 = new TAlumnos(2, "1234V", "Cristiano", "Ronaldo", 14, 654987321);
			//Integer id2=a2.getID();
			
			id_evento = sa_alumn.Alta(a2).getEvento();
			if (id_evento != Evento.AltaAlumnos_KO)
				fail("Se ha dado de alta correctamente con un DNI con un formato incorrecto");

			a2 = new TAlumnos(2, "123456789", "Cristiano", "Ronaldo", 14, 654987321);
			id_evento = sa_alumn.Alta(a2).getEvento();
			if (id_evento != Evento.AltaAlumnos_KO)
				fail("Se ha dado de alta correctamente con un DNI con todo numeros");

			a2 = new TAlumnos(2, "45612378B", "Cristiano", "Ronaldo", 14, 987);
			id_evento = sa_alumn.Alta(a2).getEvento();
			if (id_evento != Evento.AltaAlumnos_KO)
				fail("Se ha dado de alta correctamente con un telefono con un formato incorrecto");

			a2 = new TAlumnos(2, "45612378B", "Cristiano", "Ronaldo", -5, 654789123);
			id_evento = sa_alumn.Alta(a2).getEvento();
			if (id_evento != Evento.AltaAlumnos_KO)
				fail("Se ha dado de alta correctamente con una edad negativa");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.commit();
	}

	@Test
	public void B_baja() {
		Transaction t = null;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();

	
		
		TAlumnos a = new TAlumnos(1, "89742312A", "Pedro", "Romero", 13, 654789321);
		Integer id=a.getID();
		Evento id_evento;
		try {
			// intento dar de baja un alumno que no existe
			id_evento = sa_alumn.Baja(id).getEvento();
			if (id_evento != Evento.BajaAlumnos_KO)
				fail("Se ha intentado dar de baja un alumno inexistente");

			// añado a la base de datos el alumno
			id_evento = sa_alumn.Alta(a).getEvento();
			// compruebo si se ha añadido
			if (id_evento !=Evento.AltaAlumnos_OK)
				fail("No se ha dado de alta el alumno");

			Evento id2 = sa_alumn.Baja(-2).getEvento();
			if (id2 != Evento.BajaAlumnos_KO)
				fail("Se ha dado de baja algo inexistente");

			id_evento = sa_alumn.Baja(id).getEvento();
			if (id_evento != Evento.BajaAlumnos_OK)
				fail("No se ha dado de baja correctamente el alumno");

			id_evento = sa_alumn.Baja(id).getEvento();
			if (id_evento != Evento.BajaAlumnos_KO)
				fail("Se ha dado de baja un alumno que ya estaba dado de baja");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.commit();

	}
	
	@Test
	public void C_read() {
		
		Transaction t = null;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();

	
		
		TAlumnos a = new TAlumnos(1, "89742312A", "Pedro", "Romero", 13, 654789321);
		Integer id=a.getID();
		Evento id_evento;
		try {
			
			//intento consultar un alumno que no esta dado de alta en la base de datos
			id_evento=sa_alumn.Consulta(id).getEvento();
			if(id_evento!=Evento.ConsultarAlumnos_KO)
				fail("Se ha consultado un alumno que no existe en la base de datos");
			
			//intento consultar un alumno con un id<0
			id_evento= sa_alumn.Consulta(-1).getEvento();
			if(id_evento!=Evento.ConsultarAlumnos_KO)
				fail("Se ha consultado un alumno que no existe en la base de datos");
				
			//doy de alta al alumno
			id_evento=sa_alumn.Alta(a).getEvento();
			if(id_evento!=Evento.AltaAlumnos_OK)
				fail("No se ha creado al alumno");
			
		
			//doy de baja el alumno, es decir, pasa a estar inactivo
			id_evento=sa_alumn.Baja(id).getEvento();
			if(id_evento==Evento.BajaAlumnos_KO)
				fail("No se ha dado de baja correctamente el alumno");
			
			//intento consultar un alumno que está inactivo
			id_evento=sa_alumn.Consulta(id).getEvento();
			if(id_evento!=Evento.ConsultarAlumnos_KO)
				fail("Se ha consultado un alumno no activo");
		
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.commit();
		
	}

	@Test
	public void D_modificar() {
		Transaction t = null;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();
		
		TAlumnos a = new TAlumnos(1, "89742312A", "Pedro", "Romero", 13, 654789321);
		Integer id=a.getID();
		Evento id_evento;
		try {

			// pruebo a modificar un alumno que no esta dado de alta en la base
			// de datos
			id_evento = sa_alumn.Modificar(a).getEvento();
			if (id_evento != Evento.ModificarAlumnos_KO)
				fail("Se ha modificado un alumno que no existe");

			// intento modificar un alumno nulo
			id_evento = sa_alumn.Modificar(null).getEvento();
			if (id_evento != Evento.ModificarAlumnos_KO)
				fail("Se ha modificado un alumno que no existe");

			// añado alumno a la base de datos
			id_evento = sa_alumn.Alta(a).getEvento();
			if (id_evento != Evento.AltaAlumnos_OK)
				fail("No se ha creado el alumno");

			// pruebo a modificar el alumno
			TAlumnos aux1 = new TAlumnos(1, "89742312B", "Pedro", "Romero", 15, 321654987);
			id_evento = sa_alumn.Modificar(aux1).getEvento();
			if (id_evento != Evento.ModificarAlumnos_OK)
				fail("No se ha modificado el alumno");

			TAlumnos aux2 = (TAlumnos) sa_alumn.Consulta(1).getDato();
			if (!equals(aux1, aux2))
				fail("No se ha modificado correctamente el alumno");

			id_evento = sa_alumn.Baja(id).getEvento();
			if (id_evento == Evento.BajaAlumnos_KO)
				fail("No se ha dado de baja correctamente el alumno");

			id_evento = sa_alumn.Modificar(aux1).getEvento();
			if (id_evento != Evento.ModificarAlumnos_KO)
				fail("Se ha modificado un alumno dado de baja");

			// AHORA DIFERENTES CASOS CON EL DNI, EL TELEFONO Y LA EDAD CON
			// FORMATOS NO VALIDOS
			aux1 = new TAlumnos(1, "1234V", "Pedro", "Romero", 15, 321654987);
			id_evento = sa_alumn.Modificar(aux1).getEvento();
			if (id_evento != Evento.ModificarAlumnos_KO)
				fail("Se ha modificado el alumno con un DNI en un formato incorrecto");

			aux1 = new TAlumnos(1, "89742312A", "Pedro", "Romero", 15, 655);
			id_evento = sa_alumn.Modificar(aux1).getEvento();
			if (id_evento != Evento.ModificarAlumnos_KO)
				fail("Se ha modificado el alumno con un número de teléfono con un formato incorrecto");

			aux1 = new TAlumnos(1, "89742312A", "Pedro", "Romero", -30, 321654987);
			id_evento = sa_alumn.Modificar(aux1).getEvento();
			if (id_evento != Evento.ModificarAlumnos_KO)
				fail("Se ha modificado el alumno con una edad negativa");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t.commit();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void E_listar() {
		Transaction t = null;
		
		t = crearTransaccion();
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();

		TAlumnos a1 = new TAlumnos(1, "12345678V", "Cristiano", "Ronaldo", 14, 654987321);
		TAlumnos a2 = new TAlumnos(2, "89742312A", "Pedro", "Romero", 15, 321654987);
		Collection<TAlumnos> alumnos = new LinkedList<TAlumnos>();
		Evento id;
		try {
			id = sa_alumn.Listar().getEvento();
			if (id == Evento.AltaAlumnos_KO)
				fail("Se han listado alumnos inexistentes");

			id = sa_alumn.Alta(a1).getEvento();
			if (id == Evento.AltaAlumnos_KO)
				fail("No se ha dado de alta el alumno");

			alumnos = (Collection<TAlumnos>) sa_alumn.Listar().getDato();
			if (alumnos == null || alumnos.size() != 1 )
				fail("No se han listado correctamente los alumnos");

			id = sa_alumn.Alta(a2).getEvento();
			if (id == Evento.AltaAlumnos_KO)
				fail("No se ha dado de alta el alumno");

			alumnos = (Collection<TAlumnos>) sa_alumn.Listar().getDato();
			if (alumnos == null || alumnos.size() != 2)
				fail("No se han listado correctamente los alumnos");

			id = sa_alumn.Baja(1).getEvento();
			if (id == Evento.BajaAlumnos_KO)
				fail("No se ha dado de baja correctamente el alumno");

			alumnos = (Collection<TAlumnos>) sa_alumn.Listar().getDato();
			if (alumnos == null || alumnos.size() != 2)
				fail("No se han listado correctamente los alumnos");

			id = sa_alumn.Baja(2).getEvento();
			if (id ==Evento.BajaAlumnos_KO)
				fail("No se ha dado de baja correctamente el alumno");

			id = sa_alumn.Listar().getEvento();
			if (id == Evento.AltaAlumnos_KO)
				fail("Se han listado alumnos inexistentes");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.commit();

	}
	
	@Test
	public void F_ListarNotasAlumnosTest() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();
		SAMatricula sa_matr=SAFactory.getInstance().getSAMatricula();
		SAMatriculable sa_mat=SAFactory.getInstance().getSAMatriculable();
		SAAnio sa_anio=SAFactory.getInstance().getSAAnio();
		SAGrupo sa_grupo=SAFactory.getInstance().getSAGrupo();
		SAAsignatura sa_asig=SAFactory.getInstance().getSAAsignatura();
		TAlumnos a1 = new TAlumnos(1, "12345678V", "Cristiano", "Ronaldo", 14, 654987321);
		TMatricula m=new TMatricula(1,1,0,0.2);
		TMatriculable m1 = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		TMatriculable m2 = new TMatriculable(2, 41, 12, "17:30", 1, 1, 1, true);
		TAnio an1=new TAnio(1,"2003");
		TGrupo g1=new TGrupo(1,"A");
		TObligatoria o2 = new TObligatoria(1, true, "Lengua", "Mola");

		try{
			sa_alumn.Alta(a1);
			sa_matr.abrir(m);
			sa_anio.alta(an1);
			sa_grupo.alta(g1);
			sa_asig.Alta(o2);
			sa_mat.alta(m1);
			sa_mat.alta(m2);
			
			Evento a=sa_alumn.ListarNotasAlumno(null).getEvento();
			if(a!=Evento.ListarNotasAlumnos_KO)
				fail("No se permiten ids nulos");
			
			a=sa_alumn.ListarNotasAlumno(2).getEvento();
			if(a!=Evento.ListarNotasAlumnos_KO)
				fail("No se pueden listar las notas de un alumno que no existe en la BBDD");
			
			a=sa_alumn.ListarNotasAlumno(1).getEvento();
			if(a!=Evento.ListarNotasAlumnos_KO)
				fail("No se pueden listar las notas de un alumno que no tiene vinculada ninguna matricula vinculada");
			
			sa_matr.vincular(1, 1);
			sa_matr.vincular(1, 2);
			
			a=sa_alumn.ListarNotasAlumno(1).getEvento();
			if(a!=Evento.ListarNotasAlumnos_KO)
				fail("Se han listado notas que no deberian");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
		
	}
	
	@Test
	public void G_AlumnosMatriculadosAnio() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();
		SAMatricula sa_matr=SAFactory.getInstance().getSAMatricula();
		SAMatriculable sa_mat=SAFactory.getInstance().getSAMatriculable();
		SAAnio sa_anio=SAFactory.getInstance().getSAAnio();
		SAGrupo sa_grupo=SAFactory.getInstance().getSAGrupo();
		SAAsignatura sa_asig=SAFactory.getInstance().getSAAsignatura();
		TAlumnos a1 = new TAlumnos(1, "12345678V", "Cristiano", "Ronaldo", 14, 654987321);
		TMatricula m=new TMatricula(1,1,0,0.2);
		TMatriculable m1 = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		TAnio an1=new TAnio(1,"2003");
		TGrupo g1=new TGrupo(1,"A");
		TObligatoria o2 = new TObligatoria(1, true, "Lengua", "Mola");
		try{
			sa_alumn.Alta(a1);
			sa_matr.abrir(m);
			sa_anio.alta(an1);
			sa_grupo.alta(g1);
			sa_asig.Alta(o2);
			
			Evento a=sa_alumn.AlumnoAnio(null).getEvento();
			if(a!=Evento.AlumnoANIO_KO)
				fail("No se permiten anios nulos");
			
			a=sa_alumn.AlumnoAnio(1).getEvento();
			if(a!=Evento.AlumnoANIO_KO)
				fail("No se pueden listar los alumnos de un anio que no existe en la BBDD");
			
			a=sa_alumn.AlumnoAnio(2).getEvento();
			if(a!=Evento.AlumnoANIO_KO)
				fail("No se pueden listar los alumnos de un anio que no esta vinculado a ningun matriculable");
			
			sa_mat.alta(m1);
			sa_matr.vincular(1, 1);
			
			a=sa_alumn.AlumnoAnio(1).getEvento();
			if(a==Evento.AlumnoANIO_KO)
				fail("No se han listado las alumnos correctamente");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
	
	private Transaction crearTransaccion() {
		try {
			Transaction t = TransactionManager.getInstance().newTransaction();
			return t;
		} catch (Exception e) {
			fail("Error transaccional");
			return null;
		}
	}


}
