package Integracion.Asignaturas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Asignaturas.TOptativa;

public class DAOAsignaturasImp implements DAOAsignaturas {

	public Integer create(TAsignaturas asignatura) {

		try {

			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			if (conn == null)
				throw new Exception("Error de conexión con la BD");

			PreparedStatement ps = conn.prepareStatement("INSERT INTO asignaturas (nombre, activo) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, asignatura.getNombre());
			ps.setBoolean(2, true);
			ps.executeUpdate();

			ResultSet result = ps.getGeneratedKeys();

			if (result.next()) {

				int id = result.getInt(1);
				if (asignatura instanceof TObligatoria) {
					PreparedStatement ps1;
					ps1 = conn.prepareStatement(
							"INSERT INTO asignaturasobligatorias (idasignatura,itinerario) VALUES (?,?)");
					ps1.setInt(1, id);
					ps1.setString(2, ((TObligatoria) asignatura).getItinerario());
					ps1.executeUpdate();
					ps1.close();
				} else {
					PreparedStatement ps2;
					ps2 = conn.prepareStatement("INSERT INTO asignaturasoptativas (idasignatura,nivel) VALUES (?,?)");
					ps2.setInt(1, id);
					ps2.setInt(2, ((TOptativa) asignatura).getNivel());
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
				result.close();
				return id;
			} else {
				return -1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public Integer delete(Integer id) {

		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement("UPDATE Asignaturas SET activo=false where idasignatura=?");
			ps.setInt(1, id);

			ps.executeUpdate();
			ps.close();
			return id;

		} catch (Exception e) {
			return -1;
		}
	}

	@SuppressWarnings("resource")
	public Integer update(TAsignaturas asignatura) {

		try {

			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement("UPDATE asignaturas SET nombre=? where idasignatura=?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, asignatura.getNombre());
			ps.setInt(2, asignatura.getID());
			ps.executeUpdate();

			if (asignatura instanceof TObligatoria) {

				ps = conn.prepareStatement("UPDATE AsignaturasObligatorias SET itinerario=? where idasignatura=?");

				ps.setString(1, ((TObligatoria) asignatura).getItinerario());
				ps.setInt(2, asignatura.getID());
			} else {
				ps = conn.prepareStatement("UPDATE AsignaturasOptativas SET nivel=? where idasignatura=?");

				ps.setInt(1, ((TOptativa) asignatura).getNivel());
				ps.setInt(2, asignatura.getID());
			}

			ps.executeUpdate();
			ps.close();

			return asignatura.getID();

		} catch (

		Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}

	}

	public TAsignaturas read(Integer id) {

		try {

			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM asignaturas AS a JOIN asignaturasobligatorias AS ao ON a.idasignatura=ao.idasignatura WHERE a.idasignatura=? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				TObligatoria asignatura = new TObligatoria();
				asignatura.setID(rs.getInt(1));
				asignatura.setNombre(rs.getString(2));
				asignatura.setActivo(rs.getBoolean(3));
				asignatura.setItinerario(rs.getString(5));

				rs.close();
				ps.close();
				return asignatura;

			} else {
				ps = conn.prepareStatement(
						"SELECT * FROM Asignaturas AS a JOIN AsignaturasOptativas AS ao ON a.idasignatura=ao.idasignatura WHERE a.idasignatura=? FOR UPDATE");
				ps.setInt(1, id);
				rs = ps.executeQuery();

				if (rs.next()) {
					TOptativa asignatura = new TOptativa();
					asignatura.setID(rs.getInt(1));
					asignatura.setNombre(rs.getString(2));
					asignatura.setActivo(rs.getBoolean(3));
					asignatura.setNivel(rs.getInt(5));

					rs.close();
					ps.close();
					return asignatura;
				} else {
					rs.close();
					ps.close();
					return null;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ArrayList<TAsignaturas> readAll() {

		try {

			ArrayList<TAsignaturas> lista = new ArrayList<TAsignaturas>();
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Asignaturas FOR UPDATE");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id;
				String nombre;
				boolean activo;

				id = rs.getInt("idasignatura");
				nombre = rs.getString("nombre");
				activo = rs.getBoolean("activo");

				PreparedStatement ps2 = conn.prepareStatement(
						"SELECT * FROM asignaturasobligatorias WHERE idasignatura = ? FOR UPDATE",
						Statement.RETURN_GENERATED_KEYS);
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();

				if (rs2.next()) {
					String itinerario;
					itinerario = rs2.getString("itinerario");
					lista.add(new TObligatoria(id, activo, nombre, itinerario));
				} else {
					PreparedStatement ps3 = conn.prepareStatement(
							"SELECT * FROM asignaturasoptativas WHERE idasignatura = ? FOR UPDATE",
							Statement.RETURN_GENERATED_KEYS);
					ps3.setInt(1, id);
					ResultSet rs3 = ps3.executeQuery();
					if (rs3.next()) {
						int nivel;
						nivel = rs3.getInt("nivel");
						lista.add(new TOptativa(id, activo, nombre, nivel));
					}
				}
			}
			rs.close();
			ps.close();

			return lista;

		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<TObligatoria> readAllObligatorias() {

		try {

			ArrayList<TObligatoria> lista = new ArrayList<TObligatoria>();
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM Asignaturas AS a JOIN AsignaturasObligatorias AS ao ON a.idasignatura=ao.idasignatura FOR UPDATE");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				TObligatoria asignatura = new TObligatoria();
				asignatura.setID(rs.getInt(1));
				asignatura.setNombre(rs.getString(2));
				asignatura.setActivo(rs.getBoolean(3));
				asignatura.setItinerario(rs.getString(5));

				lista.add(asignatura);

			}

			rs.close();
			ps.close();

			return lista;

		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<TOptativa> readAllOptativas() {
		try {

			ArrayList<TOptativa> lista = new ArrayList<TOptativa>();
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM Asignaturas AS a JOIN AsignaturasOptativas AS ao ON a.idasignatura=ao.idasignatura FOR UPDATE");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				TOptativa asignatura = new TOptativa();
				asignatura.setID(rs.getInt(1));
				asignatura.setNombre(rs.getString(2));
				asignatura.setActivo(rs.getBoolean(3));
				asignatura.setNivel(rs.getInt(5));

				lista.add(asignatura);

			}

			rs.close();
			ps.close();

			return lista;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public TAsignaturas readByName(String nombre) {

		try {

			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();

			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM asignaturas WHERE nombre=? FOR UPDATE");

			ps1.setString(1, nombre);
			ResultSet rs1 = ps1.executeQuery();
			int id = 0;

			if (rs1.next()) {
				id = rs1.getInt(1);
				PreparedStatement ps = conn.prepareStatement(
						"SELECT * FROM asignaturas AS a JOIN asignaturasobligatorias AS ao ON a.idasignatura=ao.idasignatura WHERE a.idasignatura=? FOR UPDATE");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {

					TObligatoria asignatura = new TObligatoria();
					asignatura.setID(rs.getInt(1));
					asignatura.setNombre(rs.getString(2));
					asignatura.setActivo(rs.getBoolean(3));
					asignatura.setItinerario(rs.getString(5));

					rs.close();
					ps.close();
					return asignatura;

				} else {
					ps = conn.prepareStatement(
							"SELECT * FROM Asignaturas AS a JOIN AsignaturasOptativas AS ao ON a.idasignatura=ao.idasignatura WHERE a.idasignatura=? FOR UPDATE");
					ps.setInt(1, id);
					rs = ps.executeQuery();

					if (rs.next()) {
						TOptativa asignatura = new TOptativa();
						asignatura.setID(rs.getInt(1));
						asignatura.setNombre(rs.getString(2));
						asignatura.setActivo(rs.getBoolean(3));
						asignatura.setNivel(rs.getInt(5));

						rs.close();
						ps.close();
						return asignatura;
					} else {
						rs.close();
						ps.close();
						return null;
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}
}