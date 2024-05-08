package Negocio.Restaurante.Producto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Platos.Platos;
import Presentacion.Evento.Evento;

public class SAProductosImp implements SAProductos {

	@Override
	public ResultContext altaProducto(TProducto producto) {
		ResultContext rs = null;
		EntityManager em = FactoriaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();

		try {
			trans.begin();
			if (producto == null)
				throw new ProductoException("El producto no puede ser nulo");

			Producto p = null;

			if (producto.getId() == -1) {// crear
				Query q = em.createQuery("SELECT p FROM Producto p WHERE p.nombre =:nombre");
				q.setParameter("nombre", producto.getNombre());

				if (!q.getResultList().isEmpty())
					throw new ProductoException("No puede haber dos productos con el mismo nombre");

				em.persist(new Producto(producto));
				rs = new ResultContext(Evento.AltaProductos_OK, "producto dado de alta correctamente");
				trans.commit();

			} else { // reactivar
				p = em.find(Producto.class, producto.getId());// LockModeType.OPTIMISTIC);

				if (p == null)
					throw new ProductoException("El producto que se quiere reactivar no existe");

				if (p.getActivo())
					throw new ProductoException("El producto que se quiere reactivar ya esta activo");

				p.setActivo(true);
				rs = new ResultContext(Evento.AltaProductos_OK, "Se ha reactivado el producto");
				trans.commit();

			}
		} catch (ProductoException e) {
			if (trans != null && trans.isActive())
				trans.rollback();
			rs = new ResultContext(Evento.AltaProductos_KO, e.getMessage());

		} catch (Exception e) {
			if (trans != null && trans.isActive())
				trans.rollback();
			rs = new ResultContext(Evento.AltaProductos_KO, "Se ha producido una excepcion:" + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}

		return rs;
	}

	@Override
	public ResultContext bajaProducto(Integer id) {

		ResultContext rs;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction transaction = null;

		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();

			Producto producto = em.find(Producto.class, id);
			// Se puede dar de baja un producto con Platos activos? No ->
			// Comprobar Platos
			if (producto == null)
				throw new ProductoException("No existe un producto con id: " + id);

			if (!producto.getActivo())
				throw new ProductoException("El producto ya esta dado de baja");

			if (producto.getPlatos().size() > 0) {
				for (Platos p : producto.getPlatos()) {
					// voy bloqueando cada plato
					em.lock(p, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				}
				throw new ProductoException("El producto tiene platos asociados");
			}

			producto.setActivo(false);
			transaction.commit();
			rs = new ResultContext(Evento.BajaProductos_OK, "Producto de id: " + id + " dado de baja correctamente");

		} catch (ProductoException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			rs = new ResultContext(Evento.BajaProductos_KO, e.getMessage());

		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			rs = new ResultContext(Evento.BajaProductos_KO, "Se ha producido una excepcion:" + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;

	}

	@Override
	public ResultContext modificarProducto(TProducto producto) {

		ResultContext rc = null;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction transaction = null;
		Query q;
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			transaction = em.getTransaction();

			transaction.begin();
			if (producto == null)
				throw new ProductoException("El producto introducido es nulo");
			// Integer idProducto = producto.getId();
			Producto prod = em.find(Producto.class, producto.getId());// LockModeType.OPTIMISTIC);

			if (prod == null)
				throw new ProductoException("El producto no existe");
				
			if (!prod.getActivo())
				throw new ProductoException("El producto esta inactivo");
				
			q = em.createQuery("SELECT p FROM Producto p WHERE p.nombre =:nombre", Producto.class);
			q.setParameter("nombre", producto.getNombre());

			if (!q.getResultList().isEmpty() && !(((Producto) q.getSingleResult()).getId() == producto.getId()))
				throw new ProductoException("Ya existe un producto con nombre: " + producto.getNombre());
			
			prod.setNombre(producto.getNombre());
			prod.setCaracteristicas(producto.getCaracteristicas());
			transaction.commit();
			rc = new ResultContext(Evento.ModificarProductos_OK, "Producto se ha modificado correctamente");

		} catch (ProductoException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			rc = new ResultContext(Evento.ModificarProductos_KO, e.getMessage());

		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			rc = new ResultContext(Evento.ModificarProductos_KO, "Se ha producido una excepcion:" + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rc;
	}

	@Override
	public ResultContext consultarProducto(Integer id) {
		ResultContext rs;
		EntityManager em = FactoriaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		Producto p = null;

		try {
			trans.begin();
			if (id == null)
				throw new ProductoException("El id introducido no puede ser nulo");
			p = em.find(Producto.class, id, LockModeType.OPTIMISTIC);

			if (p == null)
				throw new ProductoException("El id introducido no corresponde a ningun producto");

			if (!p.getActivo())
				throw new ProductoException("Producto: " + id + " esta inactivo");

			rs = new ResultContext(Evento.ConsultarProductos_OK, p.toTransfer());
			trans.commit();

		} catch (ProductoException e) {
			if (trans != null && trans.isActive())
				trans.rollback();
			rs = new ResultContext(Evento.ConsultarProductos_KO, e.getMessage());

		} catch (Exception e) {
			if (trans != null && trans.isActive())
				trans.rollback();
			rs = new ResultContext(Evento.ConsultarProductos_KO, "Se ha producido una excepcion:" + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}
		return rs;
	}

	@Override
	public ResultContext listarProductos() {

		ResultContext rs;
		EntityManager em = FactoriaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<Producto> productos = null;
		List<TProducto> tProductos = new ArrayList<TProducto>();

		TypedQuery<Producto> query;

		try {

			transaction.begin();
			query = em.createNamedQuery("Producto.readAll", Producto.class);
			productos = query.getResultList();

			for (Producto p : productos) {
				tProductos.add(p.toTransfer());
			}

			rs = new ResultContext(Evento.ListarProductos_OK, tProductos);
			transaction.commit();

		} catch (Exception e) {
			rs = new ResultContext(Evento.ListarProductos_KO, "Error al listar los productos" + e.getMessage());
			transaction.rollback();

		}
		if (em != null) {
			em.close();
		}

		return rs;
	}

	@Override
	public ResultContext listarProductosPorPlato(Integer idPlato) {
		ResultContext rc;
		EntityManager em = FactoriaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		List<TProducto> tProductos = new ArrayList<TProducto>();
		TypedQuery<Producto> query;

		try {

			transaction.begin();
			if (idPlato < 0)
				throw new ProductoException("El id del plato introducido no es correcto ");

			Platos plato = em.find(Platos.class, idPlato);
			if (plato == null)
				throw new ProductoException("El plato con ID: " + idPlato + " no existe en la BBDD");

			if (!plato.getActivo())
				throw new ProductoException("El plato con ID: " + idPlato + " no esta activo");

			query = em.createQuery("SELECT p FROM Producto p JOIN p.platos pl WHERE pl.id =:idplato", Producto.class);
			query.setParameter("idplato", idPlato);
			for (Producto p : query.getResultList()) {
				tProductos.add(p.toTransfer());
			}

			rc = new ResultContext(Evento.ListarProductosPorPlato_OK, tProductos);
			transaction.commit();

		} catch (ProductoException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			rc = new ResultContext(Evento.ListarProductosPorPlato_KO, e.getMessage());

		} catch (Exception e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			rc = new ResultContext(Evento.ListarProductosPorPlato_KO,
					"Se ha producido una excepcion:" + e.getMessage());

		} finally {
			if (em != null)
				em.close();
		}

		return rc;
	}
}
