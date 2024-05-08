package Integracion.Grupo;

import java.util.Collection;

import Negocio.Academia.Grupo.TGrupo;

public interface DAOGrupo {
	public Integer create(TGrupo grupo);
	public Integer delete(Integer id);
	public Integer update(TGrupo leido);
	public TGrupo read(Integer id);
	public TGrupo readByLetra(String letra);
	public Collection<TGrupo> readAll() throws Exception;
}