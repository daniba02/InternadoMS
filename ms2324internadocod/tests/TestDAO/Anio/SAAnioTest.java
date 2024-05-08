package TestDAO.Anio;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Anio.TAnio;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAAnioTest {

	private void borrarBBDD() {

		Transaction t = null;

		try {
			TransactionManager tm = TransactionManager.getInstance();
			tm.newTransaction();
			t = tm.getTransaction();

			Connection conn = (Connection) t.getResource();
			PreparedStatement ps = conn.prepareStatement("TRUNCATE anio");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE matricula");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE matricula_matriculable");
			ps.executeUpdate();
			ps = conn.prepareStatement("TRUNCATE matriculable");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void A_alta() throws Exception {
		
		borrarBBDD();
		
		SAAnio sa = SAFactory.getInstance().getSAAnio();

		TAnio a = new TAnio(1,"1502");
		
		if(sa.alta(null).getEvento() != Evento.AltaAnio_KO)
			fail("No se puede crear un anio nulo");

		a = new TAnio(1,"1502");
		if(sa.alta(a).getEvento() == Evento.AltaAnio_KO)
			fail("No se puede crear un anio con un anio ya existente");
		
		a = new TAnio(2,"2150");
		if(sa.alta(a).getEvento() == Evento.AltaAnio_KO)
			fail("No se ha creado correctamente un anio");
	}
	
	@Test
	public void B_baja() throws Exception {
		
		SAAnio sa = SAFactory.getInstance().getSAAnio();

		if(sa.baja(null).getEvento() != Evento.BajaAnio_KO)
			fail("No se puede dar de baja un anio nulo");

		if(sa.baja(3).getEvento() != Evento.BajaAnio_KO)
			fail("No se puede dar de baja un anio que no existe");		
		
		if(sa.baja(1).getEvento() == Evento.BajaAnio_KO)
			fail("Se deberia dar de baja el anio");
	}
	
	@Test
	public void C_modificacion() throws Exception {
		
		SAAnio sa = SAFactory.getInstance().getSAAnio();
		
		if(sa.modificacion(null).getEvento() != Evento.ModificacionAnio_KO)
			fail("No se puede modificar un anio con nulo");
		
		TAnio g = new TAnio(156,"1920");
		if(sa.modificacion(g).getEvento() != Evento.ModificacionAnio_KO)
			fail("No se puede modificar un anio que no existe");
		
		g = new TAnio(1,"2122");
		if(sa.modificacion(g).getEvento() == Evento.ModificacionAnio_KO)
			fail("Se deberia modificar el anio");
	}
	
	@Test
	public void D_consulta() throws Exception {
		
		SAAnio sa = SAFactory.getInstance().getSAAnio();
		
		if(sa.consulta(null).getEvento() != Evento.ConsultaAnio_KO)
			fail("No se puede consultar un anio con id nulo");
		
		if(sa.consulta(156).getEvento() != Evento.ConsultaAnio_KO)
			fail("No se puede consultar un anio con que no existe");

		if(sa.consulta(1).getEvento() == Evento.ConsultaAnio_KO)
			fail("Se deveria devolver el anio consultado");
		
		sa.baja(1);
		if(sa.consulta(1).getEvento() != Evento.ConsultaAnio_KO)
			fail("No se puede consultar un anio inactivo");
		
	}
}
