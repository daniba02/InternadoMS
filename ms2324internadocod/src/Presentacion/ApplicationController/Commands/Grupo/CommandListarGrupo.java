package Presentacion.ApplicationController.Commands.Grupo;

import Negocio.ResultContext;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarGrupo implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAGrupo sa = SAFactory.getInstance().getSAGrupo();
		rescontext = sa.listar();
		return new Context(rescontext.getEvento(),rescontext.getDato());
	}

}
