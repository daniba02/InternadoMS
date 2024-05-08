/**
 * 
 */
package Integracion.Profesores;

import java.util.Collection;

import Negocio.Academia.Profesores.TFijo;
import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;

public interface DAOProfesor {

	public Integer create(TProfesor profesor)throws Exception;

	public Integer delete(Integer id)throws Exception;

	public Integer update(TProfesor leido)throws Exception;

	public TProfesor read(Integer id)throws Exception;

	public TProfesor readByDNI(String dni)throws Exception;

	public Collection<TProfesor> readAll()throws Exception;
	
	public Collection<TFijo> readAllProfesorFijo()throws Exception;
	
	public Collection<TInterino> readAllProfesorInterino()throws Exception;

	public Collection<TProfesor> readByMatriculable(Integer matriculable) throws Exception;
}