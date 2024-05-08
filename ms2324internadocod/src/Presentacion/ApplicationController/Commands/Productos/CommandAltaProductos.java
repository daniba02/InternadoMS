package Presentacion.ApplicationController.Commands.Productos;

import Negocio.ResultContext;
import Negocio.Restaurante.Producto.SAProductos;
import Negocio.Restaurante.Producto.TProducto;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;


public class CommandAltaProductos implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rs;
		SAProductos sp=SAFactory.getInstance().getSAProducto();
		rs=sp.altaProducto((TProducto)object);
		
		return new Context(rs.getEvento(),rs.getDato());
	}

}
