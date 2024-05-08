package Negocio.Academia.Grupo;

import Integracion.DAOFactory.DAOFactory;
import Integracion.Grupo.DAOGrupo;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.ResultContext;
import Negocio.Academia.Matriculable.TMatriculable;
import Presentacion.Evento.Evento;

import java.util.regex.Pattern;
import java.util.Collection;


public class SAGrupoIMP implements SAGrupo {
	
	private static final int DAO_ERROR = -1;

	public ResultContext alta(TGrupo grupo) {
		int id = DAO_ERROR;
		Transaction t = null;
		String msj;
		ResultContext cnx = null;
		try {
			t = TransactionManager.getInstance().newTransaction();
			if (t == null) 
				throw new Exception("Transaction error.");
			t.begin();
			
			if (grupo != null) {
				boolean esUnaLetra = Pattern.matches("[a-zA-Z]", grupo.getLetra());
				if (esUnaLetra) {
					DAOGrupo daoGrupo = DAOFactory.getInstance().getDAOGrupo();	
					String letra = grupo.getLetra();
					grupo.setLetra(letra.toUpperCase());
					TGrupo g = daoGrupo.readByLetra(grupo.getLetra());
					if (g == null) {
						id = daoGrupo.create(grupo);
						msj = "Se ha realizado el alta del grupo con id: "+id+" correctamente";
						cnx = new ResultContext(Evento.AltaGrupo_OK,msj);
						t.commit();
					} else {
						if (!g.getActivo()) {
							g.setActivo(true);
							daoGrupo.update(g);
							msj = "El grupo con id : "+g.getId()+" ya existia pero estaba inactivo, se ha activado correctamente";
							cnx = new ResultContext(Evento.AltaGrupo_OK,msj);
							t.commit();
						}
						else {
							msj = "El grupo con id : "+g.getId()+" ya esta dado de alta";
							cnx = new ResultContext(Evento.AltaGrupo_KO,msj);
							t.rollback();
						}
					}
				} else {
					msj = "No se ha realizado el alta correctamente DATOS MAL PASADOS";
					cnx = new ResultContext(Evento.AltaGrupo_KO,msj);
					t.rollback();
				}
			} else {
				msj = "No se ha realizado el alta correctamente DATOS MAL PASADOS";
				cnx = new ResultContext(Evento.AltaGrupo_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
		}
		return cnx;
	}
	
	public ResultContext baja(Integer idG) {
		int id = DAO_ERROR;
		Transaction t = null;
		String msj;
		ResultContext cnx = null;
		try {
			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();
			
			if (idG != null) {
				DAOGrupo daoGrupo = DAOFactory.getInstance().getDAOGrupo();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TGrupo g = daoGrupo.read(idG);
				if (g != null) {
					if(g.getActivo()) {
						Collection<TMatriculable> matriculables = daoMatriculable.listarMatriculablesPorGrupo(idG);
						for (TMatriculable matriculable : matriculables) {
							if (matriculable.getActivo()) {
								t.rollback();
								return new ResultContext(Evento.BajaGrupo_KO, "No se puede dar de baja este grupo ya que tiene matriculables activos");
							}
						}
					}else{
						t.rollback();
						return new ResultContext(Evento.BajaGrupo_KO, "No se puede dar de baja este grupo porque ya esta inactivo");
					}
					id = daoGrupo.delete(g.getId());
					msj = "Se ha realizado la baja del grupo con id : "+id+" correctamente";
					cnx = new ResultContext(Evento.BajaGrupo_OK,msj);
					t.commit();
				} else {
					msj = "No se ha encontrado un grupo con ese id";
					cnx = new ResultContext(Evento.BajaGrupo_KO,msj);
					t.rollback();
				}
			} else {
				msj = "No existe ningun grupo con id null";
				cnx = new ResultContext(Evento.BajaGrupo_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
		}
		return cnx;
	}

	public ResultContext modificacion(TGrupo grupo) {
		int id = DAO_ERROR;
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
			
			if(grupo != null) {
				boolean esUnaLetra = Pattern.matches("[a-zA-Z]", grupo.getLetra());
				if (esUnaLetra) {
					DAOGrupo daoGrupo = DAOFactory.getInstance().getDAOGrupo();
					TGrupo g = daoGrupo.read(grupo.getId());
					String letra = grupo.getLetra();
					grupo.setLetra(letra.toUpperCase());
					TGrupo gAux = daoGrupo.readByLetra(grupo.getLetra());
					if (g != null ) {
						if(!g.getActivo()) {
							grupo.setActivo(true);
						}
						if (gAux == null || !gAux.getActivo()) {
							id = daoGrupo.update(grupo);
							msj = "Se ha realizado la modificacion del grupo con id : "+id+" correctamente";
							cnx = new ResultContext(Evento.ModificacionGrupo_OK,msj);
							t.commit();
						} else {
							msj = "No se ha podido modificar la letra del grupo porque ya existe";
							cnx = new ResultContext(Evento.ModificacionGrupo_KO,msj);
							t.rollback();
						}
					} else {
						msj = "No se ha encontrado un grupo con ese id";
						cnx = new ResultContext(Evento.ModificacionGrupo_KO,msj);
						t.rollback();
					}
				} else {
					msj = "No se ha realizado la modificacion correctamente DATOS MAL PASADOS";
					cnx = new ResultContext(Evento.ModificacionGrupo_KO,msj);
					t.rollback();
				}
			} else {
				msj = "No se ha realizado la modificacion correctamente DATOS MAL PASADOS";
				cnx = new ResultContext(Evento.ModificacionGrupo_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
		}
		return cnx;
	}

	public ResultContext consulta(Integer id) {
		TGrupo g;
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
			
			if (id != null) {
				DAOGrupo daoGrupo = DAOFactory.getInstance().getDAOGrupo();
				g = daoGrupo.read(id);
				if (g != null) {
					if (g.getActivo()) {
						cnx = new ResultContext(Evento.ConsultaGrupo_OK,g);
						t.commit();
					} else {
						msj ="No se ha realizado la consulta, grupo no activo";
						cnx = new ResultContext(Evento.ConsultaGrupo_KO,msj);
						t.rollback();
					}
				} else {
					msj ="No se ha realizado la consulta, grupo no existe";
					cnx = new ResultContext(Evento.ConsultaGrupo_KO,msj);
					t.rollback();
				}
			} else {
				msj ="No se ha realizado la consulta, id null";
				cnx = new ResultContext(Evento.ConsultaGrupo_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			cnx=new ResultContext(Evento.ConsultaGrupo_KO,e.getMessage());
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
			Collection<TGrupo>grupos=null;
			grupos=DAOFactory.getInstance().getDAOGrupo().readAll();
			ResultContexto = new ResultContext(Evento.ListarGrupo_OK, grupos);
			t.commit();
		} catch(Exception e) {
			ResultContexto = new ResultContext(Evento.ListarGrupo_KO, "No se ha realizado correctamente el listar debido a un error "+ e.getMessage());
			t.rollback();
			//throw new Exception(e);
		}
		return ResultContexto;
	} 
	
}