package Presentacion.ApplicationController.Commands.Asignaturas;

import Negocio.ResultContext;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class CommandListarOptativas implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext resContext;
		Context context;
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();
		try {
			resContext=sa.ListarOptativas();
			context = new Context(resContext.getEvento(), resContext.getDato());
		} catch (Exception e) {
			context=new Context(Evento.ListarAsignaturasOptativas_KO,e.getMessage());
		}
		return context;
	}
}
