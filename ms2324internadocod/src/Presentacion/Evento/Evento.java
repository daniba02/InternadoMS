
package Presentacion.Evento;

public enum Evento {

	// Alumnos
	GUIALUMNOS, AltaAlumnos, BajaAlumnos, ConsultarAlumnos, ListarAlumnos, ListarNotasAlumnos, ModificarAlumnos,

	// Query
	AlumnoANIO,

	AltaAlumnos_OK, BajaAlumnos_OK, ConsultarAlumnos_OK, ListarAlumnos_OK, ListarNotasAlumnos_OK, ModificarAlumnos_OK,
	// Query
	AlumnoANIO_OK,

	AltaAlumnos_KO, BajaAlumnos_KO, ConsultarAlumnos_KO, ListarAlumnos_KO, ListarNotasAlumnos_KO, ModificarAlumnos_KO,
	// Query
	AlumnoANIO_KO,

	GUIAsignatura,

	// Matriculable
	GUIMATRICULABLE, AltaMatriculable, BajaMatriculable, ModificarMatriculable, ConsultarInformacionMatriculable, ListarMatriculables, ListarMatriculablesPorAnio, ListarMatriculablePorGrupo, ListarMatriculablesPorAsignatura, ListarMatriculablePorProfesor,

	AltaMatriculable_OK, BajaMatriculable_OK, ModificarMatriculable_OK, ListarMatriculables_OK, ListarMatriculablesPorAnio_OK, ListarMatriculablePorGrupo_OK, ListarMatriculablesPorAsignatura_OK, ListarMatriculablePorProfesor_OK, ConsultarInformacionMatriculable_OK, ReadMatriculable_OK,

	AltaMatriculable_KO, BajaMatriculable_KO, ModificarMatriculable_KO, ConsultarInformacionMatriculable_KO, ListarMatriculables_KO, ListarMatriculablesPorAnio_KO, ListarMatriculablePorGrupo_KO, ListarMatriculablesPorAsignatura_KO, ListarMatriculablePorProfesor_KO, ReadMatriculable_KO,

	// Matricula
	GUIMATRICULA, AbrirMatricula, ListarMatricula, ConsultarMatricula, ModificarMatricula, VincularMatriculaMatriculable, DesvincularMatriculaMatriculable, BajaMatricula, CerrarMatricula, ConsultarNota, MostrarMatriculasporAlumno, MostrarMatriculasporMatriculable, CalificarMatriculableMatricula,

	AbrirMatricula_OK, ListarMatricula_OK, ConsultarMatricula_OK, ModificarMatricula_OK, VincularMatriculaMatriculable_OK, DesvincularMatriculaMatriculable_OK, BajaMatricula_OK, CerrarMatricula_OK, ConsultarNota_OK, MostrarMatriculasporAlumno_OK, MostrarMatriculasporMatriculable_OK, CalificarMatriculableMatricula_OK,

	AbrirMatricula_KO, ListarMatricula_KO, ConsultarMatricula_KO, ModificarMatricula_KO, VincularMatriculaMatriculable_KO, DesvincularMatriculaMatriculable_KO, BajaMatricula_KO, CerrarMatricula_KO, ConsultarNota_KO, MostrarMatriculasporAlumno_KO, MostrarMatriculasporMatriculable_KO, CalificarMatriculableMatricula_KO,

	// Profesor
	GUIProfesores, AltaProfesor, BajaProfesor, ModificarProfesor, ConsultarProfesor, ListarProfesores, ListarProfesoresInterinos, ListarProfesoresFijos, ListarProfesoresPorMatriculable, VincularProfesorMatriculable, DesvincularProfesorMatriculable,

	AltaProfesor_OK, BajaProfesor_OK, ModificarProfesor_OK, ConsultarProfesor_OK, ListarProfesores_OK, ListarProfesoresInterinos_OK, ListarProfesoresFijos_OK, ListarProfesoresPorMatriculable_OK, VincularProfesorMatriculable_OK, DesvincularProfesorMatriculable_OK,

	AltaProfesor_KO, BajaProfesor_KO, ModificarProfesor_KO, ConsultarProfesor_KO, ListarProfesores_KO, ListarProfesoresInterinos_KO, ListarProfesoresFijos_KO, ListarProfesoresPorMatriculable_KO, VincularProfesorMatriculable_KO, DesvincularProfesorMatriculable_KO,

	// Asignatura

	GUIAsignaturas, AltaAsignaturas, BajaAsignaturas, ModificarAsignaturas, ListarAsignaturas, ListarAsignaturasObligatorias, ListarAsignaturasOptativas, MostrarAsignatura,

	AltaAsignaturas_OK, BajaAsignaturas_OK, ModificarAsignaturas_OK, ListarAsignaturas_OK, ListarAsignaturasObligatorias_OK, ListarAsignaturasOptativas_OK, MostrarAsignatura_OK,

	AltaAsignaturas_KO, BajaAsignaturas_KO, ModificarAsignaturas_KO, ListarAsignaturas_KO, ListarAsignaturasObligatorias_KO, ListarAsignaturasOptativas_KO, MostrarAsignatura_KO,

	// Anio
	GUIANIO, AltaAnio, BajaAnio, ModificacionAnio, ConsultaAnio, ListarAnio,

	AltaAnio_OK, BajaAnio_OK, ModificacionAnio_OK, ConsultaAnio_OK, ListarAnio_OK,

