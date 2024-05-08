package Presentacion.ApplicationController.Commands.Empleados;

import Negocio.ResultContext;
import Negocio.SAFactory.SAFactory;
import Negocio.Restaurante.Empleados.SAEmpleados;
import Negocio.Restaurante.Empleados.TEmpleado;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandModificarEmpleado implements Command {

	@Override
	public Context execute(Object object) {
		SAEmpleados sae = SAFactory.getInstance().getSAEmpleados();
		ResultContext rc = sae.modificarEmpleado((TEmpleado)object);
		return new Context(rc.getEvento(),rc.getDato());
	}

}
