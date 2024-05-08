
package Integracion.MatriculaMatriculable;


public interface DAOMatriculaMatriculable {
	
	public Integer vincularMatriculaMatriculable(Integer idMatricula, Integer idMatriculable);

	public Integer desvincularMatriculaMatriculable(Integer idMatricula, Integer idMatriculable);

	public Integer comprobarVinculacion(Integer idMatricula, Integer idMatriculable);

	public Integer muestraNota(Integer idMatricula, Integer idMatriculable);
	
	public Integer calificar(Integer idMatricula, Integer idMatriculable,Integer nota);
	}