package Negocio.Restaurante.Ventas;

public class VentaException extends Exception {

    private static final long serialVersionUID = 1L;

    public VentaException() {
        super(); 
    }

    public VentaException(String message) {
        super(message); 
    }

    public VentaException(String message, Throwable cause) {
        super(message, cause); 
    }

    public VentaException(Throwable cause) {
        super(cause);
    }


}
