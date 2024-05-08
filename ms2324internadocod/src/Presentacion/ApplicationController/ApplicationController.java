package Presentacion.ApplicationController;

public abstract class ApplicationController {

	private static ApplicationController instance = null;
	public static synchronized ApplicationController getInstancia() {
		if (instance == null) {
			instance = new ApplicationControllerImp();
		}
		return instance;
	}

	public abstract void handleRequest(Context requestContext);
}