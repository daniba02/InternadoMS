package Negocio.Restaurante.Producto;

public class ProductoException extends Exception {
	 private static final long serialVersionUID = 1L;

	    public ProductoException() {
	        super(); 
	    }

	    public ProductoException(String message) {
	        super(message); 
	    }

	    public ProductoException(String message, Throwable cause) {
	        super(message, cause); 
	    }

	    public ProductoException(Throwable cause) {
	        super(cause);
	    }

}
