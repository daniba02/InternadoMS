package Negocio.Restaurante.Producto;

import java.util.Collection;
import Negocio.Restaurante.Platos.Platos;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@NamedQueries({
	@NamedQuery(name = "Producto.readAll", query= "select p from Producto p")
})
@Inheritance(strategy=InheritanceType.JOINED)
public class Producto{

	@Version
	private long version;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="idProducto", nullable = false, unique = true)
	private Integer id;
	@Column(name="nombre", nullable = false, unique = true)
	private String nombre;
	@Column(name="caracteristicas", nullable = false)
	private String caracteristicas;
	@Column(name="activo", nullable = false)
	private boolean activo;

	
	
	@ManyToMany (mappedBy="productos")
	private Collection<Platos>platos;
	
	public Producto() {}
	
	//constructora con id
	public Producto(Integer id,String nombre, String caracteristicas,Boolean activo) {
		this.id=id;
		this.nombre=nombre;
		this.caracteristicas=caracteristicas;
		this.activo=activo;
	}
	//constructora sin id
	public Producto(String nombre, String caracteristicas,Boolean activo) {
		this.nombre=nombre;
		this.caracteristicas=caracteristicas;
		this.activo=activo;
	}
	
	public Producto(TProducto tp){
		this.id=tp.getId();
		this.nombre=tp.getNombre();
		this.caracteristicas=tp.getCaracteristicas();
		this.activo=tp.getActivo();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre(){
		return nombre;
	}
	public Producto(Integer id){
		this.id=id;
		this.activo=true;
	}


	public void setNombre(String nombre) {
	this.nombre=nombre;}
	
	public String getCaracteristicas(){
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
	this.caracteristicas=caracteristicas;}
	
	public boolean getActivo(){
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo=activo;
	}
	
	public void setPlatos(Collection<Platos>platos){
		this.platos=platos;
	}
	
	public Collection<Platos> getPlatos(){
		return this.platos;
	}
	
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	public String toString() {
		
		return "Producto: "+ getId()+".\nNombre: "+getNombre()+".\nCaracteristicas: "
		+getCaracteristicas()+".\nActivo: "+getActivo()+".\n";
		
	}

	public TProducto toTransfer() {
		
		return new TProducto(getId(),getNombre(),getCaracteristicas(),getActivo());
	}
	public int hashCode(){
		return nombre.hashCode() + id;
	}
	
}
