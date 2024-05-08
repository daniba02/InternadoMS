package Integracion.ProfesorMatriculable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;

public class DAOProfesorMatriculableImp implements DAOProfesorMatriculable {

	public Integer vincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable) {

		int id = -1;
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO profesor_matriculable (idprofesor,idmatriculable) VALUES (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, idProfesor);
			ps.setInt(2, idMatriculable);
			ps.executeUpdate();
			id = idProfesor;
			if (ps != null)
				ps.close();
		} catch (Exception e) {
		}
		return id;
	}

	public Integer desvincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable) {

		int id = -1;
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"DELETE FROM  profesor_matriculable WHERE idprofesor=? AND idmatriculable=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, idProfesor);
			ps.setInt(2, idMatriculable);
			ps.executeUpdate();
			id = idProfesor;
			ps.close();
		} catch (Exception e) {
		}
		return id;
	}

	@Override
	public Integer comprobarVinculacion(Integer idProfesor, Integer idMatriculable) {
		int id = -1;
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM profesor_matriculable WHERE idprofesor=? AND idmatriculable=? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, idProfesor);
			ps.setInt(2, idMatriculable);
			ResultSet res = ps.executeQuery();
			if (res.next())
				id = 0;
			if (ps != null)
				ps.close();
			if (res != null)
				res.close();
		} catch (Exception e) {
		}
		return id;
	}
}