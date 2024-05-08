package TestJPA;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Empleados.Empleado;
import Negocio.Restaurante.Mesas.Mesas;
import Negocio.Restaurante.Platos.Comida;
import Negocio.Restaurante.Platos.Platos;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;
import Negocio.Restaurante.Ventas.SAVentas;
import Negocio.Restaurante.Ventas.TCarrito;
import Negocio.Restaurante.Ventas.TFacturaVenta;
import Negocio.Restaurante.Ventas.TVentas;
import Negocio.Restaurante.Ventas.Ventas;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;
//IMPORTANTE ANTES DE EJECUTAR:
/*
	La base de datos tiene que estar en modo que se vacia al terminar
	Las ventas por alguna razon empiezan desde el 4 pero lo hemos tenido en cuenta hasta solucionar el error
*/
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestVentas {
	private void crearAussies() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		
		Empleado emp=new Empleado(1,"Mario",100.0,45.0,true);

		Mesas mesa=new Mesas();
		mesa.setID(1);
		mesa.setActivo(true);
		mesa.setEmpleado(emp);

		Mesas mesa2=new Mesas();
		mesa2.setID(2);
		mesa2.setActivo(true);
		mesa2.setEmpleado(emp);

		Comida plato1=new Comida();
		plato1.setId(1);
		plato1.setActivo(true);
		plato1.setStock(10);
		plato1.setNombre("Pasta al pesto");
		plato1.setPrecio(12.20);
		plato1.setTipo("Pasta");
		Comida plato2=new Comida();
		plato2.setId(1);
		plato2.setActivo(true);
		plato2.setStock(10);
		plato2.setNombre("Pizza margarita");
		plato2.setPrecio(12.20);
		plato2.setTipo("Pizza");
		
		emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		//persistir lo que necesitamos 
		// Consulta JPQL para seleccionar todas las entidades de un tipo

	    String jpql4 = "DELETE FROM PlatosVentas t";
	    Query query4 = em.createQuery(jpql4);

	    // Ejecutar la consulta para eliminar todas las entidades
	    query4.executeUpdate();
	    em.createNativeQuery("ALTER TABLE PlatosVentas AUTO_INCREMENT = 1").executeUpdate();
	    
		 String jpql2 = "DELETE FROM Platos t";
		    Query query2 = em.createQuery(jpql2);

		    // Ejecutar la consulta para eliminar todas las entidades
		query2.executeUpdate();
		    em.createNativeQuery("ALTER TABLE Platos AUTO_INCREMENT = 1").executeUpdate();
		    
		String jpql3 = "DELETE FROM Ventas t";
	    Query query3 = em.createQuery(jpql3);

	    // Ejecutar la consulta para eliminar todas las entidades
	    query3.executeUpdate();
	    em.createNativeQuery("ALTER TABLE Ventas AUTO_INCREMENT = 1").executeUpdate();
	    
	    String jpql = "DELETE FROM Mesas t";
		//TypedQuery<Platos> q = em.createQuery("SELECT * FROM platos",Platos.class);
		Query query = em.createQuery(jpql);

	    // Ejecutar la consulta para eliminar todas las entidades
	    query.executeUpdate();
	    em.createNativeQuery("ALTER TABLE Mesas AUTO_INCREMENT = 1").executeUpdate();
	    
	    String jpql5 = "DELETE FROM Empleado e";
		//TypedQuery<Platos> q = em.createQuery("SELECT * FROM platos",Platos.class);
		Query query5 = em.createQuery(jpql5);

	    // Ejecutar la consulta para eliminar todas las entidades
	    query5.executeUpdate();
	    em.createNativeQuery("ALTER TABLE Empleado AUTO_INCREMENT = 1").executeUpdate();

	    em.getTransaction().commit();
	    em.close();
	    
	    em = emf.createEntityManager();
	    em.getTransaction().begin();
		em.persist(emp);
		em.persist(mesa);
		em.persist(mesa2);
		em.persist(plato1);
		em.persist(plato2);

		
		em.getTransaction().commit();
		
	}
	private void crearVentas() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		
		TVentas tVenta1=new TVentas("Javier","10/10/2024",1,true);
		TVentas tVenta2=new TVentas("Javier","10/10/2025",2,true);
		
		TCarrito carrito2=new TCarrito(tVenta1);
		TCarrito carrito3=new TCarrito(tVenta2);
		ArrayList<TPlatoVenta> productos=new ArrayList<TPlatoVenta>();
		productos.add(new TPlatoVenta(1,10));
		productos.add(new TPlatoVenta(2,15));

		carrito2.setPlatos(productos);
		carrito3.setPlatos(productos);

		Ventas v2 = new Ventas(carrito2.getVenta());
		Ventas v3 = new Ventas(carrito3.getVenta());
		
		emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		//persistir lo que necesitamos 
		Mesas m=em.find(Mesas.class, 1);
		Mesas m2=em.find(Mesas.class, 2);
		v2.setMesa(m);
		v3.setMesa(m2);
		em.persist(v2);//id1
		em.persist(v3);//id2
		
		em.getTransaction().commit();
	}

	@Test
	public void A_alta() {
		TVentas tVenta=new TVentas("Carlos","10/10/2023",1,true);
		
		crearAussies();
		
		
		TCarrito carrito=new TCarrito(tVenta);
		ArrayList<TPlatoVenta> productos=new ArrayList<TPlatoVenta>();
		productos.add(new TPlatoVenta(1,10));
		productos.add(new TPlatoVenta(2,15));
		
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		ResultContext rc;
		try {
			// no va a funcionar por el em pero esta es la idea
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();

			rc=sa_ventas.altaVenta(carrito);
			if(rc.getEvento()!=Evento.AltaVenta_KO)
				fail("Se ha dado de alta una venta sin productos");
			carrito.setPlatos(productos);
			
			rc=sa_ventas.altaVenta(carrito);
			if(rc.getEvento()!=Evento.AltaVenta_KO)
				fail("Se ha dado de alta una venta con una mesa que no existe");
			
			
			rc=sa_ventas.altaVenta(carrito);
			if(rc.getEvento()!=Evento.AltaVenta_KO)
				fail("Se ha dado de alta una venta con un plato que no existe");
			
			
			
			rc=sa_ventas.altaVenta(carrito);
			if(rc.getEvento()!=Evento.AltaVenta_KO)
				fail("Se ha dado de alta una venta con un plato que no tiene las suficientes unidades");
			
			
			em=emf.createEntityManager();
			
			em.getTransaction().begin();
			Platos plato2=em.find(Platos.class, 2);
			plato2.setStock(20);
			em.getTransaction().commit();
			
			rc=sa_ventas.altaVenta(carrito);
			if(rc.getEvento()==Evento.AltaVenta_KO)
				fail("No se ha dado de alta la venta correctamente cuando todos los argumentos eran correctos");
			
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void B_listarPorCliente() {
		ResultContext rc;
		
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		List<TVentas> listaTVentas = new ArrayList<TVentas>();
		
		try {
			
			crearVentas();
			
			String cliente = "Paco";
			// No deberia existir en este moemtno
			rc=sa_ventas.listarPorCliente(cliente);
			if(rc.getEvento()==Evento.ListarVentasPorCliente_OK)
				fail("No se debe listar con cliente inexistente "+rc.getDato());
			
			cliente = "Javier";
			rc=sa_ventas.listarPorCliente(cliente);
			if(rc.getEvento()!=Evento.ListarVentasPorCliente_OK)
				fail("No se lista correctamente "+rc.getDato());
			
			listaTVentas=(List<TVentas>) rc.getDato();
			if(listaTVentas.size()==0)
				fail("No se han listado ventas");
			
			if(listaTVentas.size() != 2)
				fail("La lista deberia contener 2 elementos con cliente: " + cliente);

			
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
	}

	@Test
	public void C_modificar() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		ResultContext rc;
		
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			
			
			TVentas venta_mod = new TVentas(4,"Mikel","10/10/2022",1,true) ;		// Modificamos nombre y fecha correctamente
			rc = sa_ventas.modificacionVenta(venta_mod);
			
			if(rc.getEvento()!= Evento.ModificarVenta_OK)
				fail("Error al intentar modificar una venta correctamente");
			
			venta_mod = new TVentas(9,"Mikel","10/10/2022",1,true) ;
			rc = sa_ventas.modificacionVenta(venta_mod);
			if(rc.getEvento()!= Evento.ModificarVenta_KO)
				fail("Se �ede modificar venta con id inexistente");
			
			venta_mod = new TVentas(5,"Mikel","10/10/2022",9,true) ;
			rc = sa_ventas.modificacionVenta(venta_mod);
			if(rc.getEvento()!= Evento.ModificarVenta_KO)
				fail("Se �ede modificar venta con idMesa inexistente");
			
			em=emf.createEntityManager();
			
			em.getTransaction().begin();
			Ventas v1 = em.find(Ventas.class, 4);
			v1.setActivo(false);
			em.getTransaction().commit();
			
			venta_mod = new TVentas(4,"Mikel","10/10/2022",1,true);// Estamos en false, se deberia poder modificar a trues
			rc = sa_ventas.modificacionVenta(venta_mod);
			if(rc.getEvento()!= Evento.ModificarVenta_OK)
				fail("No me deja modificar una venta en false a true");
		
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
		
		
	}
	
	@Test
	public void D_Consultar(){
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		TVentas v = new TVentas(4,"Mikel","10/10/2022",1,true);// Estamos en false, se deberia poder modificar a trues
		
		ArrayList<TPlatoVenta> productos=new ArrayList<TPlatoVenta>();
		productos.add(new TPlatoVenta(4,1,10));
		productos.add(new TPlatoVenta(4,2,15));
		Empleado emp=new Empleado(1,"Mario",100.0,45.0,true);
		Mesas mesa=new Mesas();
		mesa.setID(1);
		mesa.setActivo(true);
		mesa.setEmpleado(emp);

		
		TFacturaVenta f=new TFacturaVenta(v,mesa.toTransfer(),productos);
		
		ResultContext rc;
		try {
			
			rc=sa_ventas.consultar(-1);
			if(rc.getEvento()==Evento.ConsultaVenta_OK)
				fail("Se ha consultado una venta con un id no existente");
			
			rc=sa_ventas.consultar(17);//En este momento de ejecucion no deberia existir 
			if(rc.getEvento()==Evento.ConsultaVenta_OK)
				fail("Se ha consultado una venta con un id no existente");
			
			rc=sa_ventas.consultar(4);//Carlos es mikel
			if(rc.getEvento()!=Evento.ConsultaVenta_OK)
				fail("Ha habido un error al comsultar una venta que existe ");
			
			TFacturaVenta facturaMikel=(TFacturaVenta)rc.getDato();
			if(!f.equals(facturaMikel)) {
				fail("La faltura que se ha devuelto no ha sido la esperada");
			}   
			
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void F_listar() {
		ResultContext rc;
		
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		List<TVentas> listaTVentas = new ArrayList<TVentas>();
		
		try {
			
			rc=sa_ventas.listar();
			if(rc.getEvento()==Evento.ListarVentas_KO)
				fail("Ha ocurrido un error en el listar "+rc.getDato());
			listaTVentas=(List<TVentas>) rc.getDato();
			if(listaTVentas.size()==0)
				fail("No se han listado ventas");

			if(listaTVentas.size()!=3)
				fail("No se realiza el listar de las 3 ventas");

			
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void G_listarPorMesa() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		ResultContext rc;
		
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		List<TVentas> listaTVentas = new ArrayList<TVentas>();
		
		
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			
			int idMesa = 5;
			// No deberia existir en este moemtno
			rc=sa_ventas.listarPorMesa(idMesa);
			if(rc.getEvento()==Evento.ListarVentasPorMesa_OK)
				fail("No se debe listar con mesa inexistente "+rc.getDato());
			
			idMesa = -1;
			rc=sa_ventas.listarPorMesa(idMesa);
			if(rc.getEvento()==Evento.ListarVentasPorMesa_OK)
				fail("No se debe listar con mesa negativa "+rc.getDato());
			
			idMesa = 1;
			rc=sa_ventas.listarPorMesa(idMesa);
			if(rc.getEvento()==Evento.ListarVentasPorMesa_KO)
				fail("Ha ocurrido un error en el listar "+rc.getDato());
			
			listaTVentas=(List<TVentas>) rc.getDato();
			if(listaTVentas.size()==0)
				fail("No se han listado ventas");
			
			if(listaTVentas.size() != 2)
				fail("La lista deberia contener 2 elementos con mesa id : " + idMesa);

			em=emf.createEntityManager();
			
			em.getTransaction().begin();
			Mesas m = em.find(Mesas.class, 2);
			m.setActivo(false);
			idMesa = 2;
			em.getTransaction().commit();
			
			rc=sa_ventas.listarPorMesa(idMesa);
			if(rc.getEvento()!=Evento.ListarVentasPorMesa_KO)
				fail("Deja listar ventas con la mesa inacitva");
			
			//m.setActivo(true);

			
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
	}
	
	
	@Test
	public void H_baja() {
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		ResultContext rc;
		
		SAVentas sa_ventas=SAFactory.getInstance().getSAVentas();
		
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();

			int id = 1;
			// No deberia existir en este moemtno
			rc=sa_ventas.bajaVenta(id);
			if(rc.getEvento()==Evento.BajaVenta_OK)
				fail("No se debe dar de baja al estar inexistente "+rc.getDato());
			
			id = -1;
			rc=sa_ventas.bajaVenta(id);
			if(rc.getEvento()==Evento.BajaVenta_OK)
				fail("No se debe dar de baja con id negativo "+rc.getDato());
			
			id = 4;
			rc= sa_ventas.bajaVenta(id);
			if(rc.getEvento() != Evento.BajaVenta_OK)
				fail("Se deberia poder dar de baja con platos activos");
		

		//Damos de baja los platos para poder dar de baja la venta
			em=emf.createEntityManager();
			em.getTransaction().begin();
			Platos plato1=em.find(Platos.class, 1);
			plato1.setActivo(false);
			Platos plato2=em.find(Platos.class, 2);
			plato2.setActivo(false);
			em.getTransaction().commit();
			
			id = 4;
			rc= sa_ventas.bajaVenta(id);
			if(rc.getEvento() == Evento.BajaVenta_OK)
				fail("No Se deberia poder dar de baja");

			em=emf.createEntityManager();
			Ventas v = em.find(Ventas.class, id);
			if (v.getActivo() != false)
				fail("Baja supuestamente correcta no cambia el activo");
			
		} catch (Exception e) {
			fail("Ha ocurrido un error"+e.getMessage());
		}
		
	}
}
