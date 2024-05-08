package Negocio.Restaurante.Producto;



public class TProducto {
	private Integer id;
	private String nombre;
	private String caracteristicas;
	private boolean activo;

	public TProducto(Integer id, String nombre, String caracteristicas, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.caracteristicas = caracteristicas;
		this.activo = activo;
	}

	public TProducto(String nombre, String caracteristicas, boolean activo) {
		this.nombre = nombre;
		this.caracteristicas = caracteristicas;
		this.activo = activo;
	}

	public TProducto(Integer id) {
		this.id=id;
	}

	public TProducto(Integer id, boolean activo) {
		this.id=id;
		this.activo=activo;
	}

	public Integer getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getCaracteristicas() {
		return this.caracteristicas;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@Override
	public boolean equals(Object obj) {
		TProducto p = (TProducto) obj;
		return this.getId().equals(p.getId()) &&
				this.getNombre().equals(p.getNombre()) &&
				this.getCaracteristicas().equals(p.getCaracteristicas()) &&
				this.getActivo().equals(p.getActivo());
	}
}
