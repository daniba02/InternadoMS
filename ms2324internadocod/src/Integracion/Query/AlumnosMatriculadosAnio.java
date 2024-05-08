package Integracion.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Alumnos.TAlumnos;


public class AlumnosMatriculadosAnio implements Query {

	public Collection<TAlumnos> execute(Object parametro) {
		
		Collection<TAlumnos> alumnos = new LinkedList<TAlumnos>();
		TransactionManager tm = TransactionManager.getInstance();
		Transaction t=tm.getTransaction();
		Connection connection = (Connection) t.getResource();
		try{
			PreparedStatement ps = connection.prepareStatement("SELECT a.* FROM ALUMNO a JOIN matricula m ON a.idalumno = m.idalumno JOIN matricula_matriculable ma ON m.idmatricula = ma.idmatricula JOIN matriculable mat ON ma.idmatriculable = mat.idmatriculable WHERE mat.idAnio = ? FOR UPDATE", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, (Integer)parametro);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Integer telefono,edad,id; Boolean activo; String DNI,Nombre,Apellido;
				id=rs.getInt("idalumno");
				telefono=rs.getInt("telefono");
				activo=rs.getBoolean("activo");
				DNI=rs.getString("DNI");
				edad=rs.getInt("edad");
				Apellido=rs.getString("apellido");
				Nombre=rs.getString("nombre");
				TAlumnos al=new TAlumnos(id,DNI,Nombre,Apellido,telefono,edad,activo);
				alumnos.add(al);
			}
			if(ps!=null)ps.close();
			if(rs!=null)rs.close();
		}catch(Exception e){}
		
		return alumnos;
	}
}