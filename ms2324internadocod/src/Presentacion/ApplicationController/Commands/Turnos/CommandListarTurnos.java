package Presentacion.ApplicationController.Commands.Turnos;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Turnos.SATurnos;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class CommandListarTurnos implements Command{
	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		Context context;
		SATurnos sa_turnos = SAFactory.getInstance().getSATurnos();
		try
		{
			rescontext = sa_turnos.Listar();
			context = new Context(rescontext.getEvento(), rescontext.getDato());
		}
		catch(Exception e)
		{
			context = new Context(Evento.ListarTurnos_KO, e.getMessage());
		}


		return context;
	}
}
