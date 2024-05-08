/**
 * 
 */
package Integracion.Transactions;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Usuario
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TransactionFactoryImp extends  TransactionFactory{

	@Override
	public Transaction newTransaction() {
		// TODO Auto-generated method stub
		return new TransactionMySQL();
	}
}