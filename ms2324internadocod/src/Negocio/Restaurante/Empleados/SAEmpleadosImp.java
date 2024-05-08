package Negocio.Restaurante.Empleados;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Mesas.Mesas;
import Negocio.Restaurante.Turnos.Turnos;
import Presentacion.Evento.Evento;

public class SAEmpleadosImp implements SAEmpleados {

	public ResultContext altaEmpleado(TEmpleado empleado) {
		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			Empleado emp;

			if (empleado == null)
				throw new EmpleadoException("El empleado no puede ser nulo");

			if (empleado.getId() == -1) {

				Turnos turno = em.find(Turnos.class, empleado.getTurno(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (turno == null)
					throw new EmpleadoException("El turno seleccionado no existe en la base de datos");

				if (!turno.getActivo())
					throw new EmpleadoException("El turno seleccionado esta inactivo");
				
				String[] partes = turno.getHoraSalida().split(":");
				int duracion = Integer.parseInt(partes[0]) * 60;
				duracion += Integer.parseInt(partes[1]);
				
				String[] partes2 = turno.getHoraEntrada().split(":");
				duracion -= Integer.parseInt(partes2[0]) * 60;
				duracion -= Integer.parseInt(partes2[1]);
				
				if(empleado.getHoras() * 60 > duracion)
					throw new EmpleadoException("Las horas del empleado no pueden ser mayores a las del turno");
				
				
				if (empleado instanceof TCocinero) {
					emp = new Cocinero(empleado);
				} else {
					emp = new Camarero(empleado);
				}
				emp.setTurno(turno);
				emp.setMesas(new ArrayList<Mesas>());
				emp.setId(-1);
				turno.getEmpleados().add(emp);
				em.persist(emp);
				t.commit();
				rs = new ResultContext(Evento.AltaEmpleados_OK,
						"Se ha dado de alta el empleado correctamente con id " + emp.getId());
			
			} else {
				emp = em.find(Empleado.class, empleado.getId());// LockModeType.OPTIMISTIC

				if (emp == null)
					throw new EmpleadoException(
							"No existe ningun empleado con el ID: " + empleado.getId() + " en la BBDD");
				if (emp.getActivo())
					throw new EmpleadoException("El empleado ya esta activo en la BBDD");

				emp.setActivo(true);
				rs = new ResultContext(Evento.AltaEmpleados_OK,
						"Se ha dado de alta al empleado con ID: " + empleado.getId());
				t.commit();
			}
		} catch (EmpleadoException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.AltaEmpleados_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.AltaEmpleados_KO, "Ha sucedido un error: " + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;

	}

	public ResultContext bajaEmpleado(Integer id) {

		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			t.begin();
			if (id == null)
				throw new EmpleadoException("No se admiten ids nulos");

			Empleado emp = em.find(Empleado.class, id);// LockModeType.OPTIMISTIC
			if (emp == null)
				throw new EmpleadoException("El empleado con id: " + id + " no existe");

			if (!emp.getActivo())
				throw new EmpleadoException("El empleado con id: " + id + " ya esta inactivo");

			for (Mesas m : emp.getMesas()) {
				em.lock(m, LockModeType.OPTIMISTIC);

				if (m.getActivo())
					throw new EmpleadoException("No se debe dar de baja un empleado con mesas activas");
			}

			emp.setActivo(false);
			t.commit();
			rs = new ResultContext(Evento.BajaEmpleados_OK, "Empleado con ID: " + id + " dado de baja correctamente");

		} catch (EmpleadoException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.BajaEmpleados_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.BajaEmpleados_KO, "Ha sucedido un error: " + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;

	}

	public ResultContext modificarEmpleado(TEmpleado empleado) {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();

		try {
			Integer idEmpleado = empleado.getId();
			Empleado emp = em.find(Empleado.class, idEmpleado);
			t.begin();
			if (emp == null)
				throw new EmpleadoException("El empleado con ID: " + idEmpleado + " no existe");

			Turnos turno = em.find(Turnos.class, empleado.getTurno(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (turno == null)
				throw new EmpleadoException("El turno introducido no existe");
			if (!turno.getActivo())
				throw new EmpleadoException("El turno introducido no esta activo");

			String[] partes = turno.getHoraSalida().split(":");
			int duracion = Integer.parseInt(partes[0]) * 60;
			duracion += Integer.parseInt(partes[1]);
			
			String[] partes2 = turno.getHoraEntrada().split(":");
			duracion -= Integer.parseInt(partes2[0]) * 60;
			duracion -= Integer.parseInt(partes2[1]);
			
			if(empleado.getHoras() * 60 > duracion)
				throw new EmpleadoException("Las horas del empleado no pueden ser mayores a las del turno");
			
			emp.setNombre(empleado.getNombre());
			emp.setsueldoPorHora(empleado.getSueldoPorHora());
			emp.setHoras(empleado.getHoras());
			emp.setTurno(turno);

			if (empleado instanceof TCocinero) {
				Cocinero cocinero = em.find(Cocinero.class, idEmpleado);
				if(cocinero==null) {
					t.rollback();
					throw new EmpleadoException("El empleado con ID: "+idEmpleado+" no es un cocinero");
				}
				((Cocinero) emp).setEspecialidad(((TCocinero) empleado).getEspecialidad());
				((Cocinero) emp).setFactor(((TCocinero) empleado).getFactor());
			}else{
				Camarero camarero = em.find(Camarero.class, empleado.getId());
				if(camarero==null){
					t.rollback();
					throw new EmpleadoException("El empleado con ID: "+idEmpleado+" no es un camarero");
				}
				((Camarero)emp).setPropinas(((TCamarero) empleado).getPropinas());
			}
			
			t.commit();
			rs = new ResultContext(Evento.ModificarEmpleados_OK, "El empleado con ID: " + idEmpleado + " se ha modificado correctamente");

		} catch (EmpleadoException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ModificarEmpleados_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ModificarEmpleados_KO, "Ha sucedido un error: " + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;
	}

	public ResultContext listCamareros() {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			TypedQuery<Camarero> q = em.createQuery("SELECT c FROM Camarero c", Camarero.class);
			Collection<TEmpleado> res = new ArrayList<TEmpleado>();
			for (Empleado e : q.getResultList()) {
				res.add(e.toTransfer());
			}
			rs = new ResultContext(Evento.ListarCamareros_OK, res);
			t.commit();
		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarCamareros_KO, e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;
	}

	public ResultContext listCocineros() {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			TypedQuery<Cocinero> q = em.createQuery("SELECT c FROM Cocinero c", Cocinero.class);
			Collection<TEmpleado> res = new ArrayList<TEmpleado>();
			for (Empleado e : q.getResultList()) {
				res.add(e.toTransfer());
			}
			rs = new ResultContext(Evento.ListarCocineros_OK, res);
			t.commit();
		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarCocineros_KO, "Ha sucedido un errror: " + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;
	}

	public ResultContext listAllEmpleados() {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			TypedQuery<Empleado> q = em.createQuery("SELECT e FROM Empleado e", Empleado.class);
			Collection<TEmpleado> res = new ArrayList<TEmpleado>();
			for (Empleado e : q.getResultList()) {
				res.add(e.toTransfer());
			}
			rs = new ResultContext(Evento.ListarEmpleados_OK, res);
			t.commit();
		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarEmpleados_KO, "Ha sucedido un error: " + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;

	}

	public ResultContext consultarEmpleado(Integer id) {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			if (id == null)
				throw new EmpleadoException("No se admiten ids nulos");

			Empleado emp = em.find(Empleado.class, id, LockModeType.OPTIMISTIC);
			if (emp == null)
				throw new EmpleadoException("No existe el empleado con ID: " + id + " en la BBDD");

			if (!emp.getActivo())
				throw new EmpleadoException("Empleado: " + id + " esta inactivo");

			
			rs = new ResultContext(Evento.ConsultarEmpleados_OK, emp.toTransfer());
			t.commit();

		} catch (EmpleadoException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ConsultarEmpleados_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ConsultarEmpleados_KO, "Ha sucedido un error: " + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;

	}

	public ResultContext mostrarEmpleadosPorTurno(Integer idTurno) {
		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();

		Collection<TEmpleado> listaTEmpleados = new ArrayList<TEmpleado>();

		try {
			t.begin();
			Turnos turno = em.find(Turnos.class, idTurno, LockModeType.OPTIMISTIC);
			if (turno == null)
				throw new EmpleadoException("No existe el turno con ID: " + idTurno + " en la bd");

			if (!turno.getActivo())
				throw new EmpleadoException("El turno con ID: " + idTurno + " esta inactivo");

			for (Empleado emp : turno.getEmpleados()) {
				listaTEmpleados.add(emp.toTransfer());
			}

			rs = new ResultContext(Evento.MostrarEmpleadosPorTurno_OK, listaTEmpleados);
			t.commit();

		} catch (EmpleadoException e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.MostrarEmpleadosPorTurno_KO, e.getMessage());

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.MostrarEmpleadosPorTurno_KO, "Ha sucedido un error:" + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;
	}

}
