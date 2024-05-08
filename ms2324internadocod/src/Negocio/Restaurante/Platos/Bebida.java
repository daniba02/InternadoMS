package Negocio.Restaurante.Platos;

import java.util.ArrayList;
import java.util.Collection;

import Negocio.Restaurante.Producto.Producto;
import Negocio.Restaurante.Producto.TProducto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bebida")
public class Bebida extends Platos{
	@Column(name="temperatura",nullable=false)
	private Double temperatura;
	
	public Bebida(){}
	public Bebida(String nombre,Integer stock,Double precio,Boolean activo,Collection<Producto>productos,Double temperatura){
		super(nombre,stock,precio,productos,activo);
		this.temperatura=temperatura;
	}
	
	public Bebida(TPlatoCompleto toap){
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
		this.setTemperatura(((TBebida)toap).getTemperatura());
	}
	
	//Getters
	public Double getTemperatura(){
		return this.temperatura;
	}
	
	//Setters
	public void setTemperatura(Double temperatura){
		this.temperatura=temperatura;
	}
}
