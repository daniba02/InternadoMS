/**
 * 
 */
package Integracion.Transactions;

public abstract class TransactionManager {
	private static TransactionManagerImp instance = null;
	public static TransactionManagerImp getInstance() {
		if(instance == null)
			instance = new TransactionManagerImp();
		return instance;
	}
	public abstract Transaction getTransaction();
	public abstract Transaction newTransaction();
	public abstract void deleteTransaction();
}