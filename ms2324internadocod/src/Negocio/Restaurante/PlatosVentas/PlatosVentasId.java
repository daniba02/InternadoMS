package Negocio.Restaurante.PlatosVentas;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PlatosVentasId implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer platos;
	private Integer ventas;
	
	public PlatosVentasId(Integer idp, Integer idv) {
		platos=idp;
		ventas=idv;
	}
	
	public PlatosVentasId(){}

	public Integer getIdPlato(){
		return this.platos;
	}
	
	public Integer getIdVenta(){
		return this.ventas;
	}
	
	public int hashCode(){
		return this.ventas+this.platos;
	}
	
	public boolean equals(Object pv){
		return (pv instanceof PlatosVentasId) && ((PlatosVentasId)pv).getIdPlato()==this.getIdPlato()&&((PlatosVentasId)pv).getIdVenta()==this.getIdVenta();
	}
	public void setIdplato(Integer idplato) {
		this.platos = idplato;
	}
	public void setIdventa(Integer idventa) {
		this.ventas = idventa;
	}
}
