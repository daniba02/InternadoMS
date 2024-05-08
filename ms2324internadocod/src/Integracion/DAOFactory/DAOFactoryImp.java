/**
 * 
 */
package Integracion.DAOFactory;

import Integracion.Alumnos.DAOAlumnos;
import Integracion.Alumnos.DAOAlumnosIMP;
import Integracion.Anio.DAOAnio;
import Integracion.Anio.DAOAnioIMP;
import Integracion.Asignaturas.DAOAsignaturas;
import Integracion.Asignaturas.DAOAsignaturasImp;
import Integracion.Grupo.DAOGrupo;
import Integracion.Grupo.DAOGrupoIMP;
import Integracion.Matricula.DAOMatricula;
import Integracion.Matricula.DAOMatriculaImp;
import Integracion.MatriculaMatriculable.DAOMatriculaMatriculable;
import Integracion.MatriculaMatriculable.DAOMatriculaMatriculableImp;
import Integracion.Matriculable.DAOMatriculable;
import Integracion.Matriculable.DAOMatriculableIMP;
import Integracion.ProfesorMatriculable.DAOProfesorMatriculable;
import Integracion.ProfesorMatriculable.DAOProfesorMatriculableImp;
import Integracion.Profesores.DAOProfesor;
import Integracion.Profesores.DAOProfesorImp;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Usuario
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class DAOFactoryImp extends DAOFactory{

	@Override
	public DAOMatricula getDAOMatricula() {
		// TODO Auto-generated method stub
		return new DAOMatriculaImp();
	}

	@Override
	public DAOMatriculable getDAOMatriculable() {
		// TODO Auto-generated method stub
		return new DAOMatriculableIMP();
	}

	@Override
	public DAOAnio getDAOAnio() {
		// TODO Auto-generated method stub
		return new DAOAnioIMP();
	}

	@Override
	public DAOGrupo getDAOGrupo() {
		// TODO Auto-generated method stub
		return new DAOGrupoIMP();
	}

	@Override
	public DAOMatriculaMatriculable getDAOMatriculaMatriculable() {
		// TODO Auto-generated method stub
		return new DAOMatriculaMatriculableImp();
	}

	@Override
	public DAOAsignaturas getDAOAsignaturas() {
		// TODO Auto-generated method stub
		return new DAOAsignaturasImp();
	}

	@Override
	public DAOProfesor getDAOProfesor() {
		// TODO Auto-generated method stub
		return new DAOProfesorImp();
	}

	@Override
	public DAOAlumnos getDAOAlumnos() {
		// TODO Auto-generated method stub
		return new DAOAlumnosIMP();
	}

	@Override
	public DAOProfesorMatriculable getDAOProfesorMatriculable() {
		// TODO Auto-generated method stub
		return new DAOProfesorMatriculableImp();
	}
}