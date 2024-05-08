package Negocio.Restaurante.Ventas;

import java.util.ArrayList;
import java.util.HashMap;

import Negocio.Restaurante.PlatosVentas.TPlatoVenta;

public class TCarrito {
	private ArrayList<TPlatoVenta> productos;
	private TVentas venta;
	
	 public TCarrito(TVentas venta,HashMap<Integer,Integer> productos){
		this.venta=venta;
		this.productos=mapToArray(productos);
	}
	 public TCarrito(TVentas venta){
			this.venta=venta;
		}
	public TCarrito() {
		// TODO Auto-generated constructor stub
	}
	private ArrayList<TPlatoVenta> mapToArray(HashMap<Integer, Integer> productos2) {
		ArrayList<TPlatoVenta> prod=new ArrayList<TPlatoVenta>();
		for(int k:productos2.keySet()){
			prod.add(new TPlatoVenta(this.venta.getId(),k,productos2.get(k)));
		}
		
		return prod;

	}
	
	public void setVenta(TVentas venta) {
		this.venta = venta;
	}
	public TVentas getVenta() {
		return venta;
	}
	public ArrayList<TPlatoVenta> getPlatos() {
		return productos;
	}
	public void setPlatos(ArrayList<TPlatoVenta> productos) {
		this.productos = productos;
	}
	
}
