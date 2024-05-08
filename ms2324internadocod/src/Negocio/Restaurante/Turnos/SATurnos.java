package Negocio.Restaurante.Turnos;

import Negocio.ResultContext;

public interface SATurnos {
	
	public ResultContext Alta(TTurno Turno);

	public ResultContext Baja(Integer ID);

	public ResultContext Modificar(TTurno Turno);

	public ResultContext Consultar(Integer ID);

	public ResultContext Listar();
}
