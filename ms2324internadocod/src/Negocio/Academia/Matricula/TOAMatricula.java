package Negocio.Academia.Matricula;

import java.util.Collection;

import Negocio.Academia.Alumnos.TAlumnos;
import Negocio.Academia.Matriculable.TOAMatriculable;

public class TOAMatricula {
	private TAlumnos alumno;
	private TMatricula matricula;
	private Collection<TOAMatriculable> matriculables;
	
	public TOAMatricula( TAlumnos alumno, TMatricula matricula, Collection<TOAMatriculable> matriculables) {
		this.alumno=alumno;
		this.matricula=matricula;
		this.matriculables=matriculables;
	}

	public TAlumnos getAlumno() {
		return alumno;
	}

	public TMatricula getMatricula() {
		return matricula;
	}

	public Collection<TOAMatriculable> getMatriculables() {
		return matriculables;
	}
}
