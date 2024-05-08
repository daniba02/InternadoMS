package Presentacion.ApplicationController.Commands.Anio;

import Negocio.ResultContext;
import Negocio.Academia.Anio.SAAnio;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarAnio implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAAnio sa = SAFactory.getInstance().getSAAnio();
		rescontext = sa.listar();
		return new Context(rescontext.getEvento(),rescontext.getDato());
	}

}
