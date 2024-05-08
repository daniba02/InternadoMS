package Presentacion.ApplicationController.Commands.Ventas;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Ventas.SAVentas;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarVentasPorMesa implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAVentas sa_venta = SAFactory.getInstance().getSAVentas();
		rescontext=sa_venta.listarPorMesa((Integer)object);
		return new Context(rescontext.getEvento(),rescontext.getDato());
	
	}

}
