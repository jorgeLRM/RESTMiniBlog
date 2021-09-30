package com.mitocode.service;

import com.mitocode.model.Usuario;

public interface IUsuarioService extends IService<Usuario> {

	Usuario traerIdUsuario(String nombreUsuario);
	Usuario login(Usuario us);
}
