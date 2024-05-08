package Presentacion.ApplicationController.Commands.Matricula;

import Negocio.ResultContext;
import Negocio.Academia.Matricula.SAMatricula;
import Negocio.Academia.MatriculaMatriculable.TMatriculaMatriculable;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandConsultarNotaMatricula implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		TMatriculaMatriculable tmm = (TMatriculaMatriculable)object;
		SAMatricula sm = SAFactory.getInstance().getSAMatricula();
		rescontext = sm.consultarNota(tmm.getIdMatricula(), tmm.getIdMatriculable());
		return new Context(rescontext.getEvento(),rescontext.getDato());
	}
	
}
