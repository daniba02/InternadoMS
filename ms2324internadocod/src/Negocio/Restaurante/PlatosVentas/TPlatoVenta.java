package Negocio.Restaurante.PlatosVentas;

import Negocio.Restaurante.Platos.TPlatoCompleto;

public class TPlatoVenta {

	private Integer idplato;
	private Integer idventa;
	private Integer cantidad;
	private Double precio;
	private TPlatoCompleto plato;


	public TPlatoVenta() {

	}

	public TPlatoVenta(int idVenta, int idPlato, int cantidad, double precio) {
		this.idventa = idVenta;
		this.idplato = idPlato;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	public TPlatoVenta(int idVenta, int idPlato, int cantidad, double precio,TPlatoCompleto plato) {
		this.idventa = idVenta;
		this.idplato = idPlato;
		this.cantidad = cantidad;
		this.precio = precio;
		this.plato=plato;
	}

	public TPlatoVenta(int idVenta, int idPlato, int cantidad) {
		this.idventa = idVenta;
		this.idplato = idPlato;
		this.cantidad = cantidad;
	}

	public TPlatoVenta(int idPlato, int cantidad) {
		// TODO Auto-generated constructor stub
		this.idplato = idPlato;
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getIdVenta() {
		return idventa;
	}

	public Integer getIdPlato() {
		return idplato;
	}

	public TPlatoCompleto getPlato() {
		return plato;
	}	

	public void setPlato(TPlatoCompleto plato) {
		this.plato = plato;
	}
	
	@Override
	public boolean equals(Object obj) {
		TPlatoVenta f = (TPlatoVenta) obj;
		return f.getIdPlato() == idplato && f.getCantidad() == cantidad && f.getIdVenta() == idventa;
	}
}
