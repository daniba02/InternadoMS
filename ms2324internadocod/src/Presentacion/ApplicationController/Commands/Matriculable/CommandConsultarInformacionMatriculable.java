package Presentacion.ApplicationController.Commands.Matriculable;

import Negocio.ResultContext;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandConsultarInformacionMatriculable implements Command {

	@Override
	public Context execute(Object object) {
		ResultContext rescontext;
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		rescontext=sa_mat.consultarInformacion((Integer)object);
		return new Context(rescontext.getEvento(),rescontext.getDato());
	}

}