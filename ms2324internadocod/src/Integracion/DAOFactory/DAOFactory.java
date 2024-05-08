/**
 * 
 */
package Integracion.DAOFactory;

import Integracion.Matricula.DAOMatricula;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.ProfesorMatriculable.DAOProfesorMatriculable;
import Integracion.Grupo.DAOGrupo;
import Integracion.MatriculaMatriculable.DAOMatriculaMatriculable;
import Integracion.Asignaturas.DAOAsignaturas;
import Integracion.Profesores.DAOProfesor;
import Integracion.Alumnos.DAOAlumnos;
import Integracion.Anio.DAOAnio;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author Usuario
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public abstract class DAOFactory {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private static DAOFactory instance=null;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public static DAOFactory getInstance() {
		// begin-user-code
		if(instance==null)instance=new DAOFactoryImp();
		return instance;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/

	public abstract DAOMatricula getDAOMatricula();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOMatriculable getDAOMatriculable();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOAnio getDAOAnio();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOGrupo getDAOGrupo() ;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOMatriculaMatriculable getDAOMatriculaMatriculable();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOAsignaturas getDAOAsignaturas() ;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOProfesor getDAOProfesor();
	
	public abstract DAOProfesorMatriculable getDAOProfesorMatriculable();
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public abstract DAOAlumnos getDAOAlumnos();
}