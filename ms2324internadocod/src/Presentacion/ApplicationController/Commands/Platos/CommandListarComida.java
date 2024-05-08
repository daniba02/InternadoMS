package Presentacion.ApplicationController.Commands.Platos;

import Negocio.ResultContext;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarComida implements Command {

	@Override
	public Context execute(Object object) {
		SAPlatos sap = SAFactory.getInstance().getSAPlatos();
		ResultContext rs = sap.listComida();
		return new Context(rs.getEvento(),rs.getDato());
	}

}
