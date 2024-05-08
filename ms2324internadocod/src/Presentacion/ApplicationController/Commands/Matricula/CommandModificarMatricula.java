package Presentacion.ApplicationController.Commands.Matricula;

import Negocio.ResultContext;
import Negocio.Academia.Matricula.SAMatricula;
import Negocio.Academia.Matricula.TMatricula;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandModificarMatricula implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAMatricula sm = SAFactory.getInstance().getSAMatricula();
		rescontext = sm.modificar((TMatricula)object);
		return new Context(rescontext.getEvento(),rescontext.getDato());
	}

}
