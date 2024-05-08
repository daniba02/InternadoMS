package Negocio.Restaurante.PlatosVentas;

import Negocio.Restaurante.Platos.Platos;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import Negocio.Restaurante.Ventas.Ventas;

@NamedQueries({
	@NamedQuery(name = "Negocio.Restaurante.PlatosVentas.PlatosVentas.getPlatosVentas", query= "select pv from PlatosVentas pv where :venta = pv.ventas ")
})

@Entity
public class PlatosVentas {
	
	@Version
	private long version;
	
	@EmbeddedId
	private PlatosVentasId id;
	
	@ManyToOne
	@MapsId private Platos platos;
	
	@ManyToOne
	@MapsId private Ventas ventas;

	@Column(nullable=false)
	private Integer cantidad;
	@Column(nullable=false)
	private Double precio;

	public PlatosVentas(){}
	
	public PlatosVentas(Platos plato,Ventas venta,Integer cantidad,Double precio){
		id=new PlatosVentasId(plato.getId(),venta.getId());
		this.platos=plato;
		this.ventas=venta;
		this.cantidad=cantidad;
		this.precio=precio;
	}

	public TPlatoVenta toTransfer() {
		return new TPlatoVenta(id.getIdVenta(),id.getIdPlato(),cantidad,precio,platos.toTransferPlato());
	}
	//Getters
	public Integer getCantidad() {
		return cantidad;
	}
	public PlatosVentasId getId() {
		return id;
	}
	public Platos getPlato() {
		return platos;
	}
	public Double getPrecio() {
		return precio;
	}
	public Ventas getVenta() {
		return ventas;
	}
	
	//Setters
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public void setId(PlatosVentasId id) {
		this.id = id;
	}
	public void setPlato(Platos plato) {
		this.platos = plato;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public void setVenta(Ventas venta) {
		this.ventas = venta;
	}
	
	public long getVersion() {
		return version;
	}
	
	public void setVersion(long version) {
		this.version = version;
	}
	
}
