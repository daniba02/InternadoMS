package Negocio.Academia.Anio;


public class TAnio {

	private Integer id;
	private String anio;
	private Boolean activo;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnio() {
		return this.anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}
	
	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo=activo;
	}

	public TAnio(Integer id, String anio) {
		this.id = id;
		this.anio = anio;
		this.activo = true;
	}
	
	public TAnio(Integer id, String anio, Boolean activo) {
		this.id = id;
		this.anio = anio;
		this.activo = activo;
	}

	public TAnio(String anio){
		this.anio=anio;
		this.activo=true;
	}
	@Override
	public boolean equals(Object o) {
		TAnio b=(TAnio) o;
		return this.getId().equals(b.getId()) && this.getAnio().equalsIgnoreCase(b.getAnio())
				&& this.getActivo().equals(b.getActivo());
	}
}