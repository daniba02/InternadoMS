package Negocio.Academia.Alumnos;

import Negocio.ResultContext;

public interface SAAlumnos {

	public ResultContext Alta(TAlumnos Alumno);

	public ResultContext Baja(Integer ID);

	public ResultContext Modificar(TAlumnos Alumno);

	public ResultContext Consulta(Integer ID);

	public ResultContext Listar();
	
	public ResultContext ListarNotasAlumno(Integer ID);

	public ResultContext AlumnoAnio(Integer ID);
}