package Negocio.Restaurante.Turnos;

public class TurnoException extends Exception{
    
	private static final long serialVersionUID = 1L;
	
	public TurnoException() {
		super();
	}
	public TurnoException(String message) {
		super(message);
	}
	public TurnoException(String message, Throwable cause) {
		super(message, cause);
	}
	public TurnoException(Throwable cause) {
		super(cause);
	}

}
