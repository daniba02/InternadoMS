/**
 * 
 */
package Integracion.Transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Usuario
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TransactionMySQL implements Transaction {
	/** 
	* (non-Javadoc)
	* @see Transaction#begin()
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	
	static String USER = "root";
    static String CLAVE = "root";
    static String URL = "jdbc:mysql://localhost:3306/academia";
	
	private Connection conn;
	
	TransactionMySQL(){
		try {
			conn = DriverManager.getConnection(URL,USER,CLAVE);
		} catch (SQLException e) {
			conn = null;
			System.out.println("No se ha establecido la conexion");
			System.out.println(e.getMessage());
		}
	}
	
	public void begin() {
		

		if(conn != null){
			try {
				conn.setAutoCommit(false);
				PreparedStatement ps = conn.prepareStatement("set transaction isolation level read committed");
				ps.executeUpdate();
				if(ps!=null)ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** 
	* (non-Javadoc)
	* @see Transaction#commit()
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void commit() {


		if(conn != null){
			try {
				conn.commit();
				conn.close();
				TransactionManager.getInstance().deleteTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** 
	* (non-Javadoc)
	* @see Transaction#rollback()
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void rollback() {
		
		if(conn != null){
			try {
				conn.rollback();
				conn.close();
				TransactionManager.getInstance().deleteTransaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** 
	* (non-Javadoc)
	* @see Transaction#getResource()
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Object getResource() {
		return conn;
	}
}