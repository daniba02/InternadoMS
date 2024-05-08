package Negocio.Academia.Matriculable;

import Negocio.Academia.Anio.TAnio;
import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.Academia.Grupo.TGrupo;

public class TOAMatriculable {
	private TGrupo grupo;
	private TAsignaturas asigantura;
	private TAnio anio;
	private TMatriculable matriculable;
	
	public TOAMatriculable(TMatriculable matriculable,TGrupo grupo,TAnio anio,TAsignaturas asignatura) {
		this.anio =anio;
		this.asigantura=asignatura;
		this.matriculable=matriculable;
		this.grupo=grupo;
	}

	public TGrupo getGrupo() {
		return grupo;
	}

	public TAsignaturas getAsignatura() {
		return asigantura;
	}

	public TAnio getAnio() {
		return anio;
	}

	public TMatriculable getMatriculable() {
		return matriculable;
	}
	@Override
	public boolean equals(Object o) {
		TOAMatriculable b=(TOAMatriculable) o;
		return this.matriculable.equals(b.getMatriculable())
				&&this.asigantura.equals(b.getAsignatura())&&
				this.grupo.equals(b.getGrupo())&&
				this.anio.equals(b.getAnio());
	}
}
