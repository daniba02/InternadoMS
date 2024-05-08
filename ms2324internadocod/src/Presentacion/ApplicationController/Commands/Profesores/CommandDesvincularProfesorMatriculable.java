package Presentacion.ApplicationController.Commands.Profesores;

import Negocio.ResultContext;
import Negocio.Academia.ProfesorMatriculable.TProfesorMatriculable;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;


public class CommandDesvincularProfesorMatriculable implements Command{

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAProfesores sa = SAFactory.getInstance().getSAProfesores();
			TProfesorMatriculable tpm = (TProfesorMatriculable) object;
			rescontext = sa.desvincularProfesorMatriculable(tpm.getIdProfesor(), tpm.getIdMatriculable());
		return new Context(rescontext.getEvento(),rescontext.getDato());
		
	}

}
