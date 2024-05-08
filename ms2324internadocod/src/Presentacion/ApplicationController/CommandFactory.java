package Presentacion.ApplicationController;

import Presentacion.Evento.Evento;

public abstract class CommandFactory {
	
	private static CommandFactory instance = null;

	public static synchronized CommandFactory getInstance(){
		if (instance == null) {
			instance = new CommandFactoryImp();
		}
		return instance;
	};

	public abstract Command getCommand(Evento evento);
}