package Negocio.Academia.Anio;

import Negocio.ResultContext;

public interface SAAnio {

	public ResultContext alta(TAnio anio);

	public ResultContext baja(Integer id);

	public ResultContext modificacion(TAnio anio);

	public ResultContext consulta(Integer id);
	
	public ResultContext listar();
}