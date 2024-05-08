package TestJPA;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Empleados.Camarero;
import Negocio.Restaurante.Empleados.Empleado;
import Negocio.Restaurante.Empleados.SAEmpleados;
import Negocio.Restaurante.Empleados.TCamarero;
import Negocio.Restaurante.Empleados.TEmpleado;
import Negocio.Restaurante.Mesas.Mesas;
import Negocio.Restaurante.Mesas.SAMesas;
import Negocio.Restaurante.Mesas.TMesas;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;
import Negocio.Restaurante.Turnos.SATurnos;
import Negocio.Restaurante.Turnos.TTurno;
import Negocio.Restaurante.Turnos.Turnos;
import Negocio.Restaurante.Ventas.TCarrito;
import Negocio.Restaurante.Ventas.TVentas;
import Negocio.Restaurante.Ventas.Ventas;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAMesasTest {

	EntityManagerFactory emf = null;
	EntityManager em = null;
	EntityTransaction et = null;

	public void inicioTest() {

		// Creamos un truno con id 1
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno t = new TTurno(1, "Tarde", "3:00", "8:00");
		saT.Alta(t);

		// Creamos un empleado con id 1 que pertenece al turno id 1
		SAEmpleados saE = SAFactory.getInstance().getSAEmpleados();
		TEmpleado e = new TCamarero(-1, 1, "Eufalesio", 500.0, 40.1, true, 5.2);
		saE.altaEmpleado(e);

	}

	private void crearVentas() {
		EntityManagerFactory emf = null;
		EntityManager em = null;

		TVentas tVenta1 = new TVentas("Javier", "10/10/2024", 1, true);

		TCarrito carrito2 = new TCarrito(tVenta1);
		ArrayList<TPlatoVenta> productos = new ArrayList<TPlatoVenta>();
		productos.add(new TPlatoVenta(1, 10));

		carrito2.setPlatos(productos);

		Ventas v2 = new Ventas(carrito2.getVenta());

		emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		em = emf.createEntityManager();
		em.getTransaction().begin();
		// persistir lo que necesitamos
		Mesas m = em.find(Mesas.class, 2);
		v2.setMesa(m);
		em.persist(v2);// id1

		em.getTransaction().commit();
	}

	@Test
	public void A_alta() {

		emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		em = emf.createEntityManager();
		et = em.getTransaction();

		// Borra la tabla y establecemos el AUTO_INCREMENT a 1
		et.begin();
		em.createQuery("DELETE FROM PlatosVentas m").executeUpdate();
		em.createQuery("DELETE FROM Platos m").executeUpdate();
		em.createQuery("DELETE FROM Ventas m").executeUpdate();
		em.createQuery("DELETE FROM Mesas m").executeUpdate();
		em.createQuery("DELETE FROM Turnos t").executeUpdate();
		em.createQuery("DELETE FROM Empleado e").executeUpdate();
		em.createNativeQuery("ALTER TABLE Mesas AUTO_INCREMENT = 1").executeUpdate();
		em.createNativeQuery("ALTER TABLE Turnos AUTO_INCREMENT = 1").executeUpdate();
		em.createNativeQuery("ALTER TABLE Empleado AUTO_INCREMENT = 1").executeUpdate();
		em.createNativeQuery("ALTER TABLE Ventas AUTO_INCREMENT = 1").executeUpdate();
		em.createNativeQuery("ALTER TABLE Platos AUTO_INCREMENT = 1").executeUpdate();
		em.createNativeQuery("ALTER TABLE PlatosVentas AUTO_INCREMENT = 1").executeUpdate();

		et.commit();

		SAMesas sam = SAFactory.getInstance().getSAMesas();
		Evento a = sam.Alta(null).getEvento();

		if (a != Evento.AltaMesa_KO) {
			fail("No se puede dar de alta una mesa nula");
		}

		// inicioTest();

		em.getTransaction().begin();
		Turnos turno = new Turnos("Tarde", "3:00", "8:00");
		em.persist(turno);
		em.getTransaction().commit();
		em.getTransaction().begin();
		Empleado emp = new Camarero();
		emp.setActivo(true);
		emp.setHoras(8.5);
		emp.setTurno(em.find(Turnos.class, 1));
		;
		emp.setNombre("juan");
		emp.setsueldoPorHora(1234.0);
		((Camarero) emp).setPropinas(5.4);
		em.persist(emp);
		em.getTransaction().commit();

		TMesas mesa1 = new TMesas(1, 2, 20, 1, true);

		ResultContext cont = sam.Alta(mesa1);
		a = cont.getEvento();
		if (a != Evento.AltaMesa_OK)
			fail("No se ha dado de alta una mesa con datos correctos");

		a = sam.Alta(mesa1).getEvento();
		if (a != Evento.AltaMesa_KO)
			fail("Se ha dado de alta una mesa que ya existe");

		sam.Baja(1);
		a = sam.Alta(mesa1).getEvento();
		if (a == Evento.AbrirMatricula_KO)
			fail("No se ha dado de alta una mesa inactiva");

	}

	@Test
	public void B_baja() {

		SAMesas sam = SAFactory.getInstance().getSAMesas();
		Evento a = sam.Baja(null).getEvento();

		if (a != Evento.BajaMesas_KO) {
			fail("No se permiten mesas nulas");
		}

		a = sam.Baja(2).getEvento();
		if (a != Evento.BajaMesas_KO)
			fail("No se puede dar de baja una mesa que no existe");

		a = sam.Baja(1).getEvento();
		if (a == Evento.BajaMesas_KO)
			fail("No se ha dado de baja una mesa con datos correctos");

		a = sam.Baja(1).getEvento();
		if (a != Evento.BajaMesas_KO)
			fail("se ha dado de baja una mesa que esta inactiva");

		crearVentas();

		a = sam.Baja(2).getEvento();
		if (a != Evento.BajaMesas_KO)
			fail("No se puede dar de baja una mesa que no existe");

		TMesas mesa1 = new TMesas(1, 2, 20, 1, true);
		sam.Alta(mesa1);
	}

	@Test
	public void C_modificar() {
		SAMesas sam = SAFactory.getInstance().getSAMesas();
		Evento a = sam.Modificar(null).getEvento();

		if (a != Evento.ModificarMesa_KO) {
			fail("No se permiten mesas nulas");
		}

		TMesas mesa1 = new TMesas();

		mesa1.setID(1);
		mesa1.setCapacidad(30);
		mesa1.setNum(4);
		mesa1.setIDEmpleado(1);

		a = sam.Modificar(mesa1).getEvento();
		if (a == Evento.ModificarMesa_KO)
			fail("No se ha modificado una mesa valida");
		mesa1.setCapacidad(40);
		a = sam.Modificar(mesa1).getEvento();
		if (a == Evento.ModificarMesa_KO)
			fail("No se ha modificado una mesa valida 2");
		mesa1.setID(2);
		a = sam.Modificar(mesa1).getEvento();
		if (a == Evento.ModificarMesa_OK)
			fail("Se ha modificado una mesa no existente");
	}

	@Test
	public void D_consultar() {
		SAMesas sam = SAFactory.getInstance().getSAMesas();
		ResultContext a = sam.Consultar(1);
		TMesas mesa1 = new TMesas(1, 4, 40, 1, true);

		if (a.getEvento() != Evento.ConsultarMesa_OK) {
			fail("No se ha consultado correctamente la mesa");
		}

		if (!mesa1.equals(a.getDato()))
			fail("Los datos de la mesa no corresponden");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void E_Listar() {

		SAMesas sam = SAFactory.getInstance().getSAMesas();
		TMesas mesa2 = new TMesas(2, 2, 20, 1, true);
		sam.Alta(mesa2);
		TMesas mesa3 = new TMesas(3, 3, 20, 1, true);
		sam.Alta(mesa3);

		ResultContext a = sam.Listar();

		if (a.getEvento() != Evento.ListarMesas_OK) {
			fail("No se han listado correctamente las mesas");
		}

		ArrayList<TMesas> list = (ArrayList<TMesas>) a.getDato();

		if (list.size() != 3)
			fail("No se han listad correctamente las mesas");

	}

	@SuppressWarnings("unchecked")
	@Test
	public void F_ListarPorEmpleado() {

		SAMesas sam = SAFactory.getInstance().getSAMesas();

		SAEmpleados saE = SAFactory.getInstance().getSAEmpleados();
		TCamarero e = new TCamarero(-1, 1, "Alberto", 500.0, 40.1, true, 5.2);
		saE.altaEmpleado(e);

		TMesas mesa1 = new TMesas(2, 1, 20, 2, true);
		sam.Alta(mesa1);

		ResultContext a = sam.ListarPorEmpleado(1);

		if (a.getEvento() != Evento.MesasPorEmpleado_OK) {
			fail("No se han listado correctamente las mesas");
		}

		ArrayList<TMesas> list = (ArrayList<TMesas>) a.getDato();

		if (list.size() != 3)
			fail("No se han listado correctamente las mesas con id de empleado 1");

	}

}
