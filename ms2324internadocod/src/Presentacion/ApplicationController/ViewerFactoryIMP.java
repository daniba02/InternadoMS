package Presentacion.ApplicationController;

import Presentacion.IGUI.IGUI;
import Presentacion.Vista.GUIMain;
import Presentacion.Vista.Academia.GUIAcademia;
import Presentacion.Vista.Academia.Alumnos.GUIAlumnos;
import Presentacion.Vista.Academia.Alumnos.AltaAlumnos.GUIAltaAlumnos;
import Presentacion.Vista.Academia.Alumnos.AlumnosAnio.GUIAlumnosAnio;
import Presentacion.Vista.Academia.Alumnos.BajaAlumnos.GUIBajaAlumnos;
import Presentacion.Vista.Academia.Alumnos.ConsultarAlumnos.GUIConsultarAlumnos;
import Presentacion.Vista.Academia.Alumnos.ListarAlumnos.GUIListarAlumnos;
import Presentacion.Vista.Academia.Alumnos.ListarNotasAlumnos.GUIListarNotasAlumnos;
import Presentacion.Vista.Academia.Alumnos.ModificarAlumnos.GUIModificarAlumnos;
import Presentacion.Vista.Academia.Anyo.GUIAnyo;
import Presentacion.Vista.Academia.Anyo.AltaAnyo.GUIAltaAnyo;
import Presentacion.Vista.Academia.Anyo.BajaAnyo.GUIBajaAnyo;
import Presentacion.Vista.Academia.Anyo.ConsultarAnyo.GUIConsultarAnyo;
import Presentacion.Vista.Academia.Anyo.ListarAnyo.GUIListarAnyo;
import Presentacion.Vista.Academia.Anyo.ModificarAnyo.GUIModificarAnyo;
import Presentacion.Vista.Academia.Asignatura.GUIAsignatura;
import Presentacion.Vista.Academia.Asignatura.GUIAltaAsignatura.GUIAltaAsignatura;
import Presentacion.Vista.Academia.Asignatura.GUIBajaAsignatura.GUIBajaAsignatura;
import Presentacion.Vista.Academia.Asignatura.GUIConsultarAsignatura.GUIConsultarAsignaturas;
import Presentacion.Vista.Academia.Asignatura.GUIListarAsignaturas.GUIListarAsignaturas;
import Presentacion.Vista.Academia.Asignatura.GUIListarAsignaturasObligatorias.GUIListarAsignaturasObligatorias;
import Presentacion.Vista.Academia.Asignatura.GUIListarAsignaturasOptativas.GUIListarAsignaturasOptativas;
import Presentacion.Vista.Academia.Asignatura.GUIModificarAsignatura.GUIModificarAsignatura;
import Presentacion.Vista.Academia.Asignatura.GUINotaMediaAsignatura.GUINotaMediaAsignatura;
import Presentacion.Vista.Academia.Grupo.GUIGrupo;
import Presentacion.Vista.Academia.Grupo.AltaGrupo.GUIAltaGrupo;
import Presentacion.Vista.Academia.Grupo.BajaGrupo.GUIBajaGrupo;
import Presentacion.Vista.Academia.Grupo.ConsultaGrupo.GUIConsultarGrupo;
import Presentacion.Vista.Academia.Grupo.ListarGrupo.GUIListarGrupo;
import Presentacion.Vista.Academia.Grupo.ModificarGrupo.GUIModificarGrupo;
import Presentacion.Vista.Academia.Matricula.GUIMatricula;
import Presentacion.Vista.Academia.Matricula.GUIAbrirMatricula.GUIAbrirMatricula;
import Presentacion.Vista.Academia.Matricula.GUIBajaMatricula.GUIBajaMatricula;
import Presentacion.Vista.Academia.Matricula.GUICalificarMatricula.GUICalificarMatricula;
import Presentacion.Vista.Academia.Matricula.GUICerrarMatricula.GUICerrarMatricula;
import Presentacion.Vista.Academia.Matricula.GUIConsultarMatricula.GUIConsultarMatricula;
import Presentacion.Vista.Academia.Matricula.GUIConsultarNotaMatricula.GUIConsultarNotaMatricula;
import Presentacion.Vista.Academia.Matricula.GUIDesvincularMatriculaMatriculable.GUIDesvincularMatriculaMatriculable;
import Presentacion.Vista.Academia.Matricula.GUIListarMatriculablesMatricula.GUIListarMatriculablesMatricula;
import Presentacion.Vista.Academia.Matricula.GUIListarMatriculas.GUIListarMatriculas;
import Presentacion.Vista.Academia.Matricula.GUIListarMatriculasAlumno.GUIListarMatriculasAlumno;
import Presentacion.Vista.Academia.Matricula.GUIModificarMatricula.GUIModificarMatricula;
import Presentacion.Vista.Academia.Matricula.GUIVincularMatriculaMatriculable.GUIVincularMatriculaMatriculable;
import Presentacion.Vista.Academia.Matriculable.GUIMatriculable;
import Presentacion.Vista.Academia.Matriculable.GUIAltaMatriculable.GUIAltaMatriculable;
import Presentacion.Vista.Academia.Matriculable.GUIBajaMatriculable.GUIBajaMatriculable;
import Presentacion.Vista.Academia.Matriculable.GUIConsultarMatriculable.GUIConsultarMatriculable;
import Presentacion.Vista.Academia.Matriculable.GUIListarMatriculable.GUIListarMatriculable;
import Presentacion.Vista.Academia.Matriculable.GUIListarMatriculablePorAnio.GUIListarMatriculablePorAnio;
import Presentacion.Vista.Academia.Matriculable.GUIListarMatriculablePorAsignatura.GUIListarMatriculablePorAsignatura;
import Presentacion.Vista.Academia.Matriculable.GUIListarMatriculablePorGrupo.GUIListarMatriculablePorGrupo;
import Presentacion.Vista.Academia.Matriculable.GUIListarMatriculablePorProfesor.GUIListarMatriculablePorProfesor;
import Presentacion.Vista.Academia.Matriculable.GUIModificarMatriculable.GUIModificarMatriculable;
import Presentacion.Vista.Academia.Profesores.GUIProfesores;
import Presentacion.Vista.Academia.Profesores.GUIAltaProfesores.GUIAltaProfesores;
import Presentacion.Vista.Academia.Profesores.GUIBajaProfesores.GUIBajaProfesores;
import Presentacion.Vista.Academia.Profesores.GUIConsultarProfesores.GUIConsultarProfesores;
import Presentacion.Vista.Academia.Profesores.GUIDesvincularMatriculable.GUIDesvincularMatriculable;
import Presentacion.Vista.Academia.Profesores.GUIListarProfesores.GUIListarProfesores;
import Presentacion.Vista.Academia.Profesores.GUIListarProfesoresFijos.GUIListarProfesoresFijos;
import Presentacion.Vista.Academia.Profesores.GUIListarProfesoresInterinos.GUIListarProfesoresInterinos;
import Presentacion.Vista.Academia.Profesores.GUIListarProfesoresPorMatriculable.GUIListarProfesoresPorMatriculable;
import Presentacion.Vista.Academia.Profesores.GUIModificarProfesores.GUIModificarProfesores;
import Presentacion.Vista.Academia.Profesores.GUIVincularMatriculable.GUIVincularMatriculable;
import Presentacion.Vista.Restaurante.GUIRestaurante;
import Presentacion.Vista.Restaurante.Empleados.GUIAltaEmpleados;
import Presentacion.Vista.Restaurante.Empleados.GUIBajaEmpleados;
import Presentacion.Vista.Restaurante.Empleados.GUIConsultarEmpleados;
import Presentacion.Vista.Restaurante.Empleados.GUIEmpleados;
import Presentacion.Vista.Restaurante.Empleados.GUIListarCamareros;
import Presentacion.Vista.Restaurante.Empleados.GUIListarCocineros;
import Presentacion.Vista.Restaurante.Empleados.GUIListarEmpleados;
import Presentacion.Vista.Restaurante.Empleados.GUIModificarEmpleados;
import Presentacion.Vista.Restaurante.Empleados.GUIMostrarEmpleadosPorTurno;
import Presentacion.Vista.Restaurante.Mesas.GUIAltaMesas;
import Presentacion.Vista.Restaurante.Mesas.GUIBajaMesas;
import Presentacion.Vista.Restaurante.Mesas.GUIConsultarMesa;
import Presentacion.Vista.Restaurante.Mesas.GUIListarMesas;
import Presentacion.Vista.Restaurante.Mesas.GUIListarMesasPorEmpleado;
import Presentacion.Vista.Restaurante.Mesas.GUIMesas;
import Presentacion.Vista.Restaurante.Mesas.GUIModificarMesas;
import Presentacion.Vista.Restaurante.Platos.AltaPlatos;
import Presentacion.Vista.Restaurante.Platos.BajaPlatos;
import Presentacion.Vista.Restaurante.Platos.ConsultarPlatos;
import Presentacion.Vista.Restaurante.Platos.DesvincularPlatoProducto;
import Presentacion.Vista.Restaurante.Platos.GUIPlatos;
import Presentacion.Vista.Restaurante.Platos.ListarBebidas;
import Presentacion.Vista.Restaurante.Platos.ListarComida;
import Presentacion.Vista.Restaurante.Platos.ListarPlatos;
import Presentacion.Vista.Restaurante.Platos.ListarPlatosporProductos;
import Presentacion.Vista.Restaurante.Platos.ModificarPlatos;
import Presentacion.Vista.Restaurante.Platos.VincularPlatoProducto;
import Presentacion.Vista.Restaurante.Productos.AltaProductos;
import Presentacion.Vista.Restaurante.Productos.BajaProductos;
import Presentacion.Vista.Restaurante.Productos.ConsultarProductos;
import Presentacion.Vista.Restaurante.Productos.GUIProductos;
import Presentacion.Vista.Restaurante.Productos.ListarProductos;
import Presentacion.Vista.Restaurante.Productos.ListarProductosPorPlato;
import Presentacion.Vista.Restaurante.Productos.ModificarProductos;
import Presentacion.Vista.Restaurante.Turnos.GUIAltaTurno;
import Presentacion.Vista.Restaurante.Turnos.GUIBajaTurno;
import Presentacion.Vista.Restaurante.Turnos.GUIConsultarTurno;
import Presentacion.Vista.Restaurante.Turnos.GUIListarTurnos;
import Presentacion.Vista.Restaurante.Turnos.GUIModificarTurno;
import Presentacion.Vista.Restaurante.Turnos.GUITurnos;
import Presentacion.Vista.Restaurante.Ventas.GUIBajaVenta;
import Presentacion.Vista.Restaurante.Ventas.GUIConsultaVenta;
import Presentacion.Vista.Restaurante.Ventas.GUIDevolucionVenta;
import Presentacion.Vista.Restaurante.Ventas.GUIListarVentas;
import Presentacion.Vista.Restaurante.Ventas.GUIModificacionVenta;
import Presentacion.Vista.Restaurante.Ventas.GUIMostrarVentasPorCliente;
import Presentacion.Vista.Restaurante.Ventas.GUIMostrarVentasPorMesa;
import Presentacion.Vista.Restaurante.Ventas.GUIVentas;
import Presentacion.Vista.Restaurante.Ventas.GUIAltaVenta.GUIAltaVenta;

