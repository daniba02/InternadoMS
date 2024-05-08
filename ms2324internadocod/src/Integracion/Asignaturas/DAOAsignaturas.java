package Integracion.Asignaturas;

import java.util.ArrayList;

import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Asignaturas.TOptativa;

public interface DAOAsignaturas {

	public Integer create(TAsignaturas asignatura);

	public Integer delete(Integer id);

	public Integer update(TAsignaturas asignatura);

	public TAsignaturas read(Integer id);
	
	public TAsignaturas readByName(String nombre);

	public ArrayList<TAsignaturas> readAll();

	public ArrayList<TObligatoria> readAllObligatorias();

	public ArrayList<TOptativa> readAllOptativas();
}