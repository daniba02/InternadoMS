package Negocio.Academia.Matriculable;

import java.util.ArrayList;
import java.util.Collection;

import Integracion.DAOFactory.DAOFactory;
import Integracion.Matricula.DAOMatricula;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.ResultContext;
import Negocio.Academia.Anio.TAnio;
import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.Academia.Matricula.TMatricula;
import Negocio.Academia.Profesores.TProfesor;
import Presentacion.Evento.Evento;

public class SAMatriculableImp implements SAMatriculable {
	@Override
	public ResultContext alta(TMatriculable matriculable) {
		int id=-1;
		ResultContext ResultContexto=null;
		DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
		Transaction t = null;
	
		t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			return new ResultContext(Evento.AltaMatriculable_KO, "Error transaccional");
		t.begin();
		if(camposValidos(matriculable)) {
			
			try {
				TGrupo grupo =DAOFactory.getInstance().getDAOGrupo().read(matriculable.getIdGrupo());
				TAsignaturas asignatura = DAOFactory.getInstance().getDAOAsignaturas().read(matriculable.getIdAsignatura());
				TAnio anio=DAOFactory.getInstance().getDAOAnio().read(matriculable.getIdAnio());
				
				if(asignatura==null||!asignatura.getActivo()) 
				{
					t.rollback();
					return new ResultContext(Evento.AltaMatriculable_KO,"La asignatura introducida no existe o no esta actiaba en la base de datos");
				}
					
				if(grupo==null||!grupo.getActivo()) 
				{
					t.rollback();
					return new ResultContext(Evento.AltaMatriculable_KO,"El grupo introducido no existe o no esta actiabo en la base de datos");
				}
				
				if(anio==null||!anio.getActivo()) 
				{
					t.rollback();
					return new ResultContext(Evento.AltaMatriculable_KO,"El anño introducido no existe o no esta actiabo en la base de datos");
				}
				
				
				TMatriculable leido = daoMatriculable.read(matriculable.getId());
				if (leido == null) {
					id = daoMatriculable.create(matriculable);
					t.commit();
					ResultContexto=new ResultContext(Evento.AltaMatriculable_OK,"El matriculable con id:"+id+" se ha dado de alta correctamente");
				}
				else if (!leido.getActivo()){
					t.commit();//??
					ResultContexto=modificar(matriculable);
					if(ResultContexto.getEvento()==Evento.ModificarMatriculable_OK){
						ResultContexto=new ResultContext(Evento.AltaMatriculable_OK,"El matriculable con id:"+leido.getId()+" se ha activado correctamente");
					}
					else {
						ResultContexto = new ResultContext(Evento.AltaMatriculable_KO, "El matriculable con id:"+leido.getId()+" no se ha activado correctamente");
					}
				}
				else {
					t.rollback();
					ResultContexto = new ResultContext(Evento.AltaMatriculable_KO, "No se ha realizado el alta matriculable correctamente");
				}
			} catch (Exception e) {
				t.rollback();
				ResultContexto = new ResultContext(Evento.AltaMatriculable_KO, "No se ha realizado el alta correctamente debido al error: "+ e.getMessage());
				//throw new Exception(e);
			}
		}else {
			t.rollback();
			return new ResultContext(Evento.AltaMatriculable_KO,"Los campos de matriculable no son validos");
		}
		return ResultContexto;
	}
	@Override
	public ResultContext baja(Integer Id){
		
		int id = -1;
		ResultContext ResultContexto=null;
		Transaction t = null;
		
		t = TransactionManager.getInstance().newTransaction();
		if (t == null)
			return new ResultContext(Evento.AltaMatriculable_KO, "Error transaccional");
		t.begin();
		
		if (Id !=null) {
			DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();

			try {
				TMatriculable m = daoMatriculable.read(Id);
				if (m != null) {
					boolean activo = m.getActivo();
					if (activo) {
						//hay que desvincular tanto matriculas como matriculables y tambien ver si hay matriculas activas
					Collection<TMatricula> matriculas=daoMatricula.readByMatriculable(Id);
					for(TMatricula matricula:matriculas) {
						if(matricula.getActivo()) {
							t.rollback();
							return new ResultContext(Evento.BajaMatriculable_KO,"No se puede dar de baja este matriculable ya que tiene matriculas activas");
						}
					}
						id = daoMatriculable.delete(Id);
						ResultContexto = new ResultContext(Evento.BajaMatriculable_OK, "Se ha realizado correctamente la baja del matriculable "+ id);
						t.commit();
					}
					else {
						ResultContexto = new ResultContext(Evento.BajaMatriculable_KO, "No se ha realizado correctamente la baja (activo) ");
						t.rollback();
					}
				}
				else {
					ResultContexto = new ResultContext(Evento.BajaMatriculable_KO, "No se ha realizado correctamente la baja (No existe) ");
					t.rollback();
				}
			} catch (Exception e) {
				ResultContexto = new ResultContext(Evento.BajaMatriculable_KO, "No se ha realizado correctamente la baja debido al error: " + e.getMessage());
				t.rollback();
				//throw new Exception(e);
			}
		} else {
			ResultContexto = new ResultContext(Evento.BajaMatriculable_KO, "No se ha realizado correctamente la baja (datos) ");
			t.rollback();
		}
		return ResultContexto;

	}
	@Override
	public ResultContext modificar(TMatriculable matriculable){

		Transaction t = null;
		ResultContext ResultContexto=null;
		t = TransactionManager.getInstance().newTransaction();
		
		if(matriculable != null&&camposValidos(matriculable)){
			DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
		try{
			if (t == null)
				throw new Exception("Transaccional");
			t.begin();
			TAsignaturas asignatura = DAOFactory.getInstance().getDAOAsignaturas().read(matriculable.getIdAsignatura());
			TGrupo grupo =DAOFactory.getInstance().getDAOGrupo().read(matriculable.getIdGrupo());
			TAnio anio=DAOFactory.getInstance().getDAOAnio().read(matriculable.getIdAnio());
			

			if(asignatura==null||!asignatura.getActivo()) {
				t.rollback();
				return new ResultContext(Evento.ModificarMatriculable_KO,"La asignatura introducida no existe o no esta actiaba en la base de datos");
			
			}
				
			if(grupo==null||!grupo.getActivo()) {
				t.rollback();
				return new ResultContext(Evento.ModificarMatriculable_KO,"El grupo introducido no existe o no esta actiabo en la base de datos");
			
			}
				
			if(anio==null||!anio.getActivo()) {
				t.rollback();
				return new ResultContext(Evento.ModificarMatriculable_KO,"El anño introducido no existe o no esta actiabo en la base de datos");
				
			}
				
			
				TMatriculable m = daoMatriculable.read(matriculable.getId());
				if(m != null ){
					
					daoMatriculable.update(matriculable);
					ResultContexto=new ResultContext(Evento.ModificarMatriculable_OK,"El matriculable  se ha modificado correctamente");

					t.commit();
				}
				else {
					ResultContexto=new ResultContext(Evento.ModificarMatriculable_KO,"El matriculable que se iba a modificar no existe en la base de datos ");
					t.rollback();
				}
			} catch (Exception e) {
				t.rollback();
				ResultContexto = new ResultContext(Evento.ModificarMatriculable_KO, "No se ha realizado correctamente la modificacion debido al error: " + e.getMessage());

			}
		}else return new ResultContext(Evento.ModificarMatriculable_KO,"Los datos introducidos no son validos");
		return ResultContexto;

	}
	@Override
	public ResultContext read(Integer Id){
		Transaction t = null;
		ResultContext ResultContexto = null;
		t = TransactionManager.getInstance().newTransaction();
		
		if (Id !=null) {
			DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
			try {
				if (t == null)
					throw new Exception("Transaccional");
				t.begin();
				TMatriculable m = daoMatriculable.read(Id);
				if (m != null) {
					if(m.getActivo()) {
						t.commit();
						ResultContexto = new ResultContext(Evento.ReadMatriculable_OK, m);					
					} else {
						ResultContexto = new ResultContext(Evento.ReadMatriculable_KO, "No se ha realizado correctamente la lectura (activo)");
						t.rollback();
					}	
				}
				else {
					ResultContexto = new ResultContext(Evento.ReadMatriculable_KO, "No se ha realizado correctamente la lectura (no existe)");
					t.rollback();
				}
			} catch (Exception e) {
				ResultContexto = new ResultContext(Evento.ReadMatriculable_KO, "No se ha realizado correctamente la lectura debido a un error : "+ e.getMessage());
				t.rollback();
				//throw new Exception(e);
			}
		}else {
			ResultContexto = new ResultContext(Evento.ReadMatriculable_KO, "No se ha realizado correctamente la lectura (datos) ");
			t.rollback();
		}
		return ResultContexto;
	}
	@Override
	public ResultContext listar(){
		Transaction t = null;
		ResultContext ResultContexto = null;
		t = TransactionManager.getInstance().newTransaction();
			
		try {
			if (t == null)
				throw new Exception("Error transaccional");
			t.begin();
			Collection<TMatriculable>matriculables=null;
			matriculables=DAOFactory.getInstance().getDAOMatriculable().readAll();
			ResultContexto = new ResultContext(Evento.ListarMatriculables_OK, matriculables);
			t.commit();
		} catch(Exception e) {
			ResultContexto = new ResultContext(Evento.ListarMatriculables_KO, "No se ha realizado correctamente el listar debido a un error "+ e.getMessage());
			t.rollback();
			//throw new Exception(e);
		}
		return ResultContexto;
	} 
	@Override
	public ResultContext consultarInformacion(Integer Id){
		ResultContext ResultContexto = null;
		TOAMatriculable TOAMat=null;
		Transaction t = null;
		t = TransactionManager.getInstance().newTransaction();
		
		
		if(Id!=null) {
			DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
		
			try {
				if (t == null)
					throw new Exception("Transaccional");
				t.begin();
				TMatriculable matriculable = daoMatriculable.read(Id);
				if (matriculable != null) {
					if (matriculable.getActivo()) {
					TAsignaturas asignatura = DAOFactory.getInstance().getDAOAsignaturas().read(matriculable.getIdAsignatura());
					TGrupo grupo =DAOFactory.getInstance().getDAOGrupo().read(matriculable.getIdGrupo());
					TAnio anio=DAOFactory.getInstance().getDAOAnio().read(matriculable.getIdAnio());
					
					TOAMat=new TOAMatriculable(matriculable,grupo,anio,asignatura);
					ResultContexto = new ResultContext(Evento.ConsultarInformacionMatriculable_OK, TOAMat);
					t.commit();
					}
					else {
						ResultContexto = new ResultContext(Evento.ConsultarInformacionMatriculable_KO, "No se ha realizado correctamente la consulta (activo)");
						t.rollback();
					}
				}
				else {
					ResultContexto = new ResultContext(Evento.ConsultarInformacionMatriculable_KO, "No se ha realizado correctamente la consulta (no existe)");
					t.rollback();
				}
			} catch (Exception e) {
				ResultContexto = new ResultContext(Evento.ConsultarInformacionMatriculable_KO, "No se ha realizado correctamente la consulta debido a un error: " + e.getMessage());
				t.rollback();
				//throw new Exception(e);
			}
		} else {
			ResultContexto = new ResultContext(Evento.ConsultarInformacionMatriculable_KO, "No se ha realizado correctamente la consulta (datos) ");
			t.rollback();
		}
		
		return ResultContexto;
	}
	@Override
	public ResultContext listarPorAnio(Integer anio){
		ResultContext ResultContexto = null;
		Transaction t = null;
		t = TransactionManager.getInstance().newTransaction();
	
		if(anio!=null) {
			try {	
				if (t == null)
					throw new Exception("Transaccional");
				t.begin();
				
				Collection<TMatriculable>matriculables=null;
				TAnio leido=DAOFactory.getInstance().getDAOAnio().read(anio);
				
				if(leido!=null) {
					if(leido.getActivo()) {
						matriculables=DAOFactory.getInstance().getDAOMatriculable().listarMatriculablesPorAnio(anio);
						ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAnio_OK, matriculables);
						t.commit();						
					} else {
						ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAnio_KO, "No se ha realizado correctamente el listar Anio (activo)");
						t.rollback();
					}
				}
				else {
					ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAnio_KO, "No se ha realizado correctamente el listar Anio (no existe)");
					t.rollback();
				}
			} catch(Exception e) {	
				ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAnio_KO, "No se ha realizado correctamente el listar Anio debido a un error: "+e.getMessage());
				t.rollback();
			//	throw new Exception(e);
			}	
		}
		else {
			ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAnio_KO, "No se ha realizado correctamente el listar Anio (datos)");
			t.rollback();
		}
		return ResultContexto;
	}
	@Override
	public ResultContext listarPorGrupo(Integer grupo){
		ResultContext ResultContexto = null;
		Transaction t = null;
		t = TransactionManager.getInstance().newTransaction();
		
		Collection<TMatriculable>matriculables=null;
		if(grupo!=null) {
			try {
				if (t == null)
					throw new Exception("Transaccional");
				t.begin();
				TGrupo leido =DAOFactory.getInstance().getDAOGrupo().read(grupo);
			if(leido!=null) {
				if(leido.getActivo()) {				
					matriculables=DAOFactory.getInstance().getDAOMatriculable().listarMatriculablesPorGrupo(grupo);
					ResultContexto = new ResultContext(Evento.ListarMatriculablePorGrupo_OK, matriculables);
					t.commit();
				}
				else {
					ResultContexto = new ResultContext(Evento.ListarMatriculablePorGrupo_KO, "No se ha realizado correctamente el listar Grupo (activo)");
					t.rollback();
				}
			}
			else {
				ResultContexto = new ResultContext(Evento.ListarMatriculablePorGrupo_KO, "No se ha realizado correctamente el listar Grupo (no existe)");
				t.rollback();
			}
		} catch(Exception e) {
			ResultContexto = new ResultContext(Evento.ListarMatriculablePorGrupo_KO, "No se ha realizado correctamente el listar por Grupo debido un error: "+ e.getMessage());
			t.rollback();
			//throw new Exception(e);
		}		
		}else {
			ResultContexto = new ResultContext(Evento.ListarMatriculablePorGrupo_KO, "No se ha realizado correctamente el listar Grupo (datos)");
			t.rollback();
		}
		return ResultContexto;
	}
	@Override
	public ResultContext listarPorProfesor(Integer idprofesor){
		ResultContext ResultContexto = null;
		Collection<TMatriculable>matriculables=new ArrayList<TMatriculable>();
		TProfesor leido;
		Transaction t = null;
		t = TransactionManager.getInstance().newTransaction();
		if(idprofesor!=null) {
			try {
				if (t == null)
					throw new Exception("Transaccional");
				t.begin();
				leido = DAOFactory.getInstance().getDAOProfesor().read(idprofesor);
				
				if(leido!=null) {
					if(leido.getActivo()) {
						matriculables=DAOFactory.getInstance().getDAOMatriculable().listarmatriculablesporProfesor(idprofesor);
						ResultContexto = new ResultContext(Evento.ListarMatriculablePorProfesor_OK, matriculables);
						t.commit();	
					} else {
						ResultContexto = new ResultContext(Evento.ListarMatriculablePorProfesor_KO, "No se ha realizado correctamente el listar Profesor (activo)");
						t.rollback();
					}
				} else {
					ResultContexto = new ResultContext(Evento.ListarMatriculablePorProfesor_KO, "No se ha realizado correctamente el listar Profesor (no existe)");
					t.rollback();
				}
			} catch(Exception e) {	
				ResultContexto = new ResultContext(Evento.ListarMatriculablePorProfesor_KO, "No se ha realizado correctamente el listar Profesor");
				t.rollback();
				//throw new Exception(e);
			}					
		} else {
			ResultContexto = new ResultContext(Evento.ListarMatriculablePorProfesor_KO, "No se ha realizado correctamente el listar Profesor (datos)");
			t.rollback();
		}
		return ResultContexto;

	}
	@Override
	public ResultContext listarPorAsignatura(Integer Asignatura) {
		ResultContext ResultContexto = null;
		Transaction t = null;
		t = TransactionManager.getInstance().newTransaction();
		try {
		if (t == null)
			throw new Exception("Transaccional");
		t.begin();
		Collection<TMatriculable>matriculables=null;
		TAsignaturas leido;
		if(Asignatura==null) {
			ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAsignatura_KO, "No se ha realizado correctamente el listar Asignatura (datos)");
			t.rollback();
		}
		else {
			
				leido = DAOFactory.getInstance().getDAOAsignaturas().read(Asignatura);
				if(leido!=null) {
					if (leido.getActivo()) {
						matriculables=DAOFactory.getInstance().getDAOMatriculable().listarMatriculablesPorAsignatura(Asignatura);	
						ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAsignatura_OK, matriculables);
						t.commit();
					} else {
						ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAsignatura_KO, "No se ha realizado correctamente el listar Asignatura (activo)");
						t.rollback();
					}
				} else {
					ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAsignatura_KO, "No se ha realizado correctamente el listar Asignatura (no existe)");
					t.rollback();
				}
		}
		}
			 catch(Exception e) {
				ResultContexto = new ResultContext(Evento.ListarMatriculablesPorAsignatura_KO, "No se ha realizado correctamente el listar Asignatura debido al error:" + e.getMessage());
				t.rollback();
				//throw new Exception(e);
			}					
		return ResultContexto;
	} 
	
	
	private boolean camposValidos(TMatriculable matriculable) {
		
		boolean validos= matriculable != null &&matriculable.getIdAnio()>=0&&
				matriculable.getIdGrupo()>=0&& matriculable.getPlazas()>=0&&
				matriculable.getIdAsignatura()>=0&&matriculable.getPrecio()>=0;
		
			return validos;
				
		
	}
}