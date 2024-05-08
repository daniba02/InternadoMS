package Negocio.Restaurante.Ventas;

import Negocio.ResultContext;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;

public interface SAVentas {

	public ResultContext altaVenta(TCarrito carritoDeVenta);
	public ResultContext bajaVenta(Integer idVenta);
	public ResultContext devolucionPlatoVenta(TPlatoVenta platoVenta);
	public ResultContext listar();
	public ResultContext modificacionVenta(TVentas venta);
	public ResultContext consultar(Integer idVenta);
	public ResultContext listarPorCliente(String cliente);
	public ResultContext listarPorMesa(Integer idMesa);

}
