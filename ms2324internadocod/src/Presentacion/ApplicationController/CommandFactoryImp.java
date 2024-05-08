package Presentacion.ApplicationController;

import Presentacion.ApplicationController.Commands.Alumnos.CommandAltaAlumnos;
import Presentacion.ApplicationController.Commands.Alumnos.CommandAlumnoAnio;
import Presentacion.ApplicationController.Commands.Alumnos.CommandBajaAlumnos;
import Presentacion.ApplicationController.Commands.Alumnos.CommandConsultarAlumnos;
import Presentacion.ApplicationController.Commands.Alumnos.CommandListarAlumnos;
import Presentacion.ApplicationController.Commands.Alumnos.CommandListarNotasAlumnos;
import Presentacion.ApplicationController.Commands.Alumnos.CommandModificarAlumnos;
import Presentacion.ApplicationController.Commands.Anio.CommandAltaAnio;
import Presentacion.ApplicationController.Commands.Anio.CommandBajaAnio;
import Presentacion.ApplicationController.Commands.Anio.CommandConsultarAnio;
import Presentacion.ApplicationController.Commands.Anio.CommandListarAnio;
import Presentacion.ApplicationController.Commands.Anio.CommandModificarAnio;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandAltaAsignatura;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandBajaAsignatura;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandListarAsignaturas;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandListarObligatorias;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandListarOptativas;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandModificarAsignatura;
import Presentacion.ApplicationController.Commands.Asignaturas.CommandMostrarAsignatura;
import Presentacion.ApplicationController.Commands.Empleados.CommandAltaEmpleados;
import Presentacion.ApplicationController.Commands.Empleados.CommandBajaEmpleados;
import Presentacion.ApplicationController.Commands.Empleados.CommandConsultarEmpleado;
import Presentacion.ApplicationController.Commands.Empleados.CommandListarCamareros;
import Presentacion.ApplicationController.Commands.Empleados.CommandListarCocineros;
import Presentacion.ApplicationController.Commands.Empleados.CommandListarEmpleados;
import Presentacion.ApplicationController.Commands.Empleados.CommandModificarEmpleado;
import Presentacion.ApplicationController.Commands.Empleados.CommandMostrarEmpleadosPorTurno;
import Presentacion.ApplicationController.Commands.Grupo.CommandAltaGrupo;
import Presentacion.ApplicationController.Commands.Grupo.CommandBajaGrupo;
import Presentacion.ApplicationController.Commands.Grupo.CommandConsultarGrupo;
import Presentacion.ApplicationController.Commands.Grupo.CommandListarGrupo;
import Presentacion.ApplicationController.Commands.Grupo.CommandModificarGrupo;
import Presentacion.ApplicationController.Commands.Matricula.CommandAbrirMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandBajaMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandCalificarNotaMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandCerrarMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandConsultarMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandConsultarNotaMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandDesvincularMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandListarMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandListarMatriculasPorAlumno;
import Presentacion.ApplicationController.Commands.Matricula.CommandListarMatriculasPorMatriculable;
import Presentacion.ApplicationController.Commands.Matricula.CommandModificarMatricula;
import Presentacion.ApplicationController.Commands.Matricula.CommandVincularMatricula;
import Presentacion.ApplicationController.Commands.Matriculable.CommandAltaMatriculable;
import Presentacion.ApplicationController.Commands.Matriculable.CommandBajaMatriculable;
import Presentacion.ApplicationController.Commands.Matriculable.CommandConsultarInformacionMatriculable;
import Presentacion.ApplicationController.Commands.Matriculable.CommandListarMatriculable;
import Presentacion.ApplicationController.Commands.Matriculable.CommandListarMatriculablesPorAnio;
import Presentacion.ApplicationController.Commands.Matriculable.CommandListarMatriculablesPorAsignatura;
import Presentacion.ApplicationController.Commands.Matriculable.CommandListarMatriculablesPorGrupo;
import Presentacion.ApplicationController.Commands.Matriculable.CommandListarMatriculablesPorProfesor;
import Presentacion.ApplicationController.Commands.Matriculable.CommandModificarMatriculable;
import Presentacion.ApplicationController.Commands.Mesas.CommandAltaMesa;
import Presentacion.ApplicationController.Commands.Mesas.CommandBajaMesa;
import Presentacion.ApplicationController.Commands.Mesas.CommandConsultarMesa;
import Presentacion.ApplicationController.Commands.Mesas.CommandListarMesas;
import Presentacion.ApplicationController.Commands.Mesas.CommandMesasPorEmpleado;
import Presentacion.ApplicationController.Commands.Mesas.CommandModificarMesa;
import Presentacion.ApplicationController.Commands.Platos.CommandAltaPlatos;
import Presentacion.ApplicationController.Commands.Platos.CommandBajaPlatos;
import Presentacion.ApplicationController.Commands.Platos.CommandConsultarPlatos;
import Presentacion.ApplicationController.Commands.Platos.CommandDesvincularPlatoProducto;
import Presentacion.ApplicationController.Commands.Platos.CommandListarBebidas;
import Presentacion.ApplicationController.Commands.Platos.CommandListarComida;
import Presentacion.ApplicationController.Commands.Platos.CommandListarPlatos;
import Presentacion.ApplicationController.Commands.Platos.CommandListarPlatosPorProducto;
import Presentacion.ApplicationController.Commands.Platos.CommandModificarPlatos;
import Presentacion.ApplicationController.Commands.Platos.CommandVincularPlatoProducto;
import Presentacion.ApplicationController.Commands.Productos.CommandAltaProductos;
import Presentacion.ApplicationController.Commands.Productos.CommandBajaProductos;
import Presentacion.ApplicationController.Commands.Productos.CommandConsultarProductos;
import Presentacion.ApplicationController.Commands.Productos.CommandListarProductos;
import Presentacion.ApplicationController.Commands.Productos.CommandListarProductosPorPlato;
import Presentacion.ApplicationController.Commands.Productos.CommandModificarProductos;
import Presentacion.ApplicationController.Commands.Profesores.CommandAltaProfesores;
import Presentacion.ApplicationController.Commands.Profesores.CommandBajaProfesores;
import Presentacion.ApplicationController.Commands.Profesores.CommandConsultarProfesores;
import Presentacion.ApplicationController.Commands.Profesores.CommandDesvincularProfesorMatriculable;
import Presentacion.ApplicationController.Commands.Profesores.CommandListarProfesorPorMatriculable;
import Presentacion.ApplicationController.Commands.Profesores.CommandListarProfesores;
import Presentacion.ApplicationController.Commands.Profesores.CommandListarProfesoresFijos;
import Presentacion.ApplicationController.Commands.Profesores.CommandListarProfesoresInterinos;
import Presentacion.ApplicationController.Commands.Profesores.CommandModificarProfesores;
import Presentacion.ApplicationController.Commands.Profesores.CommandVincularProfesorMatriculable;
import Presentacion.ApplicationController.Commands.Turnos.CommandAltaTurnos;
import Presentacion.ApplicationController.Commands.Turnos.CommandBajaTurnos;
import Presentacion.ApplicationController.Commands.Turnos.CommandConsultarTurnos;
import Presentacion.ApplicationController.Commands.Turnos.CommandListarTurnos;
import Presentacion.ApplicationController.Commands.Turnos.CommandModificarTurnos;
import Presentacion.ApplicationController.Commands.Ventas.CommandAltaVenta;
import Presentacion.ApplicationController.Commands.Ventas.CommandBajaVenta;
import Presentacion.ApplicationController.Commands.Ventas.CommandConsultarVenta;
import Presentacion.ApplicationController.Commands.Ventas.CommandDevolucionVenta;
import Presentacion.ApplicationController.Commands.Ventas.CommandListarVentas;
import Presentacion.ApplicationController.Commands.Ventas.CommandListarVentasPorCliente;
import Presentacion.ApplicationController.Commands.Ventas.CommandListarVentasPorMesa;
import Presentacion.ApplicationController.Commands.Ventas.CommandModificarVenta;
import Presentacion.Evento.Evento;

