package Integracion.Matriculable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Matriculable.TMatriculable;

public class DAOMatriculableIMP implements DAOMatriculable {

	public Integer create(TMatriculable matriculable) throws Exception {
		int id = -1;// inicialmente en -1
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO matriculable (hora,precio,plazas,idasignatura,idGrupo,idAnio, activo) VALUES (?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, matriculable.getHora());
			ps.setInt(2, matriculable.getPrecio());
			ps.setInt(3, matriculable.getPlazas());
			ps.setInt(4, matriculable.getIdAsignatura());
			ps.setInt(5, matriculable.getIdGrupo());
			ps.setInt(6, matriculable.getIdAnio());
			ps.setBoolean(7, matriculable.getActivo());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();

			if (rs.next())
				id = rs.getInt(1);// obtengo el id generado

			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");
		}
		return id;
	}

	public Collection<TMatriculable> readAll() throws Exception {
		Collection<TMatriculable> mts = new ArrayList<TMatriculable>();
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM matriculable FOR UPDATE");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id, precio, plazas, idAsignatura, idGrupo, idAnio;
				String hora;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idmatriculable");
				precio = rs.getInt("precio");
				plazas = rs.getInt("plazas");
				idAsignatura = rs.getInt("idasignatura");
				idAnio = rs.getInt("idAnio");
				idGrupo = rs.getInt("idGrupo");
				plazas = rs.getInt("plazas");
				hora = rs.getString("hora");
				activo = rs.getBoolean("activo");
				// A�ado el matriculable a la collection
				mts.add(new TMatriculable(id, precio, plazas, hora, idAnio, idAsignatura, idGrupo, activo));
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return mts;
	}

	public Integer delete(Integer ID) throws Exception {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement("UPDATE matriculable SET activo=false where idmatriculable=?");
			ps.setInt(1, ID);
			ps.executeUpdate();
			ps.close();
			return ID;

		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");
		}
	}

	public Integer update(TMatriculable matriculable) throws Exception {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn
					.prepareStatement("UPDATE matriculable SET hora = ?, precio = ?,plazas=?,activo=?,idasignatura=?,idGrupo=?,idAnio=? WHERE idmatriculable = ?");

			ps.setString(1, matriculable.getHora());
			ps.setInt(2, matriculable.getPrecio());
			ps.setInt(3, matriculable.getPlazas());
			ps.setBoolean(4, matriculable.getActivo());
			ps.setInt(5, matriculable.getIdAsignatura());
			ps.setInt(6, matriculable.getIdGrupo());
			ps.setInt(7, matriculable.getIdAnio());
			ps.setInt(8, matriculable.getId());
			ps.executeUpdate();
			ps.close();
			return matriculable.getId();

		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
	}

	public TMatriculable read(Integer ID) throws Exception {
		TMatriculable matriculable = null;// se inicializa a null
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM matriculable WHERE idmatriculable=?");
			ps.setInt(1, ID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id, precio, plazas, idAsignatura, idGrupo, idAnio;
				String hora;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idmatriculable");
				precio = rs.getInt("precio");
				plazas = rs.getInt("plazas");
				idAsignatura = rs.getInt("idasignatura");
				idAnio = rs.getInt("idAnio");
				idGrupo = rs.getInt("idGrupo");
				plazas = rs.getInt("plazas");
				hora = rs.getString("hora");
				activo = rs.getBoolean("activo");
				// A�ado el matriculable a la collection
				matriculable = new TMatriculable(id, precio, plazas, hora, idAnio, idAsignatura, idGrupo, activo);
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());

		}
		return matriculable;
	}

	public Collection<TMatriculable> listarMatriculablesPorAnio(Integer anio) throws Exception {
		Collection<TMatriculable> mts = new ArrayList<TMatriculable>();
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM matriculable WHERE idAnio=? FOR UPDATE");
			ps.setInt(1, anio);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id, precio, plazas, idAsignatura, idGrupo;
				String hora;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idmatriculable");
				precio = rs.getInt("precio");
				plazas = rs.getInt("plazas");
				idAsignatura = rs.getInt("idasignatura");
				idGrupo = rs.getInt("idGrupo");
				plazas = rs.getInt("plazas");
				hora = rs.getString("hora");
				activo = rs.getBoolean("activo");

				// A�ado el matriculable a la collection
				mts.add(new TMatriculable(id, precio, plazas, hora, anio, idAsignatura, idGrupo, activo));
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return mts;
	}

	public Collection<TMatriculable> listarMatriculablesPorGrupo(Integer grupo) throws Exception {
		Collection<TMatriculable> mts = new ArrayList<TMatriculable>();
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM matriculable WHERE idGrupo=? FOR UPDATE");
			ps.setInt(1, grupo);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id, precio, plazas, idAsignatura, idAnio, idGrupo;
				String hora;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idmatriculable");
				precio = rs.getInt("precio");
				plazas = rs.getInt("plazas");
				idAsignatura = rs.getInt("idasignatura");
				hora = rs.getString("hora");
				idGrupo = rs.getInt("idGrupo");
				activo = rs.getBoolean("activo");
				idAnio = rs.getInt("idAnio");
				// A�ado el matriculable a la collection
				mts.add(new TMatriculable(id, precio, plazas, hora, idAnio, idAsignatura, idGrupo, activo));
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return mts;
	}

	public Collection<TMatriculable> listarMatriculablesPorAsignatura(Integer asignatura) throws Exception {
		Collection<TMatriculable> mts = new ArrayList<TMatriculable>();
		try {
			// Creo la transaction
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			// Creo la conexion
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM matriculable WHERE idasignatura=? FOR UPDATE");
			ps.setInt(1, asignatura);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id, precio, plazas, idAsignatura, idAnio, idGrupo;
				String hora;
				boolean activo;
				// Accedo a los atributos de matriculale
				id = rs.getInt("idmatriculable");
				precio = rs.getInt("precio");
				plazas = rs.getInt("plazas");
				idAsignatura = rs.getInt("idasignatura");
				plazas = rs.getInt("plazas");
				hora = rs.getString("hora");
				idGrupo = rs.getInt("idGrupo");
				activo = rs.getBoolean("activo");
				idAnio = rs.getInt("idAnio");
				// A�ado el matriculable a la collection
				mts.add(new TMatriculable(id, precio, plazas, hora, idAnio, idAsignatura, idGrupo, activo));
			}
			 if(ps!=null)ps.close();
			    if(rs!=null)rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return mts;
	}
	@Override
	public Collection<TMatriculable> listarmatriculablesporMatricula(Integer idmatricula) throws Exception {
		Collection<Integer>idsmts=new ArrayList<Integer>();
		Collection<TMatriculable>mts=new ArrayList<TMatriculable>();
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM matricula_matriculable WHERE idmatricula=? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		    ps.setInt(1, idmatricula);
		    ResultSet rs=ps.executeQuery();
		   
		    while(rs.next()) {
		    	idsmts.add(rs.getInt("idmatriculable"));
		    }
		   
		    for(Integer id:idsmts) {
		    mts.add(read(id));
		    }
		 }catch (Exception e) {
				throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");
		 }
		return mts;
	}
	@Override
	public Collection<TMatriculable> listarmatriculablesporProfesor(Integer idprofesor) throws Exception {
		Collection<Integer>idsmts=new ArrayList<Integer>();
		Collection<TMatriculable>mts=new ArrayList<TMatriculable>();
		try{
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();
			
			Connection conn = (Connection) t.getResource();
		    PreparedStatement ps = conn.prepareStatement ("SELECT * FROM profesor_matriculable WHERE idprofesor=? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
		    ps.setInt(1, idprofesor);
		    ResultSet rs=ps.executeQuery();
		   
		    while(rs.next()) {
		    	idsmts.add(rs.getInt("idmatriculable"));
		    }
		   
		    for(Integer id:idsmts) {
		    mts.add(read(id));
		    }
		    if(ps!=null)ps.close();
		    if(rs!=null)rs.close();
		 }catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		 }
		return mts;
	}
}
