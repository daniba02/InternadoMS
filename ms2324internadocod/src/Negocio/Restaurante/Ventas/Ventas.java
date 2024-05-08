package Negocio.Restaurante.Ventas;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import Negocio.Restaurante.Mesas.Mesas;
import Negocio.Restaurante.Mesas.TMesas;
import Negocio.Restaurante.PlatosVentas.PlatosVentas;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;

@Entity 
//@Table(name="ventas")
@NamedQueries({
	@NamedQuery(name = "Negocio.Restaurante.Ventas.Ventas.findByCliente", query = "select v from Ventas v where v.cliente=:cliente "),
	@NamedQuery(name = "Negocio.Restaurante.Ventas.Ventas.findAll", query= "select v from Ventas v "),
	@NamedQuery(name = "Negocio.Restaurante.Ventas.Ventas.findByMesa", query= "select v from Ventas v where :mesa = v.mesa ")
})


public class Ventas {
	
	@Version
	private long version;
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column(name="idventa", nullable = false)
	private Integer id;
	@Column(name="cliente", nullable = false)
	private String cliente;
	@Column(name="fecha", nullable = false)
	private String fecha;
	@Column(name="activo",nullable = false)
	private Boolean activo;
	
	@ManyToOne
	@JoinColumn(name="idmesa")
	private Mesas mesa;
	
	@OneToMany(mappedBy="ventas",cascade = CascadeType.ALL)
	private Collection<PlatosVentas> platos;
	
	public Ventas() {}
	
	public Ventas(Integer id, String cliente, String fecha, Integer idMesa, Boolean activo) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.fecha = fecha;
	//	this.mesa.setID(idMesa);
		this.activo = activo;
	}
	
	public Ventas(String cliente, String fecha, Integer idMesa, Boolean activo) {
		super();
		this.cliente = cliente;
		this.fecha = fecha;
		//this.mesa = idMesa;
		this.activo = activo;
	}
	
	public Ventas(TVentas venta) {
		if(venta.getId()!=null)
			this.id=venta.getId();
		
		this.cliente = venta.getCliente();
		this.fecha = venta.getFecha();
		this.activo =  venta.getActivo();
		//this.mesa.setID(venta.getIdMesa());	
	}

	//GETTERS
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCliente() {
		return cliente;
	}
	
	public Boolean getActivo(){
		return this.activo;
	}
	
	//SETTERS
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public Integer getIdMesa() {
		return mesa.getID();
	}
	
	public void setIdMesa(Integer idMesa) {
		this.mesa.setID(idMesa);
	}
	
	public void setActivo(Boolean activo){
		this.activo=activo;
	}
	
	public String toString(){
		return "Plato: "+getId()+".\nCliente: "+getCliente()+".\nFecha: "+getFecha()+".\nID-Mesa: "+getIdMesa()+".\nActivo: "+getActivo()+".\n";
	}

	public TFacturaVenta toFactura() {
		TFacturaVenta factura=null;
		TVentas venta=new TVentas(this.id,this.cliente,this.fecha,this.mesa.getID(),this.activo);
		TMesas mesa=this.mesa.toTransfer();
		Collection<TPlatoVenta> platos=new ArrayList<TPlatoVenta>();
		for(PlatosVentas pv:this.platos) {
			platos.add(pv.toTransfer());
		}
		factura=new TFacturaVenta(venta,mesa,platos);
		return factura;
	}

	public TVentas toTransfer() {
		return new TVentas(id, cliente, fecha, mesa.getID(), activo);
	}
	public void setMesa(Mesas m) {
		mesa =m;
	}
	public void setPlatos(Collection<PlatosVentas> platos) {
	this.platos =platos;
	}

	public void modificarConTransfer(TVentas venta) {
		this.activo=venta.getActivo();
		this.cliente=venta.getCliente();
		this.fecha=venta.getFecha();
	}
	public boolean removePlatoDeVenta(PlatosVentas pv) {
		return platos.remove(pv);
	}
	
	public long getVersion(){
		return this.version;
	}
	public Collection<PlatosVentas> getPlatos() {
		return platos;
	}	
	
}
