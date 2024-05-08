package Presentacion.ApplicationController.Commands.Platos;

import Negocio.ResultContext;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.PlatosProductos.TPlatosProductos;
import Negocio.SAFactory.SAFactory;
import Presentacion.ApplicationController.Command;
import Presentacion.ApplicationController.Context;

public class CommandVincularPlatoProducto implements Command {

	@Override
	public Context execute(Object object) {
		SAPlatos sap = SAFactory.getInstance().getSAPlatos();
		TPlatosProductos tpp= (TPlatosProductos)object;
		ResultContext rs = sap.vincularPlatoProducto(tpp.getIdPlato(),tpp.getIdProducto());
		return new Context(rs.getEvento(),rs.getDato());
	}

}
