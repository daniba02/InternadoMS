package Integracion.Matricula;

import java.util.Collection;

import Negocio.Academia.Matricula.TMatricula;



public interface DAOMatricula {

	public Integer create(TMatricula matricula);

	public Integer delete(Integer id); //En los diagramas no tiene parametro id
	
	public Integer close(Integer id); //En los diagramas no tiene parametro id

	public Integer update(TMatricula leido);

	public TMatricula read(Integer id);

	public Collection<TMatricula> readAll();

	public Collection<TMatricula> readByAlumno(Integer idAlumno);

	public Collection<TMatricula> readByMatriculable(Integer matriculable);
}