/**
 * 
 */
package Integracion.Transactions;

import java.util.concurrent.ConcurrentHashMap;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author Usuario
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class TransactionManagerImp extends TransactionManager{
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private ConcurrentHashMap<Thread, Transaction> concurrentHashMap;
	
	public TransactionManagerImp(){
		concurrentHashMap = new ConcurrentHashMap<>();
	}
	@Override
	public Transaction getTransaction() {
		Thread th= Thread.currentThread();
		Transaction t = concurrentHashMap.get(th);
		return t;
	}


	@Override
	public Transaction newTransaction() {
		
		
		Thread th = Thread.currentThread();
		Boolean contains = concurrentHashMap.contains(th);
		Transaction t;
		
		if(!contains){
			
			t = TransactionFactory.getInstance().newTransaction();
			concurrentHashMap.put(th, t);
		}else{
			t = concurrentHashMap.get(th);
		}
		
		return t;
		
	}

	@Override
	public void deleteTransaction() {
		Thread th = Thread.currentThread();
		
		Transaction t = concurrentHashMap.get(th);
		
		if(t != null){
			concurrentHashMap.remove(th, t);
		}
	}
}