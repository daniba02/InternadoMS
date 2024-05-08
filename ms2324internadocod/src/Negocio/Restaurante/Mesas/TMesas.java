package Negocio.Restaurante.Mesas;

public class TMesas {

	private int id;
	private int num;
	private int capacidad;
	private int idEmpleado;
	private boolean activo;
	public TMesas(){
		
	}
	
	public TMesas(Integer id, int num, int capacidad, int idEmpleado, boolean activo) {
		this.id=id;
		this.num=num;
		this.capacidad=capacidad;
		this.idEmpleado=idEmpleado;
		this.activo=activo;
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
		this.idEmpleado = idEmpleado;
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
		return idEmpleado;
	}
	
	public boolean getActivo(){
		return activo;
	}

	@Override
	 public boolean equals(Object obj) {
		TMesas m= (TMesas)obj;
		return m.getActivo()==activo&&m.getCapacidad()==capacidad&&m.getID()==id&&m.getNum()==num&&m.getIDEmpleado()==idEmpleado;
	 }

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
