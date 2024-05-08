package Negocio.Restaurante.Mesas;

import java.util.ArrayList;
import java.util.Collection;
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
import Negocio.Restaurante.Ventas.Ventas;
import Presentacion.Evento.Evento;

public class SAMesasIMP implements SAMesas {

	
	// ALTA
	@Override
	public ResultContext Alta(TMesas mesa) {
		ResultContext rs = null;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction t = null;
		Query q;

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			t = em.getTransaction();

			t.begin();
			Mesas m;

			m = em.find(Mesas.class, mesa.getID(), LockModeType.OPTIMISTIC);

			if (m == null) {

				Empleado emp = em.find(Empleado.class, mesa.getIDEmpleado(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);

				if (emp == null)
					throw new MesasException("El empleado con id: " + mesa.getIDEmpleado() + " no existe en la BBDD");

				if (!emp.getActivo())
					throw new MesasException("El empleado con id: " + mesa.getIDEmpleado() + " no esta activo");

				q = em.createQuery("SELECT m FROM Mesas m WHERE m.num =:num", Mesas.class);
				q.setParameter("num", mesa.getNum());

				if (!q.getResultList().isEmpty())
					throw new MesasException("Ya existe una mesa con el numero " + mesa.getNum());

				m = new Mesas(mesa);
				em.persist(m);
				t.commit();
				rs = new ResultContext(Evento.AltaMesa_OK, "Mesa " + m.getID() + " dado de alta correctamente");
			} else {
				if (m.getActivo())
					throw new MesasException("La mesa que se esta intentando reactivar ya se encuentra activa");

				rs = new ResultContext(Evento.AltaMesa_OK, "Se ha reactivado la mesa correctamente");
				m.setActivo(true);
				t.commit();
			}

		} catch (MesasException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.AltaMesa_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.AltaMesa_KO, e.getMessage());
		}
		if (em != null) {
			em.close();
		}
		return rs;
	}

	@Override
	public ResultContext Baja(Integer id) {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		TypedQuery<Ventas> q;
		Mesas mesa;

		try {
			t.begin();
			
			
			if (id == null)
				throw new MesasException("ERROR DE BAJA id == null");

			mesa = em.find(Mesas.class, id);// LockModeType.OPTIMISTIC);
			if (mesa == null)
				throw new MesasException("ERROR DE BAJA no existe la mesa con id: " + id);

			if (!mesa.getActivo())
				throw new MesasException("ERROR DE BAJA ya esta inactiva la mesa con id: " + id);

			q = em.createNamedQuery("Negocio.Restaurante.Ventas.Ventas.findByMesa", Ventas.class);
			q.setParameter("mesa", mesa);
			Collection<Ventas> ventas = q.getResultList();

			for (Ventas v : ventas) {
				em.lock(v, LockModeType.OPTIMISTIC);

				if (v.getActivo())
					throw new MesasException("No se puede dar de baja la Mesa con ID: " + id + " con ventas activas");
			}

			rs = new ResultContext(Evento.BajaMesas_OK, "Se ha dado de baja la mesa correctamente");
			mesa.setActivo(false);
			t.commit();
		} catch (MesasException e) {
			// if(t != null && t.isActive())
			t.rollback();
			rs = new ResultContext(Evento.BajaMesas_KO, e.getMessage());

		} catch (Exception e) {
			// if(t != null && t.isActive())
			t.rollback();
			rs = new ResultContext(Evento.BajaMesas_KO, e.getMessage());
		}
		if (em != null) {
			em.close();
		}
		return rs;
	}

	// MODIFICAR
	@Override
	public ResultContext Modificar(TMesas mesa) {

		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		Query q;

		try {
			t.begin();
			if (mesa == null)
				throw new MesasException("ERROR DE MODIFICACION mesa == null");

			Mesas m = em.find(Mesas.class, mesa.getID(), LockModeType.OPTIMISTIC);
			Empleado emp = em.find(Empleado.class, mesa.getIDEmpleado(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (m == null)
				throw new MesasException("ERROR DE MODIFICACION mesa no esta en la BBDD");

			if (emp == null)
				throw new MesasException("ERROR DE MODIFICACION empleado no esta null");

			if (m.getNum() != mesa.getNum())// intenta cambiar el numero
			{
				q = em.createQuery("SELECT m FROM Mesas m WHERE m.num =:num", Mesas.class);
				q.setParameter("num", mesa.getNum());

				if (!q.getResultList().isEmpty())
					throw new MesasException("Ya existe una mesa con el numero" + mesa.getNum());
			}

			m.setNum(mesa.getNum());
			m.setCapacidad(mesa.getCapacidad());
			m.setIDEmpleado(mesa.getIDEmpleado());
			m.setActivo(true);
			t.commit();
			rs = new ResultContext(Evento.ModificarMesa_OK, "Se ha dado modificado la mesa correctamente");

		} catch (MesasException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ModificarMesa_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ModificarMesa_KO, e.getMessage());
		}
		if (em != null) {
			em.close();
		}
		return rs;
	}

	// BUSQUEDA
	@Override
	public ResultContext Consultar(Integer id) {
		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			if (id == null || id < 0)
				throw new MesasException("No se admiten ids nulos o negativos");

			Mesas m = em.find(Mesas.class, id, LockModeType.OPTIMISTIC);
			if (m == null)
				throw new MesasException("No existe la mesa con ID: " + id + " en la BBDD");
			
			if (!m.getActivo())
				throw new MesasException("La mesa con ID: " + id + " esta inactivo");

			TMesas aux = m.toTransfer();
			rs = new ResultContext(Evento.ConsultarMesa_OK, aux);
			t.commit();

		} catch (MesasException e) {
			em.getTransaction().rollback();
			rs = new ResultContext(Evento.ConsultarMesa_KO, "Error al consultar la mesa: " + e.getMessage());

		} catch (Exception e) {
			em.getTransaction().rollback();
			rs = new ResultContext(Evento.ConsultarMesa_KO, "Error al consultar la mesa: " + e.getMessage());

		} finally {
			em.close();
		}
		return rs;
	}

	@Override
	public ResultContext Listar() {
		ResultContext rc;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction transaction = null;
		List<Mesas> mesas = null;
		List<TMesas> list = new ArrayList<TMesas>();

		TypedQuery<Mesas> query;

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			query = em.createNamedQuery("Mesas.readAll", Mesas.class);
			mesas = query.getResultList();

			for (Mesas m : mesas) {
				list.add(m.toTransfer());
			}

			rc = new ResultContext(Evento.ListarMesas_OK, list);
			transaction.commit();

		} catch (Exception e) {
			rc = new ResultContext(Evento.ListarMesas_KO, "Error al listar las mesas" + e.getMessage());
			transaction.rollback();

		} finally {
			em.close();
		}

		return rc;
	}

	@Override
	public ResultContext ListarPorEmpleado(Integer idEmpleado) {
		ResultContext rc;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction transaction = null;
		List<TMesas> list = new ArrayList<TMesas>();

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();

			Empleado e = em.find(Empleado.class, idEmpleado, LockModeType.OPTIMISTIC);
			if (e == null)
				throw new MesasException("No existe el empleado con ID: " + idEmpleado + " en la bd");

			if (!e.getActivo())
				throw new MesasException("El empleado con ID: " + idEmpleado + " esta inactivo");

			for (Mesas m : e.getMesas()) {
				list.add(m.toTransfer());
			}

			rc = new ResultContext(Evento.MesasPorEmpleado_OK, list);
			transaction.commit();

		} catch (MesasException e) {
			rc = new ResultContext(Evento.MesasPorEmpleado_KO, "Error al listar las mesas" + e.getMessage());
			transaction.rollback();

		} catch (Exception e) {
			rc = new ResultContext(Evento.MesasPorEmpleado_KO, "Error al listar las mesas" + e.getMessage());
			transaction.rollback();

		} finally {
			em.close();
		}

		return rc;
	}

}
