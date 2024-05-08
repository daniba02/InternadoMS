package Negocio.Restaurante.Empleados;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Camarero extends Empleado{
	
	@Column(name="Propinas",nullable=false)
	private Double propinas;
	
	public Camarero() {}
	
	public Camarero(String nombre, Double sueldoPorHora, Double horas, Double propinas){
		super(nombre, sueldoPorHora, horas);
		this.propinas = propinas;
	}
	
	public Camarero(TEmpleado te){
		super(te.getNombre(),te.getSueldoPorHora(),te.getHoras());
		this.propinas=((TCamarero)te).getPropinas();
	}
	
	public Double getPropinas() {
		return this.propinas;
	}
	public void setPropinas(Double propinas) {
		this.propinas = propinas;
	}
}
