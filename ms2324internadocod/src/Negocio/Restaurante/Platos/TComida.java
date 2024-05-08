package Negocio.Restaurante.Platos;

import java.util.Collection;

import Negocio.Restaurante.Producto.TProducto;

public class TComida extends TPlatoCompleto{
	
	private String tipo;
	
	public TComida(Integer id, String nombre, Integer stock, Double precio, Boolean activo, Collection<TProducto>productos, String tipo){
		super(new TPlatos(id,nombre,stock,precio,activo),productos);
		this.tipo=tipo;
	}
	
	public TComida(Integer id, String nombre, Integer stock, Double precio, String type) {
		super(new TPlatos(id,nombre,stock,precio,true),null);
		this.tipo=type;
	}

	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	
	public String getTipo(){
		return this.tipo;
	}
	//PATRON VISITOR
	@Override
	public void aceptar(VisitantePlatos v) {
	v.visitaComida(this);
	}
	
}
