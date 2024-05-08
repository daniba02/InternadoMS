package Negocio.Restaurante.Ventas;

import java.util.Collection;

import Negocio.Restaurante.Mesas.TMesas;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;

public class TFacturaVenta {
	private Collection<TPlatoVenta> platos;
	private TVentas venta;
	private TMesas mesa;
	
	 public TMesas getMesa() {
		return mesa;
	}
	public void setMesa(TMesas mesa) {
		this.mesa = mesa;
	}
	public TFacturaVenta(TVentas venta,TMesas mesa, Collection<TPlatoVenta> platos){
		this.venta=venta;
		this.mesa=mesa;
		this.platos=platos;
		
	}
	
	public TVentas getVenta() {
		return venta;
	}
	
	public Collection<TPlatoVenta> getPlatos() {
		return platos;
	}
	
	@Override
	 public boolean equals(Object obj) {
		TFacturaVenta f= (TFacturaVenta)obj;
		return f.getVenta().equals(venta)&&
		f.getMesa().equals(mesa)&&
		coincidenProductos(f.getPlatos());
	 }
	
	private boolean coincidenProductos(Collection<TPlatoVenta> platos2) {
		for(TPlatoVenta p:platos2) {
			if(!platos.contains(p)) {
				return false;
			}
		}
		return true;
	}
}