	AltaAnio_KO, BajaAnio_KO, ModificacionAnio_KO, ConsultaAnio_KO, ListarAnio_KO,

	// Grupo
	GUIGRUPO, AltaGrupo, BajaGrupo, ModificacionGrupo, ConsultaGrupo, ListarGrupo,

	AltaGrupo_OK, BajaGrupo_OK, ModificacionGrupo_OK, ConsultaGrupo_OK, ListarGrupo_OK,

	AltaGrupo_KO, BajaGrupo_KO, ModificacionGrupo_KO, ConsultaGrupo_KO, ListarGrupo_KO,

	// Queries
	NotaMediaAsignatura,

	NotaMediaAsignatura_OK, NotaMediaAsignatura_KO,

	// JPA--------------------------------------------------------//

	// Platos
	GUIPlatos, AltaPlatos, BajaPlatos, ConsultarPlatos, ModificarPlatos, ListarPlatos, ListarPlatosPorProducto, ListarBebidas, ListarComida,

	AltaPlatos_OK, AltaPlatos_KO, BajaPlatos_OK, BajaPlatos_KO, ConsultarPlatos_OK, ConsultarPlatos_KO, ModificarPlatos_OK, ModificarPlatos_KO, ListarPlatos_OK, ListarPlatos_KO, ListarPlatosPorProducto_OK, ListarPlatosPorProducto_KO, ListarBebidas_OK, ListarBebidas_KO, ListarComida_OK, ListarComida_KO,
	
	VincularPlatoProducto_OK, VincularPlatoProducto_KO, VincularPlatoProducto, DesvincularPlatoProducto, DesvincularPlatoProducto_OK, DesvincularPlatoProducto_KO,

	// Turnos
	GUITurnos,

	AltaTurno, BajaTurno, ModificarTurno, ConsultarTurno, ListarTurnos,

	AltaTurno_OK, BajaTurno_OK, ModificarTurno_OK, ConsultarTurno_OK, ListarTurnos_OK,

	AltaTurno_KO, BajaTurno_KO, ModificarTurno_KO, ConsultarTurno_KO, ListarTurnos_KO,

	// Productos
	GUIProductos,

	AltaProductos, AltaProductos_OK, AltaProductos_KO, BajaProductos, BajaProductos_OK, BajaProductos_KO, ConsultarProductos, ConsultarProductos_OK, ConsultarProductos_KO, ModificarProductos, ModificarProductos_OK, ModificarProductos_KO, ListarProductos, ListarProductos_OK, ListarProductos_KO, ListarProductosPorPlato, ListarProductosPorPlato_OK, ListarProductosPorPlato_KO,

	// MESAS

	GUIMesas, AltaMesas, BajaMesas, ConsultarMesa, ModificarMesas, ListarMesas, MesasPorEmpleado,
	AltaMesa_OK, BajaMesa_OK, ModificarMesa_OK, ConsultarMesa_OK, ListarMesas_OK, MesasPorEmpleado_OK,
	AltaMesa_KO, BajaMesa_KO, ModificarMesa_KO, ConsultarMesa_KO, ListarMesas_KO, MesasPorEmpleado_KO,
	// VincularMesaVenta ,
	// DesvincularMesaVenta ,
	
	// EMPLEADOS
	GUIEmpleados, 
	AltaEmpleados,		BajaEmpleados,		ModificarEmpleados, 	ConsultarEmpleados, 	ListarEmpleados,	MostrarEmpleadosPorTurno,		MostrarEmpleadosPorMesa,	NominaEmpleados,	ListarCocineros,		ListarCamareros,
	AltaEmpleados_OK,	BajaEmpleados_OK,	ModificarEmpleados_OK, 	ConsultarEmpleados_OK, 	ListarEmpleados_OK, MostrarEmpleadosPorTurno_OK,	MostrarEmpleadosPorMesa_OK,	NominaEmpleados_OK, ListarCocineros_OK,		ListarCamareros_OK,
	AltaEmpleados_KO,	BajaEmpleados_KO, 	ModificarEmpleados_KO, 	ConsultarEmpleados_KO, 	ListarEmpleados_KO, MostrarEmpleadosPorTurno_KO,	MostrarEmpleadosPorMesa_KO,	NominaEmpleados_KO, ListarCocineros_KO,		ListarCamareros_KO,
	
	// VENTAS
	GUIVentas, 
	
	AltaVenta, BajaVenta, ConsultaVenta, DevolucionVenta, ListarVentas, ListarVentasPorCliente, ListarVentasPorMesa, ModificarVenta, ListarVentasPorPlato,
	
	AltaVenta_OK, BajaVenta_OK, ConsultaVenta_OK, DevolucionVenta_OK, ListarVentas_OK, ListarVentasPorCliente_OK, ListarVentasPorMesa_OK, ModificarVenta_OK, ListarVentasPorPlato_OK,
	
	AltaVenta_KO, BajaVenta_KO, ConsultaVenta_KO, DevolucionVenta_KO, ListarVentas_KO, ListarVentasPorCliente_KO, ListarVentasPorMesa_KO, ModificarVenta_KO, ListarVentasPorPlato_KO, BajaMesas_OK, BajaMesas_KO, 
	
	//GUIRESTAURANTE
	GUIRestaurante,
	
	//GUIACADEMIA
	GUIAcademia,
	
	//GUIMAIN
	GUIMain
	
	//ProximaEntidad

}