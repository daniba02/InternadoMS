package Negocio.Restaurante.Platos;

import java.util.ArrayList;
import java.util.Collection;

import Negocio.Restaurante.Producto.Producto;
import Negocio.Restaurante.Producto.TProducto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="comida")
public class Comida extends Platos{
	
	
	@Column(name="tipo",nullable=false)
	private String tipo;
	
	public Comida(){}
	public Comida(String nombre,Integer stock,Double precio,Boolean activo,Collection<Producto>productos,String tipo){
		super(nombre,stock,precio,productos,activo);
		this.tipo=tipo;
	}
	
	public Comida(TPlatoCompleto toap){
		super();
		this.setNombre(toap.getPlato().getNombre());
		this.setStock(toap.getPlato().getStock());
		this.setPrecio(toap.getPlato().getPrecio());
		this.setActivo(toap.getPlato().getActivo());
		Collection<Producto> prs = new ArrayList<Producto>();
		for(TProducto tp:toap.getProductos()){
			prs.add(new Producto(tp));
		}
		this.setProductos(prs);
		this.setTipo(((TComida)toap).getTipo());
	}
	
	public String getTipo(){
		return this.tipo;
	}
	
	//Setters
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	
}
