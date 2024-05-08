package Negocio.Restaurante.Platos;

import Negocio.ResultContext;

public interface SAPlatos {
	
	public ResultContext altaPlato(TPlatoCompleto toap);
	
	public ResultContext bajaPlato(Integer id);
	
	public ResultContext consultarPlatos(Integer id);
	
	public ResultContext listAllPlatos();
	
	public ResultContext listComida();
	
	public ResultContext listBebida();
	
	public ResultContext listPlatosporProducto(Integer idProducto);
	
	public ResultContext modificar(TPlatoCompleto platos);
	
	public ResultContext vincularPlatoProducto(Integer idPlato, Integer idProducto);
	
	public ResultContext desvincularPlatoProducto(Integer idPlato, Integer idProducto);
	
}
