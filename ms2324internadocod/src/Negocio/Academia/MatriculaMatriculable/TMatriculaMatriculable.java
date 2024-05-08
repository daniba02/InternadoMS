
package Negocio.Academia.MatriculaMatriculable;


public class TMatriculaMatriculable {
	
	private Integer idMatricula;
	
	private Integer idMatriculable;
	
	private Integer nota;

	public TMatriculaMatriculable(Integer idMatricula, Integer idMatriculable,Integer nota) {
		this.idMatricula=idMatricula;
		this.idMatriculable=idMatriculable;
		this.nota=nota;
	}

	public Integer getIdMatricula() {
		return idMatricula;
	}

	public Integer getIdMatriculable() {
		return idMatriculable;
	}
	
	public Integer getNota(){
		return nota;
	}
}