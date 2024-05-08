package Negocio.Restaurante.Mesas;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import Negocio.Restaurante.Empleados.Empleado;
import Negocio.Restaurante.Ventas.Ventas;

@Entity
@NamedQueries({
	@NamedQuery(name = "Mesas.readAll", query = "select m from Mesas m "),
	@NamedQuery(name = "Negocio.Restaurante.Mesas.Mesas.readByEmpleado", query = "select m from Mesas m where :empleados = m.empleados ")
})

public class Mesas {
	@Version
	private long version;
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer id;
	private int num;
	private int capacidad;
	
	@Column(name="activo", nullable = false)
	private boolean activo;
	
	@ManyToOne
	@JoinColumn(name = "idEmpleados")
	private Empleado empleados;

	@OneToMany(mappedBy="mesa")
	private Collection<Ventas> ventas;

	
	
	public Mesas(){
		
	}
	
	public Mesas(Integer numMesa, Integer capacidad, Integer idEmpleados) {
		this.num = numMesa;
		this.capacidad = capacidad;
		this.empleados = new Empleado();
		this.empleados.setId(idEmpleados);
	}
	
	public Mesas(Integer numMesa, Integer capacidad, Integer idEmpleados, Boolean activo) {
		this.num = numMesa;
		this.capacidad = capacidad;
		this.empleados = new Empleado();
		this.setIDEmpleado(idEmpleados);
		this.activo = activo;
	}

	public Mesas(TMesas tm){
		this.capacidad=tm.getCapacidad();
		this.activo=tm.getActivo();
		this.num=tm.getNum();
		this.empleados=new Empleado();
		this.setIDEmpleado(tm.getIDEmpleado());
	}

	public void setID(int id){
		this.id = id;
	}
	
	public void setNum(int num){
		this.num = num;
	}
	
	public void setCapacidad(int capacidad){
		this.capacidad = capacidad;
	}
	
	public void setIDEmpleado(int idEmpleado){
		this.empleados.setId(idEmpleado);
	}
	
	public void setActivo(boolean b) {
		this.activo = b;
	}
	
	public int getID(){
		return id;
	}
	public int getNum(){
		return num;
	}
	
	public int getCapacidad(){
		return capacidad;
	}
	
	public int getIDEmpleado(){
		return empleados.getId();
	}
	
	public boolean getActivo(){
		return activo;
	}
	
	public long getVersion(){
		return this.version;
	}
	
	public TMesas toTransfer() {
		return new TMesas(id, num, capacidad, empleados.getId(), activo);
	}

	public void setEmpleado(Empleado em) {
		this.empleados = em;
	}
	public void setEmpleados(Empleado empleados) {
		this.empleados = empleados;
	}
	public Empleado getEmpleados() {
		return empleados;
	}

	public void setVentas(Collection<Ventas> ventas) {
		this.ventas = ventas;
	}

	public Collection<Ventas> getVentas() {
		return ventas;
	}
}
