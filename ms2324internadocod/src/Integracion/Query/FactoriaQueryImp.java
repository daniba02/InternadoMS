package Integracion.Query;

public class FactoriaQueryImp extends FactoriaQuery {

	@Override
	public ListarNotasAlumno listarNotasAlumno() {
		return new ListarNotasAlumno();
	}

	@Override
	public NotaMediaAsignatura notaMediaAsignatura() {
		return new NotaMediaAsignatura();
	}

	@Override
	public AlumnosMatriculadosAnio alumnosMatriculadosAnio() {
		return new AlumnosMatriculadosAnio();
	}

}