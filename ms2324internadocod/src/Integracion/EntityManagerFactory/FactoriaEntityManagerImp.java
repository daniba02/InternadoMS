package Integracion.EntityManagerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoriaEntityManagerImp extends FactoriaEntityManager {
	private EntityManagerFactory entityManagerFactory;

	public FactoriaEntityManagerImp(){
		entityManagerFactory=Persistence.createEntityManagerFactory("ms2324internadocod");
	}
	
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManagerFactory;
	}

	public void finalize() {
		this.entityManagerFactory.close();
	}
}

