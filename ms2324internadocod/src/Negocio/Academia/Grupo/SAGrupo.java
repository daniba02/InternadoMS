/**
 * 
 */
package Negocio.Academia.Grupo;

import Negocio.ResultContext;

public interface SAGrupo {

	public ResultContext alta(TGrupo grupo) throws Exception;

	public ResultContext baja(Integer id) throws Exception;

	public ResultContext modificacion(TGrupo grupo) throws Exception;

	public ResultContext consulta(Integer id) throws Exception;

	public ResultContext listar();
}