package Negocio.Restaurante.Mesas;

import Negocio.ResultContext;

public interface SAMesas {
	//ALTA Y BAJA
	public ResultContext Alta(TMesas mesas);	
	public ResultContext Baja(Integer id);
	//MODIFICAR
	public ResultContext Modificar(TMesas mesa); 
	//BUSQUEDA
	public ResultContext Consultar(Integer id);
		
	public ResultContext Listar();
	
	public ResultContext ListarPorEmpleado(Integer idEmpleado);
}
