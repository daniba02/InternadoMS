package Negocio.Academia.Matricula;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import Integracion.DAOFactory.DAOFactory;
import Integracion.Matricula.DAOMatricula;
import Integracion.MatriculaMatriculable.DAOMatriculaMatriculable;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Integracion.Alumnos.DAOAlumnos;
import Negocio.ResultContext;
import Negocio.Academia.Alumnos.TAlumnos;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.Academia.Matriculable.TOAMatriculable;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;
public class SAMatriculaImp implements SAMatricula {
	@Override
	public ResultContext abrir(TMatricula tmatricula) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		try{
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		if (tmatricula != null) {
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			DAOAlumnos daoAlumno = DAOFactory.getInstance().getDAOAlumnos();
			TAlumnos a = daoAlumno.read(tmatricula.getIdAlumno());
			
				TMatricula leido = daoMatricula.read(tmatricula.getId());
				int id=-1;
				if (leido == null && a != null ){
					if(a.getActivo()){
						if(tmatricula.getDescuento() > 0 && tmatricula.getDescuento() <= 1) {
							id = daoMatricula.create(tmatricula);
							msj = "Se ha realizado el alta del id : "+id+" correctamente";
							cnx = new ResultContext(Evento.AbrirMatricula_OK,msj);
							t.commit();
						}else{
							msj = "No se ha realizado el alta correctamente (descuento)";
							cnx = new ResultContext(Evento.AbrirMatricula_KO,msj);
							t.rollback();
						}
					}else{
						msj = "No se ha realizado el alta correctamente (alumno)";
						cnx = new ResultContext(Evento.AbrirMatricula_KO,msj);
						t.rollback();
					}
				}else if(leido !=null && !leido.getActivo() ){
					leido.setActivo(true);
					leido.setAbierto(true);
					id = daoMatricula.update(leido);
					msj = "Se ha realizado el alta del id : "+id+" correctamente";
					cnx = new ResultContext(Evento.AbrirMatricula_OK,msj);
					t.commit();
				}else{
					msj = "No se ha realizado el alta correctamente (alumno y matricula)";
					cnx = new ResultContext(Evento.AbrirMatricula_KO,msj);
					t.rollback();
				}
			}else{
				t.rollback();
				msj = "No se ha realizado el alta correctamente DATOS MAL PASADOS";
				cnx = new ResultContext(Evento.AbrirMatricula_KO,msj);
			}
		}catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.AbrirMatricula_KO,e.getMessage());
		}
		
		return cnx;
	}
	@Override
	public ResultContext listar() {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		try{
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		Collection<TMatricula> lista = new ArrayList<TMatricula>();
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			lista = daoMatricula.readAll();
			cnx = new ResultContext (Evento.ListarMatricula_OK,lista);
			t.commit();
		}catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.ListarMatricula_KO,e.getMessage());
		}
		return cnx;
		
		
	}
	@Override
	public ResultContext consultar(Integer id) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		TOAMatricula toaMatricula=null;
		ResultContext cnx;
		String msj;
		try{
		if(t == null)
			throw new Exception("Error transaccional");
		
		t.begin();
		if (id != null) {
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			SAMatriculable samatriculable = SAFactory.getInstance().getSAMatriculable();
				TMatricula leido = daoMatricula.read(id);
				if (leido != null) {
					if(!leido.getAbierto() && leido.getActivo()) {
						TAlumnos alumno=DAOFactory.getInstance().getDAOAlumnos().read(leido.getIdAlumno());
						Collection<TOAMatriculable> toamatriculable = new ArrayList<TOAMatriculable>();
						Collection<TMatriculable> matriculables=DAOFactory.getInstance().getDAOMatriculable().listarmatriculablesporMatricula(leido.getId());
						Iterator<TMatriculable> it= matriculables.iterator();
						while(it.hasNext()){
							TMatriculable m=it.next();
							TOAMatriculable to=(TOAMatriculable) samatriculable.consultarInformacion(m.getId()).getDato();
							toamatriculable.add(to);
						}
						toaMatricula=new TOAMatricula(alumno,leido,toamatriculable);
						cnx = new ResultContext(Evento.ConsultarMatricula_OK,toaMatricula);
						t.commit();
					}
					else {
						msj ="No se ha realizado la consulta(abierto,activo)";
						cnx = new ResultContext(Evento.ConsultarMatricula_KO,msj);
						t.rollback();
					}
				}
				else {
					msj ="No se ha realizado la consulta(no existe)";
					cnx = new ResultContext(Evento.ConsultarMatricula_KO,msj);
					t.rollback();
				}
			}else {
				msj ="No se ha realizado la consulta(datos)";
				cnx = new ResultContext(Evento.ConsultarMatricula_KO,msj);
				t.rollback();
			}
		}catch (Exception e) {
			msj=e.getMessage();
			cnx = new ResultContext(Evento.ConsultarMatricula_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext modificar(TMatricula tmatricula) {
		int id=-1;
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try{
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		if(tmatricula != null){
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
				TMatricula leido = daoMatricula.read(tmatricula.getId());
				if(leido != null ){
					if(leido.getActivo() && !leido.getAbierto()){
						if(tmatricula.getDescuento()<=0||tmatricula.getDescuento()>1){
							msj = "No se ha realizado correctamente la modificación (descuento) ";
							cnx = new ResultContext(Evento.ModificarMatricula_KO,msj);
							t.rollback();
						}else{
							Integer precioTotalAux=(int)(leido.getPreciototal()/leido.getDescuento());
							leido.setDescuento(tmatricula.getDescuento());
							leido.setPreciototal((int)(precioTotalAux*leido.getDescuento()));
							id = daoMatricula.update(leido);
							msj = "Se ha realizado correctamente la modificación del id :"+id;
							cnx = new ResultContext(Evento.ModificarMatricula_OK,msj);
							t.commit();
						}
					
					}else{
						msj = "No se ha realizado correctamente la modificación (activo,abierto) ";
						cnx = new ResultContext(Evento.ModificarMatricula_KO,msj);
						t.rollback();
					}
				}else{
					msj = "No se ha realizado correctamente la modificación (no existe) ";
					cnx = new ResultContext(Evento.ModificarMatricula_KO,msj);
					t.rollback();
				}
			}else{
				t.rollback();
				msj = "No se ha realizado correctamente la modificación (datos)";
				cnx = new ResultContext(Evento.ModificarMatricula_KO,msj);
			}
		}catch (Exception e) {
			t.rollback();
			cnx = new ResultContext(Evento.ModificarMatricula_KO,e.getMessage());
		}
		return cnx;
	}
	@Override
	public ResultContext vincular(Integer idMatricula, Integer idMatriculable) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try{
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		if (idMatricula != null && idMatriculable != null) {
			DAOMatriculaMatriculable daoMM = DAOFactory.getInstance().getDAOMatriculaMatriculable();
				DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TMatricula tmatricula = daoMatricula.read(idMatricula);
				TMatriculable tmatriculable = daoMatriculable.read(idMatriculable);
				if (tmatricula != null && tmatriculable != null){
					if(tmatricula.getAbierto() && tmatricula.getActivo() && tmatriculable.getActivo()) {
						if (daoMM.comprobarVinculacion(idMatricula, idMatriculable) == -1) {
							if(tmatriculable.getPlazas() > 0){
							int id = daoMM.vincularMatriculaMatriculable(idMatricula, idMatriculable);
							tmatricula.setPreciototal((int)(tmatricula.getPreciototal() + (tmatriculable.getPrecio()*tmatricula.getDescuento())));
							tmatriculable.setPlazas(tmatriculable.getPlazas()-1);
							daoMatricula.update(tmatricula);
							daoMatriculable.update(tmatriculable);
							msj ="Se ha realizado correctamente la vinculacion de la matricula :"+id;
							cnx = new ResultContext(Evento.VincularMatriculaMatriculable_OK,msj);
							t.commit();
							}else{
								msj ="No se ha realizado correctamente la vinculacion (no hay plazas)";
								cnx = new ResultContext(Evento.VincularMatriculaMatriculable_KO,msj);
								t.rollback();
							}
						}else{
							msj ="No se ha realizado correctamente la vinculacion (ya exite)";
							cnx = new ResultContext(Evento.VincularMatriculaMatriculable_KO,msj);
							t.rollback();
						}
					}else {
						msj ="No se ha realizado correctamente la vinculacion (baja,cerrar mirar)";
						cnx = new ResultContext(Evento.VincularMatriculaMatriculable_KO,msj);
						t.rollback();
					}
				}else{
					msj ="No se ha realizado correctamente la vinculacion (no existe alguna)";
					cnx = new ResultContext(Evento.VincularMatriculaMatriculable_KO,msj);
					t.rollback();
				}
			}else{
				msj ="No se ha realizado correctamente la vinculacion (datos)";
				cnx = new ResultContext(Evento.VincularMatriculaMatriculable_KO,msj);
			}
		}catch (Exception e) {
			cnx = new ResultContext(Evento.VincularMatriculaMatriculable_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext desvincular(Integer idMatricula, Integer idMatriculable) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try {
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		if (idMatricula != null && idMatriculable != null) {
			DAOMatriculaMatriculable daoMM = DAOFactory.getInstance().getDAOMatriculaMatriculable();
			
				DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TMatricula tmatricula = daoMatricula.read(idMatricula);
				TMatriculable tmatriculable = daoMatriculable.read(idMatriculable);
				
				if (tmatricula != null && tmatriculable != null) {
					if (daoMM.comprobarVinculacion(idMatricula, idMatriculable) == 0) {
						if(tmatricula.getAbierto()){
							int id = daoMM.desvincularMatriculaMatriculable(idMatricula, idMatriculable);
							tmatricula.setPreciototal(tmatricula.getPreciototal() - tmatriculable.getPrecio());
							tmatriculable.setPlazas(tmatriculable.getPlazas()+1);
							daoMatricula.update(tmatricula);
							daoMatriculable.update(tmatriculable);
							msj ="Se ha realizado correcatmente la desvinculación de la matricula :"+id;
							cnx = new ResultContext(Evento.DesvincularMatriculaMatriculable_OK,msj);
							t.commit();
						}else{
							msj ="No se puede desvincular (matricula cerrada)";
							cnx = new ResultContext(Evento.DesvincularMatriculaMatriculable_KO,msj);
							t.rollback();
						}
						
					}else{
						msj ="No se ha realizado correcatmente la desvinculación (no vinculados)";
						cnx = new ResultContext(Evento.DesvincularMatriculaMatriculable_KO,msj);
						t.rollback();
					}
				}else{
					msj ="Se ha realizado correcatmente la desvinculación (no existen)";
					cnx = new ResultContext(Evento.DesvincularMatriculaMatriculable_KO,msj);
					t.rollback();
				}
			}else{
				msj ="Se ha realizado correctamente la desvinculación (datos)";
				cnx = new ResultContext(Evento.DesvincularMatriculaMatriculable_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			cnx = new ResultContext(Evento.DesvincularMatriculaMatriculable_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext baja(Integer idMatricula) {
		
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try {
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		if (idMatricula != null) {
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			
				TMatricula leido = daoMatricula.read(idMatricula);
				if (leido != null) {
					boolean activo = leido.getActivo();
					boolean abierto = leido.getAbierto();
					if (activo && !abierto) {
						int id = daoMatricula.delete(idMatricula);
						msj ="Se ha realizado correctamente la baja de la matricula :"+id;
						cnx = new ResultContext(Evento.BajaMatricula_OK,msj);
						t.commit();
					}else{
						msj ="No se ha realizado correctamente la baja (activo,abierto)";
						cnx = new ResultContext(Evento.BajaMatricula_KO,msj);
						t.rollback();
					}
				}else{
					msj ="No se ha realizado correctamente la baja (no existe)";
					cnx = new ResultContext(Evento.BajaMatricula_KO,msj);
					t.rollback();
				}
			}else{
				msj ="No se ha realizado correctamente la baja (datos)";
				cnx = new ResultContext(Evento.BajaMatricula_KO,msj);
				t.rollback();
			}
		} catch (Exception e) {
			cnx = new ResultContext(Evento.BajaMatricula_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext consultarNota(Integer idMatricula,Integer idMatriculable) {
		
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try{
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		if (idMatricula != null && idMatriculable != null) {
			DAOMatriculaMatriculable daoMM = DAOFactory.getInstance().getDAOMatriculaMatriculable();
				
				DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TMatricula tmatricula = daoMatricula.read(idMatricula);
				TMatriculable tmatriculable = daoMatriculable.read(idMatriculable);
				if (tmatricula != null && tmatriculable != null){
					if(tmatriculable.getActivo()) {
						Integer id = daoMM.muestraNota(idMatricula,idMatriculable);
						if(id != null){
							cnx = new ResultContext(Evento.ConsultarNota_OK,id);
							t.commit();
						}else {
							msj ="No se ha realizado correctamente la consulta de la nota(no vinculados)";
							cnx = new ResultContext(Evento.ConsultarNota_KO,msj);
							t.rollback();
						}
					}else {
						msj ="No se ha realizado correctamente la consulta de la nota(abierto,activo)";
						cnx = new ResultContext(Evento.ConsultarNota_KO,msj);
						t.rollback();
					}
				}else{
					msj ="No se ha realizado correctamente la consulta de la nota(tranfers null)";
					cnx = new ResultContext(Evento.ConsultarNota_KO,msj);
					t.rollback();
				}
			}else{
				msj ="No se ha realizado correctamente la consulta de la nota(datos)";
				cnx = new ResultContext(Evento.ConsultarNota_KO,msj);
				t.rollback();
			}
		}catch (Exception e) {
			cnx = new ResultContext(Evento.ConsultarNota_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext mostrarMatriculasporAlumno(Integer idAlumno) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		try{
			if(t == null)
				throw new Exception("Error transaccional");
			t.begin();
			Collection<TMatricula> lista = new ArrayList<TMatricula>();
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			lista = daoMatricula.readByAlumno(idAlumno);
			cnx = new ResultContext(Evento.MostrarMatriculasporAlumno_OK,lista);
			t.commit();
		} catch (Exception e) {
			cnx = new ResultContext(Evento.MostrarMatriculasporAlumno_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext mostrarMatriculasporMatriculable(Integer idMatriculable) {
		
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		try{
			if(t == null)
				throw new Exception("Error transaccional");
			t.begin();
			Collection<TMatricula> lista = new ArrayList<TMatricula>();
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			lista = daoMatricula.readByMatriculable(idMatriculable);
			cnx = new ResultContext(Evento.MostrarMatriculasporMatriculable_OK,lista);
			t.commit();
		}catch (Exception e) {
			cnx = new ResultContext(Evento.MostrarMatriculasporMatriculable_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext cerrar(Integer idMatricula) {
		int id = -1;
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try {
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		if (idMatricula != null) {
			DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
			
				TMatricula leido = daoMatricula.read(idMatricula);
				if (leido != null) {
					if(leido.getAbierto() && leido.getActivo()){
					id= daoMatricula.close(idMatricula);
					msj ="Se ha cerrado correctamente de la matricula :"+id;
					cnx = new ResultContext(Evento.CerrarMatricula_OK,msj);
					t.commit();
					}else{
						msj ="No se ha cerrado correctamente (activo,abierto)";
						cnx = new ResultContext(Evento.CerrarMatricula_KO,msj);
						t.rollback();
					}
				}else{
					msj ="No se ha cerrado correctamente (no existe)";
					cnx = new ResultContext(Evento.CerrarMatricula_KO,msj);
					t.rollback();
				}
			}else{
				msj ="No se ha cerrado correctamente (datos)";
				cnx = new ResultContext(Evento.CerrarMatricula_KO,msj);
				t.rollback();
			}
		}catch (Exception e) {
			cnx = new ResultContext(Evento.CerrarMatricula_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
	@Override
	public ResultContext calificarMatriculableMatricula(Integer idMatricula, Integer idMatriculable, Integer nota) {
		Transaction t = TransactionManager.getInstance().newTransaction();
		ResultContext cnx;
		String msj;
		try {
		if(t == null)
			throw new Exception("Error transaccional");
		
		t.begin();
		
		if (idMatricula != null && idMatriculable != null && nota !=null) {
			DAOMatriculaMatriculable daoMM = DAOFactory.getInstance().getDAOMatriculaMatriculable();
			
				
				DAOMatricula daoMatricula = DAOFactory.getInstance().getDAOMatricula();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TMatricula tmatricula = daoMatricula.read(idMatricula);
				TMatriculable tmatriculable = daoMatriculable.read(idMatriculable);
				if (tmatricula != null && tmatriculable != null){
					if(daoMM.comprobarVinculacion(idMatricula, idMatriculable) != -1){
						if(!tmatricula.getAbierto() && tmatricula.getActivo() && tmatriculable.getActivo()) {
							int id = daoMM.calificar(idMatricula,idMatriculable,nota);
							msj ="Se ha calificado correctamente en la matricula :"+id;
							cnx = new ResultContext(Evento.CalificarMatriculableMatricula_OK,msj);
							t.commit();
						} else {
							msj ="No se ha calificado correctamente(activo,abierto)";
							cnx = new ResultContext(Evento.CalificarMatriculableMatricula_KO,msj);
							t.rollback();
						}
					}else{
						msj ="No se ha calificado correctamente(no vinculados)";
						cnx = new ResultContext(Evento.CalificarMatriculableMatricula_KO,msj);
						t.rollback();
					}
				}else{
					msj ="No se ha calificado correctamente(transfers null)";
					cnx = new ResultContext(Evento.CalificarMatriculableMatricula_KO,msj);
					t.rollback();
				}
			}else{
				msj="Los datos introducidos no pueden ser nulos";
				cnx= new ResultContext(Evento.CalificarMatriculableMatricula_KO,msj);
				t.rollback();
			}
		}catch (Exception e) {
			cnx = new ResultContext(Evento.CalificarMatriculableMatricula_KO,e.getMessage());
			t.rollback();
		}
		return cnx;
	}
}