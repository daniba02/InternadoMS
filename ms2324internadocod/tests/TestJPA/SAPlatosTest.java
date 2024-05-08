package TestJPA;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.Platos.TBebida;
import Negocio.Restaurante.Platos.TComida;
import Negocio.Restaurante.Platos.TPlatoCompleto;
import Negocio.Restaurante.Producto.SAProductos;
import Negocio.Restaurante.Producto.TProducto;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

/*
 * HAY QUE ELIMINAR LA BBDD ANTES DE EMPEZAR (HABILITAR LA ELIMINACION DE LA BBDD EN persistence.xml)
 * 
 * */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAPlatosTest {

	@Test
	public void A_alta() { /*Al terminar habra un plato de id=1 dado de alta*/
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		//Borra la tabla y establecemos el AUTO_INCREMENT a 1
		t.begin();
		em.createQuery("DELETE FROM PlatosVentas p").executeUpdate();
		em.createNativeQuery("ALTER TABLE PlatosVentas AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Platos p").executeUpdate();
		em.createNativeQuery("ALTER TABLE Platos AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Producto p").executeUpdate();
		em.createNativeQuery("ALTER TABLE Producto AUTO_INCREMENT = 1").executeUpdate();
		t.commit();
		
		Evento a = sap.altaPlato(null).getEvento();
		if(a!=Evento.AltaPlatos_KO){
			fail("No se permiten platos nulos");
		}
		Collection<TProducto>colpr=new ArrayList<TProducto>();
		a = sap.altaPlato(new TComida(-1,"Cocido",5,4.2,true,colpr,"caliente")).getEvento();
		if(a==Evento.AltaPlatos_KO){
			fail("No se ha creado un plato correctamente");
		}
		a = sap.altaPlato(new TComida(-1,"Cocido",8,4.1,true,colpr,"fria")).getEvento();
		if(a!=Evento.AltaPlatos_KO){
			fail("No se permite un plato con un nombre que ya existe en la BBDD");
		}
		
		a = sap.altaPlato(new TComida(1,null,null,null,true,colpr,null)).getEvento();
		if(a!=Evento.AltaPlatos_KO) {
			fail("El plato ya estaba dado de alta");
		}
			
		sap.bajaPlato(1);
		a = sap.altaPlato(new TComida(1,null,null,null,true,colpr,null)).getEvento();
		if(a==Evento.AltaPlatos_KO){
			fail("No se ha dado de alta a un plato que estaba inactivo");
		}
		
		a = sap.altaPlato(new TComida(1000,null,null,null,true,colpr,null)).getEvento();
		if(a!=Evento.AltaPlatos_KO){
			fail("No se puede dar de alta un plato que no existe en la BBDD");
		}

	}
	
	@Test
	public void B_baja() {/*Al terminar habra un plato de id=1 dado de baja*/
		
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
			
		Evento a = sap.bajaPlato(null).getEvento();
		if(a!=Evento.BajaPlatos_KO){
			fail("No se permiten ids nulos");
		}
			
		a=sap.bajaPlato(1000).getEvento();
		if(a!=Evento.BajaPlatos_KO){
			fail("No se puede dar de baja un plato inexistente en la BBDD");
		}
			
		a=sap.bajaPlato(1).getEvento();
		if(a==Evento.BajaPlatos_KO){
			fail("No se ha dado de baja un plato que estaba activo");
		}
			
		a=sap.bajaPlato(1).getEvento();
		if(a!=Evento.BajaPlatos_KO){
			fail("No se puede dar de baja un plato que estaba inactivo ya");
		}
			
	}
	
	@Test
	public void C_consultar() {/*No se ha modificado nada aqui*/
		
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		
		Evento a = sap.consultarPlatos(null).getEvento();
		if(a!=Evento.ConsultarPlatos_KO){
			fail("No se permiten ids nulos");
		}
		
		a = sap.consultarPlatos(1000).getEvento();
		if(a!=Evento.ConsultarPlatos_KO){
			fail("No se puede consultar un plato inexistente en la BBDD");
		}
			
		a = sap.consultarPlatos(1).getEvento();
		if(a!=Evento.ConsultarPlatos_KO){
			fail("Se ha consultado un plato inactivo");
		}
		
		sap.altaPlato(new TComida(1,null,null,null,true,null,null));
		a = sap.consultarPlatos(1).getEvento();
		if(a==Evento.ConsultarPlatos_KO){
			fail("No se ha consultado un plato correctamente");
		}
	}
	
	@Test
	public void D_listar() {/*Al terminar la tabla de platos estara vacia*/
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		EntityManagerFactory emf=FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		
		try{
			
			Evento a = sap.listAllPlatos().getEvento();
			if(a==Evento.ListarPlatos_KO){
				fail("No se han listado correctamente los platos existentes");
			}
			
			t.begin();
			Query q = em.createQuery("DELETE FROM Platos p");
			q.executeUpdate();
			t.commit();
			em.close();
			
			a = sap.listAllPlatos().getEvento();
			if(a==Evento.ListarPlatos_KO){
				fail("Que no haya platos en la tabla no es un error");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void F_listarComidas(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		
		try{
			
			Collection<TProducto>colpr=new ArrayList<TProducto>();
			Evento a = sap.altaPlato(new TComida(-1,"Cocido",5,4.2,true,colpr,"primerplato")).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			a = sap.altaPlato(new TComida(-1,"Sushi",8,4.1,true,colpr,"buffet")).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			
			a = sap.listComida().getEvento();
			if(a==Evento.ListarComida_KO){
				fail("No se han listado correctamente las comidas existentes");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void G_listarBebida(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		
		try{
			Collection<TProducto>colpr=new ArrayList<TProducto>();
			Evento a = sap.altaPlato(new TBebida(-1,"Chocolate caliente",5,4.2,true,colpr,25.3)).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			a = sap.altaPlato(new TBebida(-1,"Zumito de piña",8,4.1,true,colpr,10.2)).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			
			a = sap.listBebida().getEvento();
			if(a==Evento.ListarBebidas_KO){
				fail("No se han listado correctamente los platos existentes");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void H_listarPlatosporProducto(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		SAProductos sapr=SAFactory.getInstance().getSAProducto();
		
		try{
			Collection<TProducto>colpr=new ArrayList<TProducto>();
			Evento a = sap.altaPlato(new TBebida(-1,"Chocolate muy caliente",5,4.2,true,colpr,25.3)).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			
			a = sapr.altaProducto(new TProducto(-1,"chocolateee","ingrediente",true)).getEvento();
			if(a==Evento.AltaProductos_KO){
				fail("No se ha creado un producto correctamente");
			}
			
			a = sap.vincularPlatoProducto(2,1).getEvento();
			if(a==Evento.VincularPlatoProducto_KO){
				fail("No se han vinculado correctamente los platos existentes con los productos existentes");
			}
			a = sap.listPlatosporProducto(1).getEvento();
			if(a==Evento.ListarPlatosPorProducto_KO){
				fail("No se han listado correctamente los platos existentes con los productos existentes");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void I_modificar(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		
		try{
			Collection<TProducto>colpr=new ArrayList<TProducto>();
			TPlatoCompleto tb = new TBebida(-1,"Chocolate ardiendo",5,4.2,true,colpr,25.3);
			Evento a = sap.altaPlato(tb).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			tb.getPlato().setId(6);
			tb.getPlato().setNombre("Pepi");
			tb.getPlato().setPrecio(200.12);
			tb.getPlato().setStock(12);
			((TBebida)tb).setTemperatura(30.5);
			a = sap.modificar(tb).getEvento();
			if(a==Evento.ModificarPlatos_KO){
				fail("No se ha modificado rellenando todos los campos de un plato existente");
			}
			TBebida tb2 = new TBebida(1000,"Batido",5,4.2,true,colpr,25.3);
			a = sap.modificar(tb2).getEvento();
			if(a!=Evento.ModificarPlatos_KO){
				fail("Se ha modificado un plato inexistente");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void J_vincular(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		SAProductos sapr=SAFactory.getInstance().getSAProducto();
		
		try{
			Collection<TProducto>colpr=new ArrayList<TProducto>();
			Evento a = sap.altaPlato(new TBebida(-1,"Chocolate derretido",5,4.2,true,colpr,25.3)).getEvento();
			if(a==Evento.AltaPlatos_KO){
				fail("No se ha creado un plato correctamente");
			}
			
			a = sapr.altaProducto(new TProducto(-1,"chocolateado","ingrediente",true)).getEvento();
			if(a==Evento.AltaProductos_KO){
				fail("No se ha creado un producto correctamente");
			}
			
			a = sap.vincularPlatoProducto(2,2).getEvento();
			if(a==Evento.VincularPlatoProducto_KO){
				fail("No se han vinculado correctamente los platos existentes con los productos existentes");
			}
			a = sap.vincularPlatoProducto(2,100).getEvento();
			if(a!=Evento.VincularPlatoProducto_KO){
				fail("Se han vinculado correctamente los platos existentes con los productos inexistente");
			}
			a = sap.vincularPlatoProducto(100,1).getEvento();
			if(a!=Evento.VincularPlatoProducto_KO){
				fail("Se han vinculado correctamente el plato inexistente con los productos existentes");
			}
			a = sap.vincularPlatoProducto(2,1).getEvento();
			if(a!=Evento.VincularPlatoProducto_KO){
				fail("Se han vinculado correctamente los platos existentes con los productos existentes ya vinculados");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void K_desvincular(){
		SAPlatos sap=SAFactory.getInstance().getSAPlatos();
		
		try{
			
			Evento a = sap.desvincularPlatoProducto(2,1).getEvento();
			if(a==Evento.DesvincularPlatoProducto_KO){
				fail("No se han desvinculado correctamente los platos existentes con los productos existentes");
			}
			a = sap.desvincularPlatoProducto(2,100).getEvento();
			if(a!=Evento.DesvincularPlatoProducto_KO){
				fail("Se han desvinculado correctamente los platos existentes con los productos inexistente");
			}
			a = sap.desvincularPlatoProducto(100,1).getEvento();
			if(a!=Evento.DesvincularPlatoProducto_KO){
				fail("Se han desvinculado correctamente el plato inexistente con los productos existentes");
			}
			a = sap.desvincularPlatoProducto(2,1).getEvento();
			if(a!=Evento.DesvincularPlatoProducto_KO){
				fail("Se han desvinculado correctamente los platos existentes con los productos existentes ya desvinculados");
			}
			a = sap.desvincularPlatoProducto(3,2).getEvento();
			if(a!=Evento.DesvincularPlatoProducto_KO){
				fail("Se han desvinculado correctamente los platos existentes con los productos existentes pero no vinculados");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
}
