package TestDAO.Profesor_matriculable;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import Integracion.DAOFactory.DAOFactory;
import Integracion.ProfesorMatriculable.DAOProfesorMatriculable;
import Integracion.Transactions.Transaction;
import Integracion.Transactions.TransactionManager;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOProfesorMatriculableTest {
	

		@Test
		public void A_vincularProfesorMatriculable() {
			DAOProfesorMatriculable daoPM = DAOFactory.getInstance().getDAOProfesorMatriculable();
			Transaction t;

			t = crearTransaccion();
			t.begin();
			
			try {
				Connection conn = (Connection) t.getResource();
				PreparedStatement ps = conn.prepareStatement("TRUNCATE profesor_matriculable");
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int id=daoPM.vincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
			
			t.commit();
		}
		@Test
		public void B_desvincular() {
			DAOProfesorMatriculable daoPM = DAOFactory.getInstance().getDAOProfesorMatriculable();
			Transaction t;

			t = crearTransaccion();
			t.begin();
			
			try {
				Connection conn = (Connection) t.getResource();
				PreparedStatement ps = conn.prepareStatement("TRUNCATE profesor_matriculable");
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int id=daoPM.vincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
		
			 id=daoPM.desvincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han desvinculado correctamnete");
			}
			
			t.commit();
		}
		@Test
		public void C_matriculablesPorProfesor() {
			DAOProfesorMatriculable daoPM = DAOFactory.getInstance().getDAOProfesorMatriculable();
			Transaction t;

			t = crearTransaccion();
			t.begin();
			
			try {
				Connection conn = (Connection) t.getResource();
				PreparedStatement ps = conn.prepareStatement("TRUNCATE profesor_matriculable");
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int id=daoPM.vincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
			 id=daoPM.vincularProfesorMatriculable(1, 2);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
	
			
			 id=daoPM.desvincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han desvinculado correctamnete");
			}
			
			 id=daoPM.desvincularProfesorMatriculable(1, 2);
			if(id < 0){
				fail("No se han desvinculado correctamnete");
			}
			
			t.commit();
		}

		@Test
		public void E_comprobarVinculacion() {
			DAOProfesorMatriculable daoPM = DAOFactory.getInstance().getDAOProfesorMatriculable();
			Transaction t;

			t = crearTransaccion();
			t.begin();
			
			try {
				Connection conn = (Connection) t.getResource();
				PreparedStatement ps = conn.prepareStatement("TRUNCATE profesor_matriculable");
				ps.executeUpdate();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int id=daoPM.vincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
			id=daoPM.comprobarVinculacion(1, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
			 id=daoPM.vincularProfesorMatriculable(2, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
			id=daoPM.comprobarVinculacion(2, 1);
			if(id < 0){
				fail("No se han vinculado correctamnete");
			}
			
			 id=daoPM.desvincularProfesorMatriculable(1, 1);
			if(id < 0){
				fail("No se han desvinculado correctamnete");
			}
			id=daoPM.comprobarVinculacion(1, 1);
			if(id > 0){
				fail("No se han desvinculado correctamnete");
			}
			 id=daoPM.desvincularProfesorMatriculable(1, 2);
			if(id < 0){
				fail("No se han desvinculado correctamnete");
			}
			id=daoPM.comprobarVinculacion(1, 2);
			if(id > 0){
				fail("No se han desvinculado correctamnete");
			}
			
			t.commit();
		}
		
		private Transaction crearTransaccion() {
			try {
				TransactionManager tm = TransactionManager.getInstance();
				tm.newTransaction();
				Transaction t = tm.getTransaction();
				return t;
			} catch (Exception e) {
				fail("Error transaccional");
				System.out.println(e.getMessage());
				return null;
			}
		}

	
}
