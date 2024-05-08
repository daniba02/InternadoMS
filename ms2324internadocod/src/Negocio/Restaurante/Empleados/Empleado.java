package Negocio.Restaurante.Empleados;

import java.util.Collection;

import Negocio.Restaurante.Mesas.Mesas;
import Negocio.Restaurante.Turnos.Turnos;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
@NamedQueries({
	@NamedQuery(name = "Negocio.Restaurante.Empleado.Empleado.readByTurno", query = "select e from Empleado e where :turno = e.turno ")
})
@Inheritance(strategy=InheritanceType.JOINED)
public class Empleado {
	 
	@Version
	private long version;
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column(name="idEmpleado",nullable = false)
	private Integer id;
	
	@Column(name="nombre",nullable = false)
	private String nombre;
	@Column(name="sueldoPorHora",nullable = false)
	private Double sueldoPorHora;
	@Column(name="horas",nullable = false)
	private Double horas;
	@Column(name="activo",nullable = false)
	private Boolean activo;
	
	@OneToMany(mappedBy="empleados")
	private Collection<Mesas> mesas;
	
	@ManyToOne
	@JoinColumn(name="idturno")
	private Turnos turno;
	
	//CONSTRUCTORES
	
	public Empleado() {}
	
	public Empleado(String nombre, Double sueldoPorHora, Double horas){
		super();
		this.nombre = nombre;
		this.sueldoPorHora = sueldoPorHora;
		this.horas = horas;
		this.activo = true;
	}
	
	public Empleado(Integer id, String nombre, Double sueldoPorHora, Double horas, Boolean activo){
		super();
		this.id = id;
		this.nombre = nombre;
		this.sueldoPorHora = sueldoPorHora;
		this.horas = horas;
		this.activo = activo;
		
	}
	
	//GETTERS
	
	public Integer getId() {
		return id;
	}
	public Turnos getTurno() {
		return turno;
	}
	public String getNombre() {
		return nombre;
	}
	public Double getsueldoPorHora() {
		return sueldoPorHora;
	}
	public Boolean getActivo() {
		return activo;
	}
	public long getVersion(){
		return this.version;
	}
	
	//SETTERS
	
	public void setHoras(Double horas) {
		this.horas = horas;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setsueldoPorHora(Double sueldoPorHora) {
		this.sueldoPorHora = sueldoPorHora;
	}
	public Double getHoras() {
		return horas;
	}
	public void setTurno(Turnos turno) {
		this.turno = turno;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public void setMesas(Collection<Mesas>mesas){
		this.mesas=mesas;
	}
	
	public int hashCode(){
		return id + nombre.hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof Camarero){
			return (((Camarero)o).getPropinas()==((Camarero)this).getPropinas()&&this.getActivo()==((Camarero)o).getActivo()
					&& this.getId()==((Camarero)o).getId() && this.getHoras()==((Camarero)o).getHoras()&&this.getNombre().equals(((Camarero)o).getNombre())&&
					this.getsueldoPorHora()==((Camarero)o).getsueldoPorHora())&&this.getVersion()==((Camarero)o).getVersion();
		}else{
			return (((Cocinero)o).getEspecialidad()==((Cocinero)this).getEspecialidad()&&this.getActivo()==((Cocinero)o).getActivo()
			&& this.getId()==((Cocinero)o).getId() && this.getHoras()==((Cocinero)o).getHoras()&&this.getNombre().equals(((Cocinero)o).getNombre())&&
			this.getsueldoPorHora()==((Cocinero)o).getsueldoPorHora())&&this.getVersion()==((Cocinero)o).getVersion()&&((Cocinero)o).getFactor()==((Cocinero)this).getFactor();
		}
	}
	
	public TEmpleado toTransfer(){
		TEmpleado e;
		if(this instanceof Camarero){
			e = new TCamarero(this.getId(), this.turno.getId(), this.getNombre(), this.getsueldoPorHora(), this.getHoras(),this.getActivo(), ((Camarero)this).getPropinas());
		}else{
			e = new TCocinero(this.getId(), this.turno.getId(), this.getNombre(), this.getsueldoPorHora(), this.getHoras(),this.getActivo(), ((Cocinero)this).getEspecialidad(),((Cocinero)this).getFactor());
		}
		return e;
	}
	public Collection<Mesas> getMesas() {
		return mesas;
	}
}
