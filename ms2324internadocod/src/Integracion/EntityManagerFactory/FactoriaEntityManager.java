package Integracion.EntityManagerFactory;

import javax.persistence.EntityManagerFactory;

public abstract class FactoriaEntityManager {
	private static FactoriaEntityManager instance=null;
	
	public static synchronized FactoriaEntityManager getInstance(){
		if(instance==null){
			instance = new FactoriaEntityManagerImp();
		}
		return instance;
	}
	
	public abstract EntityManagerFactory getEntityManagerFactory();
}