public class ViewerFactoryIMP extends ViewerFactory {

	@Override
	public IGUI generarVista(Context context) {
		// TODO Auto-generated method stub

		switch (context.getEvento()) {

//ACADEMIA		
		
		// ALUMNO
		case GUIALUMNOS:
			return new GUIAlumnos();
		case AltaAlumnos:
			return new GUIAltaAlumnos();
		case AltaAlumnos_KO:
			return new GUIAltaAlumnos();
		case AltaAlumnos_OK:
			return new GUIAltaAlumnos();
		case BajaAlumnos:
			return new GUIBajaAlumnos();
		case BajaAlumnos_KO:
			return new GUIBajaAlumnos();
		case BajaAlumnos_OK:
			return new GUIBajaAlumnos();
		case ConsultarAlumnos:
			return new GUIConsultarAlumnos();
		case ConsultarAlumnos_OK:
			return new GUIConsultarAlumnos();
		case ConsultarAlumnos_KO:
			return new GUIConsultarAlumnos();
		case ListarAlumnos:
			return new GUIListarAlumnos();
		case ListarAlumnos_KO:
			return new GUIListarAlumnos();
		case ListarAlumnos_OK:
			return new GUIListarAlumnos();
		case ListarNotasAlumnos:
			return new GUIListarNotasAlumnos();
		case ListarNotasAlumnos_OK:
			return new GUIListarNotasAlumnos();
		case ListarNotasAlumnos_KO:
			return new GUIListarNotasAlumnos();
		case ModificarAlumnos:
			return new GUIModificarAlumnos();
		case ModificarAlumnos_KO:
			return new GUIModificarAlumnos();
		case ModificarAlumnos_OK:
			return new GUIModificarAlumnos();
		case AlumnoANIO:
			return new GUIAlumnosAnio();
		case AlumnoANIO_KO:
			return new GUIAlumnosAnio();
		case AlumnoANIO_OK:
			return new GUIAlumnosAnio();

		// ANYO
		case GUIANIO:
			return new GUIAnyo();
		case AltaAnio:
			return new GUIAltaAnyo();
		case AltaAnio_OK:
			return new GUIAltaAnyo();
		case AltaAnio_KO:
			return new GUIAltaAnyo();
		case BajaAnio:
			return new GUIBajaAnyo();
		case BajaAnio_KO:
			return new GUIBajaAnyo();
		case BajaAnio_OK:
			return new GUIBajaAnyo();
		case ConsultaAnio:
			return new GUIConsultarAnyo();
		case ConsultaAnio_KO:
			return new GUIConsultarAnyo();
		case ConsultaAnio_OK:
			return new GUIConsultarAnyo();
		case ModificacionAnio:
			return new GUIModificarAnyo();
		case ModificacionAnio_KO:
			return new GUIModificarAnyo();
		case ModificacionAnio_OK:
			return new GUIModificarAnyo();
		case ListarAnio:
			return new GUIListarAnyo();
		case ListarAnio_KO:
			return new GUIListarAnyo();
		case ListarAnio_OK:
			return new GUIListarAnyo();

		// ASIGNATURA
		case GUIAsignatura:
			return new GUIAsignatura();
		case AltaAsignaturas:
			return new GUIAltaAsignatura();
		case AltaAsignaturas_KO:
			return new GUIAltaAsignatura();
		case AltaAsignaturas_OK:
			return new GUIAltaAsignatura();
		case BajaAsignaturas:
			return new GUIBajaAsignatura();
		case BajaAsignaturas_KO:
			return new GUIBajaAsignatura();
		case BajaAsignaturas_OK:
			return new GUIBajaAsignatura();
		case MostrarAsignatura:
			return new GUIConsultarAsignaturas();
		case MostrarAsignatura_KO:
			return new GUIConsultarAsignaturas();
		case MostrarAsignatura_OK:
			return new GUIConsultarAsignaturas();

		case ListarAsignaturas:
			return new GUIListarAsignaturas();
		case ListarAsignaturas_KO:
			return new GUIListarAsignaturas();
		case ListarAsignaturas_OK:
			return new GUIListarAsignaturas();

		case ListarAsignaturasObligatorias:
			return new GUIListarAsignaturasObligatorias();
		case ListarAsignaturasObligatorias_KO:
			return new GUIListarAsignaturasObligatorias();
		case ListarAsignaturasObligatorias_OK:
			return new GUIListarAsignaturasObligatorias();

		case ListarAsignaturasOptativas:
			return new GUIListarAsignaturasOptativas();
		case ListarAsignaturasOptativas_KO:
			return new GUIListarAsignaturasOptativas();
		case ListarAsignaturasOptativas_OK:
			return new GUIListarAsignaturasOptativas();

		case ModificarAsignaturas:
			return new GUIModificarAsignatura();
		case ModificarAsignaturas_KO:
			return new GUIModificarAsignatura();
		case ModificarAsignaturas_OK:
			return new GUIModificarAsignatura();
		case NotaMediaAsignatura:
			return new GUINotaMediaAsignatura();
		case NotaMediaAsignatura_OK:
			return new GUINotaMediaAsignatura();
		case NotaMediaAsignatura_KO:
			return new GUINotaMediaAsignatura();

		// GRUPO
		case GUIGRUPO:
			return new GUIGrupo();
		case AltaGrupo:
			return new GUIAltaGrupo();
		case AltaGrupo_OK:
			return new GUIAltaGrupo();
		case AltaGrupo_KO:
			return new GUIAltaGrupo();
		case BajaGrupo:
			return new GUIBajaGrupo();
		case BajaGrupo_KO:
			return new GUIBajaGrupo();
		case BajaGrupo_OK:
			return new GUIBajaGrupo();
		case ConsultaGrupo:
			return new GUIConsultarGrupo();
		case ConsultaGrupo_KO:
			return new GUIConsultarGrupo();
		case ConsultaGrupo_OK:
			return new GUIConsultarGrupo();
		case ModificacionGrupo:
			return new GUIModificarGrupo();
		case ModificacionGrupo_KO:
			return new GUIModificarGrupo();
		case ModificacionGrupo_OK:
			return new GUIModificarGrupo();
		case ListarGrupo:
			return new GUIListarGrupo();
		case ListarGrupo_KO:
			return new GUIListarGrupo();
		case ListarGrupo_OK:
			return new GUIListarGrupo();

		// MATRICULA
		case GUIMATRICULA:
			return new GUIMatricula();
		case AbrirMatricula:
			return new GUIAbrirMatricula();
		case AbrirMatricula_KO:
			return new GUIAbrirMatricula();
		case AbrirMatricula_OK:
			return new GUIAbrirMatricula();
		case BajaMatricula:
			return new GUIBajaMatricula();
		case BajaMatricula_KO:
			return new GUIBajaMatricula();
		case BajaMatricula_OK:
			return new GUIBajaMatricula();
		case CalificarMatriculableMatricula:
			return new GUICalificarMatricula();
		case CalificarMatriculableMatricula_KO:
			return new GUICalificarMatricula();
		case CalificarMatriculableMatricula_OK:
			return new GUICalificarMatricula();
		case CerrarMatricula_KO:
			return new GUICerrarMatricula();
		case CerrarMatricula_OK:
			return new GUICerrarMatricula();
		case CerrarMatricula:
			return new GUICerrarMatricula();
		case ConsultarMatricula:
			return new GUIConsultarMatricula();
		case ConsultarMatricula_KO:
			return new GUIConsultarMatricula();
		case ConsultarMatricula_OK:
			return new GUIConsultarMatricula();
		case ConsultarNota:
			return new GUIConsultarNotaMatricula();
		case ConsultarNota_KO:
			return new GUIConsultarNotaMatricula();
		case ConsultarNota_OK:
			return new GUIConsultarNotaMatricula();
		case VincularMatriculaMatriculable:
			return new GUIVincularMatriculaMatriculable();
		case VincularMatriculaMatriculable_KO:
			return new GUIVincularMatriculaMatriculable();
		case VincularMatriculaMatriculable_OK:
			return new GUIVincularMatriculaMatriculable();
		case DesvincularMatriculaMatriculable_KO:
			return new GUIDesvincularMatriculaMatriculable();
		case DesvincularMatriculaMatriculable_OK:
			return new GUIDesvincularMatriculaMatriculable();
		case DesvincularMatriculaMatriculable:
			return new GUIDesvincularMatriculaMatriculable();
		case ModificarMatricula:
			return new GUIModificarMatricula();
		case ModificarMatricula_KO:
			return new GUIModificarMatricula();
		case ModificarMatricula_OK:
			return new GUIModificarMatricula();
		case ListarMatricula:
			return new GUIListarMatriculas();
		case ListarMatricula_KO:
			return new GUIListarMatriculas();
		case ListarMatricula_OK:
			return new GUIListarMatriculas();
		case MostrarMatriculasporAlumno:
			return new GUIListarMatriculasAlumno();
		case MostrarMatriculasporAlumno_KO:
			return new GUIListarMatriculasAlumno();
		case MostrarMatriculasporAlumno_OK:
			return new GUIListarMatriculasAlumno();
		case MostrarMatriculasporMatriculable:
			return new GUIListarMatriculablesMatricula();
		case MostrarMatriculasporMatriculable_KO:
			return new GUIListarMatriculablesMatricula();
		case MostrarMatriculasporMatriculable_OK:
			return new GUIListarMatriculablesMatricula();

		// MATRICULABLE
		case GUIMATRICULABLE:
			return new GUIMatriculable();
		case AltaMatriculable:
			return new GUIAltaMatriculable();
		case AltaMatriculable_KO:
			return new GUIAltaMatriculable();
		case AltaMatriculable_OK:
			return new GUIAltaMatriculable();
		case BajaMatriculable:
			return new GUIBajaMatriculable();
		case BajaMatriculable_KO:
			return new GUIBajaMatriculable();
		case BajaMatriculable_OK:
			return new GUIBajaMatriculable();
		case ConsultarInformacionMatriculable:
			return new GUIConsultarMatriculable();
		case ConsultarInformacionMatriculable_KO:
			return new GUIConsultarMatriculable();
		case ConsultarInformacionMatriculable_OK:
			return new GUIConsultarMatriculable();
		case ListarMatriculables:
			return new GUIListarMatriculable();
		case ListarMatriculables_KO:
			return new GUIListarMatriculable();
		case ListarMatriculables_OK:
			return new GUIListarMatriculable();
		case ListarMatriculablesPorAnio:
			return new GUIListarMatriculablePorAnio();
		case ListarMatriculablesPorAnio_KO:
			return new GUIListarMatriculablePorAnio();
		case ListarMatriculablesPorAnio_OK:
			return new GUIListarMatriculablePorAnio();
		case ListarMatriculablePorGrupo:
			return new GUIListarMatriculablePorGrupo();
		case ListarMatriculablePorGrupo_KO:
			return new GUIListarMatriculablePorGrupo();
		case ListarMatriculablePorGrupo_OK:
			return new GUIListarMatriculablePorGrupo();
		case ListarMatriculablePorProfesor:
			return new GUIListarMatriculablePorProfesor();
		case ListarMatriculablePorProfesor_KO:
			return new GUIListarMatriculablePorProfesor();
		case ListarMatriculablePorProfesor_OK:
			return new GUIListarMatriculablePorProfesor();
		case ListarMatriculablesPorAsignatura:
			return new GUIListarMatriculablePorAsignatura();
		case ListarMatriculablesPorAsignatura_KO:
			return new GUIListarMatriculablePorAsignatura();
		case ListarMatriculablesPorAsignatura_OK:
			return new GUIListarMatriculablePorAsignatura();
		case ModificarMatriculable:
			return new GUIModificarMatriculable();
		case ModificarMatriculable_KO:
			return new GUIModificarMatriculable();
		case ModificarMatriculable_OK:
			return new GUIModificarMatriculable();

		// PROFESOR
		case GUIProfesores:
			return new GUIProfesores();
		case AltaProfesor:
			return new GUIAltaProfesores();
		case AltaProfesor_KO:
			return new GUIAltaProfesores();
		case AltaProfesor_OK:
			return new GUIAltaProfesores();
		case BajaProfesor:
			return new GUIBajaProfesores();
		case BajaProfesor_OK:
			return new GUIBajaProfesores();
		case BajaProfesor_KO:
			return new GUIBajaProfesores();
		case ModificarProfesor:
			return new GUIModificarProfesores();
		case ModificarProfesor_KO:
			return new GUIModificarProfesores();
		case ModificarProfesor_OK:
			return new GUIModificarProfesores();
		case ConsultarProfesor:
			return new GUIConsultarProfesores();
		case ConsultarProfesor_KO:
			return new GUIConsultarProfesores();
		case ConsultarProfesor_OK:
			return new GUIConsultarProfesores();
		case ListarProfesores:
			return new GUIListarProfesores();
		case ListarProfesores_KO:
			return new GUIListarProfesores();
		case ListarProfesores_OK:
			return new GUIListarProfesores();
		case ListarProfesoresPorMatriculable:
			return new GUIListarProfesoresPorMatriculable();
		case ListarProfesoresPorMatriculable_KO:
			return new GUIListarProfesoresPorMatriculable();
		case ListarProfesoresPorMatriculable_OK:
			return new GUIListarProfesoresPorMatriculable();
		case VincularProfesorMatriculable:
			return new GUIVincularMatriculable();
		case VincularProfesorMatriculable_KO:
			return new GUIVincularMatriculable();
		case VincularProfesorMatriculable_OK:
			return new GUIVincularMatriculable();
		case ListarProfesoresInterinos:
			return new GUIListarProfesoresInterinos();
		case ListarProfesoresInterinos_KO:
			return new GUIListarProfesoresInterinos();
		case ListarProfesoresInterinos_OK:
			return new GUIListarProfesoresInterinos();
		case ListarProfesoresFijos:
			return new GUIListarProfesoresFijos();
		case ListarProfesoresFijos_KO:
			return new GUIListarProfesoresFijos();
		case ListarProfesoresFijos_OK:
			return new GUIListarProfesoresFijos();
		case DesvincularProfesorMatriculable:
			return new GUIDesvincularMatriculable();
		case DesvincularProfesorMatriculable_KO:
			return new GUIDesvincularMatriculable();
		case DesvincularProfesorMatriculable_OK:
			return new GUIDesvincularMatriculable();
			
			
//RESTAURANTE
			
		//PLATOS
		case GUIPlatos:
			return new GUIPlatos();
		case AltaPlatos:
			return new AltaPlatos();
		case AltaPlatos_OK:
			return new AltaPlatos();
		case AltaPlatos_KO:
			return new AltaPlatos();
		case BajaPlatos:
			return new BajaPlatos();
		case BajaPlatos_OK:
			return new BajaPlatos();
		case BajaPlatos_KO:
			return new BajaPlatos();
		case ConsultarPlatos:
			return new ConsultarPlatos();
		case ConsultarPlatos_OK:
			return new ConsultarPlatos();
		case ConsultarPlatos_KO:
			return new ConsultarPlatos();
		case ModificarPlatos:
			return new ModificarPlatos();
		case ModificarPlatos_OK:
			return new ModificarPlatos();
		case ModificarPlatos_KO:
			return new ModificarPlatos();
		case ListarPlatos:
			return new ListarPlatos();
		case ListarPlatos_OK:
			return new ListarPlatos();
		case ListarPlatos_KO:
			return new ListarPlatos();
		case ListarBebidas:
			return new ListarBebidas();
		case ListarBebidas_OK:
			return new ListarBebidas();
		case ListarBebidas_KO:
			return new ListarBebidas();
		case ListarComida:
			return new ListarComida();
		case ListarComida_OK:
			return new ListarComida();
		case ListarComida_KO:
			return new ListarComida();
		case ListarPlatosPorProducto:
			return new ListarPlatosporProductos();
		case ListarPlatosPorProducto_OK:
			return new ListarPlatosporProductos();
		case ListarPlatosPorProducto_KO:
			return new ListarPlatosporProductos();
		case VincularPlatoProducto:
			return new VincularPlatoProducto();
		case VincularPlatoProducto_OK:
			return new VincularPlatoProducto();
		case VincularPlatoProducto_KO:
			return new VincularPlatoProducto();
		case DesvincularPlatoProducto:
			return new DesvincularPlatoProducto();
		case DesvincularPlatoProducto_OK:
			return new DesvincularPlatoProducto();
		case DesvincularPlatoProducto_KO:
			return new DesvincularPlatoProducto();

		//MESAS
			
		case GUIMesas:
			return new GUIMesas();
		case AltaMesas:
			return new GUIAltaMesas();
		case AltaMesa_OK:
			return new GUIAltaMesas();
		case AltaMesa_KO:
			return new GUIAltaMesas();
		case BajaMesas_OK:
			return new GUIBajaMesas();
		case BajaMesas_KO:
			return new GUIBajaMesas();
		case BajaMesas:
			return new GUIBajaMesas();
		case ModificarMesas:
			return new GUIModificarMesas();
		case ModificarMesa_OK:
			return new GUIModificarMesas();
		case ModificarMesa_KO:
			return new GUIModificarMesas();
			
		case ConsultarMesa:
			return new GUIConsultarMesa();
		case ConsultarMesa_OK:
			return new GUIConsultarMesa();
		case ConsultarMesa_KO:
			return new GUIConsultarMesa();
	
		case ListarMesas:
			return new GUIListarMesas();
		case ListarMesas_OK:
			return new GUIListarMesas();
		case ListarMesas_KO:
			return new GUIListarMesas();
			
		case MesasPorEmpleado:
			return new GUIListarMesasPorEmpleado();
		case MesasPorEmpleado_OK:
			return new GUIListarMesasPorEmpleado();
		case MesasPorEmpleado_KO:
			return new GUIListarMesasPorEmpleado();


			
		//EMPLEADOS
			
		case GUIEmpleados:
			return new GUIEmpleados();
		case AltaEmpleados:
			return new GUIAltaEmpleados();
		case AltaEmpleados_OK:
			return new GUIAltaEmpleados();
		case AltaEmpleados_KO:
			return new GUIAltaEmpleados();
		case BajaEmpleados:
			return new GUIBajaEmpleados();
		case BajaEmpleados_OK:
			return new GUIBajaEmpleados();
		case BajaEmpleados_KO:
			return new GUIBajaEmpleados();
		case ModificarEmpleados:
			return new GUIModificarEmpleados();
		case ModificarEmpleados_OK:
			return new GUIModificarEmpleados();
		case ModificarEmpleados_KO:
			return new GUIModificarEmpleados();
		case ConsultarEmpleados:
			return new GUIConsultarEmpleados();
		case ConsultarEmpleados_OK:
			return new GUIConsultarEmpleados();
		case ConsultarEmpleados_KO:
			return new GUIConsultarEmpleados();
		case ListarEmpleados:
			return new GUIListarEmpleados();
		case ListarEmpleados_OK:
			return new GUIListarEmpleados();
		case ListarEmpleados_KO:
			return new GUIListarEmpleados();
		case MostrarEmpleadosPorTurno:
			return new GUIMostrarEmpleadosPorTurno();
		case MostrarEmpleadosPorTurno_OK:
			return new GUIMostrarEmpleadosPorTurno();
		case MostrarEmpleadosPorTurno_KO:
			return new GUIMostrarEmpleadosPorTurno();
		case ListarCocineros:
			return new GUIListarCocineros();
		case ListarCocineros_OK:
			return new GUIListarCocineros();
		case ListarCocineros_KO:
			return new GUIListarCocineros();
		case ListarCamareros:
			return new GUIListarCamareros();
		case ListarCamareros_OK:
			return new GUIListarCamareros();
		case ListarCamareros_KO:
			return new GUIListarCamareros();

		//VENTAS
			
		case GUIVentas:
			return new GUIVentas();
		case AltaVenta:
			return new GUIAltaVenta();
		case AltaVenta_OK:
			return new GUIAltaVenta();
		case AltaVenta_KO:
			return new GUIAltaVenta();
		case BajaVenta:
			return new GUIBajaVenta();
		case BajaVenta_OK:
			return new GUIBajaVenta();
		case BajaVenta_KO:
			return new GUIBajaVenta();
		case ConsultaVenta:
			return new GUIConsultaVenta();
		case ConsultaVenta_OK:
			return new GUIConsultaVenta();
		case ConsultaVenta_KO:
			return new GUIConsultaVenta();
		case DevolucionVenta:
			return new GUIDevolucionVenta();
		case DevolucionVenta_OK:
			return new GUIDevolucionVenta();
		case DevolucionVenta_KO:
			return new GUIDevolucionVenta();
		case ListarVentas:
			return new GUIListarVentas();
		case ListarVentas_OK:
			return new GUIListarVentas();
		case ListarVentas_KO:
			return new GUIListarVentas();
		case ListarVentasPorCliente:
			return new GUIMostrarVentasPorCliente();
		case ListarVentasPorCliente_OK:
			return new GUIMostrarVentasPorCliente();
		case ListarVentasPorCliente_KO:
			return new GUIMostrarVentasPorCliente();
		case ListarVentasPorMesa:
			return new GUIMostrarVentasPorMesa();
		case ListarVentasPorMesa_OK:
			return new GUIMostrarVentasPorMesa();
		case ListarVentasPorMesa_KO:
			return new GUIMostrarVentasPorMesa();
		case ModificarVenta:
			return new GUIModificacionVenta();
		case ModificarVenta_OK:
			return new GUIModificacionVenta();
		case ModificarVenta_KO:
			return new GUIModificacionVenta();
		// PRODUCTO
		case GUIProductos:
			return new GUIProductos();
		case AltaProductos:
			return new AltaProductos();
		case AltaProductos_OK:
			return new AltaProductos();
		case AltaProductos_KO:
			return new AltaProductos();
		case BajaProductos:
			return new BajaProductos();
		case BajaProductos_OK:
			return new BajaProductos();
		case BajaProductos_KO:
			return new BajaProductos();
		case ConsultarProductos:
			return new ConsultarProductos();
		case ConsultarProductos_OK:
			return new ConsultarProductos();
		case ConsultarProductos_KO:
			return new ConsultarProductos();
		case ListarProductos:
			return new ListarProductos();
		case ListarProductos_OK:
			return new ListarProductos();
		case ListarProductos_KO:
			return new ListarProductos();
		case ListarProductosPorPlato:
			return new ListarProductosPorPlato();
		case ListarProductosPorPlato_OK:
			return new ListarProductosPorPlato();
		case ListarProductosPorPlato_KO:
			return new ListarProductosPorPlato();
		case ModificarProductos:
			return new ModificarProductos();
		case ModificarProductos_OK:
			return new ModificarProductos();
		case ModificarProductos_KO:
			return new ModificarProductos();
				
		//TURNOS
			//VENTAS
			
		case GUITurnos:
			return new GUITurnos();
		case AltaTurno:
			return new GUIAltaTurno();
		case AltaTurno_OK:
			return new GUIAltaTurno();
		case AltaTurno_KO:
			return new GUIAltaTurno();
		case BajaTurno:
			return new GUIBajaTurno();
		case BajaTurno_OK:
			return new GUIBajaTurno();
		case BajaTurno_KO:
			return new GUIBajaTurno();
		case ConsultarTurno:
			return new GUIConsultarTurno();
		case ConsultarTurno_OK:
			return new GUIConsultarTurno();
		case ConsultarTurno_KO:
			return new GUIConsultarTurno();
		case ListarTurnos:
			return new GUIListarTurnos();
		case ListarTurnos_OK:
			return new GUIListarTurnos();
		case ListarTurnos_KO:
			return new GUIListarTurnos();
		case ModificarTurno:
			return new GUIModificarTurno();
		case ModificarTurno_OK:
			return new GUIModificarTurno();
		case ModificarTurno_KO:
			return new GUIModificarTurno();
		case GUIRestaurante:
			return new GUIRestaurante();
		case GUIAcademia:
			return new GUIAcademia();
		case GUIMain:
			return new GUIMain();
		default:
			return null;
		}
	}

}
