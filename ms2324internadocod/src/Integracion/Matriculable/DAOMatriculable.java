package Integracion.Matriculable;

import java.util.Collection;

import Negocio.Academia.Matriculable.TMatriculable;


public interface DAOMatriculable {
	
	public Integer create(TMatriculable matriculable)throws Exception ;

	public Collection<TMatriculable> readAll()throws Exception ;

	public Integer delete(Integer id)throws Exception ;

	public Integer update(TMatriculable Matriculable)throws Exception ;

	public TMatriculable read(Integer ID)throws Exception ;

	public Collection<TMatriculable> listarMatriculablesPorAnio(Integer anio)throws Exception ;

	public Collection<TMatriculable> listarMatriculablesPorGrupo(Integer grupo)throws Exception ;

	public Collection<TMatriculable> listarMatriculablesPorAsignatura(Integer Asignatura)throws Exception ;

	Collection<TMatriculable> listarmatriculablesporMatricula(Integer idmatricula)throws Exception ;

	Collection<TMatriculable> listarmatriculablesporProfesor(Integer idprofesor)throws Exception ;

}