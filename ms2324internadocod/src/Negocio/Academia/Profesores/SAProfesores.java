
package Negocio.Academia.Profesores;

import Negocio.ResultContext;


public interface SAProfesores {

	public ResultContext alta(TProfesor profesor);

	public ResultContext baja(Integer id);

	public ResultContext modificar(TProfesor tProfesor);

	public ResultContext consulta(Integer id) ; // consulta de un TProfesor

	public ResultContext listar() ; // devuelve una colección de TProfesor

	public ResultContext listarProfesorInterino() ; // devuelve una Collection<TInterino>

	public ResultContext listarProfesorFijo(); // devuelve una Collection<TFijo>

	public ResultContext listarProfesorPorMatriculable(Integer idMatriculable) ; // devuelve una colección de TProfesor por idMatriculable

	public ResultContext vincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable) ;

	public ResultContext desvincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable) ;
}