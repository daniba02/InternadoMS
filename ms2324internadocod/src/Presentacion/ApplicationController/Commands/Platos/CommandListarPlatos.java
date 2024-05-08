package Presentacion.ApplicationController.Commands.Platos;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Platos.SAPlatos;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarPlatos implements Command {

	@Override
	public Context execute(Object object) {
		SAPlatos sap = SAFactory.getInstance().getSAPlatos();
		ResultContext rs = sap.listAllPlatos();
		return new Context(rs.getEvento(),rs.getDato());
	}

}
