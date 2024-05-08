package Integracion.Matricula;

public class TOA {

	private Integer idMatricula;
	private Integer idAlumno;
	private Integer idMatriculable;
	
	public TOA (Integer idMatricula,Integer idAlumno,Integer idMatriculable){
		this.idAlumno=idAlumno;
		this.idMatricula=idMatricula;
		this.idMatriculable=idMatriculable;
	}
	
	public Integer getidAlumno(){
		return idAlumno;
	}
	
	public void setidAlumno(Integer idAlumno){
		this.idAlumno = idAlumno;
	}
	
	public Integer getidMatricula(){
		return idMatricula;
	}
	
	public void setidMatricula(Integer idMatricula){
		this.idMatricula = idMatricula;
	}
	
	public Integer getidMatriculable(){
		return idMatriculable;
	}
	
	public void setidMatriculable(Integer idMatriculable){
		this.idMatriculable = idMatriculable;
	}
}
