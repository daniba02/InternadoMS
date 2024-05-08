package Negocio.Restaurante.Mesas;

public class MesasException extends Exception {
	 private static final long serialVersionUID = 1L;
	 
	    public MesasException() {
	        super(); 
	    }
	    
	    public MesasException(String message) {
	        super(message); 
	    }
	    
	    public MesasException(String message, Throwable cause) {
	        super(message, cause); 
	    }
	    
	    public MesasException(Throwable cause) {
	        super(cause); 
	    }

}
