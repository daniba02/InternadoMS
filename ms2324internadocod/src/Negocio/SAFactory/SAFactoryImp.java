package Negocio.SAFactory;

import Negocio.Academia.Alumnos.SAAlumnos;
import Negocio.Academia.Alumnos.SAAlumnosImp;
import Negocio.Academia.Anio.SAAnio;
import Negocio.Academia.Anio.SAAnioIMP;
import Negocio.Academia.Asignaturas.SAAsignatura;
import Negocio.Academia.Asignaturas.SAAsignaturaImp;
import Negocio.Academia.Grupo.SAGrupo;
import Negocio.Academia.Grupo.SAGrupoIMP;
import Negocio.Academia.Matricula.SAMatricula;
import Negocio.Academia.Matricula.SAMatriculaImp;
import Negocio.Academia.Matriculable.SAMatriculable;
import Negocio.Academia.Matriculable.SAMatriculableImp;
import Negocio.Academia.Profesores.SAProfesores;
import Negocio.Academia.Profesores.SAProfesoresImp;
import Negocio.Restaurante.Empleados.SAEmpleados;
import Negocio.Restaurante.Empleados.SAEmpleadosImp;
import Negocio.Restaurante.Mesas.SAMesas;
import Negocio.Restaurante.Mesas.SAMesasIMP;
import Negocio.Restaurante.Platos.SAPlatos;
import Negocio.Restaurante.Platos.SAPlatosImp;
import Negocio.Restaurante.Producto.SAProductos;
import Negocio.Restaurante.Producto.SAProductosImp;
import Negocio.Restaurante.Turnos.SATurnos;
import Negocio.Restaurante.Turnos.SATurnosImp;
import Negocio.Restaurante.Ventas.SAVentas;
import Negocio.Restaurante.Ventas.SAVentasIMP;

public class SAFactoryImp extends SAFactory{

	@Override
	public SAGrupo getSAGrupo() {
		return new SAGrupoIMP() ;
	}

	@Override
	public SAMatricula getSAMatricula() {
		return new SAMatriculaImp();
	}

	@Override
	public SAAnio getSAAnio() {
		return new SAAnioIMP();
	}

	@Override
	public SAProfesores getSAProfesores() {
		return new SAProfesoresImp();
	}

	@Override
	public SAMatriculable getSAMatriculable() {
		return new SAMatriculableImp();
	}

	@Override
	public SAAsignatura getSAAsignatura() {
		return new SAAsignaturaImp();
	}

	@Override
	public SAAlumnos getSAAlumnos() {
		return new SAAlumnosImp();
	}

	@Override
	public SAEmpleados getSAEmpleados() {
		return new SAEmpleadosImp();
	}

	@Override
	public SAMesas getSAMesas() {
		return new SAMesasIMP();
	}

	@Override
	public SAPlatos getSAPlatos() {
		return new SAPlatosImp();
	}

	@Override
	public SAProductos getSAProducto() {
		return new SAProductosImp();
	}

	@Override
	public SATurnos getSATurnos() {
		// TODO Auto-generated method stub
		return new SATurnosImp();
	}

	@Override
	public SAVentas getSAVentas() {
	
		return new SAVentasIMP();
	}
}