package Presentacion.ApplicationController.Commands.Productos;

import Negocio.ResultContext;
import Negocio.Restaurante.Producto.SAProductos;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;


public class CommandListarProductosPorPlato implements Command {

	@Override
	public Context execute(Object object) {
		SAProductos sp=SAFactory.getInstance().getSAProducto();
	
			ResultContext rs=sp.listarProductosPorPlato((Integer)object);
		
		return new Context(rs.getEvento(),rs.getDato());
		}

}
