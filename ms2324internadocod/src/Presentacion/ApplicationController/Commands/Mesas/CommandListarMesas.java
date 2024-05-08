package Presentacion.ApplicationController.Commands.Mesas;

import Negocio.ResultContext;
import Negocio.Restaurante.Mesas.SAMesas;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarMesas implements Command{

	@Override
	public Context execute(Object object) {
		SAMesas sa = SAFactory.getInstance().getSAMesas();
		ResultContext rc = sa.Listar();
		return new Context(rc.getEvento(),rc.getDato());
	}
}
