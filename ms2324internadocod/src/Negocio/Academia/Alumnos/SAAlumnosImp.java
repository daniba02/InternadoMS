package Negocio.Academia.Alumnos;

import Integracion.Alumnos.DAOAlumnos;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Query.FactoriaQuery;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.ResultContext;
import Presentacion.Evento.Evento;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SAAlumnosImp implements SAAlumnos {

	@Override
	public ResultContext Alta(TAlumnos Alumno) {
		int id = -1;

		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;

		try {
			if (t == null)
				return new ResultContext(Evento.AltaMatriculable_KO, "Error transaccional");
			
			t.begin();

			if (camposValidos(Alumno)) {
				DAOAlumnos daoalumnos = DAOFactory.getInstance().getDAOAlumnos();

				TAlumnos leido = daoalumnos.readByDNI(Alumno.getDNI());
				if (leido == null) {
					id = daoalumnos.create(Alumno);
					t.commit();
					msj = "Se ha realizado el alta de id: " + id + " correctamente";
					cnx = new ResultContext(Evento.AltaAlumnos_OK, msj);
				} else if (!leido.getActivo()) {
					leido.setActivo(true);
					id = daoalumnos.update(leido);
					t.commit();
					msj = "Se ha realizado el alta de id: " + id + " correctamente";
					cnx = new ResultContext(Evento.AltaAlumnos_OK, msj);
				} else {
					t.rollback();
					msj = "El alumno con id: " + leido.getID() + " ya se encuetra activo";
					cnx = new ResultContext(Evento.AltaAlumnos_KO, msj);
				}

			} else {
				t.rollback();
				msj = "Los datos introducidos no son correctos";
				cnx = new ResultContext(Evento.AltaAlumnos_KO, msj);
			}
		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.AltaAlumnos_KO, e.getMessage());
		}

		return cnx;
	}

	@Override
	public ResultContext Baja(Integer ID) {

		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try {
			if (t == null)
				return new ResultContext(Evento.BajaAlumnos_KO, "Error transaccional");
			t.begin();

			if (ID != null) {
				DAOAlumnos daoalumnos = DAOFactory.getInstance().getDAOAlumnos();

				TAlumnos leido = daoalumnos.read(ID);
				if (leido != null && leido.getActivo()) {
					daoalumnos.delete(ID);
					t.commit();
					msj = "Se ha realizado la baja del id: " + ID + " correctamente";
					cnx = new ResultContext(Evento.BajaAlumnos_OK, msj);

				} else if (leido == null) {
					t.rollback();
					msj = "No existe alumno con id: " + ID + " registrado";
					cnx = new ResultContext(Evento.BajaAlumnos_KO, msj);
				} else {
					t.rollback();
					msj = "No se ha realizado correctamente la baja del : " + ID + ", ya esta inactivo";
					cnx = new ResultContext(Evento.BajaAlumnos_KO, msj);
				}

			} else {
				t.rollback();
				msj = "Los datos introducidos no son correctos";
				cnx = new ResultContext(Evento.BajaAlumnos_KO, msj);
			}
		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.BajaAlumnos_KO, e.getMessage());
		}

		return cnx;
	}

	@Override
	public ResultContext Modificar(TAlumnos Alumno) {
		int id = -1;

		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try {

			if (t == null)
				return new ResultContext(Evento.ModificarAlumnos_KO, "Error transaccional");
			t.begin();

			DAOAlumnos daoalumnos = DAOFactory.getInstance().getDAOAlumnos();
			if (Alumno != null && camposValidos(Alumno)) {

				TAlumnos leido = daoalumnos.read(Alumno.getID());
				TAlumnos mismoDNI = daoalumnos.readByDNI(Alumno.getDNI());

				if (leido != null && leido.getActivo() && (mismoDNI == null || mismoDNI.getDNI().equals(leido.getDNI()))) {
					id = daoalumnos.update(Alumno);
					t.commit();
					msj = "Se ha realizado la modificacion del id: " + id + " correctamente";
					cnx = new ResultContext(Evento.ModificarAlumnos_OK, msj);

				} else if (mismoDNI != null) {
					t.rollback();
					msj = "No se puede modificar el DNI a otro ya existente";
					cnx = new ResultContext(Evento.ModificarAlumnos_KO, msj);
				} else {
					t.rollback();
					msj = "No se ha realizado correctamente la modificacion del : " + Alumno.getID()
							+ ", esta inactivo";
					cnx = new ResultContext(Evento.ModificarAlumnos_KO, msj);
				}

			} else {
				t.rollback();
				msj = "Los datos introducidos no son correctos";
				cnx = new ResultContext(Evento.ModificarAlumnos_KO, msj);
			}
		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.ModificarAlumnos_KO, e.getMessage());
		}

		return cnx;
	}

	@Override
	public ResultContext Consulta(Integer ID) {

		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try {
			if (t == null)
				return new ResultContext(Evento.ConsultarAlumnos_KO, "Error transaccional");
			t.begin();

			if (ID >= 0) {
				try {
					DAOAlumnos daoalumnos = DAOFactory.getInstance().getDAOAlumnos();
					TAlumnos leido = daoalumnos.read(ID);
					if (leido != null && leido.getActivo()) {
						t.commit();
						// msj = "Se ha realizado la consulta del id: "+ID+ "
						// correctamente";
						cnx = new ResultContext(Evento.ConsultarAlumnos_OK, leido);
					} else {
						t.rollback();
						msj = "No se ha realizado la consulta del id: " + ID + ", esta inactivo";
						cnx = new ResultContext(Evento.ConsultarAlumnos_KO, msj);
					}

				} catch (Exception e) {
					t.rollback();
					msj = "El alumno con id: " + ID + " no existe";
					cnx = new ResultContext(Evento.ConsultarAlumnos_KO, msj);
				}
			} else {
				t.rollback();
				msj = "Los datos introducidos no son correctos";
				cnx = new ResultContext(Evento.ConsultarAlumnos_KO, msj);
			}
		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.ConsultarAlumnos_KO, e.getMessage());
		}

		return cnx;
	}

	@Override
	public ResultContext Listar(){

		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try {
			if (t == null)
				return new ResultContext(Evento.ListarAlumnos_KO, "Error transaccional");
			t.begin();

			try {
				DAOAlumnos daoalumnos = DAOFactory.getInstance().getDAOAlumnos();
				Collection<TAlumnos> alumnos = new LinkedList<TAlumnos>();
				alumnos = daoalumnos.readAll();

				if (alumnos.size() == 0)
					throw new Exception();

				t.commit();
				// msj = "Se ha realizado la lista correctamente";
				cnx = new ResultContext(Evento.ListarAlumnos_OK, alumnos);

			} catch (Exception e) {
				t.rollback();
				msj = "No se encuentran alumnos registrados";
				cnx = new ResultContext(Evento.ListarAlumnos_KO, msj);
			}
		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.ListarAlumnos_KO, e.getMessage());
		}

		return cnx;
	}

	private boolean camposValidos(TAlumnos a) {
		return (a != null && a.getTelefono() / 100000000 > 0 && a.getEdad() >= 0 && DNIvalido(a.getDNI()));
	}

	private boolean DNIvalido(String dni) {

		// Verificar que el DNI tiene 9 caracteres
		if (dni.length() != 9) {
			return false;
		}

		// Verificar que los primeros 8 caracteres son números
		for (int i = 0; i < 8; i++) {
			char c = dni.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}

		// Verificar que el último carácter es una letra mayúscula
		char letra = dni.charAt(8);
		if (letra < 'A' || letra > 'Z') {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultContext ListarNotasAlumno(Integer ID){

		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try {
			if (t == null)
				return new ResultContext(Evento.ListarNotasAlumnos_KO, "Error transaccional");
			t.begin();

			Map<String, Integer> listaNotas = new HashMap<String, Integer>();
			if (ID != null) {
				listaNotas = (Map<String, Integer>) FactoriaQuery.getInstancia().listarNotasAlumno().execute(ID);
				if (listaNotas.size() > 0) {
					cnx = new ResultContext(Evento.ListarNotasAlumnos_OK, listaNotas);
					t.commit();
				} else {
					msj = "El alumno no tiene ninguna nota o no existe en la Base de Datos";
					cnx = new ResultContext(Evento.ListarNotasAlumnos_KO, msj);
					t.rollback();
				}
			} else {
				msj = "El id del alumno es nulo";
				cnx = new ResultContext(Evento.ListarNotasAlumnos_KO, msj);
				t.rollback();
			}

		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.ListarNotasAlumnos_KO, e.getMessage());
		}
		return cnx;
	}

	@Override
	public ResultContext AlumnoAnio(Integer anio){
		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try {
			if (t == null)
				return new ResultContext(Evento.AlumnoANIO_KO, "Error transaccional");
			t.begin();

			Collection<TAlumnos> alumnos = new LinkedList<TAlumnos>();
			if (anio!=null&&anio >= 0) {
				alumnos = (Collection<TAlumnos>) FactoriaQuery.getInstancia().alumnosMatriculadosAnio().execute(anio);
				if (alumnos.size() > 0) {
					cnx = new ResultContext(Evento.AlumnoANIO_OK, alumnos);
					t.commit();
				} else {
					msj = "No hay ningun alumno matriculado ese anio";
					cnx = new ResultContext(Evento.AlumnoANIO_KO, msj);
					t.rollback();
				}
			} else {
				msj = "El Id de anio debe ser un numero positivo";
				cnx = new ResultContext(Evento.AlumnoANIO_KO, msj);
				t.rollback();
			}

		} catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.AlumnoANIO_KO, e.getMessage());
		}
		return cnx;
	}

}