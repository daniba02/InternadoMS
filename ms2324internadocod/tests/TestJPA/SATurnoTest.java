package TestJPA;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Turnos.SATurnos;
import Negocio.Restaurante.Turnos.TTurno;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SATurnoTest {

	EntityManagerFactory emf = null;
	EntityManager em = null;
	EntityTransaction et = null;

	@Test
	public void A_alta() {

		emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		em = emf.createEntityManager();
		et = em.getTransaction();

		// Borra la tabla y establecemos el AUTO_INCREMENT a 1
		et.begin();
		em.createQuery("DELETE FROM Mesas t").executeUpdate();
		em.createNativeQuery("ALTER TABLE Mesas AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Empleado t").executeUpdate();
		em.createNativeQuery("ALTER TABLE Empleado AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Turnos t").executeUpdate();
		em.createNativeQuery("ALTER TABLE Turnos AUTO_INCREMENT = 1").executeUpdate();
		et.commit();

		SATurnos sa_turno = SAFactory.getInstance().getSATurnos();

		TTurno a = new TTurno(1, "Tarde", "10:00", "12:00");
		Integer id = a.getId();
		Evento id_evento;
		try {

			// creo el turno en la base de datos
			id_evento = sa_turno.Alta(a).getEvento();

			// compruebo si lo he añadido directamente
			if (id_evento != Evento.AltaTurno_OK)
				fail("No se ha creado el nuevo turno");

			// doy de baja a ver si se puede
			id_evento = sa_turno.Baja(id).getEvento();
			if (id_evento != Evento.BajaTurno_OK)
				fail("No se ha dado de baja correctamente el turno");

			// Intento dar de alta un turno dado de alta, pero inactivo
			id_evento = sa_turno.Alta(a).getEvento();
			if (id_evento != Evento.AltaTurno_OK)
				fail("El turno no fue dado de alta correctamente anteriormente");

			// Pruebo a a�adir un turno ya existente y activo
			id_evento = sa_turno.Alta(a).getEvento();
			if (id_evento != Evento.AltaTurno_KO)
				fail("Se ha dado de alta un turno ya existente y activo");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void B_baja() {
		SATurnos sa_turno = SAFactory.getInstance().getSATurnos();

		TTurno a = new TTurno(2, "Noche", "10:00", "12:00");
		Integer id = a.getId();
		Evento id_evento;
		try {
			// intento dar de baja un turno que no existe
			id_evento = sa_turno.Baja(2).getEvento();
			if (id_evento != Evento.BajaTurno_KO)
				fail("Se ha intentado dar de baja un turno inexistente");

			// añado a la base de datos el turno
			id_evento = sa_turno.Alta(a).getEvento();
			// compruebo si se ha anadido
			if (id_evento != Evento.AltaTurno_OK)
				fail("No se ha dado de alta el turno");

			Evento id2 = sa_turno.Baja(-2).getEvento();
			if (id2 != Evento.BajaTurno_KO)
				fail("Se ha dado de baja algo inexistente");

			id_evento = sa_turno.Baja(id).getEvento();
			if (id_evento != Evento.BajaTurno_OK)
				fail("No se ha dado de baja correctamente el turno");

			id_evento = sa_turno.Baja(id).getEvento();
			if (id_evento != Evento.BajaTurno_KO)
				fail("Se ha dado de baja un turno que ya estaba dado de baja");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void C_read() {
		SATurnos sa_turnos = SAFactory.getInstance().getSATurnos();
		ResultContext rc;
		try {
			rc = sa_turnos.Consultar(-1);
			if (rc.getEvento() == Evento.ConsultarTurno_OK)
				fail("Se ha consultado un turno con un id inexistente");

			rc = sa_turnos.Consultar(3);
			if (rc.getEvento() == Evento.ConsultarTurno_OK)
				fail("Se ha consultado un turno con un id inexistente");

			rc = sa_turnos.Consultar(1);
			if (rc.getEvento() != Evento.ConsultarTurno_OK)
				fail("Se ha producido un error al intentar leer un turno existente");
		} catch (Exception e) {
			fail("Ha ocurrido un error" + e.getMessage());
		}
	}

	@Test
	public void D_modificar() {
		ResultContext rc;

		SATurnos sa_turnos = SAFactory.getInstance().getSATurnos();

		try {

			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();

			TTurno aux2 = new TTurno(1, "mañana", "9:00", "16:00");
			rc = sa_turnos.Modificar(aux2);
			if (rc.getEvento() != Evento.ModificarTurno_OK)
				fail("Error al intentar modificar una venta correctamente");

			aux2 = new TTurno(9, "mañana", "8:00", "15:00");
			rc = sa_turnos.Modificar(aux2);
			if (rc.getEvento() != Evento.ModificarTurno_KO)
				fail("Se ha intentado modificar un turno con id inexistente");

		} catch (Exception e) {
			fail("Ha ocurrido un error" + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void E_listar() {
		SATurnos sa_turnos = SAFactory.getInstance().getSATurnos();
		List<TTurno> listaTurnos = new ArrayList<TTurno>();
		ResultContext rc;

		try {

			rc = sa_turnos.Listar();
			if (rc.getEvento() == Evento.ListarTurnos_KO)
				fail("Ha ocurrido un error al listar");
			listaTurnos = (List<TTurno>) rc.getDato();
			int turnos = listaTurnos.size();
			if (turnos == 0)
				fail("No se han listado turnos");
			if (listaTurnos.size() != 2)
				fail("No se han listado los dos turnos");

			rc = sa_turnos.Baja(1);
			if (rc.getEvento() != Evento.BajaTurno_OK)
				fail("Error al dar de baja el turno");

			rc = sa_turnos.Listar();
			if (rc.getEvento() == Evento.ListarTurnos_KO)
				fail("Ha ocurrido un error al listar");
			listaTurnos = (List<TTurno>) rc.getDato();
			if (listaTurnos.size() == 0)
				fail("No se han listado turnos");
			if (listaTurnos.size() != 2)
				fail("No se han listado turnos inactivos");

		} catch (Exception e) {
			fail("Ha ocurrido un error" + e.getMessage());
		}

	}

}
