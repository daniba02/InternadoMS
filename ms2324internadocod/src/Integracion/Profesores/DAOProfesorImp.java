package Integracion.Profesores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Profesores.TFijo;
import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;

public class DAOProfesorImp implements DAOProfesor {

	@Override
	public Integer create(TProfesor profesor) throws Exception {

		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();

			if (connection == null)
				throw new Exception("Error de conexión con la BD");

			PreparedStatement ps = connection.prepareStatement(
					"INSERT INTO profesores (dni,edad,nombre,activo) VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, profesor.getDNI());
			ps.setInt(2, profesor.getEdad());
			ps.setString(3, profesor.getNombreCompleto());
			ps.setBoolean(4, profesor.getActivo());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				if (profesor instanceof TFijo) {
					PreparedStatement ps1;
					ps1 = connection.prepareStatement("INSERT INTO profesoresfijos (idprofesor) VALUES (?)");
					ps1.setInt(1, id);
					ps1.executeUpdate();
					ps1.close();
				} else {
					PreparedStatement ps2;
					ps2 = connection
							.prepareStatement("INSERT INTO profesoresinterinos (idprofesor,duracion) VALUES (?,?)");
					ps2.setInt(1, id);
					ps2.setInt(2, ((TInterino) profesor).getDuracion());
					ps2.executeUpdate();
					ps2.close();
				}
				ps.close();
				rs.close();
				return id;
			} else {
				return -1;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	@Override
	public Integer delete(Integer id) throws Exception {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();

			PreparedStatement ps = connection.prepareStatement("UPDATE profesores SET activo=false where idprofesor=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
			return id;

		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public Integer update(TProfesor profesor) throws Exception {

		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();
			PreparedStatement ps = connection.prepareStatement(
					"UPDATE profesores SET dni = ?, edad = ?, nombre = ?, activo = ? WHERE idprofesor = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, profesor.getDNI());
			ps.setInt(2, profesor.getEdad());
			ps.setString(3, profesor.getNombreCompleto());
			ps.setBoolean(4, profesor.getActivo());
			ps.setInt(5, profesor.getId());

			ps.executeUpdate();
			if (profesor instanceof TInterino) {

				ps = connection.prepareStatement("UPDATE profesoresinterinos SET duracion=? where idprofesor=?");

				ps.setInt(1, ((TInterino) profesor).getDuracion());
				ps.setInt(2, profesor.getId());
			}
//			else{
//				ps = connection.prepareStatement("UPDATE profesoresfijos SET duracion=? where idprofesor=?");
//			}

			ps.executeUpdate();
			ps.close();

			return profesor.getId();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}

	}

	@Override
	public TProfesor read(Integer id) throws Exception {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();
			PreparedStatement ps = connection.prepareStatement(
					"SELECT * FROM profesores AS p JOIN profesoresfijos AS pf ON p.idprofesor=pf.idprofesor WHERE p.idprofesor = ? FOR UPDATE");

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				TFijo pf = new TFijo();
				pf.setId(rs.getInt("idprofesor"));
				pf.setDNI(rs.getString("dni"));
				pf.setEdad(rs.getInt("edad"));
				pf.setNombreCompleto(rs.getString("nombre"));
				pf.setActivo(rs.getBoolean("activo"));

				rs.close();
				ps.close();
				return pf;

			} else {
				ps = connection.prepareStatement(
						"SELECT * FROM profesores AS p JOIN profesoresinterinos AS pi ON p.idprofesor=pi.idprofesor WHERE p.idprofesor = ? FOR UPDATE");

				ps.setInt(1, id);
				rs = ps.executeQuery();

				if (rs.next()) {

					TInterino pi = new TInterino();
					pi.setId(rs.getInt("idprofesor"));
					pi.setDNI(rs.getString("dni"));
					pi.setEdad(rs.getInt("edad"));
					pi.setNombreCompleto(rs.getString("nombre"));
					pi.setActivo(rs.getBoolean("activo"));
					pi.setDuracion(rs.getInt("duracion"));

					rs.close();
					ps.close();
					return pi;
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

	@Override
	public TProfesor readByDNI(String dni) throws Exception {

		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();
			PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM profesores WHERE dni = ? FOR UPDATE");

			ps1.setString(1, dni);
			ResultSet rs1 = ps1.executeQuery();
			int id = 0;
			if (rs1.next()) {
				id = rs1.getInt(1);
				PreparedStatement ps = connection.prepareStatement(
						"SELECT * FROM profesores AS p JOIN profesoresfijos AS pf ON p.idprofesor=pf.idprofesor WHERE p.idprofesor=? FOR UPDATE");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {

					TFijo pf = new TFijo();
					pf.setId(rs.getInt("idprofesor"));
					pf.setDNI(rs.getString("dni"));
					pf.setEdad(rs.getInt("edad"));
					pf.setNombreCompleto(rs.getString("nombre"));
					pf.setActivo(rs.getBoolean("activo"));
					rs.close();
					ps.close();
					return pf;

				} else {
					ps = connection.prepareStatement(
							"SELECT * FROM profesores AS p JOIN profesoresinterinos AS pi ON p.idprofesor=pi.idprofesor WHERE p.idprofesor = ? FOR UPDATE");

					ps.setInt(1, id);
					rs = ps.executeQuery();

					if (rs.next()) {

						TInterino pi = new TInterino();
						pi.setId(rs.getInt("idprofesor"));
						pi.setDNI(rs.getString("dni"));
						pi.setEdad(rs.getInt("edad"));
						pi.setNombreCompleto(rs.getString("nombre"));
						pi.setActivo(rs.getBoolean("activo"));
						pi.setDuracion(rs.getInt("duracion"));

						rs.close();
						ps.close();
						return pi;
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

	@Override
	public Collection<TProfesor> readAll() throws Exception {
		try {
			Collection<TProfesor> profesores = new LinkedList<TProfesor>();
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM profesores FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String dni, nombreCompleto;
				int id, edad;
				boolean activo;
				id = rs.getInt("idprofesor");
				dni = rs.getString("dni");
				edad = rs.getInt("edad");
				activo = rs.getBoolean("activo");
				nombreCompleto = rs.getString("nombre");

				PreparedStatement ps2 = connection.prepareStatement(
						"SELECT * FROM profesoresinterinos WHERE idprofesor = ?  FOR UPDATE",
						Statement.RETURN_GENERATED_KEYS);
				ps2.setInt(1, id);
				ResultSet rs2 = ps2.executeQuery();
				if (rs2.next()) {
					int duracion;
					duracion = rs2.getInt("duracion");
					profesores.add(new TInterino(id, dni, edad, nombreCompleto, activo, duracion));

				} else {
					profesores.add(new TFijo(id, dni, edad, nombreCompleto, activo));
				}

			}
			rs.close();
			ps.close();

			return profesores;

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Collection<TFijo> readAllProfesorFijo() throws Exception {
		Collection<TFijo> profesores = new LinkedList<TFijo>();
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();
			PreparedStatement ps = connection.prepareStatement(
					"SELECT * FROM profesores AS p JOIN profesoresfijos AS pf ON p.idprofesor=pf.idprofesor FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TFijo pf = new TFijo();
				pf.setId(rs.getInt("idprofesor"));
				pf.setDNI(rs.getString("dni"));
				pf.setEdad(rs.getInt("edad"));
				pf.setNombreCompleto(rs.getString("nombre"));
				pf.setActivo(rs.getBoolean("activo"));
				profesores.add(pf);

			}
			rs.close();
			ps.close();
		} catch (Exception e) {
		}
		return profesores;
	}

	@Override
	public Collection<TInterino> readAllProfesorInterino() throws Exception {

		Collection<TInterino> profesores = new LinkedList<TInterino>();
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection connection = (Connection) t.getResource();
			PreparedStatement ps = connection.prepareStatement(
					"SELECT * FROM profesores AS p JOIN profesoresinterinos AS pi ON p.idprofesor=pi.idprofesor FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TInterino pi = new TInterino();
				pi.setId(rs.getInt("idprofesor"));
				pi.setDNI(rs.getString("dni"));
				pi.setEdad(rs.getInt("edad"));
				pi.setNombreCompleto(rs.getString("nombre"));
				pi.setActivo(rs.getBoolean("activo"));
				pi.setDuracion(rs.getInt("duracion"));
				profesores.add(pi);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
		}
		return profesores;
	}

	@Override
	public Collection<TProfesor> readByMatriculable(Integer matriculable) throws Exception {
		Collection<Integer> listaids = new ArrayList<Integer>();
		Collection<TProfesor> lista = new ArrayList<TProfesor>();
		try {
			TransactionManager tm = TransactionManager.getInstance();
			Transaction t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM profesor_matriculable WHERE idmatriculable=? FOR UPDATE",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, matriculable);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				listaids.add(rs.getInt("idprofesor"));
			}

			for (Integer id : listaids) {
				lista.add(read(id));
			}
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			throw new Exception("La conexion con la base de datos no se ha realizado correctamnete");

		}
		return lista;
	}
}