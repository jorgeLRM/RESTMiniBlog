package com.mitocode.dao;

import javax.ejb.Local;

import com.mitocode.model.Usuario;

@Local
public interface IUsuarioDAO extends IDAO<Usuario> {

	Usuario traerIdUsuario(String nombreUsuario);
	String traerPassHashed(String us);
	Usuario login(Usuario us);
}
