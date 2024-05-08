package Presentacion.ApplicationController.Commands.Grupo;

import Negocio.ResultContext;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;
import Presentacion.Evento.Evento;

public class CommandModificarGrupo implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		Context context;
		SAGrupo sg = SAFactory.getInstance().getSAGrupo();
		try {
			rescontext = sg.modificacion((TGrupo)object);
			context=new Context(rescontext.getEvento(),rescontext.getDato());

		} catch (Exception e) {
			context=new Context(Evento.ModificacionGrupo_KO,e.getMessage());
		}
		return context;
	}

}
