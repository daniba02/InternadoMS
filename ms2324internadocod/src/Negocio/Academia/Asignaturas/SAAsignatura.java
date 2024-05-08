package Negocio.Academia.Asignaturas;

import Negocio.ResultContext;


public interface SAAsignatura {
	public ResultContext Alta(TAsignaturas asignatura);
	public ResultContext Baja(Integer id);
	public ResultContext Modificar(TAsignaturas asignatura);
	public ResultContext Mostrar(Integer id) throws Exception;
	public ResultContext Listar();
	public ResultContext ListarOptativas();
	public ResultContext ListarObligatorias();
	public ResultContext NotaMediaAsignatura(Integer id) throws Exception;
}