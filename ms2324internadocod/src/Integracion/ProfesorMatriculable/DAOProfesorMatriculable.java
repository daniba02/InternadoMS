/**
 * 
 */
package Integracion.ProfesorMatriculable;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Usuario
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface DAOProfesorMatriculable {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idProfesor
	* @param idMatriculable
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Integer vincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idProfesor
	* @param idMatriculable
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Integer desvincularProfesorMatriculable(Integer idProfesor, Integer idMatriculable);
	
	public Integer comprobarVinculacion(Integer idProfesor, Integer idMatriculable);
}