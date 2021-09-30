package com.mitocode.dao;

import java.util.List;

import javax.ejb.Local;

import com.mitocode.model.Persona;
import com.mitocode.model.PublicadorSeguidor;

@Local
public interface ISeguidorDAO {
	
	Integer registrarPublicadoresSeguidores(List<PublicadorSeguidor> publicadores_seguidores);
	List<PublicadorSeguidor> listarSeguidores(Persona per);	
	Integer dejarSeguir(List<PublicadorSeguidor> publicadores_seguidores);		
	List<PublicadorSeguidor> listarSeguidos(Persona per);
}
