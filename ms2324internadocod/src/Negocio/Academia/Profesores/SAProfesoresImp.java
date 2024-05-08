package Negocio.Academia.Profesores;

import java.util.ArrayList;
import java.util.Collection;

import Integracion.DAOFactory.DAOFactory;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.ProfesorMatriculable.DAOProfesorMatriculable;
import Integracion.Profesores.DAOProfesor;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.ResultContext;
import Negocio.Academia.Matriculable.TMatriculable;
import Presentacion.Evento.Evento;

public class SAProfesoresImp implements SAProfesores {

	private static final int DAO_ERROR = -1;

	@Override
	public ResultContext alta(TProfesor profesor) {

		int id = DAO_ERROR;
		Transaction t = TransactionManager.getInstance().newTransaction();
		String mensaje;
		ResultContext Rcontext;
		if (t == null)
			return new ResultContext(Evento.AltaProfesor_KO, "Error transacional");
		t.begin();

		if (profesor != null) {
			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();

			try {
				TProfesor leido = dao.readByDNI(profesor.getDNI());
				if (leido == null) {
					id = dao.create(profesor);
					mensaje = "Se ha realizado el alta del profesor con ID: " + id + " correctamente";
					Rcontext = new ResultContext(Evento.AltaProfesor_OK, mensaje);
					t.commit();
				} else {
					if (!leido.getActivo()) {
						leido.setActivo(true);
						id = dao.update(leido);
						mensaje = "Se ha reactivado el profesor con DNI: " + leido.getDNI() + " correctamente";
						Rcontext = new ResultContext(Evento.AltaProfesor_OK, mensaje);
						t.commit();
					} else {
						t.rollback();
						mensaje = "Ya existe el profesor con dni: " + profesor.getDNI();
						Rcontext = new ResultContext(Evento.AltaProfesor_KO, mensaje);
						return Rcontext;
					}
				}

			} catch (Exception e) {
				t.rollback();
				mensaje = "No se ha realizado el alta correctamente";
				Rcontext = new ResultContext(Evento.AltaProfesor_KO, mensaje);
				// throw new Exception(e);
			}
		} else {
			t.rollback();
			mensaje = "No se ha realizado el alta correctamente, datos mal puestos";
			Rcontext = new ResultContext(Evento.AltaProfesor_KO, mensaje);
		}

		return Rcontext;
	}

	@Override
	public ResultContext baja(Integer idProfesor) {

		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext Rcontext;
		String mensaje;

		if (t == null)
			return new ResultContext(Evento.BajaProfesor_KO, "Error en la transaccion");
		t.begin();

		if (idProfesor != null) {
			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();

			try {
				TProfesor leido = dao.read(idProfesor);
				if (leido != null) {
					boolean activo = leido.getActivo();
					if (activo) {
						Collection<TMatriculable> matriculables = daoMatriculable
								.listarmatriculablesporProfesor(idProfesor);
						for (TMatriculable matriculable : matriculables) {
							if (matriculable.getActivo()) {
								t.rollback();
								return new ResultContext(Evento.BajaProfesor_KO,
										"No se puede dar de baja este profesor ya que tiene matriculables activos");
							}
						}
						int id = dao.delete(idProfesor);
						mensaje = "Se ha realizado correctamente la baja del profesor con ID: " + id;
						Rcontext = new ResultContext(Evento.BajaProfesor_OK, mensaje);
						t.commit();
					} else {
						mensaje = "Ya está dado de baja el profesor con id: " + idProfesor;
						Rcontext = new ResultContext(Evento.BajaProfesor_KO, mensaje);
						t.rollback();
					}
				} else {
					mensaje = "No existe el profesor con id: " + idProfesor;
					Rcontext = new ResultContext(Evento.BajaProfesor_KO, mensaje);
					t.rollback();
				}
			} catch (Exception e) {
				mensaje = "No se ha realizado correctamente la baja";
				Rcontext = new ResultContext(Evento.BajaProfesor_KO, mensaje);
				t.rollback();
				// throw new Exception(e);
			}
		} else {
			mensaje = "Introduzca un id";
			Rcontext = new ResultContext(Evento.BajaProfesor_KO, mensaje);
			t.rollback();
		}

		return Rcontext;
	}

