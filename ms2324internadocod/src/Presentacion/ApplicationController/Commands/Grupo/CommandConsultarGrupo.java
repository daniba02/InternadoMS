package Presentacion.ApplicationController.Commands.Grupo;

import Negocio.ResultContext;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class CommandConsultarGrupo implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		 Context context;
		SAGrupo sg = SAFactory.getInstance().getSAGrupo();
		try {
			rescontext = sg.consulta((Integer)object);
			context=new Context(rescontext.getEvento(),rescontext.getDato());

		} catch (Exception e) {
			context=new Context(Evento.ConsultaGrupo_KO,e.getMessage());
		}
		return context;
	}

}
