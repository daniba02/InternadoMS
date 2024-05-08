package Negocio.Restaurante.Turnos;

public class TTurno {
	
	private Integer id;
	private String nombre;
	private String horaEntrada;
	private String horaSalida;
	private boolean activo;
	
	
	public TTurno(String nombre, String horaEntrada, String horaSalida) {
		this.nombre=nombre;
		this.horaEntrada=horaEntrada;
		this.horaSalida=horaSalida;
		this.activo=true;
	}
	
	public TTurno(Integer Id, String nombre, String horaEntrada, String horaSalida) {
		this.id=Id;
		this.nombre=nombre;
		this.horaEntrada=horaEntrada;
		this.horaSalida=horaSalida;
		this.activo=true;
	}
	
	public TTurno(Integer Id, String nombre, String horaEntrada, String horaSalida, Boolean activo) {
		this.id=Id;
		this.nombre=nombre;
		this.horaEntrada=horaEntrada;
		this.horaSalida=horaSalida;
		this.activo=activo;
	}
	
	public TTurno(Integer Id) {
		this.id = Id;
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
	
	
	public void setId(Integer id) {
		this.id=id;
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada=horaEntrada;
	}
	
	public void setHoraSalida(String horaSalida) {
		this.horaSalida=horaSalida;
	}
	public void setActivo(boolean activo) {
		this.activo=activo;
	}
}