	@Override
	public ResultContext modificar(TProfesor tprofesor) {
		int id = -1;
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext Rcontext;
		String mensaje;
		try {
			if (t == null)
				return new ResultContext(Evento.ModificarProfesor_KO, "Error en la transaccion");

			t.begin();

			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			if (tprofesor != null) {

				TProfesor leido = dao.read(tprofesor.getId());
				TProfesor dniIgual = dao.readByDNI(tprofesor.getDNI());
				if (leido != null && leido.getActivo() && leido.getActivo()
						&& (dniIgual == null || dniIgual.getDNI().equals(leido.getDNI()))) {

					if (tprofesor.getClass() == leido.getClass()) {
						id = dao.update(tprofesor);
						mensaje = "Se ha realizado correctamente la modificación del id: " + id + " .";
						Rcontext = new ResultContext(Evento.ModificarProfesor_OK, mensaje);
						t.commit();
					} else {
						mensaje = "No se puede cambiar el tipo del profesor";
						Rcontext = new ResultContext(Evento.ModificarProfesor_KO, mensaje);
						t.rollback();
					}

				} else if (dniIgual != null) {
					t.rollback();
					mensaje = "No se puede modificar el DNI a otro ya existente";
					Rcontext = new ResultContext(Evento.ModificarProfesor_KO, mensaje);
				} else if (leido == null) {
					t.rollback();
					mensaje = "No existe el profesor con id: " + tprofesor.getId();
					Rcontext = new ResultContext(Evento.ModificarProfesor_KO, mensaje);
				} else {
					t.rollback();
					mensaje = "Error: el profesor está inactivo";
					Rcontext = new ResultContext(Evento.ModificarProfesor_KO, mensaje);
				}
			} else {
				t.rollback();
				mensaje = "No se ha realizado correctamente la modificación (datos)";
				Rcontext = new ResultContext(Evento.ModificarProfesor_KO, mensaje);
			}
		} catch (Exception e) {
			t.rollback();
			mensaje = "No se ha realizado correctamente la modificación";
			Rcontext = new ResultContext(Evento.ModificarProfesor_KO, mensaje);
			// throw new Exception(e);
		}
		return Rcontext;
	}

	@Override
	public ResultContext consulta(Integer id) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext Rcontext;
		String mensaje;

		if (id != null) {
			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			try {
				if (t == null)
					return new ResultContext(Evento.BajaProfesor_KO, "Error en la transaccion");
				t.begin();
				TProfesor leido = dao.read(id);
				if (leido != null) {
					if (leido.getActivo()) {
						TProfesor profesor = dao.read(id);
						mensaje = "Se ha podido realizar la consulta correctamente";
						Rcontext = new ResultContext(Evento.ConsultarProfesor_OK, profesor);
						t.commit();
					} else {
						mensaje = "Profesor inactivo";
						Rcontext = new ResultContext(Evento.ConsultarProfesor_KO, mensaje);
						t.rollback();
					}
				} else {
					mensaje = "No existe ese profesor";
					Rcontext = new ResultContext(Evento.ConsultarProfesor_KO, mensaje);
					t.rollback();
				}

			} catch (Exception e) {
				mensaje = "No se ha podido realizar la consulta";
				Rcontext = new ResultContext(Evento.ConsultarProfesor_KO, mensaje);
				t.rollback();
				// throw new Exception(e);
			}
		} else {
			mensaje = "No se ha podido realizar la consulta";
			Rcontext = new ResultContext(Evento.ConsultarProfesor_KO, mensaje);
			t.rollback();
		}

