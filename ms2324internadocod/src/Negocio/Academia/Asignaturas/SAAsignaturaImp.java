package Negocio.Academia.Asignaturas;

import java.util.ArrayList;
import java.util.Collection;

import Integracion.Asignaturas.DAOAsignaturas;
import Integracion.DAOFactory.DAOFactory;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Query.FactoriaQuery;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.ResultContext;
import Negocio.Academia.Matriculable.TMatriculable;
import Presentacion.Evento.Evento;


public class SAAsignaturaImp implements SAAsignatura {

	@Override
	public ResultContext Alta(TAsignaturas asignatura) {

		int id;
		Transaction t = null;
		String str;
		ResultContext cnx;
		try {
			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error transaccional");
			
			t.begin();

			if (asignatura.getNombre() != null) {
				DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();
				TAsignaturas leido = daoAsig.readByName(asignatura.getNombre());
				
				if (leido == null) {
					id = daoAsig.create(asignatura);
					str = ("Se ha realizado el alta de la asignatura" + asignatura.getNombre() + " correctamente con id: " + id + ".");
					cnx = new ResultContext(Evento.AltaAsignaturas_OK, str);
					t.commit();
				} else {
					id=leido.getID();
					if (!asignatura.getActivo()) {
						asignatura.setActivo(true);
						id = daoAsig.update(asignatura);
						str = ("Se ha actualizado la asignatura" + asignatura.getNombre() + " correctamente con id: " + id + ".");
						cnx = new ResultContext(Evento.AltaAsignaturas_OK, str);
						t.commit();
					} else {
						t.rollback();
						str = ("Ya existe la asignatura" + asignatura.getNombre() + " con id: " + id + ".");
						cnx = new ResultContext(Evento.AltaAsignaturas_KO, str);
					}
				}
			} else {
				t.rollback();
				str = ("No se ha dado de alta correctamente la asignatura" + asignatura.getNombre() + " .");
				cnx = new ResultContext(Evento.AltaAsignaturas_KO, str);
			}
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRASACCIONAL");
			cnx = new ResultContext(Evento.AltaAsignaturas_KO, str);
		}

		return cnx;
	}

	@Override
	public ResultContext Baja(Integer id) {

		Transaction t = null;
		String str;
		ResultContext cnx;
		
		try {
			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();

			if (id != null) {
 
				DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();
				DAOMatriculable daoMatriculable = DAOFactory.getInstance().getDAOMatriculable();
				TAsignaturas a=daoAsig.read(id);
				if (a != null) {
					if(a.getActivo()){
						Collection<TMatriculable> matriculables = daoMatriculable.listarMatriculablesPorAsignatura(id);
						for (TMatriculable matriculable : matriculables) {
							if (matriculable.getActivo()) {
								t.rollback();
								return new ResultContext(Evento.BajaAsignaturas_KO, "No se puede dar de baja este grupo ya que tiene matriculables activos");
							}
						}
						daoAsig.delete(id);
						str = ("Se ha realizado la baja de la asignatura con id: " + id + ".");
						cnx = new ResultContext(Evento.BajaAsignaturas_OK, str);
						t.commit();
					}else{
						str = ("La asignatura con id: " + id + " ya estaba inactiva");
						cnx = new ResultContext(Evento.BajaAsignaturas_KO, str);
					}
				} else {
					t.rollback();
					str = ("No existe la asignatura con id: " + id + ".");
					cnx = new ResultContext(Evento.BajaAsignaturas_KO, str);
				}
			} else {
				t.rollback();
				str = ("No se ha dado de baja correctamente la asignatura ERROR DATOS DE ENTRADA");
				cnx = new ResultContext(Evento.BajaAsignaturas_KO, str);
			}
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRANSACIONAL");
			cnx = new ResultContext(Evento.BajaAsignaturas_KO, str);
		}

		return cnx;

	}
	
	@Override
	public ResultContext Modificar(TAsignaturas asignatura) {

		Transaction t = null;
		String str;
		ResultContext cnx;

		try {

			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();

			DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();

			if ( asignatura != null && asignatura.getID() != null && asignatura.getNombre() != null && asignatura.getActivo() != null) {

				TAsignaturas leido = daoAsig.readByName(asignatura.getNombre());
				if (daoAsig.read(asignatura.getID()) == null) {
					t.rollback();
					str = ("No existe la asignatura con el id: " + asignatura.getID());
					cnx = new ResultContext(Evento.ModificarAsignaturas_KO, str);
				}
				else if ( leido != null && leido.getID() != asignatura.getID()) {
					t.rollback();
					str = ("Fallo de lectura");
					cnx = new ResultContext(Evento.ModificarAsignaturas_KO, str);
				}  else {
					daoAsig.update(asignatura);
					t.commit();
					str = ("Se ha actualizado correctamente la asignatura");
					cnx = new ResultContext(Evento.ModificarAsignaturas_OK, str);
				}
				
			} else {
				t.rollback();
				str = ("ERROR DATOS DE ENTRADA");
				cnx = new ResultContext(Evento.ModificarAsignaturas_KO, str);
			}
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRANSACIONAL");
			cnx = new ResultContext(Evento.ModificarAsignaturas_KO, str);
		}

		return cnx;
	}

