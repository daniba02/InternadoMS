package Presentacion.ApplicationController.Commands.Mesas;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Mesas.SAMesas;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandBajaMesa implements Command{

	@Override
	public Context execute(Object object) {
		SAMesas sa = SAFactory.getInstance().getSAMesas();
		ResultContext rc = sa.Baja((Integer) object);
		return new Context(rc.getEvento(),rc.getDato());
	}
}
