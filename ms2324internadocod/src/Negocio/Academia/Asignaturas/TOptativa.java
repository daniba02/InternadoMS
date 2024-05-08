/**
 * 
 */
package Negocio.Academia.Asignaturas;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Usuario
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TOptativa extends TAsignaturas {
	
	public TOptativa(int id, boolean active, String nombre, int nivel){
		super(id, active, nombre);
		this.nivel = nivel;
	}
	
	public TOptativa(){
		
	}
	private Integer nivel;
	public Integer getNivel() {
		return this.nivel;
	}
	public void setNivel(Integer nv) {
		this.nivel = nv;
	}

	public TOptativa(Integer nivel, String nombre) {
		super(nombre);
		this.nivel = nivel;
	}
}