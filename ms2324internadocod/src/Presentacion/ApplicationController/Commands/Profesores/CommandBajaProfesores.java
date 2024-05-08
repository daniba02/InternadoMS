package Presentacion.ApplicationController.Commands.Profesores;

import Negocio.ResultContext;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;


public class CommandBajaProfesores implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAProfesores sa = SAFactory.getInstance().getSAProfesores();
			rescontext = sa.baja((Integer) object);
		return new Context(rescontext.getEvento(),rescontext.getDato());
		
	}
}
