package TestDAO.Matricula;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Alumnos.SAAlumnos;
import Negocio.Academia.Alumnos.TAlumnos;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Anio.TAnio;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.Academia.Asignaturas.TOptativa;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.Academia.Matricula.SAMatricula;
import Negocio.Academia.Matricula.TMatricula;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Matriculable.TMatriculable;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAMatriculaTest {
	
	private boolean equals(TMatricula a, TMatricula b) {
		return a.getId().equals(b.getId()) && a.getDescuento().equals(b.getDescuento()) 
				&& a.getActivo().equals(b.getActivo() && a.getAbierto().equals(b.getAbierto())
						&& a.getPreciototal().equals(b.getPreciototal()) && a.getIdAlumno().equals(b.getIdAlumno()));
	}
	private boolean equals(int a, Double b) {
		int x=b.intValue()+1;
		if(x-b>=0.5)
			x-=1;
		return a == x;
	}
	@Test
	public void A_abrir() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatricula sm = SAFactory.getInstance().getSAMatricula();
		SAAlumnos sa = SAFactory.getInstance().getSAAlumnos();

		try{

		Evento a = sm.abrir(null).getEvento();
		if(a != Evento.AbrirMatricula_KO)
			fail("No se puede crear una matricula nula");

		TAlumnos a1= new TAlumnos(1,"12345678A","Matthew","Perry",14,612345678);
		sa.Alta(a1);
		TMatricula m1=new TMatricula(1,1,0,0.2);
		a = sm.abrir(m1).getEvento();
		if(a != Evento.AbrirMatricula_OK)
			fail("No creada una matricula no existente previamente y con alumno valido");

		TMatricula m2=new TMatricula(2,1,0,3.2);
		a = sm.abrir(m2).getEvento();
		if(a != Evento.AbrirMatricula_KO)
			fail("No se puede crear una matricula con descuento que no esta entre 0 y 1");

		TMatricula m3= new TMatricula(a1.getID(),0.3);
		sa.Baja(a1.getID());
		a = sm.abrir(m3).getEvento();
		if(a != Evento.AbrirMatricula_KO)
			fail("No se puede crear una matricula a un alumno inactivo");

		a=sm.abrir(m1).getEvento();
		if(a != Evento.AbrirMatricula_KO)
			fail("No se puede abrir una matricula que ya existe en la base de datos o su alumno no exista en la base de datos");

		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void F_listar() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();

		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
		SAMatricula sm=SAFactory.getInstance().getSAMatricula();
		TAlumnos a1=new TAlumnos(1,"36512751D","Ronnie","Coleman",12,876465079);
		SAAlumnos sa=SAFactory.getInstance().getSAAlumnos();
		sa.Alta(a1);
		TMatricula tm =new TMatricula(1,1,0,0.3);
		sm.abrir(tm);

		Collection<TMatricula>cms=(Collection<TMatricula>) sm.listar().getDato();
		if(cms.size()==0)
			fail("No se ha(n) leido la(s) matricula(s) ya creada(s)");
		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
	@Test
	public void L_consultar() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		//Pendiente
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Test
	public void I_modificar() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SAMatricula sm = SAFactory.getInstance().getSAMatricula();
		SAAlumnos sa = SAFactory.getInstance().getSAAlumnos();

		try{

			TAlumnos a1= new TAlumnos(1,"12345678A","Matthew","Perry",14,612345678);
			sa.Alta(a1);
			TMatricula m1=new TMatricula(1,1,0,0.2);

			sm.abrir(m1);
			m1.setPreciototal(200);
			sm.cerrar(m1.getId());
			Evento a=sm.modificar(m1).getEvento();
			if(a!= Evento.ModificarMatricula_OK)
				fail("No se ha modificado una matricula con datos correctos y existente en la base de datos");

			a=sm.modificar(null).getEvento();
			if(a!= Evento.ModificarMatricula_KO)
				fail("Matricula nula no se puede modificar");
			sm.baja(m1.getId());
			a=sm.modificar(m1).getEvento();
			if(a != Evento.ModificarMatricula_KO)
				fail("No se puede modificar una matricula inactiva");
			TMatricula m2=new TMatricula(a1.getID(),0.2);
			sm.abrir(m2);
			a=sm.modificar(m2).getEvento();
			if(a != Evento.ModificarMatricula_KO)
				fail("No se puede modificar una matricula abierta");

		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
	@Test
	public void D_vincular() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE asignaturas");
			ps2.executeUpdate();
			PreparedStatement ps3 = conn.prepareStatement("TRUNCATE anio");
			ps3.executeUpdate();
			PreparedStatement ps4 = conn.prepareStatement("TRUNCATE grupo");
			ps4.executeUpdate();
			PreparedStatement ps5 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps5.executeUpdate();
			PreparedStatement ps6 = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps6.executeUpdate();
			ps.close();
			ps1.close();
			ps2.close();
			ps3.close();
			ps4.close();
			ps5.close();
			ps6.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			SAMatricula sm=SAFactory.getInstance().getSAMatricula();
			SAMatriculable sma=SAFactory.getInstance().getSAMatriculable();
			SAAsignatura sa=SAFactory.getInstance().getSAAsignatura();
			SAAnio sanio=SAFactory.getInstance().getSAAnio();
			SAGrupo sgrupo=SAFactory.getInstance().getSAGrupo();
			SAAlumnos sal = SAFactory.getInstance().getSAAlumnos();


			TAnio ta = new TAnio(1,"2023");
			TGrupo tg = new TGrupo(1,"A");
			TOptativa tas = new TOptativa(1,true,"Lengua",3);

			sanio.alta(ta);
			sgrupo.alta(tg);
			sa.Alta(tas);

			TAlumnos a1= new TAlumnos(1,"12345678A","Matthew","Perry",14,612345678);
			sal.Alta(a1);


			TMatriculable tma = new TMatriculable(1,123,10,"11:23",ta.getId(),tas.getID(),tg.getId(),true);
			sma.alta(tma);
			TMatricula tm = new TMatricula(1,a1.getID(),0,0.1);
			sm.abrir(tm);

			Evento a=sm.vincular(tm.getId(),tma.getId()).getEvento();
			if(a != Evento.VincularMatriculaMatriculable_OK)
				fail("Fallo al haber intentado vincular matricula con matriculable correctamente");

			a=sm.vincular(null, null).getEvento();
			if(a !=Evento.VincularMatriculaMatriculable_KO)
				fail("Fallo al vincular con ids nulos");

			a=sm.vincular(tm.getId(),tma.getId()).getEvento();
			if(a!=Evento.VincularMatriculaMatriculable_KO)
				fail("Fallo al vincular una matricula cerrada");

			a=sm.vincular(tm.getId(), 6).getEvento();
			if(a!=Evento.VincularMatriculaMatriculable_KO)
				fail("Fallo al vincular con una matricula o matriculable inexistente en la base de datos");

			TMatriculable tma1=new TMatriculable(2,421,32,"98:12",1,1,1,true);
			sma.alta(tma1);
			sma.baja(tma1.getId());
			a=sm.vincular(tm.getId(),tma1.getId()).getEvento();
			if(a!=Evento.VincularMatriculaMatriculable_KO)
				fail("Fallo al vincular con una matricula o matriculable inactivo");

		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
	@Test
	public void E_desvincular() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			PreparedStatement ps5 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps5.executeUpdate();
			ps.close();
			ps1.close();
			ps5.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			SAMatricula sm=SAFactory.getInstance().getSAMatricula();
			SAMatriculable sma =SAFactory.getInstance().getSAMatriculable();
			SAAlumnos sal = SAFactory.getInstance().getSAAlumnos();

			Evento a=sm.desvincular(null, null).getEvento();
			if(a !=Evento.DesvincularMatriculaMatriculable_KO)
				fail("Fallo al desvincular una matricula y un matriculable nulos");

			a=sm.desvincular(100, 150).getEvento();
			if(a!=Evento.DesvincularMatriculaMatriculable_KO)
				fail("Fallo al desvincular una matricula y un matriculable inexistente");

			TAlumnos a1= new TAlumnos(1,"12345678A","Matthew","Perry",14,612345678);
			sal.Alta(a1);

			TMatricula tm = new TMatricula(1,1,20,0.2);
			sm.abrir(tm);
			TMatriculable tma = new TMatriculable(1,522,12,"12:12",1,1,1,true);
			sma.alta(tma);
			a=sm.desvincular(tm.getId(), tma.getId()).getEvento();
			if(a!=Evento.DesvincularMatriculaMatriculable_KO)
				fail("Fallo al desvincular una matricula y un matriculable que no estaban vinculados");
			
			sm.vincular(tm.getId(), tma.getId());
			a=sm.desvincular(tm.getId(), tma.getId()).getEvento();
			if(a!=Evento.DesvincularMatriculaMatriculable_OK)
				fail("Fallo al desvincular correctamente un matriculable y una matricula");
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void B_baja() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
		SAMatricula sm=SAFactory.getInstance().getSAMatricula();
		SAAlumnos sa = SAFactory.getInstance().getSAAlumnos();

		TAlumnos a1= new TAlumnos(1,"12345678A","Matthew","Perry",14,612345678);
		sa.Alta(a1);
		TMatricula m1 = new TMatricula(1,1,32,0.12);
		sm.abrir(m1);

		Evento a=sm.baja(null).getEvento();
		if(a !=Evento.BajaMatricula_KO)
			fail("Fallo al tratar de dar de baja a un id nulo");

		a=sm.baja(210).getEvento();
		if(a!=Evento.BajaMatricula_KO)
			fail("Fallo al tratar de dar de baja a un id que no existe en la base de datos");
		sm.cerrar(1);
		a=sm.baja(1).getEvento();
		if(a!=Evento.BajaMatricula_OK)
			fail("Fallo al dar de baja una matricula correctamente");

		TMatricula m2 = new TMatricula(2,a1.getID(),123,0.31);
		sm.abrir(m2);

		sm.cerrar(m2.getId());
		sm.baja(m2.getId());
		a=sm.baja(m2.getId()).getEvento();
		if(a!=Evento.BajaMatricula_KO)
			fail("Fallo al haber dado de baja una matricula que estaba cerrada e inactiva");

		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
	@Test
	public void C_cerrar() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try{
			SAMatricula sm=SAFactory.getInstance().getSAMatricula();
			TMatricula m1 = new TMatricula(1,1,32,0.12);
			sm.abrir(m1);

			Evento a=sm.cerrar(null).getEvento();
			if(a !=Evento.CerrarMatricula_KO)
				fail("Fallo al tratar de cerrar un id de matricula nulo");

			a=sm.cerrar(m1.getId()).getEvento();
			if(a !=Evento.CerrarMatricula_OK)
				fail("Fallo al tratar de cerrar correctamente una matricula");

			a=sm.cerrar(230).getEvento();
			if(a!=Evento.CerrarMatricula_KO)
				fail("Fallo al tratar de cerrar una matricula que no existe en la base de datos");

		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
	@Test
	public void K_consultarNota() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE matriculable");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps2.executeUpdate();
			ps.close();
			ps1.close();
			ps2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			SAMatricula sm = SAFactory.getInstance().getSAMatricula();
			SAMatriculable sma = SAFactory.getInstance().getSAMatriculable();

			Evento a=sm.consultarNota(null, null).getEvento();
			if(a!=Evento.ConsultarNota_KO)
				fail("Fallo al consultar la nota de ids nulos de matricula y matriculable");

			a=sm.consultarNota(100, 110).getEvento();
			if(a!=Evento.ConsultarNota_KO)
				fail("Fallo al consultar la nota de ids no existentes en la base de datos");

			TMatricula tm = new TMatricula(1,1,889,0.5);
			sm.abrir(tm);
			TMatriculable tma = new TMatriculable(1,912,81,"12:00",1,1,1,true);
			sma.alta(tma);
			a=sm.consultarNota(tm.getId(), tma.getId()).getEvento();
			if(a!=Evento.ConsultarNota_KO)
				fail("Fallo al consultar la nota de una matricula que aun esta abierta");

			sm.vincular(tm.getId(), tma.getId());
			a=sm.consultarNota(tm.getId(), tma.getId()).getEvento();
			if(a!=Evento.ConsultarNota_OK)
				fail("Fallo al consultar la nota de una matricula y matriculable que estan vinculados pero no esta cerrada la matricula");

			sm.cerrar(tm.getId());
			a=sm.consultarNota(tm.getId(), tma.getId()).getEvento();
			if(a !=Evento.ConsultarNota_OK)
				fail("Fallo al consultar la nota de una matricula activa, cerrada y vinculada con un matriculable activo");


		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void G_mostrarMatriculasporAlumno() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE alumno");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			
			SAMatricula sm=SAFactory.getInstance().getSAMatricula();
			SAAlumnos sa =SAFactory.getInstance().getSAAlumnos();

			TAlumnos al=new TAlumnos(1,"76134561G","Michael","Phelps",8,876543123);

			sa.Alta(al);
			Collection<TMatricula>ctrl=new LinkedList<>();
			TMatricula m1=new TMatricula(1,al.getID(),8,0.15);
			TMatricula m2=new TMatricula(2,al.getID(),12,0.1);

			sm.abrir(m1);
			sm.abrir(m2);
			ctrl.add(m1);
			ctrl.add(m2);

			Collection<TMatricula> cms=(Collection<TMatricula>) sm.mostrarMatriculasporAlumno(null).getDato();
			if(cms.size()!=0)
				fail("Fallo al querer mostrar las matriculas de un id nulo");

			cms=(Collection<TMatricula>) sm.mostrarMatriculasporAlumno(al.getID()).getDato();

			Iterator<TMatricula> itctrl=ctrl.iterator();
			Iterator<TMatricula> itcms=cms.iterator();
			while(itctrl.hasNext())
				if(!equals(itctrl.next(),itcms.next()))
					fail("Fallo en la lista de matriculas devueltas por la base de datos");
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void H_mostrarMatriculasporMatriculable() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE matriculable");
			ps1.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			
			SAMatricula sm=SAFactory.getInstance().getSAMatricula();
			SAMatriculable sma =SAFactory.getInstance().getSAMatriculable();
			SAAnio san=SAFactory.getInstance().getSAAnio();
			SAGrupo sag=SAFactory.getInstance().getSAGrupo();
			SAAsignatura saa=SAFactory.getInstance().getSAAsignatura();
			
			san.alta(new TAnio("2023"));
			sag.alta(new TGrupo("Z"));
			saa.Alta(new TOptativa(1,true,"Lenguado",2));
			
			TMatricula m1=new TMatricula(1,1,5,0.88);
			TMatricula m2=new TMatricula(2,1,15,0.62);
			sm.abrir(m1);
			sm.abrir(m2);

			TMatriculable ma =new TMatriculable(1,521,10,"17:01",1,1,1,true);
			sma.alta(ma);

			Collection<TMatricula> cms=(Collection<TMatricula>) sm.mostrarMatriculasporAlumno(null).getDato();
			if(cms.size()!=0)
				fail("Fallo al querer mostrar las matriculas de un id nulo");

			cms=(Collection<TMatricula>) sm.mostrarMatriculasporMatriculable(ma.getId()).getDato();
			if(cms.size()!=0)
				fail("Fallo al querer mostrar matriculas cuando aun no se ha vinculado el matriculable");

			sm.vincular(m1.getId(), ma.getId());
			sm.vincular(m2.getId(), ma.getId());
			cms=(Collection<TMatricula>) sm.mostrarMatriculasporMatriculable(ma.getId()).getDato();
			
			Iterator<TMatricula> itcms=cms.iterator();
			if(!equals(itcms.next().getPreciototal(),(m1.getPreciototal()+ma.getPrecio())*m1.getDescuento())){
				fail("Fallo en la lista de matriculas devueltas por la base de datos");
			}else if(!equals(itcms.next().getPreciototal(),(m2.getPreciototal()+ma.getPrecio())*m2.getDescuento())){
				fail("Fallo en la lista de matriculas devueltas por la base de datos");
			}
			t.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Test
	public void J_calificarMatriculableMatricula() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();

		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE matriculable");
			ps1.executeUpdate();
			ps.close();
			ps1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			SAMatricula sm=SAFactory.getInstance().getSAMatricula();
			SAMatriculable sma=SAFactory.getInstance().getSAMatriculable();

			Evento a=sm.calificarMatriculableMatricula(null, null, null).getEvento();
			if(a!=Evento.CalificarMatriculableMatricula_KO)
				fail("Fallo al querer calificar con ids nulos");

			a=sm.calificarMatriculableMatricula(105, 322, 10).getEvento();
			if(a!=Evento.CalificarMatriculableMatricula_KO)
				fail("Fallo al querer calificar con ids no existentes en la base de datos");

			TMatricula tm = new TMatricula(1,1,5,0.88);
			TMatriculable tma = new TMatriculable(1,521,10,"17:01",1,1,1,true);
			sm.abrir(tm);
			sma.alta(tma);
			a=sm.calificarMatriculableMatricula(tm.getId(),tma.getId(), 10).getEvento();
			if(a!=Evento.CalificarMatriculableMatricula_KO)
				fail("Fallo al querer calificar una matricula y un matriculable no vinculados");

			sm.vincular(tm.getId(),tma.getId());
			a=sm.calificarMatriculableMatricula(tm.getId(),tma.getId(), 10).getEvento();
			if(a !=Evento.CalificarMatriculableMatricula_KO)
				fail("Fallo al querer calificar una matricula y un matriculable cuando la matricula no esta cerrada/activa o el matriculable activo");

			sm.cerrar(tm.getId());
			a=sm.calificarMatriculableMatricula(tm.getId(),tma.getId(), 10).getEvento();
			if(a !=Evento.CalificarMatriculableMatricula_OK)
				fail("Fallo al no calificar una matricula cerrada, activa y vinculada con un matriculable activo");
			t.commit();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}