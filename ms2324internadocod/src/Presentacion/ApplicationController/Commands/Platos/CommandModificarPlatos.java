package Presentacion.ApplicationController.Commands.Platos;

import Negocio.ResultContext;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandModificarPlatos implements Command {

	@Override
	public Context execute(Object object) {
		TPlatoCompleto toap = (TPlatoCompleto)object;
		SAPlatos sap = SAFactory.getInstance().getSAPlatos();
		ResultContext rs = sap.modificar(toap);
		return new Context(rs.getEvento(),rs.getDato());
	}

}
