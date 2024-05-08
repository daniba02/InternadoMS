package Negocio.Restaurante.Empleados;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Cocinero")
public class Cocinero extends Empleado {
		
	@Column(name="Especialidad",nullable=false)
	private String especialidad;
	
	@Column(name="Factor",nullable=false)
	private Double factor;
	public Cocinero() {}
	
	public Cocinero(String nombre, Double sueldoPorHora, Double horas, String especialidad){
		super(nombre, sueldoPorHora, horas);
		this.especialidad = especialidad;
	}

	public Cocinero(String nombre, Double sueldoPorHora, Double horas, String especialidad,Double factor){
		super(nombre, sueldoPorHora, horas);
		this.especialidad = especialidad;
		this.factor=factor;
	}
	
	public Cocinero(TEmpleado te){
		super(te.getNombre(),te.getSueldoPorHora(),te.getHoras());
		this.especialidad=((TCocinero)te).getEspecialidad();
		this.factor=((TCocinero)te).getFactor();
	}
	
	public String getEspecialidad() {
		return this.especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Double getFactor() {
		return factor;
	}

	public void setFactor(Double factor) {
		this.factor = factor;
	}
}
