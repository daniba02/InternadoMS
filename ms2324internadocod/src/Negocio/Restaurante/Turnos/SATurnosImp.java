package Negocio.Restaurante.Turnos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Empleados.Empleado;
import Presentacion.Evento.Evento;

public class SATurnosImp implements SATurnos {

	@Override
	public ResultContext Alta(TTurno turno) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc;
		Query q;

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();

			q = em.createQuery("SELECT t FROM Turnos t WHERE t.nombre =:nombre", Turnos.class);
			q.setParameter("nombre", turno.getNombre());
			Turnos t = null;

			if (!q.getResultList().isEmpty()) {
				t = (Turnos) q.getSingleResult();
				if (t.getActivo())
					throw new TurnoException("Turno: " + turno.getNombre() + " ya existente y activo");

				if ((!t.getHoraEntrada().equals(turno.getHoraEntrada())
						&& !t.getHoraEntrada().equals("0" + turno.getHoraEntrada()))
						|| (!t.getHoraSalida().equals(turno.getHoraSalida())
								&& !t.getHoraSalida().equals("0" + turno.getHoraSalida())))
					throw new TurnoException(
							"Para activar un turno de nuevo las horas de entrada y de salida deben ser las mismas");

				t.setActivo(true);

			} else {
				t = new Turnos(turno);
			}
			em.persist(t);
			et.commit();
			rc = new ResultContext(Evento.AltaTurno_OK, "Turno " + t.getId() + " dado de alta correctamente");

		} catch (TurnoException e) {
			et.rollback();
			rc = new ResultContext(Evento.AltaTurno_KO, e.getMessage());

		} catch (Exception e) {
			et.rollback();
			rc = new ResultContext(Evento.AltaTurno_KO, "Error al dar de alta el turno");

		} finally {
			em.close();
		}
		return rc;
	}

	@Override
	public ResultContext Baja(Integer idTurno) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc;

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();

			Turnos turno = em.find(Turnos.class, idTurno);// LockModeType.OPTIMISTIC

			if (turno == null)
				throw new TurnoException("El turno con id: " + idTurno + " no existe");

			if (!turno.getActivo())
				throw new TurnoException("El turno con ID: " + idTurno + " ya estaba dado de baja");

			for (Empleado e : turno.getEmpleados()) {
				em.lock(e, LockModeType.OPTIMISTIC);

				if (e.getActivo())
					throw new TurnoException("No se puede dar de baja un turno con empleados activos");
			}

			turno.setActivo(false);
			et.commit();
			rc = new ResultContext(Evento.BajaTurno_OK, "Turno con ID: " + idTurno + " dado de baja correctamente");

		} catch (TurnoException e) {
			et.rollback();
			rc = new ResultContext(Evento.BajaTurno_KO, e.getMessage());

		} catch (Exception e) {
			et.rollback();
			rc = new ResultContext(Evento.BajaTurno_KO, "Error al dar de alta el turno");

		} 
		if(em!=null)
			em.close();
		return rc;
	}

	@Override
	public ResultContext Modificar(TTurno turno) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc;
		Query q;
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();

			Turnos t = em.find(Turnos.class, turno.getId());// ,
															// LockModeType.OPTIMISTIC

			if (t == null)
				throw new TurnoException("Turno: " + turno.getId() + " no existente");
			if (!t.getActivo())
				throw new TurnoException("Turno: " + turno.getId() + " no activo");

			q = em.createQuery("SELECT t FROM Turnos t WHERE t.nombre =:nombre", Turnos.class);
			q.setParameter("nombre", turno.getNombre());


			if (!q.getResultList().isEmpty() && !(((Turnos) q.getSingleResult()).getId() == turno.getId()))
				throw new TurnoException("Ya existe un turno con nombre: " + turno.getNombre());

			t.setNombre(turno.getNombre());
			t.setHoraEntrada(turno.getHoraEntrada());
			t.setHoraSalida(turno.getHoraSalida());
			et.commit();
			rc = new ResultContext(Evento.ModificarTurno_OK, "El turno se ha modificado correctamente");

		} catch (TurnoException e) {
			et.rollback();
			rc = new ResultContext(Evento.ModificarTurno_KO, e.getMessage());

		} finally {
			em.close();
		}
		return rc;
	}

	@Override
	public ResultContext Consultar(Integer idTurno) {
		ResultContext rc = null;
		EntityManager em = null;
		try {
			em = FactoriaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			Turnos t = em.find(Turnos.class, idTurno, LockModeType.OPTIMISTIC);
			if (t == null)
				throw new TurnoException("Turno: " + idTurno + " no existente");

			if (!t.getActivo())
				throw new TurnoException("Turno: " + idTurno + " esta inactivo");

			TTurno turno = new TTurno(t.getId(), t.getNombre(), t.getHoraEntrada(), t.getHoraSalida());
			em.getTransaction().commit();
			rc = new ResultContext(Evento.ConsultarTurno_OK, turno);

		} catch (TurnoException e) {
			em.getTransaction().rollback();
			rc = new ResultContext(Evento.ConsultarTurno_KO, e.getMessage());

		} catch (Exception e) {
			em.getTransaction().rollback();
			rc = new ResultContext(Evento.ConsultarTurno_KO, "Error al dar de alta el turno");

		} finally {
			em.close();
		}
		return rc;
	}

	@Override
	public ResultContext Listar() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;

		List<Turnos> listaTurnos = null;
		List<TTurno> lista = new ArrayList<TTurno>();
		TypedQuery<Turnos> query;

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();
			query = em.createNamedQuery("Turnos.readAll", Turnos.class);
			listaTurnos = query.getResultList();
			for (Turnos t : listaTurnos) {
				lista.add(t.toTransfer());

			}

			rc = new ResultContext(Evento.ListarTurnos_OK, lista);
			et.commit();

		} catch (Exception e) {
			rc = new ResultContext(Evento.ListarTurnos_KO, "Error al listar los turnos");
			et.rollback();
		} finally {
			em.close();
		}
		return rc;
	}

}
