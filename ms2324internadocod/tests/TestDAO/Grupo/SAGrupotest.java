package TestDAO.Grupo;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.TGrupo;
import Negocio.SAFactory.SAFactory;
import Presentacion.Evento.Evento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SAGrupotest {

	private void borrarBBDD() {

		Transaction t = null;

		try {
			TransactionManager tm = TransactionManager.getInstance();
			t = tm.newTransaction();

			Connection conn = (Connection) t.getResource();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("TRUNCATE grupo");
			ps.executeUpdate();
			ps.close();
			t.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void A_alta() throws Exception {
		
		borrarBBDD();
		
		SAGrupo sg = SAFactory.getInstance().getSAGrupo();

		TGrupo g = new TGrupo(1,"12");
		
		if(sg.alta(null).getEvento() != Evento.AltaGrupo_KO)
			fail("No se puede crear un grupo nulo");

		if(sg.alta(g).getEvento() != Evento.AltaGrupo_KO)
			fail("No se puede crear un grupo con el atributo letra diferente de una letra");		
		
		g = new TGrupo("A");
		sg.alta(g);
		//g = new TGrupo("A");
		if(sg.alta(g).getEvento() != Evento.AltaGrupo_KO)
			fail("No se puede crear un grupo con el mismo id");
	}
	
	@Test
	public void B_baja() throws Exception {
		
		SAGrupo sg = SAFactory.getInstance().getSAGrupo();

		if(sg.baja(null).getEvento() != Evento.BajaGrupo_KO)
			fail("No se puede dar de baja un grupo nulo");

		if(sg.baja(2).getEvento() != Evento.BajaGrupo_KO)
			fail("No se puede dar de baja un grupo que no existe");		
		
		if(sg.baja(1).getEvento() != Evento.BajaGrupo_OK)
			fail("Se deberia dar de baja el grupo");
	}
	
	@Test
	public void C_modificacion() throws Exception {
		
		SAGrupo sg = SAFactory.getInstance().getSAGrupo();
		
		if(sg.modificacion(null).getEvento() != Evento.ModificacionGrupo_KO)
			fail("No se puede modificar un grupo con nulo");
		
		TGrupo g = new TGrupo(1,"12");
		if(sg.modificacion(g).getEvento() != Evento.ModificacionGrupo_KO)
			fail("No se puede modificar la letra de un grupo por un valor que no sea una letra");
		
		g = new TGrupo(156,"D");
		if(sg.modificacion(g).getEvento() != Evento.ModificacionGrupo_KO)
			fail("No se puede modificar un grupo que no existe");
		
		g = new TGrupo(1,"D");
		if(sg.modificacion(g).getEvento() != Evento.ModificacionGrupo_OK)
			fail("Se deberia modificar el grupo");
	}
	
	@Test
	public void D_consulta() throws Exception {
		
		SAGrupo sg = SAFactory.getInstance().getSAGrupo();
		
		if(sg.consulta(null).getEvento() != Evento.ConsultaGrupo_KO)
			fail("No se puede consultar un grupo con id nulo");
		
		if(sg.consulta(156).getEvento() != Evento.ConsultaGrupo_KO)
			fail("No se puede consultar un grupo con que no existe");

		if(sg.consulta(1).getEvento() != Evento.ConsultaGrupo_OK)
			fail("Se deveria devolver el grupo consultado");
		
		sg.alta(new TGrupo("F"));
		sg.baja(2);
		if(sg.consulta(2).getEvento() != Evento.ConsultaGrupo_KO)
			fail("No se puede consultar un grupo inactivo");
		
	}
	
}
