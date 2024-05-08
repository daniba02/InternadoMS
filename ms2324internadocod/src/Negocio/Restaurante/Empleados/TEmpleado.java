package Negocio.Restaurante.Empleados;

public abstract class TEmpleado {

	private Integer id;
	private Integer turno;
	private String nombre;
	private Double sueldoPorHora;
	private Double horas;
	private Boolean activo;
	
	public TEmpleado () {}
	
	public TEmpleado(Integer id, Integer turno, String nombre, Double sueldoPorHora, Double horas, Boolean activo){
		this.id = id;
		this.turno = turno;
		this.nombre = nombre;
		this.sueldoPorHora = sueldoPorHora;
		this.horas = horas;
		this.activo = activo;
	}
	
	public TEmpleado (Integer id2) {
		this.id=id2;
		this.activo=true;
	}
	
	//GETTERS
	
	public Integer getId() {
		return id;
	}
	public Integer getTurno() {
		return turno;
	}
	public String getNombre() {
		return nombre;
	}
	public Double getSueldoPorHora() {
		return sueldoPorHora;
	}
	public Double getHoras() {
		return horas;
	}
	public Boolean getActivo() {
		return activo;
	}
	
	
	//SETTERS
	
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTurno(Integer turno) {
		this.turno = turno;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setSueldoPorHora(Double sueldoPorHora) {
		this.sueldoPorHora = sueldoPorHora;
	}
	public void setHoras(Double horas) {
		this.horas = horas;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	/*public Empleado toEntity() {
		Empleado e;
		if(this instanceof TCamarero){
			e = new Camarero(this.getNombre(), this.getSueldoPorHora(), this.getHoras(), ((TCamarero)this).getPropinas());
		}else{
			e = new Cocinero(this.getNombre(), this.getSueldoPorHora(), this.getHoras(), ((TCocinero)this).getEspecialidad(),((TCocinero)this).getFactor());
		}
		return e;
	}*/
	
	//Patron visitator
	public abstract void aceptar(VisitanteEmpleados v);

	public Double calcularNominatotal(){
		return null;
	}
}
