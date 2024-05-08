package Presentacion.ApplicationController;

import Presentacion.IGUI.IGUI;

public abstract class ViewerFactory {

	private static ViewerFactory instance=null;

	public static synchronized ViewerFactory getInstance() {
		
		if(instance==null)instance=new ViewerFactoryIMP();
		return instance;
		
	}
	
	public abstract IGUI generarVista(Context context);
}