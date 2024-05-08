package Negocio.Academia.Profesores;

public class TFijo extends TProfesor {

	public TFijo(Integer id, String dni, Integer edad, String nombreCompleto, boolean activo) {
		super(id, dni, edad, nombreCompleto, activo);

	}

	public TFijo(String dni, Integer edad, String nombreCompleto, boolean activo) {
		super(dni, edad, nombreCompleto, activo);
	}

	public TFijo() {
		// TODO Auto-generated constructor stub
	}
}