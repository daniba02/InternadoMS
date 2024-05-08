/**
 * 
 */
package Negocio.Academia.Asignaturas;
import Negocio.Academia.Asignaturas.TAsignaturas;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Usuario
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TObligatoria extends TAsignaturas {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private String itinerario;
	public TObligatoria(int id, boolean activo, String nombre, String itinerario){
		super(id, activo, nombre);
		this.itinerario = itinerario;
	}

	public TObligatoria(){
		
	}
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public String getItinerario() {
		// begin-user-code
		return itinerario;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param it
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void setItinerario(String it) {
		// begin-user-code
		this.itinerario = it;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param itinerario
	* @param nombre
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public TObligatoria(Integer itinerario, String nombre) {
		super(nombre);
		// begin-user-code
		// TODO Auto-generated constructor stub
		// end-user-code
	}
}