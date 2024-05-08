package Negocio.Restaurante.Ventas;

public class TVentas {

	private Integer id;
	private String cliente;
	private String fecha;
	private Integer idMesa;
	private Boolean activo;
	
	public TVentas(Integer id, String cliente, String fecha, Integer idMesa, Boolean activo) {
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
		this.idMesa = idMesa;
		this.activo = activo;
	}

	public TVentas(String cliente, String fecha, Integer idMesa, Boolean activo) {
		this.cliente = cliente;
		this.fecha = fecha;
		this.idMesa = idMesa;
		this.activo = activo;
	}

	//GETTERS
	public Integer getId() {
		return id;
	}

	public String getCliente() {
		return cliente;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public Integer getIdMesa() {
		return idMesa;
	}

	public Boolean getActivo() {
		return activo;
	}

	//SETTERS
	public void setId(Integer id) {
		this.id = id;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public void setIdMesa(Integer idMesa) {
		this.idMesa = idMesa;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	@Override
	 public boolean equals(Object obj) {
		TVentas v= (TVentas)obj;
		return v.getActivo()==activo&&getActivo()==activo&&v.getId()==id&&v.getIdMesa()==idMesa;
	 }


}
