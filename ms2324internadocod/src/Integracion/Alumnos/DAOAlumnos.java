package Integracion.Alumnos;

import java.util.Collection;

import Negocio.Academia.Alumnos.TAlumnos;

public interface DAOAlumnos {
	
	public Integer create(TAlumnos alumno);
	public Integer delete(Integer id);
	public Integer update(TAlumnos alumno);
	public TAlumnos read(Integer id);
	public TAlumnos readByDNI(String DNI);
	public Collection<TAlumnos> readAll();
}