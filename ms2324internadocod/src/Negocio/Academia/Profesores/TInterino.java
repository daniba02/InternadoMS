/**
 * 
 */
package Negocio.Academia.Profesores;

public class TInterino extends TProfesor {
	private Integer duracion;
	
	public TInterino(Integer id, String dni, Integer edad, String nombreCompleto, boolean activo, Integer duracion) {
		super(id, dni, edad, nombreCompleto, activo);
		this.duracion = duracion;
	}
	
	public TInterino(String dni, Integer edad, String nombreCompleto, boolean activo, Integer duracion) {
		super(dni, edad, nombreCompleto, activo);
		this.duracion = duracion;
	}

	public TInterino() {
		// TODO Auto-generated constructor stub
	}

	public Integer getDuracion() {

		return this.duracion;

	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
}