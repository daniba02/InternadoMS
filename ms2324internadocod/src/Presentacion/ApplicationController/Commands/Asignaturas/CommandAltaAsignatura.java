package Presentacion.ApplicationController.Commands.Asignaturas;

import Negocio.ResultContext;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class CommandAltaAsignatura implements Command{

	@Override
	public Context execute(Object object) {
		Context context;
		ResultContext resContext;
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();
		try {
			resContext=sa.Alta((TAsignaturas)object);
			context = new Context(resContext.getEvento(), resContext.getDato());
		} catch (Exception e) {
			context=new Context(Evento.AltaAsignaturas_KO,e.getMessage());
		}
		return context;
	}
}
