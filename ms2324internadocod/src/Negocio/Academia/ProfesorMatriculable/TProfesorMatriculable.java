/**
 * 
 */
package Negocio.Academia.ProfesorMatriculable;


public class TProfesorMatriculable {
	
	private Integer idProfesor;
	
	private Integer idMatriculable;

	public Integer getIdProfesor() {

		return this.idProfesor;
	}


	public Integer getIdMatriculable() {
		
		return this.idMatriculable;
		
	}

	public TProfesorMatriculable(Integer idProfesor, Integer idMatriculable) {
		this.idMatriculable=idMatriculable;
		this.idProfesor=idProfesor;
	}
}