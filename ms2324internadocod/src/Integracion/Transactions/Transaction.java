/**
 * 
 */
package Integracion.Transactions;

public interface Transaction {

	public void begin();

	public void commit();

	public void rollback();

	public Object getResource();
}