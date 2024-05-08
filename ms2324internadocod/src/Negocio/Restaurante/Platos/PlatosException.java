package Negocio.Restaurante.Platos;

public class PlatosException extends Exception {
	 private static final long serialVersionUID = 1L;

	    public PlatosException() {
	        super(); 
	    }

	    public PlatosException(String message) {
	        super(message); 
	    }

	    public PlatosException(String message, Throwable cause) {
	        super(message, cause); 
	    }

	    public PlatosException(Throwable cause) {
	        super(cause);
	    }

}
