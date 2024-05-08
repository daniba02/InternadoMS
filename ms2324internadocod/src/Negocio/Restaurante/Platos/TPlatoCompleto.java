package Negocio.Restaurante.Platos;

import java.util.Collection;

import Negocio.Restaurante.Producto.TProducto;

public abstract class TPlatoCompleto {

	private TPlatos plato;
	private Collection<TProducto>productos;
	
	public TPlatoCompleto(TPlatos plato, Collection<TProducto>productos) {
		this.plato=plato;
		this.productos=productos;
	}
	
	public TPlatos getPlato() {
		return plato;
	}
	
	public Collection<TProducto> getProductos(){
		return productos;
	}
	
	public void setProductos(Collection<TProducto> productos){
		this.productos=productos;
	}
	
	//PATRON VISITOR
	public abstract void aceptar(VisitantePlatos v);
	
	
}
