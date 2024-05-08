package Negocio.Academia.Profesores;

import java.util.Collection;

import Negocio.Academia.Matriculable.TMatriculable;

public class TOAProfesores {
	private TProfesor profesor;
	private Collection<TMatriculable> matriculables;
	
	public TOAProfesores(TProfesor profesor, Collection<TMatriculable> matriculables){
		this.profesor = profesor;
		this.matriculables = matriculables;
	}
	
	public TProfesor getProfesor(){
		return this.profesor;
	}
	
	public Collection<TMatriculable> getMatriculables(){
		return this.matriculables;
	}
}
