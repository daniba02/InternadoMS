/**
 * 
 */
package Negocio.Academia.Asignaturas;

public class TAsignaturas {
	
	public TAsignaturas(){}
	public TAsignaturas(int id, boolean active, String nombre){
		this.id = id;
		this.activo = active;
		this.nombre = nombre;
	}
	public TAsignaturas(int id){
		this.id = id;
	}
	private Integer id;
	private Boolean activo;
	private String nombre;
	
	public Integer getID() {
		return this.id;
	}
	public void setID(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TAsignaturas(String nombre) {
		this.activo = true;
		this.nombre = nombre;
	}
	@Override
	public boolean equals(Object o) {
		TAsignaturas b=(TAsignaturas) o;
		return this.getID().equals(b.getID()) && this.getNombre().equals(b.getNombre())
				&& this.getActivo().equals(b.getActivo());
	}
}