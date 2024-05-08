
package Integracion.Anio;

import java.util.Collection;

import Negocio.Academia.Anio.TAnio;

public interface DAOAnio {

	public Integer create(TAnio anio);
	public Integer delete(Integer id);
	public Integer update(TAnio leido);
	public TAnio read(Integer id);
	public TAnio readByAnio(String anio);
	public Collection<TAnio> readAll() throws Exception;
}