
package Negocio.Academia.Matriculable;


public class TMatriculable {

	private Integer id;

	private String hora;

	private Integer precio;

	private Integer plazas;

	private Integer idAsignatura;

	private Integer idGrupo;

	private Integer idAnio;

	private Boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer Id) {
		id = Id;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String Hora) {
		hora = Hora;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer Precio) {
		precio = Precio;
	}

	public Integer getPlazas() {
		return plazas;
	}

	public void setPlazas(Integer Plazas) {
		plazas = Plazas;
	}

	public Integer getIdAsignatura() {
		return idAsignatura;
	}

	public Integer getIdAnio() {
		return idAnio;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}


	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean Activo) {
		activo = Activo;
	}

	public TMatriculable(Integer precio, Integer plazas, String hora) {
		this.precio = precio;
		this.plazas = plazas;
		this.hora = hora;
	}

	public TMatriculable(Integer id, Integer precio, Integer plazas, String hora, Integer idAnio, Integer idAsignatura, Integer idGrupo, Boolean activo) {
		this.id = id;
		this.precio = precio;
		this.plazas = plazas;
		this.hora = hora;
		this.idAnio = idAnio;
		this.idAsignatura = idAsignatura;
		this.idGrupo = idGrupo;
		this.activo = activo;
	}

	public TMatriculable(Integer precio, Integer plazas, String hora, Integer idAnio, Integer idAsignatura, Integer idGrupo, Boolean activo) {
		this.precio = precio;
		this.plazas = plazas;
		this.hora = hora;
		this.idAnio = idAnio;
		this.idAsignatura = idAsignatura;
		this.idGrupo = idGrupo;
		this.activo = activo;
	}
	@Override
	public boolean equals(Object o) {
		TMatriculable m2=(TMatriculable) o;
		return this.getId().equals(m2.getId()) && this.getActivo().equals(m2.getActivo()) && this.getHora().equals(m2.getHora()) &&
				this.getPlazas().equals(m2.getPlazas()) && this.getPrecio().equals(m2.getPrecio());
		
	}

}