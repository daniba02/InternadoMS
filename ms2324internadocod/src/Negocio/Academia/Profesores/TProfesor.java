package Negocio.Academia.Profesores;

public class TProfesor {

	public TProfesor() {
	}

	public TProfesor(Integer id, String dni, Integer edad, String nombreCompleto, boolean activo) {
		this.id = id;
		this.dni = dni;
		this.edad = edad;
		this.nombreCompleto = nombreCompleto;
		this.activo = activo;
	}

	// sin id
	public TProfesor(String dni, Integer edad, String nombreCompleto, boolean activo) {
		this.dni = dni;
		this.edad = edad;
		this.nombreCompleto = nombreCompleto;
		this.activo = activo;
	}

	private Integer id;
	private String dni;
	private Integer edad;
	private String nombreCompleto;
	private boolean activo;

	public String getDNI() {
		return this.dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public Integer getEdad() {
		return this.edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	public boolean equals(Object obj) {
		TProfesor p = (TProfesor) obj;
		return this.getId().equals(p.getId()) && this.getDNI().equals(p.getDNI()) && this.getEdad().equals(p.getEdad())
				&& this.getNombreCompleto().equals(p.getNombreCompleto()) && this.getActivo().equals(p.getActivo());
	}
}