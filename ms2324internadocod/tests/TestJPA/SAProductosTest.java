package TestJPA;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Producto.SAProductos;
import Negocio.Restaurante.Producto.TProducto;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAProductosTest {

	EntityManagerFactory emf=null;
	EntityManager em=null;
	EntityTransaction et=null;
	
	@Test
	public void A_alta() {
		
		emf=FactoriaEntityManager.getInstance().getEntityManagerFactory();
		em=emf.createEntityManager();
		et=em.getTransaction();
		et.begin();
		em.createQuery("DELETE FROM Producto p").executeUpdate();
		em.createNativeQuery("ALTER TABLE Producto AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Platos pl").executeUpdate();
		em.createNativeQuery("ALTER TABLE Platos AUTO_INCREMENT = 1").executeUpdate();
		et.commit();
		
		SAProductos sp = SAFactory.getInstance().getSAProducto();
		
		try {
		
			// comprobacion alta producto nulo
			if (sp.altaProducto(null).getEvento() != Evento.AltaProductos_KO)
				fail("No se puede crear un producto nulo");
			
			TProducto p = new TProducto(-1, "Fresas", "fruta fresca", true);
			if (sp.altaProducto(p).getEvento() == Evento.AltaProductos_KO)
				fail("No se ha creado correctamente el producto");
			// comprobacion nombre incorrecto
			 p = new TProducto(-1, "Fresas", "fruta fresca", true);
			 
			if (sp.altaProducto(p).getEvento() != Evento.AltaProductos_KO)
				fail("Dos productos no pueden tener el mismo nombre");
			
			if (sp.altaProducto(p).getEvento() != Evento.AltaProductos_KO)
				fail("No se puede dar de alta un producto que ya está activo");
			
			// producto ya existe
			if (sp.altaProducto(p).getEvento() != Evento.AltaProductos_KO)
				fail("No se puede crear un producto que ya existe");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	@Test
	public void B_baja() {
			
		SAProductos sp = SAFactory.getInstance().getSAProducto();
		try {
			
			TProducto p = new TProducto(-1, "Zumos", "liquido", true);
			sp.altaProducto(p);
			
			Evento a = sp.bajaProducto(null).getEvento();
			if(a != Evento.BajaProductos_KO)
				fail("Fallo al intentar dar de baja a un id nulo");
			
			a = sp.bajaProducto(12).getEvento();
			if(a != Evento.BajaProductos_KO)
				fail("Fallo al intentar dar de baja a un id que no existe en la bbdd");
			
			a = sp.bajaProducto(1).getEvento();
			if(a != Evento.BajaProductos_OK)	
				fail("Fallo al dar de baja un producto correctamente");

			TProducto p2 = new TProducto(-1, "Mango", "fruta tropical", true);
			sp.altaProducto(p2);
			
			sp.bajaProducto(1);
			a = sp.bajaProducto(p2.getId()).getEvento();
			if(a != Evento.BajaProductos_KO)
			fail("Fallo al intentar dar de baja a un producto que está inactivo");
			
			
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	@Test
	public void C_modificar() {
		SAProductos sp = SAFactory.getInstance().getSAProducto();
		try{
			TProducto p1 = new TProducto(-1, "Agua", "liquido", true);
			Evento a = sp.altaProducto(p1).getEvento();
			
			if(a!= Evento.AltaProductos_OK)
				fail("no se ha creado un producto correctamente");
			
			p1.setCaracteristicas("liquido y caliente");
			p1.setNombre("te");
			p1.setId(2);
			a = sp.modificarProducto(p1).getEvento();
			
			if(a!= Evento.ModificarProductos_OK)
				fail("no se ha modificado correctamente");
			
//			a = sp.modificarProducto(null).getEvento();
//			if (a != Evento.ModificarProductos_KO)
//				fail("Producto nulo no se puede modificar");
			
			sp.bajaProducto(p1.getId());
			a = sp.modificarProducto(p1).getEvento();
			if(a != Evento.ModificarProductos_KO)
				fail("No se puede modificar un producto inactivo");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void D_consultar() {
		
		SAProductos sp = SAFactory.getInstance().getSAProducto();

		TProducto p1 = new TProducto(3, "Patata", "Francesas", true);
		TProducto p2 = new TProducto(4, "Ternera", "Fresca", true);

		sp.altaProducto(p1);
		sp.altaProducto(p2);

		try {
			Integer idNulo = null;
			// comprobar que un producto no existe
			if (sp.consultarProducto(-1).getEvento() != Evento.ConsultarProductos_KO)
				fail("producto no existe");

			// id de producto nulo
			if (sp.consultarProducto(idNulo).getEvento() != Evento.ConsultarProductos_KO)
				fail("id de producto no puede ser nulo");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void E_listarProductos(){
		SAProductos sp = SAFactory.getInstance().getSAProducto();
		
		TProducto p1 = new TProducto(5, "Patata", "Francesas", true);
		TProducto p2 = new TProducto(6, "Ternera", "Fresca", true);
		sp.altaProducto(p1);
		sp.altaProducto(p2);
		
		@SuppressWarnings("unchecked")
		Collection<TProducto> productos = (Collection<TProducto>) sp.listarProductos().getDato();
		
		try{
			if(productos == null)
				fail("No recibe bien la lista, no debería devolver null");
			
			if(productos.size() != 4)
				fail("Fallo de tamaño, debería devolver 4");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void F_listarProductosPorPlato(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		SAProductos sapr=SAFactory.getInstance().getSAProducto();
		
		try{
			
			Collection<TProducto>listaproductos=new ArrayList<TProducto>();
			Evento a = sap.altaPlato(new TBebida(-1,"Sangria",5,4.2,true,listaproductos,25.3)).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			
			a = sapr.altaProducto(new TProducto(-1,"limon","fresco",true)).getEvento();
			if(a==Evento.AltaProductos_KO){
				fail("No se ha creado un producto correctamente");
			}
			
			a = sap.vincularPlatoProducto(1,3).getEvento();
			if(a==Evento.VincularPlatoProducto_KO){
				fail("No se han vinculado correctamente los platos existentes con los productos existentes");
			}
			a = sapr.listarProductosPorPlato(1).getEvento();
			if(a==Evento.ListarProductosPorPlato_KO){
				fail("No se han listado correctamente los productos vinculados al plato introducido");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
		
	}
	
}
