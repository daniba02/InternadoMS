package Negocio.Restaurante.Ventas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EntityManagerFactory.FactoriaEntityManager;
import Negocio.ResultContext;
import Negocio.Restaurante.Mesas.Mesas;
import Negocio.Restaurante.Platos.Platos;
import Negocio.Restaurante.PlatosVentas.PlatosVentas;
import Negocio.Restaurante.PlatosVentas.PlatosVentasId;
import Negocio.Restaurante.PlatosVentas.TPlatoVenta;
import Presentacion.Evento.Evento;

public class SAVentasIMP implements SAVentas {

	@Override
	public ResultContext altaVenta(TCarrito carritoDeVenta) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();
			if(!esCarritoBienEscrito(carritoDeVenta))
				throw new VentaException("El carrito introducido es nulo o tiene parametros erroneos");
				
			if(carritoDeVenta.getPlatos().isEmpty())
				throw new VentaException("No se puede registrar una venta sin platos");
			 
				Mesas mesa = em.find(Mesas.class,carritoDeVenta.getVenta().getIdMesa(),LockModeType.OPTIMISTIC_FORCE_INCREMENT);//Tratamos mesa como un departamento 
			
				if(mesa==null||!mesa.getActivo())
					throw new VentaException("No se puede registrar una venta con una mesa inactiva o inexistente");

				Ventas venta=new Ventas(carritoDeVenta.getVenta());
				em.persist(venta);
				ArrayList<PlatosVentas> platosDeventa=new ArrayList<PlatosVentas>();
				
