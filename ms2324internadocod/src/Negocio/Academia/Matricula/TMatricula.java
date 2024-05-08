package Negocio.Academia.Matricula;


public class TMatricula {
	
	private Integer id;
	
	private Integer idAlumno;
	
	private Integer preciototal;
	
	private Double descuento;
	
	private Boolean activo;
	
	private Boolean abierto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer Id) {
		id=Id;
	}
	
	public Integer getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(Integer IdAlumno) {
		idAlumno=IdAlumno;
	}
	
	public Integer getPreciototal() {
		return preciototal;
	}

	public void setPreciototal(Integer Preciototal) {
		preciototal=Preciototal;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double Descuento) {
		descuento =Descuento;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean Activo) {
		activo=Activo;
	}
	
	public Boolean getAbierto() {
		return abierto;
	}

	public void setAbierto(Boolean Abierto) {
		abierto = Abierto;
	}
	public TMatricula(Integer id,Integer idAlumno, Integer preciototal, Double descuento) {
		this.id = id;
		this.idAlumno = idAlumno;
		this.preciototal = preciototal;
		this.descuento = descuento;
		this.activo = true;
		this.abierto = true;
	}
	
	public TMatricula(Double descuento, Integer id){
		this.idAlumno=id;
		this.descuento=descuento;
		this.preciototal=0;
		this.activo=true;
		this.abierto=true;
	}
	
	public TMatricula(Integer id, Double descuento){
		this.id=id;
		this.preciototal=0;
		this.descuento=descuento;
		this.activo=true;
		this.abierto=true;
	}
	
	public TMatricula(Integer id, Integer idalumno, Integer preciototal, Double descuento, Boolean abierto,Boolean activo)  { //Transfer de update TMatricula y reads
		this.id = id;
		this.preciototal = preciototal;
		this.descuento = descuento;
		this.abierto = abierto;
		this.activo = activo;
		this.idAlumno=idalumno;
	}
	
}