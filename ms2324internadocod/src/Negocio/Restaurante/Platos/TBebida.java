package Negocio.Restaurante.Platos;

import java.util.Collection;

import Negocio.Restaurante.Producto.TProducto;

public class TBebida extends TPlatoCompleto{
	
	private Double temperatura;
	
	public TBebida(Integer id, String nombre, Integer stock, Double precio, Boolean activo, Collection<TProducto>productos,Double temperatura){
		super(new TPlatos(id,nombre,stock,precio,activo),productos);
		this.temperatura=temperatura;
	}
	
	public TBebida(Integer id, String nombre, Integer stock, Double precio,Double temperatura) {
		super(new TPlatos(id,nombre,stock,precio,true), null);
		this.temperatura=temperatura;
	}

	public void setTemperatura(Double temperatura){
		this.temperatura=temperatura;
	}
	
	public Double getTemperatura(){
		return this.temperatura;
	}
	//PATRON VISITOR
	public void aceptar(VisitantePlatos v) {
		v.visitaBebida(this);
	}
}