	@Override
	public ResultContext Mostrar(Integer id) throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		String str;
		ResultContext cnx;
		t.begin();
		TAsignaturas res;

		try {

			DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();

			if (id != null) {
				res = daoAsig.read(id);
				if (res == null) {
					t.rollback();
					str = ("ERROR de lectura la asignatura con id: "+ id + " no existe");
					cnx = new ResultContext(Evento.MostrarAsignatura_KO, str);
				} else {
					if(!res.getActivo()) {
						t.rollback();
						str = ("ERROR de lectura la asignatura con id: "+ id + " no esta activa");
						cnx = new ResultContext(Evento.MostrarAsignatura_KO, str);
					}else {
						t.commit();
						cnx = new ResultContext(Evento.MostrarAsignatura_OK, res);
					}
				}
			}else {
				t.rollback();
				str = ("ERROR DATOS DE ENTRADA");
				cnx = new ResultContext(Evento.MostrarAsignatura_KO, str);
			}
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRANSACIONAL");
			cnx = new ResultContext(Evento.MostrarAsignatura_KO, str);
		}
		return cnx;
	}

	@Override
	public ResultContext Listar() {
		String str;
		ResultContext cnx;
		Transaction t = null;
		ArrayList<TAsignaturas> res = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();

			DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();

			res = daoAsig.readAll();
			if (res == null) {
				t.rollback();
				str = ("ERROR de lectura");
				cnx = new ResultContext(Evento.ListarAsignaturas_KO, str);
			}else{
				str = ("se ha leido correctamente");
				cnx = new ResultContext(Evento.ListarAsignaturas_OK, res);
				t.commit();
			}
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRANSACIONAL");
			cnx = new ResultContext(Evento.ListarAsignaturas_KO, str);
		}

		return cnx;
	}

	@Override
	public ResultContext ListarOptativas() {

		String str;
		ResultContext cnx;
		Transaction t = null;
		ArrayList<TOptativa> res = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();

			DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();

			res = daoAsig.readAllOptativas();
			if (res == null) {
				t.rollback();
				str = ("ERROR de lectura");
				cnx = new ResultContext(Evento.ListarAsignaturasOptativas_KO, str);
			}else{
				str = ("se ha leido correctamente");
				cnx = new ResultContext(Evento.ListarAsignaturasOptativas_OK, res);
				t.commit();
			}

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRANSACIONAL");
			cnx = new ResultContext(Evento.ListarAsignaturasOptativas_KO, str);
		}

		return cnx;

	}

	@Override
	public ResultContext ListarObligatorias() {

		String str;
		ResultContext cnx;
		Transaction t = null;
		ArrayList<TObligatoria> res = null;

		try {

			t = TransactionManager.getInstance().newTransaction();
			if (t == null)
				throw new Exception("Error en la transaccion");
			t.begin();

			DAOAsignaturas daoAsig = DAOFactory.getInstance().getDAOAsignaturas();

			res = daoAsig.readAllObligatorias();
			if (res == null) {
				t.rollback();
				str = ("ERROR de lectura");
				cnx = new ResultContext(Evento.ListarAsignaturasObligatorias_KO, str);
				return cnx;
			}
			
			str = ("se ha leido correctamente");
			cnx = new ResultContext(Evento.ListarAsignaturasObligatorias_OK, res);
			t.commit();

		} catch (Exception e) {
			if (t != null)
				t.rollback();
			str = ("ERROR TRANSACIONAL");
			cnx = new ResultContext(Evento.ListarAsignaturasObligatorias_KO, str);
		}

		return cnx;
	}

	@Override
	public ResultContext NotaMediaAsignatura(Integer id) throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		String msj;
		ResultContext cnx;
		if (t == null)
			throw new Exception("Error en la transaccion");
		t.begin();
		try {
			Integer notamedia;
			if(id!=null){
				TAsignaturas a=DAOFactory.getInstance().getDAOAsignaturas().read(id);
				if(a!=null){
					notamedia=(Integer)FactoriaQuery.getInstancia().notaMediaAsignatura().execute(id);
					if(notamedia!=null){
						cnx=new ResultContext(Evento.NotaMediaAsignatura_OK,notamedia);
						t.commit();
					}else{
						msj="La asignatura no tiene ninguna calificacion";
						cnx=new ResultContext(Evento.NotaMediaAsignatura_KO,msj);
						t.rollback();
					}
				}else{
					msj="No existe ninguna asignatura con ese ID en la BBDD";
					cnx=new ResultContext(Evento.NotaMediaAsignatura_KO,msj);
					t.rollback();
				}
				
			}else{
				msj="No se permite un id nulo";
				cnx=new ResultContext(Evento.NotaMediaAsignatura_KO,msj);
				t.rollback();
			}
		}catch(Exception e){
			t.rollback();
			cnx=new ResultContext(Evento.NotaMediaAsignatura_KO,e.getMessage());
		}
		return cnx;
	}
	
}