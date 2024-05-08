package Presentacion.ApplicationController.Commands.Mesas;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Mesas.SAMesas;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandConsultarMesa implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		Context context;
		SAMesas sa_mesas = SAFactory.getInstance().getSAMesas();

		rescontext = sa_mesas.Consultar((Integer) object);
		context = new Context(rescontext.getEvento(), rescontext.getDato());

		return context;
	}
}
