package Presentacion.ApplicationController.Commands.Profesores;

import Negocio.ResultContext;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.Academia.Profesores.TProfesor;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;


public class CommandAltaProfesores implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext recontext;
		SAProfesores sa = SAFactory.getInstance().getSAProfesores();
		recontext = sa.alta((TProfesor) object);
	
		 return new Context(recontext.getEvento(), recontext.getDato());
		 
	}
}