public class CommandFactoryImp extends CommandFactory {

	@Override
	public Command getCommand(Evento evento) {
		// TODO Auto-generated method stub

		switch (evento) {

		// ALUMNO
		case AltaAlumnos:
			return new CommandAltaAlumnos();
		case BajaAlumnos:
			return new CommandBajaAlumnos();
		case ConsultarAlumnos:
			return new CommandConsultarAlumnos();
		case ListarAlumnos:
			return new CommandListarAlumnos();
		case ListarNotasAlumnos:
			return new CommandListarNotasAlumnos();
		case ModificarAlumnos:
			return new CommandModificarAlumnos();
		case AlumnoANIO:
			return new CommandAlumnoAnio();
			
		// Anyo
		case AltaAnio:
			return new CommandAltaAnio();
		case BajaAnio:
			return new CommandBajaAnio();
		case ConsultaAnio:
			return new CommandConsultarAnio();
		case ModificacionAnio:
			return new CommandModificarAnio();
		case ListarAnio:
			return new CommandListarAnio();
			
		// ASIGNATURA
	
		case AltaAsignaturas:
			return new CommandAltaAsignatura();
		case BajaAsignaturas:
			return new CommandBajaAsignatura();
		case MostrarAsignatura:
			return new CommandMostrarAsignatura();
		case ListarAsignaturas:
			return new CommandListarAsignaturas();
		case ListarAsignaturasObligatorias:
			return new CommandListarObligatorias();
		case ListarAsignaturasOptativas:
			return new CommandListarOptativas();
		case ModificarAsignaturas:
			return new CommandModificarAsignatura();
			
		// GRUPO
		case AltaGrupo:
			return new CommandAltaGrupo();
		case BajaGrupo:
			return new CommandBajaGrupo();
		case ConsultaGrupo:
			return new CommandConsultarGrupo();
		case ModificacionGrupo:
			return new CommandModificarGrupo();
		case ListarGrupo:
			return new CommandListarGrupo();
		// MATRICULA
		
		case AbrirMatricula:
			return new CommandAbrirMatricula();
		case BajaMatricula:
			return new CommandBajaMatricula();
		case CalificarMatriculableMatricula:
			return new CommandCalificarNotaMatricula();
		case CerrarMatricula:
			return new CommandCerrarMatricula();
		case ConsultarMatricula:
			return new CommandConsultarMatricula();
		case ConsultarNota:
			return new CommandConsultarNotaMatricula();
		case VincularMatriculaMatriculable:
			return new CommandVincularMatricula();
		case DesvincularMatriculaMatriculable:
			return new CommandDesvincularMatricula();
		case ModificarMatricula:
			return new CommandModificarMatricula();
		case ListarMatricula:
			return new CommandListarMatricula();
		case MostrarMatriculasporAlumno:
			return new CommandListarMatriculasPorAlumno();
		case MostrarMatriculasporMatriculable:
			return new CommandListarMatriculasPorMatriculable();

		// MATRICULABLE

		case AltaMatriculable:
			return new CommandAltaMatriculable();
		case BajaMatriculable:
			return new CommandBajaMatriculable();
		case ConsultarInformacionMatriculable:
			return new CommandConsultarInformacionMatriculable();
		case ListarMatriculables:
			return new CommandListarMatriculable();
		case ListarMatriculablesPorAnio:
			return new CommandListarMatriculablesPorAnio();
		case ListarMatriculablePorGrupo:
			return new CommandListarMatriculablesPorGrupo();
		case ListarMatriculablePorProfesor:
			return new CommandListarMatriculablesPorProfesor();
		case ListarMatriculablesPorAsignatura:
			return new CommandListarMatriculablesPorAsignatura();
		case ModificarMatriculable:
			return new CommandModificarMatriculable();

		// PROFESOR
		case AltaProfesor:
			return new CommandAltaProfesores();
		case BajaProfesor:
			return new CommandBajaProfesores();
		case ModificarProfesor:
			return new CommandModificarProfesores();
		case ConsultarProfesor:
			return new CommandConsultarProfesores();
		case ListarProfesores:
			return new CommandListarProfesores();
		case ListarProfesoresPorMatriculable:
			return new CommandListarProfesorPorMatriculable();
		case VincularProfesorMatriculable:
			return new CommandVincularProfesorMatriculable();
		case ListarProfesoresInterinos:
			return new CommandListarProfesoresInterinos();
		case ListarProfesoresFijos:
			return new CommandListarProfesoresFijos();
		case DesvincularProfesorMatriculable:
			return new CommandDesvincularProfesorMatriculable();
			//platos
		case AltaPlatos:
			return new CommandAltaPlatos();
		case BajaPlatos:
			return new CommandBajaPlatos();
		case ConsultarPlatos:
			return new CommandConsultarPlatos();
		case ModificarPlatos:
			return new CommandModificarPlatos();
		case ListarPlatosPorProducto:
			return new CommandListarPlatosPorProducto();
		case ListarPlatos:
			return new CommandListarPlatos();
		case ListarBebidas:
			return new CommandListarBebidas();
		case ListarComida:
			return new CommandListarComida();
		case VincularPlatoProducto:
			return new CommandVincularPlatoProducto();
		case DesvincularPlatoProducto:
			return new CommandDesvincularPlatoProducto();

		//producto
		
		case AltaProductos:
			return new CommandAltaProductos();
		case BajaProductos:
			return new CommandBajaProductos();
		case ModificarProductos:
			return new CommandModificarProductos();
		case ConsultarProductos:
			return new CommandConsultarProductos();
		case ListarProductos:
			return new CommandListarProductos();
		case ListarProductosPorPlato:
			return new CommandListarProductosPorPlato();
			
			
		//MESAS
		case AltaMesas:
			return new CommandAltaMesa();
		case BajaMesas:
			return new CommandBajaMesa();
		case ConsultarMesa:
			return new CommandConsultarMesa();
		case ModificarMesas:
			return new CommandModificarMesa();
		case ListarMesas:
			return new CommandListarMesas();
		case MesasPorEmpleado:
			return new CommandMesasPorEmpleado();
			
		//EMPLEADOS
		case AltaEmpleados:
			return new CommandAltaEmpleados();
		case BajaEmpleados:
			return new CommandBajaEmpleados();
		case ConsultarEmpleados:
			return new CommandConsultarEmpleado();
		case ListarEmpleados:
			return new CommandListarEmpleados();
		case ListarCamareros:
			return new CommandListarCamareros();
		case ListarCocineros:
			return new CommandListarCocineros();
		case ModificarEmpleados:
			return new CommandModificarEmpleado();
		case MostrarEmpleadosPorTurno:
			return new CommandMostrarEmpleadosPorTurno();	
			
		//Turno
		case AltaTurno:
			return new CommandAltaTurnos();
		case BajaTurno:
			return new CommandBajaTurnos();
		case ModificarTurno:
			return new CommandModificarTurnos();
		case ListarTurnos:
			return new CommandListarTurnos();
		case ConsultarTurno:
			return new CommandConsultarTurnos();
			
		//VENTAS
		case AltaVenta:
			return new CommandAltaVenta();
		case BajaVenta:
			return new CommandBajaVenta();
		case ConsultaVenta:
			return new CommandConsultarVenta();
		case DevolucionVenta:
			return new CommandDevolucionVenta();
		case ListarVentas:
			return new CommandListarVentas();
		case ListarVentasPorCliente:
			return new CommandListarVentasPorCliente();
		case ListarVentasPorMesa:
			return new CommandListarVentasPorMesa();
		case ModificarVenta:
			return new CommandModificarVenta();
		
		default:
			return null;
		}
	}

}

