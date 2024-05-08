package Negocio;

import Presentacion.Evento.Evento;

public class ResultContext {
	private Evento evento;
	private Object dato;
	
	public Evento getEvento() {
		return evento;
	}

	public Object getDato() {
		return dato;
	}
	
	public ResultContext(Evento evento, Object dato)
	{
		this.evento = evento;
		this.dato = dato;
	}
}
