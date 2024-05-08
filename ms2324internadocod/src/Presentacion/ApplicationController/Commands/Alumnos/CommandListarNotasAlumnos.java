package Presentacion.ApplicationController.Commands.Alumnos;

import Negocio.ResultContext;
import Negocio.Academia.Alumnos.SAAlumnos;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandListarNotasAlumnos implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		Context context;
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();

		rescontext = sa_alumn.ListarNotasAlumno((Integer) object);
		context = new Context(rescontext.getEvento(), rescontext.getDato());

		return context;
	}
}
