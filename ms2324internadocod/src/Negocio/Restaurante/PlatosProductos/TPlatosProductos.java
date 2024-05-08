package Negocio.Restaurante.PlatosProductos;

public class TPlatosProductos {
    
	private Integer idplatos;
	
	private Integer idproducto;

	public TPlatosProductos(Integer idPlatos, Integer idProducto) {
		this.idplatos=idPlatos;
		this.idproducto=idProducto;
	}

	public Integer getIdPlato() {
		return idplatos;
	}

	public Integer getIdProducto() {
		return idproducto;
	}
}
