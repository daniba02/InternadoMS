package Negocio.Academia.Alumnos;

public class TAlumnos {
	
	private Integer ID;
	private String DNI;
	private String Nombre;
	private String Apellido;
	private Integer Telefono;
	private Integer Edad;
	private Boolean Activo;


	public Integer getID() {
		return this.ID;
	}
	
	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	public String getDNI() {
		return this.DNI;
	}


	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public String getNombre() {
		return this.Nombre;
	}

	public void setNombre(String Nombre) {
		this.Nombre = Nombre;
	}

	public String getApellido() {
		return this.Apellido;
	}

	public void setApellido(String Apellido) {
		this.Apellido = Apellido;
	}

	public Integer getTelefono() {
		return this.Telefono;
	}

	public void setTelefono(Integer Telefono) {
		this.Telefono = Telefono;
	}

	public Integer getEdad() {
		return this.Edad;
	}

	public void setEdad(Integer Edad) {
		this.Edad = Edad;
	}

	public Boolean getActivo() {
		return this.Activo;
	}

	public void setActivo(Boolean Estado) {
		this.Activo = Estado;
	}

	public TAlumnos(String DNI, String Nombre, String Apellido, Integer Edad, Integer Telefono) {

		this.DNI = DNI;
		this.Nombre = Nombre;
		this.Apellido = Apellido;
		this.Telefono = Telefono;
		this.Edad = Edad;
		this.Activo = true;
	}
	
	public TAlumnos (Integer id,String DNI, String Nombre, String Apellido, Integer Edad, Integer Telefono){
		this.ID=id;
		this.DNI = DNI;
		this.Nombre = Nombre;
		this.Apellido = Apellido;
		this.Telefono = Telefono;
		this.Edad = Edad;
		this.Activo = true;
	}
	
	public TAlumnos (String DNI, String Nombre, String Apellido, Integer Edad, Integer Telefono, Boolean activo){
		this.DNI = DNI;
		this.Nombre = Nombre;
		this.Apellido = Apellido;
		this.Telefono = Telefono;
		this.Edad = Edad;
		this.Activo = activo;
	}
	
	public TAlumnos (Integer id,String DNI, String Nombre, String Apellido, Integer Edad, Integer Telefono, Boolean activo){
		this.ID=id;
		this.DNI = DNI;
		this.Nombre = Nombre;
		this.Apellido = Apellido;
		this.Telefono = Telefono;
		this.Edad = Edad;
		this.Activo = activo;
	}
	
}