		return Rcontext;
	}

	@Override
	public ResultContext listar() {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext Rcontext;
		String mensaje;

		Collection<TProfesor> profesores = new ArrayList<TProfesor>();

		try {
			if (t == null)
				return new ResultContext(Evento.BajaProfesor_KO, "Error en la transaccion");
			t.begin();
			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			profesores = dao.readAll();
			Rcontext = new ResultContext(Evento.ListarProfesores_OK, profesores);
			t.commit();
		} catch (Exception e) {
			mensaje = "No se ha podido realizar el listado de profesores";
			Rcontext = new ResultContext(Evento.ListarProfesores_KO, mensaje);
			t.rollback();
			// throw new Exception(e);

		}

		return Rcontext;
	}

	@Override
	public ResultContext listarProfesorFijo() {
		Transaction t = TransactionManager.getInstance().newTransaction();
		Collection<TFijo> fijos = null;
		ResultContext Rcontext;
		String mensaje;

		try {
			if (t == null)
				return new ResultContext(Evento.BajaProfesor_KO, "Error en la transaccion");
			t.begin();
			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			fijos = dao.readAllProfesorFijo();
			Rcontext = new ResultContext(Evento.ListarProfesoresFijos_OK, fijos);
			t.commit();
		} catch (Exception e) {
			mensaje = "No se ha podido realizar el listado de profesores";
			Rcontext = new ResultContext(Evento.ListarProfesoresFijos_KO, mensaje);
			t.rollback();
			// throw new Exception(e);

		}

		return Rcontext;
	}

	@Override
	public ResultContext listarProfesorInterino() {
		Transaction t = TransactionManager.getInstance().newTransaction();
		Collection<TInterino> interinos = null;
		ResultContext Rcontext;
		String mensaje;

		try {
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();

			DAOProfesor dao = DAOFactory.getInstance().getDAOProfesor();
			interinos = dao.readAllProfesorInterino();
			Rcontext = new ResultContext(Evento.ListarProfesoresInterinos_OK, interinos);
			t.commit();
		} catch (Exception e) {
			mensaje = "No se ha podido realizar el listado de profesores";
			Rcontext = new ResultContext(Evento.ListarProfesoresInterinos_KO, mensaje);
			// throw new Exception(e);
			t.rollback();
		}

		return Rcontext;
	}

	@Override
	public ResultContext listarProfesorPorMatriculable(Integer idMatriculable) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext Rcontext;

		try {
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();
			Collection<TProfesor> lista = new ArrayList<TProfesor>();
			DAOProfesor daoProfesores = DAOFactory.getInstance().getDAOProfesor();
			lista = daoProfesores.readByMatriculable(idMatriculable);
			Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_OK,lista);
			t.commit();
			
		} catch (Exception e) {
			Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_KO, e.getMessage());
			t.rollback();
			// throw new Exception(e);
		}
		return Rcontext;
	}

	// @Override
	// public ResultContext listarProfesorPorMatriculable(Integer
	// idMatriculable) {
	// Transaction t = TransactionManager.getInstance().newTransaction();
	// ResultContext Rcontext;
	//
	// TMatriculable leido;
	// try {
	// if (t == null)
	// throw new Exception("Error en la transaccion");
	// t.begin();
	// Collection<TProfesor> profesores = new ArrayList<TProfesor>();
	//
	// if (idMatriculable != null) {
	//
	// leido =
	// DAOFactory.getInstance().getDAOMatriculable().read(idMatriculable);
	// if (leido != null) {
	// if (leido.getActivo()) {
	// profesores =
	// DAOFactory.getInstance().getDAOProfesor().readByMatriculable(idMatriculable);
	// if (profesores.size() != 0) {
	// Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_OK,
	// profesores);
	// t.commit();
	// } else {
	// Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_KO,
	// "No esta vinculado a ningún profesor");
	// t.rollback();
	// }
	//
	// } else {
	// Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_KO,
	// "Ese matriculable esta inactivo");
	// t.rollback();
	// }
	// } else {
	//
	// Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_KO,
	// "Ese id matriculable no existe");
	// t.rollback();
	// }
	//
	// } else {
	// Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_KO,
	// "No se ha realizado correctamente el listar Profesor (datos)");
	// t.rollback();
	// }
	// } catch (Exception e) {
	// Rcontext = new ResultContext(Evento.ListarProfesoresPorMatriculable_KO,
	// "No se ha realizado correctamente el listado por idMatriculable");
	// t.rollback();
	// // throw new Exception(e);
	// }
	// return Rcontext;
	// }

	public ResultContext vincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable) {
		@SuppressWarnings("unused")
		int id = -1;
		ResultContext Rcontext;
		Transaction t = TransactionManager.getInstance().newTransaction();
		try {
			if (t == null) {
				return new ResultContext(Evento.BajaProfesor_KO, "Error en la transaccion");
			}
			t.begin();
			if (idProfesor != null && idMatriculable != null) {

				DAOProfesorMatriculable daopm = DAOFactory.getInstance().getDAOProfesorMatriculable();

				DAOProfesor daoProfesor = DAOFactory.getInstance().getDAOProfesor();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();

				TProfesor tprofesor = daoProfesor.read(idProfesor);
				TMatriculable tmatriculable = daoMatriculable.read(idMatriculable);

				if (tprofesor != null && tmatriculable != null) {
					if (tprofesor.getActivo() && tmatriculable.getActivo()) {
						if (daopm.comprobarVinculacion(idProfesor, idMatriculable) == -1) {
							id = daopm.vincularProfesorMatriculable(idProfesor, idMatriculable);
							Rcontext = new ResultContext(Evento.VincularProfesorMatriculable_OK,
									"Se vincula correctamente el profesor con ID :" + idProfesor
											+ " al ID matriculable: " + idMatriculable + " .");
							t.commit();
						} else {
							Rcontext = new ResultContext(Evento.VincularProfesorMatriculable_KO, "Ya estan vinculados");
							t.rollback();
						}
					} else {
						Rcontext = new ResultContext(Evento.VincularProfesorMatriculable_KO,
								"Error: profesor y/o matriculable inactivo");
						t.rollback();
					}
				} else {
					Rcontext = new ResultContext(Evento.VincularProfesorMatriculable_KO,
							"Error: profesor y/o matriculable no existen");
					t.rollback();
				}

			} else {
				Rcontext = new ResultContext(Evento.VincularProfesorMatriculable_KO,
						"No se vincula correctamente Profesor-Matriculable (datos)");
				t.rollback();
			}
		} catch (Exception e) {
			Rcontext = new ResultContext(Evento.VincularProfesorMatriculable_KO,
					"No se vincula correctamente Profesor-Matriculable");
			t.rollback();
			// throw new Exception(e);
		}
		return Rcontext;
	}

	public ResultContext desvincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable) {
		int id = -1;
		ResultContext Rcontext;
		Transaction t = TransactionManager.getInstance().newTransaction();
		try {
			if (t == null) {
				return new ResultContext(Evento.BajaProfesor_KO, "Error en la transaccion");
			}
			t.begin();
			if (idProfesor != null && idMatriculable != null) {
				DAOProfesorMatriculable daopm = DAOFactory.getInstance().getDAOProfesorMatriculable();

				DAOProfesor daoProfesor = DAOFactory.getInstance().getDAOProfesor();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();

				TProfesor tprofesor = daoProfesor.read(idProfesor);
				TMatriculable tmatriculable = daoMatriculable.read(idMatriculable);

				if (tprofesor != null && tmatriculable != null) {
					if (daopm.comprobarVinculacion(idProfesor, idMatriculable) == 0) {
						id = daopm.desvincularProfesorMatriculable(idProfesor, idMatriculable);
						Rcontext = new ResultContext(Evento.DesvincularProfesorMatriculable_OK,
								"Se desvincula correctamente el profesor :" + id);
						t.commit();
					} else {
						Rcontext = new ResultContext(Evento.DesvincularProfesorMatriculable_KO,
								"No se desvincula correctamente Profesor-Matriculable (no vinculados)");
						t.rollback();
					}
				} else {
					Rcontext = new ResultContext(Evento.DesvincularProfesorMatriculable_KO,
							"No se desvincula correctamente Profesor-Matriculable (no existen)");
					t.rollback();
				}

			} else {
				Rcontext = new ResultContext(Evento.DesvincularProfesorMatriculable_KO,
						"No se desvincula correctamente Profesor-Matriculable (datos)");
				t.rollback();
			}
		} catch (Exception e) {
			Rcontext = new ResultContext(Evento.DesvincularProfesorMatriculable_KO,
					"No se desvincula correctamente Profesor-Matriculable");
			t.rollback();
			// throw new Exception(e);
		}
		return Rcontext;
	}
}
