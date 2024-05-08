package Negocio.Restaurante.Platos;

import java.util.ArrayList;
import java.util.Collection;

import Negocio.ResultContext;
import Negocio.Restaurante.Producto.Producto;
import Presentacion.Evento.Evento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Integracion.EntityManagerFactory.FactoriaEntityManager;

public class SAPlatosImp implements SAPlatos{
	
	@Override
	public ResultContext altaPlato(TPlatoCompleto toap) {
		
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			if(toap==null) 
				throw new PlatosException("El plato no puede ser nulo"); 
		
			Integer idplato=toap.getPlato().getId();
			if(idplato == -1){
				Query q = em.createQuery("SELECT p FROM Platos p WHERE p.nombre =:nombre");
				q.setParameter("nombre", toap.getPlato().getNombre());
			
				if(!q.getResultList().isEmpty())
					throw new PlatosException("No puede haber dos platos con el mismo nombre\nNombre repetido: "+toap.getPlato().getNombre());
				Platos pl;
				if(toap instanceof TComida){
					pl = new Comida(toap);
				}else{
					pl = new Bebida(toap);
				}
				em.persist(pl);
				rs = new ResultContext(Evento.AltaPlatos_OK,"Se ha dado de alta el plato correctamente");
				t.commit();

			}else{
		
				Platos p = em.find(Platos.class, idplato);
		
				if(p==null)
					throw new PlatosException("No existe ningun plato con el ID: "+idplato);
			
				if(p.getActivo())
					throw new PlatosException("El plato con ID: " + idplato + " ya esta activo");
				
				p.setActivo(true);
				rs = new ResultContext(Evento.AltaPlatos_OK,"Se ha dado de alta al plato con ID: "+idplato);
				t.commit();

			}
			
		}catch(PlatosException pe){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.AltaPlatos_KO, pe.getMessage());
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.AltaPlatos_KO,"Ha occurrido un error:\n" + e.getMessage());
		}finally{
			if (em != null) {
				em.close();
 		  	}
		}
		
		return rs;
	}
	
	@Override
	public ResultContext bajaPlato(Integer id){
		
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			if(id==null)
				throw new PlatosException("No se admiten ids nulos");
			
			Platos pl = em.find(Platos.class, id);
			
			if(pl == null)
				throw new PlatosException("No se ha encontrado ningun plato con el ID: "+id);

			if(!pl.getActivo())
				throw new PlatosException("El plato con ID: "+id+" ya esta inactivo");

			pl.setActivo(false);
			t.commit();
			rs = new ResultContext(Evento.BajaPlatos_OK,"Plato con ID: "+id+" dado de baja correctamente");
					
		}catch(PlatosException pe){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.BajaPlatos_KO, pe.getMessage());
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.BajaPlatos_KO, "Ha occurido un error:"+ e.getMessage());
		}finally{
			if (em != null ) {
        	 em.close();
 		  	}
		}
		return rs;
	}

	@Override
	public ResultContext consultarPlatos(Integer id){
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			if(id == null)
				throw new PlatosException("No se admiten ids nulos");

			Platos pl = em.find(Platos.class, id, LockModeType.OPTIMISTIC);
			if(pl==null)
				throw new PlatosException("No existe el plato con ID: "+id+" en la BBDD");

			if (!pl.getActivo())
				throw new PlatosException("Platos: " + id + " esta inactivo");
			
			rs = new ResultContext(Evento.ConsultarPlatos_OK,pl.toTransfer());
			t.commit();
			
			
		}catch(PlatosException pe){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ConsultarPlatos_KO, pe.getMessage());
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ConsultarPlatos_KO,"Ha occurrido un error:\n" + e.getMessage());
		}finally{
			if (em != null ) {
        	 em.close();
 		  	}
		}
		return rs;
	}

	@Override
	public ResultContext listAllPlatos(){
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			TypedQuery<Platos> q = em.createQuery("SELECT p FROM Platos p",Platos.class);
			Collection<TPlatoCompleto> res = new ArrayList<TPlatoCompleto>();
			for(Platos p: q.getResultList()){
				res.add(p.toTransfer());
			}
			rs = new ResultContext(Evento.ListarPlatos_OK,res);
			t.commit();
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarPlatos_KO, e.getMessage());
		}
		if (em != null ) {
            em.close();
        }
		return rs;
	}
	
	@Override
	public ResultContext modificar(TPlatoCompleto platos) {
	    ResultContext rs;
	    EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction t = em.getTransaction();
    
	        try {
	            t.begin();
	        	TPlatos tplato = platos.getPlato();
	    	    Integer idPlato = tplato.getId();
	    	    Platos plato = em.find(Platos.class, idPlato);
	    	    
	            if (plato == null) 
					throw new PlatosException("El plato con ID: " + idPlato + " no existe");

				plato.setStock(tplato.getStock());
				plato.setPrecio(tplato.getPrecio());
				plato.setNombre(tplato.getNombre());

				if (platos instanceof TBebida) {
					Bebida bebida = em.find(Bebida.class, idPlato);
					if(bebida==null) {
						t.rollback();
						throw new PlatosException("El plato con ID: "+idPlato+" no es una bebida");
					}
					((Bebida) plato).setTemperatura(((TBebida) platos).getTemperatura());
				}else{
					Comida comida = em.find(Comida.class, idPlato);
					if(comida==null){
						t.rollback();
						throw new PlatosException("El plato con ID: "+idPlato+" no es una comida");
					}
					((Comida)plato).setTipo(((TComida) platos).getTipo());
				}
				
				t.commit();
				rs = new ResultContext(Evento.ModificarPlatos_OK, "El plato con ID: " + idPlato + " se ha modificado correctamente");

	        }catch(PlatosException pe){
	        	if(t != null && t.isActive())
					t.rollback();
				rs = new ResultContext(Evento.ModificarPlatos_KO, pe.getMessage());
	        } catch (Exception e) {
	            if (t != null && t.isActive()) {
	                t.rollback();
	            }
	            rs = new ResultContext(Evento.ModificarPlatos_KO,"Ha occurrido un error:\n" + e.getMessage());
	        }finally{
				em.close();
			}
	    return rs;
	}

	@Override
	public ResultContext listComida() {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			TypedQuery<Platos> q = em.createQuery("SELECT p FROM Platos p WHERE p.id IN (SELECT c.id FROM Comida c)", Platos.class);
			Collection<TPlatoCompleto> res = new ArrayList<TPlatoCompleto>();
			for(Platos p: q.getResultList()){
				res.add(p.toTransfer());
			}
			rs = new ResultContext(Evento.ListarComida_OK, res);
			t.commit();
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarComida_KO, e.getMessage());
		}finally{
			if (em != null ) {
        	 em.close();
 		  	}
		}
		return rs;
	}

	@Override
	public ResultContext listBebida() {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			TypedQuery<Platos> q = em.createQuery("SELECT p FROM Platos p WHERE p.id IN (SELECT b.id FROM Bebida b)", Platos.class);
			Collection<TPlatoCompleto> res = new ArrayList<TPlatoCompleto>();
			for(Platos p: q.getResultList()){
				res.add(p.toTransfer());
			}
			rs = new ResultContext(Evento.ListarBebidas_OK, res);
			t.commit();
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarBebidas_KO, e.getMessage());
		}finally{
			if (em != null ) {
        	 em.close();
 		  	}
		}
		return rs;
	}

	@Override
	public ResultContext listPlatosporProducto(Integer idProducto) {
		ResultContext rs;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			TypedQuery<Platos> q = em.createQuery("SELECT p FROM Platos p JOIN p.productos pr WHERE pr.id = :productoId", Platos.class);
			q.setParameter("productoId", idProducto);
			Collection<TPlatoCompleto> res = new ArrayList<TPlatoCompleto>();
			for(Platos p: q.getResultList()){
				res.add(p.toTransfer());
			}
			rs = new ResultContext(Evento.ListarPlatosPorProducto_OK, res);
			t.commit();
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.ListarPlatosPorProducto_KO, "Ha occurrido un error:\n" + e.getMessage());
		}finally{
			if (em != null ) {
				em.close();
 		  	}
		}
		return rs;
	}
	
	@Override
	public ResultContext vincularPlatoProducto(Integer idPlato, Integer idProducto) {
		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();

		try{
			t.begin();
			Platos plato = em.find(Platos.class, idPlato,LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			Producto producto = em.find(Producto.class, idProducto, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(plato == null)
				throw new PlatosException("El plato de ID: " + idPlato + " no existe");
				
			if(producto == null)
				throw new PlatosException("El producto de ID: " + idProducto + " no existe");
				
			if(plato.getProductos().contains(producto))
				throw new PlatosException("El plato de ID: " + idPlato + " y el producto de ID: " + idProducto + " ya estan vinculados");

			if(!plato.getActivo() || !producto.getActivo())
				throw new PlatosException("El plato de ID: " + idPlato + " y/o el producto de ID: " + idProducto + " no estan/esta activo");

			plato.getProductos().add(producto); // Agregar producto al conjunto de productos de Platos
			rs = new ResultContext(Evento.VincularPlatoProducto_OK,"Se han vinculado correctamente");
			t.commit();

		}catch(PlatosException pe){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.VincularPlatoProducto_KO, pe.getMessage());
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.VincularPlatoProducto_KO,"Ha occurrido un error:\n" + e.getMessage());
		} finally {
			if (em != null ) {
				em.close();
			}			
		}
		return rs;
	}

	@Override
	public ResultContext desvincularPlatoProducto(Integer idPlato, Integer idProducto) {
		ResultContext rs = null;
		EntityManagerFactory emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			Platos plato = em.find(Platos.class, idPlato,LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			Producto producto = em.find(Producto.class, idProducto, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(plato == null)
				throw new PlatosException("El plato de ID: " + idPlato + " no existe");
				
			if(producto == null)
				throw new PlatosException("El producto de ID: " + idProducto + " no existe");
				
			if(!plato.getProductos().contains(producto))
				throw new PlatosException("El plato con ID: " + idPlato + " y el producto con ID: " + idProducto + " no estan vinculados");
				
			plato.getProductos().remove(producto); // Agregar producto al conjunto de productos de Platos
	        rs = new ResultContext(Evento.DesvincularPlatoProducto_OK,"Se han desvinculado correctamente");
	        t.commit();

		}catch(PlatosException pe){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.DesvincularPlatoProducto_KO, pe.getMessage());
		}catch(Exception e){
			if(t != null && t.isActive())
				t.rollback();
			rs = new ResultContext(Evento.DesvincularPlatoProducto_KO,"Ha occurrido un error:\n" + e.getMessage());
		}finally{
			if (em != null ) {
        	 em.close();
 		  	}
		}
		return rs;
	}

}
