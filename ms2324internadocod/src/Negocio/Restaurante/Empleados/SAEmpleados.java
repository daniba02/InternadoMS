package Negocio.Restaurante.Empleados;

import Negocio.ResultContext;

public interface SAEmpleados {
	
	public ResultContext altaEmpleado(TEmpleado empleado);
	
	public ResultContext bajaEmpleado(Integer id);
	
	public ResultContext modificarEmpleado(TEmpleado empleado);
	
	public ResultContext consultarEmpleado(Integer id);
	
	public ResultContext listCamareros();
	
	public ResultContext listCocineros();
	
	public ResultContext listAllEmpleados();
	
	public ResultContext mostrarEmpleadosPorTurno(Integer idTurno);
}
