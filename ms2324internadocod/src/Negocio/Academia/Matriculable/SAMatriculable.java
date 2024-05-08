
package Negocio.Academia.Matriculable;

import Negocio.ResultContext;

public interface SAMatriculable {

	public ResultContext alta(TMatriculable matriculable) ;

	public ResultContext baja(Integer Id) ;

	public ResultContext modificar(TMatriculable matriculabre) ;

	public ResultContext read(Integer Id) ;
	
	public ResultContext consultarInformacion(Integer Id) ;

	public ResultContext listar() ;

	public ResultContext listarPorAnio(Integer anio) ;

	public ResultContext listarPorGrupo(Integer grupo) ;

	public ResultContext listarPorProfesor(Integer dniprofesor);

	public ResultContext listarPorAsignatura(Integer Asignatura);
}