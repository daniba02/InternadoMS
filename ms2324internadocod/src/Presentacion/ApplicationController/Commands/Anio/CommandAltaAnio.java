package Presentacion.ApplicationController.Commands.Anio;

import Negocio.ResultContext;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Anio.TAnio;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandAltaAnio implements Command  {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		Context context;
		SAAnio sa = SAFactory.getInstance().getSAAnio();
		rescontext = sa.alta((TAnio)object);
		context=new Context(rescontext.getEvento(),rescontext.getDato());
		return context;
	}

}
