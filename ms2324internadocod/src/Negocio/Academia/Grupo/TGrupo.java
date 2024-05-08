package Negocio.Academia.Grupo;

public class TGrupo {
	
	private Integer id;
	private String letra;
	private Boolean activo;
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLetra() {
		return this.letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}
	
	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo=activo;
	}

	public TGrupo(Integer id, String letra) {
		this.id = id;
		this.letra = letra;
		this.activo = true;
	}
	
	public TGrupo(String letra) {
		this.letra = letra;
		this.activo = true;
	}
	
	public TGrupo(Integer id, String letra, Boolean activo) {
		this.id = id;
		this.letra = letra;
		this.activo = activo;
	}
	@Override
	public boolean equals(Object o) {
		TGrupo b=(TGrupo) o;
		return this.getId().equals(b.getId()) && this.getLetra().equalsIgnoreCase(b.getLetra())
				&& this.getActivo().equals(b.getActivo());
	}
}