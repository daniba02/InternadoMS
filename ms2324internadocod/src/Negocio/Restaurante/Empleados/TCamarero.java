package Negocio.Restaurante.Empleados;

public class TCamarero extends TEmpleado {

	private Double propinas;
	
	public TCamarero () {}
	
	public TCamarero(Integer id, Integer idTurno, String nombre, Double sueldoPorHora, Double horas, Boolean activo, Double propinas){
		super(id, idTurno, nombre, sueldoPorHora, horas, activo);
		this.propinas = propinas;
	}
	
	//GETTERS
	public Double getPropinas() {
		return propinas;
	}
	
	//SETTERS
	public void setPropinas(Double propinas) {
		this.propinas = propinas;
	}
	
	// PATRON VISITATOR
	@Override
	public void aceptar(VisitanteEmpleados v) {
		v.visitarCamarero(this);
	}
	
	@Override
	public Double calcularNominatotal(){
		return this.getHoras()*this.getSueldoPorHora() + this.getPropinas();
	}
	
}
