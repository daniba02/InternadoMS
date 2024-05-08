package Negocio.Restaurante.Empleados;

public class TCocinero extends TEmpleado {

	private String especialidad;
	private Double factor;
	
	public TCocinero () {}
	
	public TCocinero(Integer id, Integer idTurno, String nombre, Double sueldoPorHora, Double horas, Boolean activo, String especialidad){
		super(id, idTurno, nombre, sueldoPorHora, horas, activo);
		this.especialidad = especialidad;
	}
	
	public TCocinero(Integer id, Integer idTurno, String nombre, Double sueldoPorHora, Double horas, Boolean activo, String especialidad, Double factor){
		super(id, idTurno, nombre, sueldoPorHora, horas, activo);
		this.especialidad = especialidad;
		this.factor = factor;
	}
	
	//GETTERS
	public String getEspecialidad() {
		return especialidad;
	}
	
	//SETTERS
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	public Double getFactor() {
		return factor;
	}
	
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	//Patron Visitator
	@Override
	public void aceptar(VisitanteEmpleados v){
		v.visitarCocinero(this);
	}
	@Override
	public Double calcularNominatotal(){
		return this.getHoras()*this.getSueldoPorHora()*this.factor;
	}
}
