package Negocio.Restaurante.Platos;

public class TPlatos {
	
	private Integer id;
	private String nombre;
	private Integer stock;
	private Double precio;
	private Boolean activo;
	
	public TPlatos(Integer id,String nombre,Integer stock,Double precio,Boolean activo){
		this.id=id;
		this.nombre=nombre;
		this.stock=stock;
		this.precio=precio;
		this.activo=activo;
	}
	
	public TPlatos(Integer id,String nombre,Integer stock,Double precio){
		this.id=id;
		this.nombre=nombre;
		this.stock=stock;
		this.precio=precio;
		this.activo=true;
	}

	public TPlatos() {}

	public TPlatos(Integer id2) {
		this.id=id2;
		this.activo=true;
	}

	//Getters
	public Integer getId(){
		return this.id;
	}
	public String getNombre(){
		return this.nombre;
	}
	public Double getPrecio(){
		return this.precio;
	}
	public Integer getStock(){
		return this.stock;
	}
	public Boolean getActivo(){
		return this.activo;
	}
	
	//Setters
	public void setId(Integer id){
		this.id=id;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setPrecio(Double precio){
		this.precio=precio;
	}
	public void setStock(Integer stock){
		this.stock=stock;
	}
	public void setActivo(Boolean activo){
		this.activo=activo;
	}
	
}
