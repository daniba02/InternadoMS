package Presentacion.ApplicationController;

public interface Command {

	public Context execute(Object object);
}