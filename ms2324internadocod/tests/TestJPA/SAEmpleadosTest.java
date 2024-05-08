package TestJPA;

import static org.junit.Assert.*;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Empleados.SAEmpleados;
import Negocio.Restaurante.Empleados.TCamarero;
import Negocio.Restaurante.Empleados.TCocinero;
import Negocio.Restaurante.Empleados.TEmpleado;
import Negocio.Restaurante.Turnos.SATurnos;
import Negocio.Restaurante.Turnos.TTurno;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAEmpleadosTest {
	
	public void inicioTest() {
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		//Borra la tabla y establecemos el AUTO_INCREMENT a 1
		et.begin();
		em.createQuery("DELETE FROM PlatosVentas e").executeUpdate();
		em.createNativeQuery("ALTER TABLE PlatosVentas AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Platos e").executeUpdate();
		em.createNativeQuery("ALTER TABLE Platos AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Ventas e").executeUpdate();
		em.createNativeQuery("ALTER TABLE Ventas AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Mesas e").executeUpdate();
		em.createNativeQuery("ALTER TABLE Mesas AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Empleado e").executeUpdate();
		em.createNativeQuery("ALTER TABLE Empleado AUTO_INCREMENT = 1").executeUpdate();
		em.createQuery("DELETE FROM Turnos t").executeUpdate();
		em.createNativeQuery("ALTER TABLE Turnos AUTO_INCREMENT = 1").executeUpdate();
		et.commit();
	}
	
	@Test
	public void A_alta() {
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		rc = sa.altaEmpleado(emp);
		if(rc.getEvento()!=Evento.AltaEmpleados_KO)
			fail("Se ha dado de alta un empleado nulo");
		
		emp = new TCocinero(1,1,"Josete",1200.0,8.2,true,"Kebab");
		rc = sa.altaEmpleado(emp);
		if(rc.getEvento()!=Evento.AltaEmpleados_KO)
			fail("Se ha dado de alta un empleado con un id que no existe en la bd");
		
		emp = new TCocinero(-1,2,"Josete",1200.0,8.2,true,"Kebab");
		rc = sa.altaEmpleado(emp);
		if(rc.getEvento()!=Evento.AltaEmpleados_KO)
			fail("Se ha dado de alta un empleado con un turno que no existe en la bd");
		
		emp = new TCocinero(-1,1,"Josete",1200.0,8.2,true,"Kebab",25.2);
		rc = sa.altaEmpleado(emp);
		if(rc.getEvento()!=Evento.AltaEmpleados_OK)
			fail("No se ha podido dar de alta a un empleado valido");
	}
	
	@Test
	public void B_Consultar() {
		inicioTest();
        SATurnos saT = SAFactory.getInstance().getSATurnos();
        TTurno turno = new TTurno("mañana","6:00","15:00");
        saT.Alta(turno);

        SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
        ResultContext rc;

        rc = sa.consultarEmpleado(null);
        if(rc.getEvento()!=Evento.ConsultarEmpleados_KO){
            fail("No se permiten ids nulos");
        }

        TEmpleado emp = new TCocinero(-1, 1, "Manolo", 1000.0, 5.5, false,"Kebab",10.2);
        rc = sa.altaEmpleado(emp);
        rc = sa.consultarEmpleado(2);
        if(rc.getEvento()!=Evento.ConsultarEmpleados_KO){
            fail("No se puede consultar un empleado inexistente");
        }

        rc = sa.consultarEmpleado(1);
        if(rc.getEvento()!=Evento.ConsultarEmpleados_OK){
            fail("No se ha consultado el empleado correctamente");
        }
	}
	
	@Test
	public void C_modificar() {
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		rc = sa.modificarEmpleado(null);
		if(rc.getEvento()!=Evento.ModificarEmpleados_KO)
			fail("No se puede modificar un empleado nulo");
		
		emp = new TCocinero(-1,1,"Diego",1200.0,6.5,true, "paella",5.8);
		rc = sa.altaEmpleado(emp);
		
		emp = new TCocinero(2,1,"Diego",1200.0,6.5,true, "paella",5.1);
		rc = sa.modificarEmpleado(emp);
		if(rc.getEvento()!=Evento.ModificarEmpleados_KO)
			fail("No se puede modificar un empleado con un id que no existe");
		
		emp = new TCocinero(1,2,"Diego",1200.0,6.5,true, "paella",7.1);
		rc = sa.modificarEmpleado(emp);
		if(rc.getEvento()!=Evento.ModificarEmpleados_KO)
			fail("No existe el turno que intenta modificar");
		
		emp = new TCocinero(1,1,"Penélope",1200.0,6.5,true, "arroz",5.1);
		rc = sa.modificarEmpleado(emp);
		if(rc.getEvento()!=Evento.ModificarEmpleados_OK)
			fail("No se ha podido modificar a un empleado valido");
	}
	
	@Test
	public void D1_listar() { // Todos los listar algo		
		
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		try{
			emp = new TCocinero(-1,1,"Elsa",1000.0,50.1,true, "paella");
			rc = sa.altaEmpleado(emp);
			emp = new TCamarero(-1,1,"Luisa",1500.0,46.5,true, 30.7);
			rc = sa.altaEmpleado(emp);
			
			rc = sa.listAllEmpleados();
			if(rc.getEvento()==Evento.ListarEmpleados_KO){
				fail("No se han listado correctamente los empleados existentes");
			}
			
			inicioTest();
			
			rc = sa.listAllEmpleados();
			if(rc.getEvento()==Evento.ListarEmpleados_KO){
				fail("Que no haya empleados en la tabla no es un error");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void D2_listarCocineros() {
		
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		try{
			emp = new TCocinero(-1,1,"Elsa",1000.0,50.1,true, "paella");
			rc = sa.altaEmpleado(emp);
			emp = new TCamarero(-1,1,"Luisa",1500.0,46.5,true, 30.7);
			rc = sa.altaEmpleado(emp);
			
			rc = sa.listCocineros();
			if(rc.getEvento()==Evento.ListarEmpleados_KO){
				fail("No se han listado correctamente los cocineros existentes");
			}
			
			inicioTest();
			
			rc = sa.listCocineros();
			if(rc.getEvento()==Evento.ListarEmpleados_KO){
				fail("Que no haya empleados en la tabla no es un error");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void D2_listarCamareros() {
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		try{
			emp = new TCocinero(-1,1,"Elsa",1000.0,50.1,true, "paella");
			rc = sa.altaEmpleado(emp);
			emp = new TCamarero(-1,1,"Luisa",1500.0,46.5,true, 30.7);
			rc = sa.altaEmpleado(emp);
			
			rc = sa.listAllEmpleados();
			if(rc.getEvento()==Evento.ListarEmpleados_KO){
				fail("No se han listado correctamente los camareros existentes");
			}
			
			inicioTest();
			
			rc = sa.listAllEmpleados();
			if(rc.getEvento()==Evento.ListarEmpleados_KO){
				fail("Que no haya empleados en la tabla no es un error");
			}
			
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	@Test
	public void E_mostrar() { //Todos loa mostrar por algo
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		rc = sa.mostrarEmpleadosPorTurno(2);
		if(rc.getEvento()!=Evento.MostrarEmpleadosPorTurno_KO)
			fail("Se ha mostrado una lista de empleados correspondiente a un turno que no existe");
		
		emp = new TCocinero(-1,1,"Josete",1200.0,50.0,true,"Kebab");
		sa.altaEmpleado(emp);
		emp = new TCamarero(-1,1,"Ramoncin",1000.0,35.0,true,2.1);
		sa.altaEmpleado(emp);
		emp = new TCocinero(-1,1,"Alberto",1500.0,40.0,true,"Butifarra");
		sa.altaEmpleado(emp);
		rc = sa.mostrarEmpleadosPorTurno(1);
		if(rc.getEvento()!=Evento.MostrarEmpleadosPorTurno_OK)
			fail("No se ha mostrado una lista de empleados cuando si deberia");
		
		
		saT.Baja(1);
		rc = sa.mostrarEmpleadosPorTurno(1);
		if(rc.getEvento()!=Evento.MostrarEmpleadosPorTurno_KO)
			fail("Se ha mostrado una lista de empleados correspondiente a un turno que esta inactivo");
	}
	
	@Test
	public void F_baja() {
		inicioTest();
		SATurnos saT = SAFactory.getInstance().getSATurnos();
		TTurno turno = new TTurno("mañana","6:00","15:00");
		saT.Alta(turno);
		
		SAEmpleados sa = SAFactory.getInstance().getSAEmpleados();
		TEmpleado emp = null;
		ResultContext rc;
		
		rc = sa.bajaEmpleado(1);
		if(rc.getEvento()!=Evento.BajaEmpleados_KO)
			fail("Se ha dado de baja a un empleado que no existe");
		
		emp = new TCocinero(-1,1,"pepon",1200.0,3.5,true,"pollo",1.7);
		sa.altaEmpleado(emp);
		
		saT.Baja(1);
		rc = sa.bajaEmpleado(1);
		if(rc.getEvento()!=Evento.BajaEmpleados_OK)
			fail("No se ha podido dar de baja a un empleado valido");
		
		
	}
	
}
