package Negocio.Restaurante.Platos;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.Collection;

import Negocio.Restaurante.PlatosVentas.PlatosVentas;
import Negocio.Restaurante.Producto.Producto;
import Negocio.Restaurante.Producto.TProducto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Platos {
	
	@Version
	private long version;
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY) 
	@Column(name="idplato",nullable = false)
	private Integer id;
	@Column(name="nombre",nullable = false)
	private String nombre;
	@Column(name="stock",nullable = false)
	private Integer stock;
	@Column(name="precio",nullable = false)
	private Double precio;
	@Column(name="activo")
	private Boolean activo;
	
	@ManyToMany
	private Collection<Producto> productos;
	
	@OneToMany(mappedBy="platos")
	private Collection<PlatosVentas> ventas;
	
	public Platos(){}
	
	public Platos(String nombre, Integer stock, Double precio, Collection<Producto>productos){
		this.nombre=nombre;
		this.stock=stock;
		this.precio=precio;
		this.productos=productos;
		this.activo=true;
	}
	public Platos(String nombre, Integer stock, Double precio,Collection<Producto> productos,Boolean activo) {
		this.nombre=nombre;
		this.stock=stock;
		this.precio=precio;
		this.activo=activo;
		this.productos=productos;
	}
	public Platos(Integer id){
		this.id=id;
		this.activo=true;
	}
	
	//Getters
	public String getNombre(){
		return this.nombre;
	}
	public Integer getId(){
		return this.id;
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
	public Collection<Producto> getProductos(){
		return this.productos;
	}
	public long getVersion(){
		return this.version;
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
	public void setProductos(Collection<Producto>productos){
		this.productos=productos;
	}
	
	public boolean equals(Object o){
		return (o instanceof Platos) && this.nombre.equals(((Platos)o).getNombre())
					&& this.stock==((Platos)o).getStock() && this.id==((Platos)o).getId() && this.activo==((Platos)o).getActivo()
					&& this.precio==((Platos)o).getPrecio() && this.version==((Platos)o).getVersion();
	}
	
	public TPlatoCompleto toTransfer(){
		Collection<TProducto>p = new ArrayList<TProducto>();
		for(Producto pl : getProductos())
			p.add(pl.toTransfer());
		TPlatoCompleto toap;
		if(this instanceof Bebida){
			toap = new TBebida(getId(),getNombre(),getStock(),getPrecio(),getActivo(),p,((Bebida)this).getTemperatura());
		}else{
			toap = new TComida(getId(),getNombre(),getStock(),getPrecio(),getActivo(),p,((Comida)this).getTipo());
		}
		return toap;
	}
	
	public int hashCode(){
		return nombre.hashCode() + id;
	}
	
	public TPlatoCompleto toTransferPlato(){
		if(this instanceof Bebida)
			return new TBebida(getId(),getNombre(),getStock(),getPrecio(),((Bebida)this).getTemperatura());
		else
			return new TComida(getId(),getNombre(),getStock(),getPrecio(),((Comida)this).getTipo());	
	}

}