package Presentacion.ApplicationController.Commands.Platos;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandAltaPlatos implements Command {

	@Override
	public Context execute(Object object) {
		TPlatoCompleto toap = (TPlatoCompleto)object;
		SAPlatos sap = SAFactory.getInstance().getSAPlatos();
		ResultContext rc = sap.altaPlato(toap);
		return new Context(rc.getEvento(),rc.getDato());
	}

}
