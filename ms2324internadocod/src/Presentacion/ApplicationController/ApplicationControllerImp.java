package Presentacion.ApplicationController;

import Presentacion.IGUI.IGUI;

public class ApplicationControllerImp extends ApplicationController {
	
	@Override
	public void handleRequest(Context requestContext) {

		Command comando = CommandFactory.getInstance().getCommand(requestContext.getEvento());
		
		if (comando != null) {

			Context commandContext = comando.execute(requestContext.getDato());

			IGUI vista = ViewerFactory.getInstance().generarVista(commandContext);
			vista.actualizar(commandContext);
		} else
			ViewerFactory.getInstance().generarVista(requestContext);
	}
} 