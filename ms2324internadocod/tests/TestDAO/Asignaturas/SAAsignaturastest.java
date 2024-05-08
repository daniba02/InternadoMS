package TestDAO.Asignaturas;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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
import Negocio.Academia.Asignaturas.TAsignaturas;
import Negocio.Academia.Asignaturas.TObligatoria;
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
public class SAAsignaturastest {

	private boolean equals(TAsignaturas a, TAsignaturas b) {
		return a.getID().equals(b.getID()) && a.getNombre().equals(b.getNombre())
				&& a.getActivo().equals(b.getActivo());
	}

	@SuppressWarnings("unused")
	private boolean equals(TObligatoria a, TObligatoria b) {
		return equals((TAsignaturas) a, (TAsignaturas) b) && a.getItinerario().equals(b.getItinerario());
	}
	
	@SuppressWarnings("unused")
	private boolean equals(TOptativa a, TOptativa b) {
		return equals((TAsignaturas) a, (TAsignaturas) b) && a.getNivel() == b.getNivel();
	}

	private void borrarBBDD() {

		Transaction t = null;

		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			PreparedStatement ps1 = conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps1.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void A_alta() {

		borrarBBDD();

		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();

		TOptativa o1 = new TOptativa(1, true, "Mates", 2);
		TObligatoria o2 = new TObligatoria(2, true, "Lengua", "Mola");

		TOptativa vacia = new TOptativa();

		if (sa.Alta(o1).getEvento() != Evento.AltaAsignaturas_OK)
			fail("Se tendría que haber introducido optativa en la bbdd");
		if (sa.Alta(o2).getEvento() != Evento.AltaAsignaturas_OK)
			fail("Se tendría que haber introducido obligatoria en la bbdd");

		if (sa.Alta(o2).getEvento() != Evento.AltaAsignaturas_KO) //-3
			fail("Ya existe la asignatura, se tendría que reactivar o no hacer nada");
		if (sa.Alta(vacia).getEvento() != Evento.AltaAsignaturas_KO) //-2
			fail("Los datos no se introducen bien, retorna mal");

	}

	@Test
	public void B_baja() {
		
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();

		TOptativa o1 = new TOptativa(1, true, "Mates", 2);

		if (sa.Baja(o1.getID()).getEvento() != Evento.BajaAsignaturas_OK) //1
			fail("Se tendría que haber borrado optativa en la bbdd");

		if (sa.Alta(o1).getEvento() != Evento.AltaAsignaturas_KO) //-3
			fail("Ya existe la asignatura, se tendría que reactivar o no hacer nada");

		if (sa.Baja(5).getEvento() != Evento.BajaAsignaturas_KO) //-4
			fail("No existe en la base de datos, debe devolver -4");

		if (sa.Baja(null).getEvento() != Evento.BajaAsignaturas_KO) //-2
			fail("Datos mal introducidos, debe devolver -2");

	}
	
	@Test
	public void C_Modificar() {

		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();

		TOptativa opt2 = new TOptativa(3, true, "Ingles", 2);


		sa.Alta(opt2);
		
		opt2 = new TOptativa(1, true, "Ingles", 2);
		
		if (sa.Modificar(opt2).getEvento() != Evento.ModificarAsignaturas_KO)//-3
			fail("Ya existe una asignatura con ese nombre en la bbdd");

		opt2 = new TOptativa(4, true, "Ingles", 2);
		
		if (sa.Modificar(opt2).getEvento() != Evento.ModificarAsignaturas_KO) //-4
			fail("No existe esa asignatura");
		
		opt2 = new TOptativa(1, true, "EF", 2);

		if (sa.Modificar(opt2).getEvento() != Evento.ModificarAsignaturas_OK) // 1
			fail("Se tiene que haber modificado la asignatura con id 1 y devolver 1");

		if (sa.Modificar(null).getEvento() != Evento.ModificarAsignaturas_KO) //-2
			fail("Datos mal introducidos, debe devolver -2");

	}
	
	@Test
	public void D_Mostrar(){
		
		borrarBBDD();
		
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();
		TOptativa tOpt = new TOptativa(1, true, "Mates", 2);
		TOptativa opt2 = new TOptativa(2, true, "Ingles", 2);
		TObligatoria obl1 = new TObligatoria(3, true, "Lengua", "Mola");
		
		sa.Alta(tOpt);
		sa.Alta(opt2);
		sa.Alta(obl1);
		
		try{
			if (sa.Mostrar(4).getEvento() != Evento.MostrarAsignatura_KO){
				fail("Ha devuelto auna asignatura que no existe");
			}
		 
			if(sa.Mostrar(1).getEvento() == Evento.MostrarAsignatura_KO){ //
				fail("no muestra una asignatura que esxiste en BBDD: devuelve null");
			}
		
			if(!((TAsignaturas)sa.Mostrar(1).getDato()).equals(tOpt)){ //coge el transfer
				fail("devuelve datos erronneos en una busqueda valida");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void E_Listar(){
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();
		ArrayList<TAsignaturas> lista = new ArrayList<TAsignaturas>();
		lista.add(new TOptativa(1, true, "Mates", 2));
		lista.add(new TOptativa(2, true, "Ingles", 2));
		lista.add(new TObligatoria(3, true, "Lengua", "Mola"));
		//
		
		try{
			ArrayList<TAsignaturas> listaR = (ArrayList<TAsignaturas>)sa.Listar().getDato();
			if(listaR == null){
				fail("no recibe bien una lista: ha devuelto null");
			}
			
			if(lista.size() != 3){
				fail("fallo de tamaño resultado deberia ser 3 y es " + lista.size());
			}
			if(!lista.containsAll(listaR)){
				fail("no se ha leido la lista entera");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void F_ListarOptativa(){
	
		borrarBBDD();
		
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();
		ArrayList<TAsignaturas> lista = new ArrayList<TAsignaturas>();
		lista.add(new TOptativa(1, true, "Mates", 2));
		lista.add(new TOptativa(2, true, "Ingles", 2));
		lista.add(new TObligatoria(3, true, "Informatica", "II"));
		
		
		sa.Alta(new TOptativa(1, true, "Mates", 2));
		sa.Alta(new TOptativa(2, true, "Ingles", 2));
		sa.Alta(new TObligatoria(3, true, "Informatica", "II"));
		
		try{
			ArrayList<TOptativa> listaR = (ArrayList<TOptativa>)sa.ListarOptativas().getDato();
			if(listaR == null){
				fail("no recibe bien una lista: ha devuelto null");
			}
			
			if(listaR.size() != 2){
				fail("fallo de tamaño resultado deberia ser 2 y es " + listaR.size());
			}
			if(!lista.containsAll(listaR)){
				fail("no se ha leido la lista entera");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void G_ListarObligatoria(){
	
		borrarBBDD();
		
		SAAsignatura sa = SAFactory.getInstance().getSAAsignatura();
		ArrayList<TObligatoria> lista = new ArrayList<TObligatoria>();
		lista.add(new TObligatoria(1, true, "Mates", "II" ));
		lista.add(new TObligatoria(2, true, "Ingles", "III"));
		
		
		sa.Alta(new TObligatoria(1, true, "Mates", "II" ));
		sa.Alta(new TObligatoria(2, true, "Ingles", "III"));
		
		try{
			ArrayList<TObligatoria> listaR = (ArrayList<TObligatoria>)sa.ListarObligatorias().getDato();
			if(listaR == null){
				fail("no recibe bien una lista: ha devuelto null");
			}
			
			if(lista.size() != 2){
				fail("fallo de tamaño resultado deberia ser 2 y es " + lista.size());
			}
			if(!lista.containsAll(listaR)){
				fail("no se ha leido la lista entera");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void H_NotaMediaAsignatura() throws Exception {
		Transaction t = TransactionManager.getInstance().newTransaction();
		if(t == null)
			throw new Exception("Error transaccional");
		t.begin();
		try {
			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE alumno");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturas");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturasobligatorias");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
			ps=conn.prepareStatement("TRUNCATE asignaturasoptativas");
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SAAlumnos sa_alumn = SAFactory.getInstance().getSAAlumnos();
		SAMatricula sa_matr=SAFactory.getInstance().getSAMatricula();
		SAMatriculable sa_mat=SAFactory.getInstance().getSAMatriculable();
		SAAnio sa_anio=SAFactory.getInstance().getSAAnio();
		SAGrupo sa_grupo=SAFactory.getInstance().getSAGrupo();
		SAAsignatura sa_asig=SAFactory.getInstance().getSAAsignatura();
		TAlumnos a1 = new TAlumnos(1, "12345678V", "Cristiano", "Ronaldo", 14, 654987321);
		TAlumnos a2 = new TAlumnos(2, "01234567H", "Messi", "Cuccittini", 15, 192939102);
		TMatricula m=new TMatricula(1,1,0,0.2);
		TMatricula m2=new TMatricula(2,2,0,0.4);
		TMatriculable m1 = new TMatriculable(1, 33, 20, "17:30", 1, 1, 1, true);
		TAnio an1=new TAnio(1,"2003");
		TGrupo g1=new TGrupo(1,"A");
		TObligatoria o2 = new TObligatoria(1, true, "Lengua", "Mola");
		
		try{
			sa_alumn.Alta(a1);
			sa_alumn.Alta(a2);
			sa_matr.abrir(m);
			sa_matr.abrir(m2);
			sa_anio.alta(an1);
			sa_grupo.alta(g1);
			sa_asig.Alta(o2);
			
			Evento a=sa_asig.NotaMediaAsignatura(null).getEvento();
			if(a!=Evento.NotaMediaAsignatura_KO)
				fail("No se permiten ids nulos");
			
			a=sa_asig.NotaMediaAsignatura(3).getEvento();
			if(a!=Evento.NotaMediaAsignatura_KO)
				fail("No se puede mostrar la media de una asignatura que no existe en la BBDD");
			
			a=sa_asig.NotaMediaAsignatura(1).getEvento();
			if(a!=Evento.NotaMediaAsignatura_KO)
				fail("No se puede mostrar la media de una asignatura que no ha sido vinculada a ninguna matricula");
			
			sa_mat.alta(m1);
			sa_matr.vincular(1, 1);
			sa_matr.vincular(2, 1);
			sa_matr.cerrar(1);
			sa_matr.cerrar(2);
			
			a=sa_asig.NotaMediaAsignatura(1).getEvento();
			if(a!=Evento.NotaMediaAsignatura_KO)
				fail("No se puede mostrar la media de una asignatura que no ha sido vinculada a ninguna matricula");
			
			
			sa_matr.calificarMatriculableMatricula(1, 1, 6);
			sa_matr.calificarMatriculableMatricula(2, 1, 4);
			
			a=sa_asig.NotaMediaAsignatura(1).getEvento();
			if(a==Evento.NotaMediaAsignatura_KO)
				fail("No se han listado las notas correctamente");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		t.commit();
	}
}
