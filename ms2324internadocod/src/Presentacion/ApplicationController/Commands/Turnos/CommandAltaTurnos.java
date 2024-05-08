package Presentacion.ApplicationController.Commands.Turnos;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Turnos.SATurnos;
import Negocio.Restaurante.Turnos.TTurno;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandAltaTurnos implements Command{
	
	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		Context context;
		SATurnos sa_turnos = SAFactory.getInstance().getSATurnos();
		
		rescontext = sa_turnos.Alta((TTurno) object);
		context = new Context(rescontext.getEvento(), rescontext.getDato());
		
		return context;
	}
}