				for(TPlatoVenta pv:carritoDeVenta.getPlatos()){
					
					Platos plato = em.find(Platos.class,pv.getIdPlato());//No nos hace falta un incremento forzado porque al modificar el plato ya se incrementa la version
					
					if(plato==null||!plato.getActivo())
						throw new VentaException("No se puede registrar una venta con platos que no existen o no estan activos");
					
					if(plato.getStock()<pv.getCantidad())
						throw new VentaException("El plato "+pv.getIdPlato()+" no tiene stock, por lo que no se puede realizar la venta");
 
					plato.setStock(plato.getStock()-pv.getCantidad());

					PlatosVentas platoventa=new PlatosVentas(plato, venta, pv.getCantidad(), pv.getCantidad()*plato.getPrecio());
					em.persist(platoventa);
					platosDeventa.add(platoventa);
				}
				venta.setMesa(mesa);
				venta.setPlatos(platosDeventa);
				et.commit();
				rc = new ResultContext(Evento.AltaVenta_OK,"Se ha dado de alta correctamente la venta con id "+venta.getId());
				
		} catch(VentaException e) {
			et.rollback();
			rc = new ResultContext(Evento.AltaVenta_KO,e.getMessage());
			
		}
		catch(Exception e) {
			et.rollback();
			rc = new ResultContext(Evento.AltaVenta_KO, "Error al dar de alta a la venta" +  " " + e.getMessage());
			
		}finally {
			if (em != null)
				em.close();
		} 
		
		return rc;
	}

	private boolean esCarritoBienEscrito(TCarrito c) {
		TVentas v=c.getVenta();
		return c!=null&&v.getIdMesa()>0;//mas comprobsciones si son necesarias
	}


	@Override
	public ResultContext bajaVenta(Integer idVenta) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();
			
			Ventas venta = em.find(Ventas.class, idVenta);
			
			if(venta == null)
				throw new VentaException("La venta con id:"+idVenta+ "no existe");

			if(!venta.getActivo())
				throw new VentaException("La venta con id:" +idVenta+ " no se encuentra activa");
			
					
			venta.setActivo(false);
			//em.persist(venta);
			et.commit();
			rc = new ResultContext(Evento.BajaVenta_OK, "Venta: " + idVenta + " dada de baja correctamente");
				
		} catch(VentaException e) {
			rc = new ResultContext(Evento.ListarVentas_KO, e.getMessage());
			et.rollback();
		}catch(Exception e) {
			et.rollback();
			rc = new ResultContext(Evento.ListarVentas_KO, "Error al listar la venta" +  " " + e.getMessage());
			
		} finally {
			if (em != null)
				em.close();
		}
		
		return rc;
	}

	@Override
	public ResultContext listar() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		
		List<Ventas> listaVentas = null;
		List<TVentas> listaTVentas = new ArrayList<TVentas>();
		TypedQuery<Ventas> q;
		
		try{
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();

			q = em.createNamedQuery("Negocio.Restaurante.Ventas.Ventas.findAll", Ventas.class);
			listaVentas = q.getResultList();

			for(Ventas v : listaVentas) {
				listaTVentas.add(v.toTransfer());
			}
			
			rc = new ResultContext(Evento.ListarVentas_OK, listaTVentas);
			et.commit();
		} catch(Exception e) {
			rc = new ResultContext(Evento.ListarVentas_KO, "Error al listar las Ventas" + e.getMessage());
			et.rollback();
		} finally {
			if (em != null)
				em.close();
		}

		return rc;
	}

	@Override
	public ResultContext modificacionVenta(TVentas venta) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		
		try {
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();
			
			Ventas v = em.find(Ventas.class, venta.getId());
			
			if(v == null) 
				throw new VentaException("Venta: " + venta.getId() + " no existente");
			
			//Tratamos mesa como el departamento de la venta
			Mesas m = em.find(Mesas.class, venta.getIdMesa(),LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			if(m == null)
				throw new VentaException("Mesa: " + venta.getIdMesa() + " no existe");
			
			if(!m.getActivo()) 
				throw new VentaException("Mesa: " + venta.getIdMesa() + " no esta activa en la base de datos");
			
			v.modificarConTransfer(venta);
			v.setMesa(m);
			et.commit();
			rc = new ResultContext(Evento.ModificarVenta_OK, "La venta con id "+v.getId()+" ha sido modificada correctamente");

			
			
		} catch (VentaException e) {
			et.rollback();
			rc = new ResultContext(Evento.ModificarVenta_KO, e.getMessage());
		}catch (Exception e) {
			et.rollback();

			rc = new ResultContext(Evento.ModificarVenta_KO, 
					"Error al modificar la venta: " + venta.getId() + " " + e.getMessage());
		} finally {
			if (em != null)
				em.close();
		}
		return rc;
	}

	@Override
	public ResultContext consultar(Integer idVenta) {
		ResultContext ctx=null;
		EntityManager em=null;
		try {
			em=FactoriaEntityManager.getInstance().getEntityManagerFactory().createEntityManager();
			em.getTransaction().begin();
			if(idVenta<=0) 
				throw new VentaException("El id introducido de la venta no es valido");
			
			Ventas v=em.find(Ventas.class, idVenta, LockModeType.OPTIMISTIC);
			if(v==null) 
				throw new VentaException("La venta no existe");
			
			if (!v.getActivo())
				throw new VentaException("Ventas: " + idVenta + " esta inactivo");
			
			TFacturaVenta f=v.toFactura();
			em.getTransaction().commit();
			ctx=new ResultContext(Evento.ConsultaVenta_OK,f);

		}
		catch(VentaException e) {
			em.getTransaction().rollback();
			ctx=new ResultContext(Evento.ConsultaVenta_KO,e.getMessage());

		}
		catch(Exception e) {
			em.getTransaction().rollback();
			ctx=new ResultContext(Evento.ConsultaVenta_KO,"Error al consultar la factura: "+e.getMessage());

		}
		finally {
			if (em != null)
				em.close();
		}
		return ctx;
	}

	@Override
	public ResultContext listarPorCliente(String cliente) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		
		List<Ventas> listaVentas = null;
		List<TVentas> listaTVentas = new ArrayList<TVentas>();
		TypedQuery<Ventas> q;
		
		try{
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();

			q = em.createNamedQuery("Negocio.Restaurante.Ventas.Ventas.findByCliente", Ventas.class);
			q.setParameter("cliente",cliente);
			listaVentas = q.getResultList();
			
			if(listaVentas.isEmpty()) {
				et.rollback();
				rc = new ResultContext(Evento.ListarVentasPorCliente_KO, "Cliente: " + cliente + " No existe en la BBDD");
			} else {
				for(Ventas v : listaVentas) {
					listaTVentas.add(v.toTransfer());
				}
				
				rc = new ResultContext(Evento.ListarVentasPorCliente_OK, listaTVentas);
				et.commit();				
			}
		} catch(Exception e) {
			et.rollback();

			rc = new ResultContext(Evento.ListarVentasPorCliente_KO, "Error al listar las Ventas por Cliente" + e.getMessage());
		} finally {
			if (em != null)
				em.close();
		}

		return rc;
		
	}

	@Override
	public ResultContext listarPorMesa(Integer idMesa) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		List<TVentas> listaTVentas = new ArrayList<TVentas>();
		
		
		try{
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			et.begin();
			if(idMesa<0) 
				throw new VentaException("El id de la mesa introducido es erroneo ");
				
			Mesas m=em.find(Mesas.class, idMesa, LockModeType.OPTIMISTIC);
			if(m==null)
				throw new VentaException("La mesa introducida no existe");
			if(!m.getActivo()) 
				throw new VentaException("La mesa introducida no se encuentra activa");
			

			if(m.getVentas().isEmpty()) {
				et.rollback();
				rc = new ResultContext(Evento.ListarVentasPorMesa_KO, "Mesa: " + idMesa + " No existe en la BBDD");
			} else {
				for(Ventas v : m.getVentas()) {
					listaTVentas.add(v.toTransfer());
				}
				
				rc = new ResultContext(Evento.ListarVentasPorMesa_OK, listaTVentas);
				et.commit();				
			}
		
		} catch(VentaException e) {
			et.rollback();

			rc = new ResultContext(Evento.ListarVentasPorMesa_KO, e.getMessage());
		}catch(Exception e) {
			et.rollback();

			rc = new ResultContext(Evento.ListarVentasPorMesa_KO, "Error al listar las VentasPorMesa" + e.getMessage());
		}finally {
			if (em != null)
				em.close();
		}

		return rc;
		
	}

	@Override
	public ResultContext devolucionPlatoVenta(TPlatoVenta platoVenta) {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction et = null;
		ResultContext rc = null;
		
		try {
			
			emf = FactoriaEntityManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			et = em.getTransaction();

			
			et.begin();
			
			
			Ventas venta = em.find(Ventas.class, platoVenta.getIdVenta(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			
			if(venta == null) {
				throw new VentaException( "Venta: " + platoVenta.getIdVenta() + " no existente");
			}
			if(!venta.getActivo()) {
				throw new VentaException("Venta: " + platoVenta.getIdVenta() + " no se encuentra activa");
			}
			
			Platos plato = em.find(Platos.class, platoVenta.getIdPlato());
			if(plato == null) {
				throw new VentaException("Venta: " + platoVenta.getIdVenta() + " no existente");
			}
			if(!plato.getActivo()) {
				throw new VentaException( "Venta: " + platoVenta.getIdVenta() + " no activa");
			}
			
			//COMPROBAR CANTIDADES
			PlatosVentasId primaryKey = new PlatosVentasId( platoVenta.getIdPlato(),  platoVenta.getIdVenta());

			
			
			PlatosVentas pv = em.find(PlatosVentas.class, primaryKey);
			
			if(pv==null) {
				throw new VentaException("El plato y la venta introducidos no estan vinculados por lo que no se puede devolver el plato");
			}
			if(platoVenta.getCantidad()>pv.getCantidad()) {
				throw new VentaException("La cantidad que se quiere devolver es superior a la cantidad que se ha comprado");
			}
			
			plato.setStock(plato.getStock()+platoVenta.getCantidad());

			
			if(platoVenta.getCantidad()==pv.getCantidad()) {
				venta.removePlatoDeVenta(pv);
				em.remove(pv);	
			}
			else {
				pv.setCantidad(pv.getCantidad()-platoVenta.getCantidad());
				pv.setPrecio(pv.getPrecio()-plato.getPrecio()*platoVenta.getCantidad());
			}
			et.commit();
			rc = new ResultContext(Evento.DevolucionVenta_OK, "Se ha devuelto correctamente el plato de la venta");

		} catch (VentaException e) {
			et.rollback();

			rc = new ResultContext(Evento.DevolucionVenta_KO,e.getMessage());
		} 
		catch (Exception e) {
			et.rollback();

			rc = new ResultContext(Evento.DevolucionVenta_KO, "Error al devolver plato: " + platoVenta.getIdPlato() + " " + e.getMessage());
		}finally {
			if (em != null)
				em.close();
		}

		return rc;
	}

}
