package Presentacion.ApplicationController.Commands.Ventas;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Ventas.SAVentas;
import Negocio.Restaurante.Ventas.TVentas;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandModificarVenta implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAVentas sa_venta = SAFactory.getInstance().getSAVentas();
		rescontext=sa_venta.modificacionVenta((TVentas)object);
		return new Context(rescontext.getEvento(),rescontext.getDato());
	
	}

}
