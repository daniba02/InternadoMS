package Negocio.SAFactory;

import Negocio.Academia.Alumnos.SAAlumnos;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Matricula.SAMatricula;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.Restaurante.Empleados.SAEmpleados;
import Negocio.Restaurante.Mesas.SAMesas;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.Producto.SAProductos;
import Negocio.Restaurante.Turnos.SATurnos;
import Negocio.Restaurante.Ventas.SAVentas;

public abstract class SAFactory {
	
	private static SAFactory instance=null;

	public static synchronized SAFactory getInstance() {
		if(instance==null)instance=new SAFactoryImp();
		return instance;
	}
	
	public abstract SAGrupo getSAGrupo();
	public abstract SAMatricula getSAMatricula();
	public abstract SAAnio getSAAnio() ;
	public abstract SAProfesores getSAProfesores();
	public abstract SAMatriculable getSAMatriculable();
	public abstract SAAsignatura getSAAsignatura();
	public abstract SAAlumnos getSAAlumnos();
	
	//RESTAURANTE
	public abstract SAEmpleados getSAEmpleados();
	public abstract SAMesas getSAMesas();
	public abstract SAPlatos getSAPlatos() ;
	public abstract SAProductos getSAProducto();
	public abstract SATurnos getSATurnos();
	public abstract SAVentas getSAVentas();	
}