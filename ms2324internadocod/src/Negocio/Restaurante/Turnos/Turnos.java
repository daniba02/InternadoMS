package Negocio.Restaurante.Turnos;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import Negocio.Restaurante.Empleados.Empleado;

@Entity
@NamedQueries(
		{ @NamedQuery(name = "Turnos.readAll", query = "select t from Turnos t") }
		
		)

public class Turnos {

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idturno", nullable = false, unique = true)
	private Integer id;
	@Column(name = "nombre", nullable = false, unique = true)

	private String nombre;
	@Column(name = "horaEntrada", nullable = false)
	private String horaEntrada;
	@Column(name = "horaSalida", nullable = false)
	private String horaSalida;
	@Column(name = "activo")
	private Boolean activo;

	@OneToMany(mappedBy = "turno")
	private Collection<Empleado> empleados;

	public Turnos() {
	}

	public Turnos(String nombre, String horaEntrada, String horaSalida) {
		this.nombre = nombre;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.activo = true;
	}

	public Turnos(Integer Id, String nombre, String horaEntrada, String horaSalida) {
		this.id = Id;
		this.nombre = nombre;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.activo = true;
	}

	public Turnos(TTurno turno) {
		this.nombre = turno.getNombre();
		this.horaEntrada = turno.getHoraEntrada();
		this.horaSalida = turno.getHoraSalida();
		this.activo = turno.getActivo();
	}

	public Integer getId() {
		return this.id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getHoraEntrada() {
		return this.horaEntrada;
	}

	public String getHoraSalida() {
		return this.horaSalida;
	}

	public boolean getActivo() {
		return this.activo;
	}

	public long getVersion() {
		return this.version;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public void setHoraSalida(String horaSalida) {
		this.horaSalida = horaSalida;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int hashCode() {
		return nombre.hashCode() + id;
	}

	public TTurno toTransfer() {
		return new TTurno(id, nombre, horaEntrada, horaSalida, activo);
	}

	public void setEmpleados(Collection<Empleado> empleados) {
		this.empleados = empleados;
	}

	public Collection<Empleado> getEmpleados() {
		return this.empleados;
	}

}
