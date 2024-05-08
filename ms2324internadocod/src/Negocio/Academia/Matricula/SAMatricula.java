
package Negocio.Academia.Matricula;

import Negocio.ResultContext;


public interface SAMatricula {
	
	public ResultContext abrir(TMatricula tmatricula);

	public ResultContext listar();

	public ResultContext consultar(Integer id);

	public ResultContext modificar(TMatricula tmatricula);

	public ResultContext vincular(Integer idMatricula, Integer idMatriculable);

	public ResultContext desvincular(Integer idMatricula, Integer idMatriculable);

	public ResultContext baja(Integer idMatricula);
	
	public ResultContext cerrar(Integer idMatricula);

	public ResultContext consultarNota(Integer idMatricula,Integer idMatriculable);

	public ResultContext mostrarMatriculasporAlumno(Integer idAlumno);

	public ResultContext mostrarMatriculasporMatriculable(Integer idMatriculable);
	
	public ResultContext calificarMatriculableMatricula(Integer idMatricula,Integer idMatriculable,Integer nota);
}