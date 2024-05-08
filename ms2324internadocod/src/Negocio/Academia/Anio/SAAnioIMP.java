package Negocio.Academia.Anio;

import java.util.Collection;
import java.util.regex.Pattern;

import Integracion.Anio.DAOAnio;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.ResultContext;
import Negocio.Academia.Matriculable.TMatriculable;
import Presentacion.Evento.Evento;


public class SAAnioIMP implements SAAnio {
	
	public ResultContext alta(TAnio anio) {
		int id;
		Transaction t = null;
		String msj;
		ResultContext cnx = null;
		try {
			t = TransactionManager.getInstance().newTransaction();
			if (t == null) 
				throw new Exception("Transaction error.");
			t.begin();
			
			if (anio != null && Pattern.matches("\\d{4}", anio.getAnio())) {
				DAOAnio dao = DAOFactory.getInstance().getDAOAnio();								
				TAnio a = dao.readByAnio(anio.getAnio());
				if (a == null) {
					id = dao.create(anio);
					msj = "Se ha realizado el alta del anio con id : "+id+" correctamente";
					cnx = new ResultContext(Evento.AltaAnio_OK,msj);
					t.commit();
				} else {
					if (!a.getActivo()) {
						a.setActivo(true);
						dao.update(a);
						msj = "El anio con id : "+a.getId()+" ya existia pero estaba inactivo, se ha activado correctamente";
						cnx = new ResultContext(Evento.AltaAnio_OK,msj);
						t.commit();
					}
					else {
						msj = "El anio con id : "+a.getId()+" ya esta dado de alta";
						cnx = new ResultContext(Evento.AltaAnio_KO,msj);
						t.rollback();
					}
				}
			} else {
				msj = "No se ha realizado el alta correctamente DATOS MAL PASADOS \n El anio tiene el siguiente formato: 'aaaa'";
				cnx = new ResultContext(Evento.AltaAnio_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			cnx=new ResultContext(Evento.AltaAnio_KO,e.getMessage());
		}
		return cnx;
	}
	
	public ResultContext baja(Integer idA) {
		int id;
		Transaction t = null;
		String msj;
		ResultContext cnx = null;
		try {
			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();
			
			if (idA != null) {
				DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TAnio a = dao.read(idA);
				if (a != null) {
					Collection<TMatriculable> matriculables = daoMatriculable.listarMatriculablesPorAnio(idA);
					for (TMatriculable matriculable : matriculables) {
						if (matriculable.getActivo()) {
							t.rollback();
							cnx = new ResultContext(Evento.BajaAnio_KO, "No se puede dar de baja este anio ya que tiene matriculables activos");
						}
					}
					if(cnx==null){
						id = dao.delete(a.getId());
						msj = "Se ha realizado la baja del anio con id : "+id+" correctamente";
						cnx = new ResultContext(Evento.BajaAnio_OK,msj);
						t.commit();
					}
				} else {
					msj = "No se ha encontrado un anio con ese id";
					cnx = new ResultContext(Evento.BajaAnio_KO,msj);
					t.rollback();
				}
			} else {
				msj = "No existe ningun anio con id null";
				cnx = new ResultContext(Evento.BajaAnio_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			cnx=new ResultContext(Evento.BajaAlumnos_KO,e.getMessage());
		}
		return cnx;
	}

	public ResultContext modificacion(TAnio anio) {
		int id;
		Transaction t = null;
		String msj;
		ResultContext cnx = null;
		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			t = tm.getTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();
			
			if(anio != null && Pattern.matches("\\d{4}", anio.getAnio())) {
				DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
				TAnio a = dao.read(anio.getId());
				TAnio aAux = dao.readByAnio(anio.getAnio());
				if (a != null ) {
					if(!a.getActivo()) {
						anio.setActivo(true);
					}
					if (aAux == null) {
						id = dao.update(anio);
						msj = "Se ha realizado la modificacion del anio con id : "+id+" correctamente";
						cnx = new ResultContext(Evento.ModificacionAnio_OK,msj);
						t.commit();
					} else {
						msj = "No se ha podido modificar el anio porque ya existe";
						cnx = new ResultContext(Evento.ModificacionGrupo_KO,msj);
						t.rollback();
					}
				} else {
					msj = "No se ha encontrado un grupo con ese id";
					cnx = new ResultContext(Evento.ModificacionAnio_KO,msj);
					t.rollback();
				}
			} else {
				msj = "No se ha realizado la modificacion correctamente DATOS MAL PASADOS \\n El anio tiene el siguiente formato: 'aaaa'";
				cnx = new ResultContext(Evento.ModificacionAnio_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			cnx=new ResultContext(Evento.ModificacionAnio_KO,e.getMessage());
		}
		return cnx;
	}

	public ResultContext consulta(Integer id) {
		Transaction t = null;
		TAnio a;
		String msj;
		ResultContext cnx = null;
		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			t = tm.getTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();
			
			if (id != null) {
				DAOAnio dao = DAOFactory.getInstance().getDAOAnio();
				a = dao.read(id);
				if (a != null) {
					if (a.getActivo()) {
						cnx = new ResultContext(Evento.ConsultaAnio_OK,a);
						t.commit();
					} else {
						msj ="No se ha realizado la consulta, anio no activo";
						cnx = new ResultContext(Evento.ConsultaAnio_KO,msj);
						t.rollback();
					}
				} else {
					msj ="No se ha realizado la consulta, anio no existe";
					cnx = new ResultContext(Evento.ConsultaAnio_KO,msj);
					t.rollback();
				}
			} else {
				msj ="No se ha realizado la consulta, id null";
				cnx = new ResultContext(Evento.ConsultaAnio_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			cnx=new ResultContext(Evento.ConsultaAnio_KO,e.getMessage());
		}	
		return cnx;
	}

	@Override
	public ResultContext listar() {
		Transaction t = null;
		ResultContext ResultContexto = null;
		t = TransactionManager.getInstance().newTransaction();
			
		try {
			if (t == null)
				throw new Exception("Error transaccional");
			t.begin();
			Collection<TAnio>anios=null;
			anios=DAOFactory.getInstance().getDAOAnio().readAll();
			ResultContexto = new ResultContext(Evento.ListarAnio_OK, anios);
			t.commit();
		} catch(Exception e) {
			ResultContexto = new ResultContext(Evento.ListarAnio_KO, "No se ha realizado correctamente el listar debido a un error "+ e.getMessage());
			t.rollback();
		}
		return ResultContexto;
	}
	
	
}