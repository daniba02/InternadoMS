package TestDAO.Matriculable;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Anio.TAnio;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.Academia.Asignaturas.TObligatoria;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.Academia.Matriculable.TOAMatriculable;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.Academia.Profesores.TInterino;
import Negocio.Academia.Profesores.TProfesor;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAMatriculabletest {

	
	@Test
	public void A_create() {
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		Transaction t;
		
		t = crearTransaccion();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			t.commit();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                                                                                 
	 	
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		Evento id;
		try {
		
		//Creo el matriculable en la base de datos
		id = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id != Evento.AltaMatriculable_OK)
			fail("No se ha creado el matriculable");
		//Doy de baja para ver si se puede
		id=sa_mat.baja(1).getEvento();
		
		if(id != Evento.BajaMatriculable_OK)
			fail("No se ha dado de baja correctamente el matriculable");
		
		id= sa_mat.alta(m).getEvento();
		
		if(id != Evento.AltaMatriculable_OK) 
			fail(" no se ha puesto en true correctamente el campo activo de baja correctamente el matriculable");		
		
		//Intento dar de alta a un matriculable que ya existe
		id= sa_mat.alta(m).getEvento();
		if(id != Evento.AltaMatriculable_KO) 
			fail("Sa ha a�adido un matriculable que ya existia");
		
		//Casos varios : donde algun parametro esta mal o no existen los auxiliares
		TMatriculable m2 = new TMatriculable(2, -1, 20, "17:30", 1, 1, 1, true);
		id= sa_mat.alta(m2).getEvento();
		if(id != Evento.AltaMatriculable_KO)
			fail("Se ha dado de alta correctamentecon un campo en negativo");
		
		m2 = new TMatriculable(2, 1, -20, "17:30", 1, 1, 1, true);
		id= sa_mat.alta(m2).getEvento();
		if(id != Evento.AltaMatriculable_KO)
			fail("Se ha dado de alta correctamentecon un campo en negativo");
		
		m2 = new TMatriculable(2, 33, 20, "17:30", 2, 1, 1, true);
		id= sa_mat.alta(m2).getEvento();
		if(id != Evento.AltaMatriculable_KO)
			fail("Se ha dado de alta correctamentecon un anio que no existe");
		
		m2 = new TMatriculable(2, 33, 20, "17:30", 1, 2, 1, true);
		id= sa_mat.alta(m2).getEvento();
		if(id!= Evento.AltaMatriculable_KO)
			fail("Se ha dado de alta correctamentecon una asigantura que no existe");
		
		m2 = new TMatriculable(2, 33, 20, "17:30", 2, 1, 1, true);
		id= sa_mat.alta(m2).getEvento();
		if(id!= Evento.AltaMatriculable_KO)
			fail("Se ha dado de alta correctamentecon un anio que no existe");
	
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	
	
	@Test
	public void B_delete() throws Exception{
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();	                                                                                       
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		Evento id;
		try {
		sa_mat.alta(m);
		
		id=sa_mat.baja(-1).getEvento();
		if(id != Evento.BajaMatriculable_KO)
			fail("se ha dado de baja algo que no existe");
		
		//Doy de baja el que he a�adido para ver si se puede
		id=sa_mat.baja(1).getEvento();
		if(id != Evento.BajaMatriculable_OK)
			fail("No se ha dado de baja correctamente el matriculable");
		
		//Doy de baja el que he a�adido y ya he dado de baja para ver si se puede
		id=sa_mat.baja(1).getEvento();
		if(id != Evento.BajaMatriculable_KO)
			fail("Se ha dado de baja el matriculable no activo ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
	}  
	@Test
	public void C_read() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
	                                                                                  
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);         
		Evento id;
	//	if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		try {
			id = sa_mat.alta(m).getEvento();
			//Compruebo si se ha a�adido 
			if(id != Evento.AltaMatriculable_OK)
				fail("No se ha creado el matriculable");
			
			//Intento consult un matriculable que aun no existe 
			id =sa_mat.read(2).getEvento();
			if(id != Evento.ReadMatriculable_KO)
				fail("se ha consulatdo algo que no existe");
		
		//Intento consult un matriculable que no existe 
		 id=sa_mat.read(-1).getEvento();
				if(id != Evento.ReadMatriculable_KO)
					fail("se ha consulatdo algo que no existe");
		
		//Doy de baja el que he a�adido para ver si se puede
		sa_mat.baja(1).getEvento();
		id=sa_mat.read(1).getEvento();
		if(id != Evento.ReadMatriculable_KO)
			fail("No se ha consultado correctamente el matriculable");
		
		//Doy de baja el que he a�adido y ya he dado de baja para ver si se puede
		id=sa_mat.baja(1).getEvento();
		if(id != Evento.BajaMatriculable_KO)
			fail("No se ha dado de baja correctamente el matriculable");
		//Doy de baja el que he a�adido para ver si se puede
		id=sa_mat.read(1).getEvento();
		if(id!=Evento.ReadMatriculable_KO)
			fail("Se ha consultado algo que no esta activo");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
	}  
	@Test
	public void D_modificar() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		
		                                                                                       
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true); 
		   
		Evento id;
		//if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		try {
		
		//Intento consult un matriculable que no existe 
		 id=sa_mat.modificar(null).getEvento();
		if(id != Evento.ModificarMatriculable_KO)
					fail("se ha consulatdo algo que no existe");
				
		//Creo el matriculable en la base de datos
		id = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id != Evento.AltaMatriculable_OK)
			fail("No se ha creado el matriculable");
		
		TMatriculable aux1 = new TMatriculable(1, 35, 222, "17:30", 1, 1, 1, true);
		id=sa_mat.modificar(aux1).getEvento();
		if(id!=Evento.ModificarMatriculable_OK)
			fail("No se ha modificado el matriculable");
		
		TMatriculable aux2=(TMatriculable) sa_mat.read(1).getDato();
		if(!aux2.equals(aux1))
			fail("No se ha modificado correctamente el matriculable");
		
		//Doy de baja el que he a�adido y ya he dado de baja para ver si se puede
		id=sa_mat.baja(1).getEvento();
		if(id != Evento.BajaMatriculable_OK)
			fail("No se ha dado de baja correctamente el matriculable");
		
		id=sa_mat.modificar(aux1).getEvento();
		if(id!=Evento.ModificarMatriculable_OK)
			fail("No se ha modificado el matriculable");
		
		 aux2=(TMatriculable) sa_mat.read(1).getDato();
		if(!aux2.equals(aux1))
			fail("No se ha modificado correctamente el matriculable");
		
		//Casos varios : donde algun parametro esta mal o no existen los auxiliares
		 aux1 = new TMatriculable(1, -1, 222, "17:30", 1, 1, 1, true);
			id=sa_mat.modificar(aux1).getEvento();
			if(id!=Evento.ModificarMatriculable_KO)
				fail("se ha modificado el matriculable con campos en negativo");
			
		aux1 = new TMatriculable(1, 111, -222, "17:30", 1, 1, 1, true);
		id=sa_mat.modificar(aux1).getEvento();
			if(id!=Evento.ModificarMatriculable_KO)
				fail("se ha modificado el matriculable con campos en negativo");
			
			
		aux1 = new TMatriculable(1, 111, 222, "17:30", 2, 1, 1, true);
		id=sa_mat.modificar(aux1).getEvento();
		if(id!=Evento.ModificarMatriculable_KO)
			fail("se ha modificado el matriculable con un a�o que no existe");
		
		aux1 = new TMatriculable(1, 111, 222, "17:30", 1, 2, 1, true);
		id=sa_mat.modificar(aux1).getEvento();
		if(id!=Evento.ModificarMatriculable_KO)
			fail("se ha modificado el matriculable con un grupo que no existe");
		
		aux1 = new TMatriculable(1, 111, 222, "17:30", 1, 1, 2, true);
		id=sa_mat.modificar(aux1).getEvento();
		if(id!=Evento.ModificarMatriculable_KO)
			fail("se ha modificado el matriculable con una asignatura que no existe");
	
		id=sa_mat.baja(1).getEvento();
		if(id == Evento.BajaMatriculable_KO)
			fail("No se ha dado de baja correctamente el matriculable");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	
	@SuppressWarnings("unchecked")
	@Test
	public void E_listar() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		                                                                                       
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true); 
		TMatriculable aux1 = new TMatriculable(2, 35, 222, "17:30", 1, 1, 1, true);
		Collection<TMatriculable> matriculables=new ArrayList<TMatriculable>();
		Evento id;
		//if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		try {
		matriculables=(Collection<TMatriculable>) sa_mat.listar().getDato();
		if(matriculables.size()!=0)
			fail("Se han listado matriculables que no existen");   
		
		//Creo el matriculable en la base de datos
		id = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id ==Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listar().getDato();
		if(matriculables==null||matriculables.size()!=1||!matriculables.contains(m))
			fail("No se han listado correctamente los matriculables con tamanio 1 "); 
	
		id = sa_mat.alta(aux1).getEvento();
		//Compruebo si se ha a�adido 
		if(id ==Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listar().getDato();
		if(matriculables==null||matriculables.size()!=2||!matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio2 "); 
	
		id=sa_mat.baja(1).getEvento();
		if(id==Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
	
		id=sa_mat.baja(2).getEvento();
		if(id==Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
	}  
	
	@SuppressWarnings("unchecked")
	@Test
	public void F_listarPorAnio() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		                                                                           
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true); 
		TMatriculable aux1 = new TMatriculable(2, 35, 222, "17:30", 1, 1, 1, true);
		TMatriculable aux2 = new TMatriculable(3, 35, 222, "17:30", 2, 1, 1, true);
		Collection<TMatriculable> matriculables=new ArrayList<TMatriculable>();
		Evento id;
		
		//if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		
		SAAnio sa_anio = SAFactory.getInstance().getSAAnio();
		TAnio Anio = new TAnio(2,"2022");
		     
	
		try {
			id=sa_anio.alta(Anio).getEvento();
			if(id == Evento.AltaAnio_KO)fail("No se ha creado el anio extra ");                     
		
			matriculables=(Collection<TMatriculable>) sa_mat.listarPorAnio(1).getDato();
		if(matriculables.size()!=0)
				fail("Se han listado matriculables por a�o cuando la base de datos esta vacia");
		
		//Creo el matriculable en la base de datos
		id = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
	
		if(sa_mat.listarPorAnio(-1).getEvento()!=Evento.ListarMatriculablesPorAnio_KO)
			fail("Se han listado matriculables por a�o con un a�o que no existe");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAnio(1).getDato();
		if(matriculables==null||matriculables.size()!=1)
			fail("No se han listado correctamente los matriculables con tamanio 1 "); 
	
		id = sa_mat.alta(aux1).getEvento();
		//Compruebo si se ha a�adido 
		if(id == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAnio(1).getDato();
		if(matriculables==null||matriculables.size()!=2||!matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio2 "); 
	
		id = sa_mat.alta(aux2).getEvento();
		//Compruebo si se ha a�adido 
		if(id == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		// el aux 2 no puede estar porque tiene el anio 2
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAnio(1).getDato();
		if(matriculables==null||matriculables.size()!=2||matriculables.contains(aux2)||!matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio2 y anio 1"); 
	
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAnio(2).getDato();//solo tiene que estar aux2
		if(matriculables==null||matriculables.size()!=1||!matriculables.contains(aux2)||matriculables.contains(m)||matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio1 y anio 2 "); 
	
		id=sa_mat.baja(1).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAnio(1).getDato();
		if(matriculables==null||matriculables.size()!=2||matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio1 y el primero dado de baja "); 
		
		id=sa_mat.baja(2).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		
		id=sa_mat.baja(3).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente");
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	@SuppressWarnings("unchecked")
	@Test
	public void G_listarPorAsignatura() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		                                                                                   
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true); 
		TMatriculable aux1 = new TMatriculable(2, 35, 222, "17:30", 1, 1, 1, true);
		TMatriculable aux2 = new TMatriculable(3, 35, 222, "17:30", 1, 2, 1, true);
		Collection<TMatriculable> matriculables=new ArrayList<TMatriculable>();
		Evento id;
		
		//if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		
		SAAsignatura sa_asig = SAFactory.getInstance().getSAAsignatura();
		
	
		try {
			TObligatoria asignatura = new TObligatoria(2,true,"SitemasOperativos","pepe");
			id=sa_asig.Alta(asignatura).getEvento();
			if(id == Evento.AltaAsignaturas_KO)fail("No se ha creado el matriculable por los auxiliares");
			
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(1).getDato();
		if(matriculables.size()!=0)
				fail("Se han listado matriculables por asignatura cuando la base de datos esta vacia");
		
		//Creo el matriculable en la base de datos
		Evento id1 = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id1 == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
	
		if(Evento.ListarMatriculablesPorAsignatura_KO!=sa_mat.listarPorAsignatura(-1).getEvento())
			fail("Se han listado matriculables por asigantura con un asigantura que no existe");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=1)fail("No se han listado correctamente los matriculables con asigantura 1 ");
		}
		if(!matriculables.contains(m))fail("No se han listado correctamente los matriculables con asigantura 1 ");

		Evento id2 = sa_mat.alta(aux1).getEvento();
		//Compruebo si se ha a�adido 
		if(id2 == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(1).getDato();
		
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=1)
				fail("No se han listado correctamente los matriculables con asigantura 1 ");
		}
		if(!matriculables.contains(aux1)||!matriculables.contains(m))fail("No se han listado correctamente los matriculables con asigantura 1 ");
		
		Evento id3 = sa_mat.alta(aux2).getEvento();
		//Compruebo si se ha a�adido 
		if(id3 == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		// el aux 2 no puede estar porque tiene el anio 2
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=1)
				fail("No se han listado correctamente los matriculables con asigantura 1 ");
		}
		if(matriculables.contains(aux2))
			fail("No se han listado correctamente los matriculables con asigantura 1 ");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(2).getDato();//solo tiene que estar aux2
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=2)
				fail("No se han listado correctamente los matriculables con asigantura 2 ");
		}
		if(!matriculables.contains(aux2))fail("No se han listado correctamente los matriculables con asigantura 2 ");
		
		id=sa_mat.baja(1).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=1)
				fail("No se han listado correctamente los matriculables con asigantura 1 ");
		}
		if(matriculables.contains(m))
			fail("No se han listado correctamente los matriculables con asigantura 1 ");

		id=sa_mat.baja(2).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=1)
				fail("No se han listado correctamente los matriculables con asigantura 1 ");
		}
		if(matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con asigantura 1 ");

		id=sa_mat.baja(3).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorAsignatura(2).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdAsignatura()!=2)
				fail("No se han listado correctamente los matriculables con asigantura 2 ");
		}
		if(matriculables.contains(aux2))
			fail("No se han listado correctamente los matriculables con asigantura 2 ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	@SuppressWarnings("unchecked")
	@Test
	public void H_listarPorGrupo() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		
		                                                                                       
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true); 
		TMatriculable aux1 = new TMatriculable(2, 35, 222, "17:30", 1, 1, 1, true);
		TMatriculable aux2 = new TMatriculable(3, 35, 222, "17:30", 1, 1,2, true);
		Collection<TMatriculable> matriculables=new ArrayList<TMatriculable>();
		Evento id;
		
		//if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		try {
			
		SAGrupo sa_grup= SAFactory.getInstance().getSAGrupo();
		TGrupo grupo = new TGrupo(2,"C",true);
		id=sa_grup.alta(grupo).getEvento();
		if(id == Evento.AltaGrupo_KO)fail("No se ha creado el matriculable por los auxiliares"); 
		
	
		//Creo el matriculable en la base de datos
		Evento id1 = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id1 ==Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
	
		if(Evento.ListarMatriculablePorGrupo_KO!=sa_mat.listarPorGrupo(-1).getEvento())
			fail("Se han listado matriculables por asigantura con un grupo que no existe");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=1)fail("No se han listado correctamente los matriculables con grupo 1 ");
		}
		if(!matriculables.contains(m))fail("No se han listado correctamente los matriculables con grupo 1 ");

		Evento id2 = sa_mat.alta(aux1).getEvento();
		//Compruebo si se ha a�adido 
		if(id2 == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(1).getDato();
		
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=1)fail("No se han listado correctamente los matriculables con grupo 1 ");
		}
		if(!matriculables.contains(aux1)||!matriculables.contains(m))fail("No se han listado correctamente los matriculables con grupo 1 ");
		
		id2=sa_mat.alta(aux2).getEvento();
		//Compruebo si se ha a�adido 
		if(id2 == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
		// el aux 2 no puede estar porque tiene el anio 2
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=1)fail("No se han listado correctamente los matriculables con grupo 1 ");
		}
		if(matriculables.contains(aux2))fail("No se han listado correctamente los matriculables con grupo 1 ");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(2).getDato();//solo tiene que estar aux2
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=2)fail("No se han listado correctamente los matriculables con grupo 2 ");
		}
		if(!matriculables.contains(aux2))fail("No se han listado correctamente los matriculables con grupo 2 ");
		
		id=sa_mat.baja(1).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=1)fail("No se han listado correctamente los matriculables con grupo 1 ");
		}
		if(matriculables.contains(m))fail("No se han listado correctamente los matriculables con grupo 1 ");

		id=sa_mat.baja(2).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(1).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=1)fail("No se han listado correctamente los matriculables con grupo 1 ");
		}
		if(matriculables.contains(aux1))fail("No se han listado correctamente los matriculables con grupo 1 ");

		id=sa_mat.baja(3).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorGrupo(2).getDato();
		if(matriculables==null)
			fail("No se han listado correctamente los matriculables con "); 
		for(TMatriculable matri:matriculables) {
			if(matri.getIdGrupo()!=2)fail("No se han listado correctamente los matriculables con grupo 2 ");
		}
		if(matriculables.contains(aux2))fail("No se han listado correctamente los matriculables con grupo 2 ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	@SuppressWarnings("unchecked")
	@Test
	public void I_listarPorProfesor() throws Exception{
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE profesores");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
	                                                                                
		// Repasar si los argumentos tienen sentido
		TMatriculable m = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true); 
		TMatriculable aux1 = new TMatriculable(2, 35, 222, "17:30", 1, 1, 1, true);
		TMatriculable aux2 = new TMatriculable(3, 35, 222, "17:30", 2, 1, 1, true);
		Collection<TMatriculable> matriculables=new ArrayList<TMatriculable>();
		Evento id;
		
	
		SAProfesores saProfesor = SAFactory.getInstance().getSAProfesores();
		TProfesor profesor=new TInterino(1,"0556647S",30,"Pablo Gonz�lez",true,20);
		TProfesor profesor2=new TInterino(2,"05577647S",30,"Juan algo",true,30);
		try {
			
		id=saProfesor.alta(profesor).getEvento();
		if(id != Evento.AltaProfesor_OK)fail("falla el alta profesor"); 
		
		id=saProfesor.alta(profesor2).getEvento();
		if(id != Evento.AltaProfesor_OK)fail("falla el alta profesor"); 
		
	
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables.size()!=0)
				fail("Se han listado matriculables por profesor cuando la base de datos esta vacia");
		
		//Creo el matriculable en la base de datos
		Evento id1 = sa_mat.alta(m).getEvento();
		//Compruebo si se ha a�adido 
		if(id1 == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
	
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables.size()!=0)
			fail("Se han listado matriculables por profesor cuando no hay ningun matriculable vinculado a un profesor que no existe");
		
		id=saProfesor.vincularProfesorMatriculable(1, 1).getEvento();
		if(id != Evento.VincularProfesorMatriculable_OK)
			fail("No se ha vinculado correctamente el matriculable y el profesor");
		
		if(Evento.ListarMatriculablePorProfesor_KO!=sa_mat.listarPorProfesor(-1).getEvento())
			fail("Se han listado matriculables por profesor cuando el id de profesor no es valido un profesor que no existe");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables==null||matriculables.size()!=1||!matriculables.contains(m))
			fail("No se han listado correctamente los matriculables con tamanio 1 "); 
	
		id = sa_mat.alta(aux1).getEvento();
		//Compruebo si se ha a�adido 
		if(id == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");
	
		id=saProfesor.vincularProfesorMatriculable(1, 2).getEvento();
		if(id != Evento.VincularProfesorMatriculable_OK)
			fail("No se ha vinculado correctamente el matriculable y el profesor");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables==null||matriculables.size()!=2||!matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio2 "); 
	
		id = sa_mat.alta(aux2).getEvento(); 
		if(id == Evento.AltaMatriculable_KO)
			fail("No se ha creado el matriculable");

		id=saProfesor.vincularProfesorMatriculable(2, 3).getEvento();
		if(id  != Evento.VincularProfesorMatriculable_OK)
			fail("No se ha vinculado correctamente el matriculable y el profesor");
		
		
		// el aux 2 no puede estar porque tiene el grupo 2
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables==null||matriculables.size()!=2||matriculables.contains(aux2)||!matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio2 y profesor 1"); 
	
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(2).getDato();//solo tiene que estar aux2
		if(matriculables==null||matriculables.size()!=1||!matriculables.contains(aux2)||matriculables.contains(m)||matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio1 y grupo 2 "); 
	
		
		id=saProfesor.desvincularProfesorMatriculable(1, 1).getEvento();
		if(id != Evento.DesvincularProfesorMatriculable_OK)
			fail("No se ha vinculado correctamente el matriculable y el profesor");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables==null||matriculables.size()!=1||matriculables.contains(m)||!matriculables.contains(aux1))
			fail("No se han listado correctamente los matriculables con tamanio1 y el primero dado de baja "); 
		
		id=saProfesor.desvincularProfesorMatriculable(1, 2).getEvento();
		if(id != Evento.DesvincularProfesorMatriculable_OK)
		fail("No se han desvinculado correctamente "); 
	
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables.size()!=0)
			fail("se ha listado matriculables que no estan activos o no estan vinculados con ese profesor"); 
		
		id=sa_mat.baja(2).getEvento();
		if(id == Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente"); 
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(1).getDato();
		if(matriculables.size()!=0)
			fail("se ha listado matriculables que no estan activos "); 
		
		id=sa_mat.baja(3).getEvento();
		if(id== Evento.BajaMatriculable_KO)
			fail("No se han dado de baja correctamente");
		id=saProfesor.desvincularProfesorMatriculable(2, 3).getEvento();
		if(id != Evento.DesvincularProfesorMatriculable_OK)
			fail("No se ha vinculado correctamente el matriculable y el profesor");
		
		matriculables=(Collection<TMatriculable>) sa_mat.listarPorProfesor(2).getDato();
		if(matriculables.size()!=0)
			fail("se ha listado matriculables que no estan activos "); 
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	@Test
	public void J_ConsultarInformacion() throws Exception{
		Transaction t = TransactionManager.getInstance().newTransaction();
		
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatriculable sa_mat = SAFactory.getInstance().getSAMatriculable();
		
		                                                                                       
		// Repasar si los argumentos tienen sentido
		TMatriculable mat= new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);         
		TAnio Anio = new TAnio(1,"2021");
		TGrupo grupo = new TGrupo(1,"E",true);
		TObligatoria asignatura = new TObligatoria(1,true,"ModeladoSoftware","hola");
		TOAMatriculable m=new TOAMatriculable(mat,grupo,Anio,asignatura);
		Evento id;
	//	if(!crearauxiliares()) fail("No se ha creado el matriculable por los auxiliares");                        
		try {
		//Intento consult un matriculable que aun no existe 
		
			if(Evento.ConsultarInformacionMatriculable_KO != sa_mat.consultarInformacion(-1).getEvento())
				fail("se ha consulatado algo que no existe");
		
		//Creo el matriculable en la base de datos
		Evento id1 = sa_mat.alta(mat).getEvento();
		//Compruebo si se ha a�adido 
		if(id1 != Evento.AltaMatriculable_OK)
			fail("No se ha creado el matriculable");
		
		//Doy de baja el que he a�adido para ver si se puede
		TOAMatriculable aux=(TOAMatriculable) sa_mat.consultarInformacion(1).getDato();
		if(!aux.equals(m))
			fail("No se ha consultado correctamente el matriculable");
		
		//Doy de baja el que he a�adido y ya he dado de baja para ver si se puede
		id=sa_mat.baja(1).getEvento();
		if(id != Evento.BajaMatriculable_OK)
			fail("No se ha dado de baja correctamente el matriculable");
		
		//Doy de baja el que he a�adido para ver si se puede
		//aux=(TOAMatriculable) sa_mat.consultarInformacion(1).getEvento();
		if(Evento.ConsultarInformacionMatriculable_KO != sa_mat.consultarInformacion(1).getEvento())
			fail("Se ha consultado algo que no esta activo");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}  
	private boolean crearauxiliares() {
		SAAnio sa_anio = SAFactory.getInstance().getSAAnio();
		SAGrupo sa_grup= SAFactory.getInstance().getSAGrupo();
		SAAsignatura sa_asig=SAFactory.getInstance().getSAAsignatura();
		Transaction t;
		
		t = crearTransaccion();
		t.begin();
		
		try {
		Connection conn = (Connection) t.getResource();
		PreparedStatement ps = conn.prepareStatement("TRUNCATE anio");
		ps.executeUpdate();
		
		ps = conn.prepareStatement("TRUNCATE grupo");
		ps.executeUpdate();
		ps = conn.prepareStatement("TRUNCATE profesores");
		ps.executeUpdate();
		ps = conn.prepareStatement("TRUNCATE profesoresinterinos");
		ps.executeUpdate();
		ps = conn.prepareStatement("TRUNCATE profesor_matriculable");
		ps.executeUpdate();
		ps = conn.prepareStatement("TRUNCATE asignaturas");
		ps.executeUpdate();
		ps = conn.prepareStatement("TRUNCATE asignaturasobligatorias");
		ps.executeUpdate();
		ps = conn.prepareStatement("TRUNCATE matricula_matriculable");
		ps.executeUpdate();
		ps.close();
		t.commit();
		
		Evento id;
		TAnio Anio = new TAnio(1,"2021");
		id=sa_anio.alta(Anio).getEvento();
		if(id != Evento.AltaAnio_OK )return false;
		
		TGrupo grupo = new TGrupo(1,"E",true);
		id=sa_grup.alta(grupo).getEvento();
		if(id != Evento.AltaGrupo_OK)return false;
		
		TObligatoria asignatura = new TObligatoria(1,true,"ModeladoSoftware","hola");
		id=sa_asig.Alta(asignatura).getEvento();
		if(id!= Evento.AltaAsignaturas_OK)return false;
		
		return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Transaction crearTransaccion() {
		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			Transaction t = tm.getTransaction();
			t.begin();
			return t;
		} catch (Exception e) {
			fail("Error transaccional");
			return null;
		}
	}


}
