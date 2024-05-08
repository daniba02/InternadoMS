package Negocio.Restaurante.Producto;

import Negocio.ResultContext;

public interface SAProductos {

	public ResultContext altaProducto(TProducto producto);
	public ResultContext bajaProducto(Integer id);
	public ResultContext modificarProducto(TProducto producto);
	public ResultContext consultarProducto(Integer id);
	public ResultContext listarProductos();
	public ResultContext listarProductosPorPlato(Integer idPlato);
	
}
