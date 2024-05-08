package Presentacion.ApplicationController.Commands.Mesas;

import Negocio.ResultContext;
import Negocio.Restaurante.Mesas.SAMesas;
import Negocio.Restaurante.Mesas.TMesas;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandModificarMesa implements Command{

	@Override
	public Context execute(Object object) {
		SAMesas sa = SAFactory.getInstance().getSAMesas();
		ResultContext rc = sa.Modificar((TMesas)object);
		return new Context(rc.getEvento(),rc.getDato());
	}
}
