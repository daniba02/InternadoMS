/**
 * 
 */
package Integracion.Query;

public abstract class FactoriaQuery {

	private static FactoriaQuery instancia;

	public static FactoriaQuery getInstancia() {
		if (instancia == null) {
			instancia = new FactoriaQueryImp();
		}
		return instancia;
	}

	public Query nuevaQuery(String nombre){
		switch(nombre) {
		case "listarnotasalumnos":
			return listarNotasAlumno();
		case "notamediaasignatura":
			return notaMediaAsignatura();
		case "alumnosMatriculadosAnio":
			return alumnosMatriculadosAnio();
		default:
			return null;
		}
	}
	
	public abstract ListarNotasAlumno listarNotasAlumno();
	
	public abstract NotaMediaAsignatura notaMediaAsignatura();
	
	public abstract AlumnosMatriculadosAnio alumnosMatriculadosAnio();
}