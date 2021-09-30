package com.mitocode.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import com.mitocode.dao.IUsuarioDAO;
import com.mitocode.model.Usuario;
import com.mitocode.service.IUsuarioService;

@Named
public class UsuarioServiceImpl implements Serializable, IUsuarioService {

	@EJB
	private IUsuarioDAO dao;

	@Override
	public int registrar(Usuario t) throws Exception {
		t.setEstado("A");
		int rpta = dao.registrar(t);		
		return rpta > 0 ? 1 : 0;		
	}

	@Override
	public int modificar(Usuario t) throws Exception {
		int rpta = dao.modificar(t);
		return rpta > 0 ? 1 : 0;
	}

	@Override
	public List<Usuario> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario listarPorId(Usuario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario login(Usuario us) {
		String clave = us.getContrasena();		
		
		String claveHash = dao.traerPassHashed(us.getUsuario());
		
		if(BCrypt.checkpw(clave, claveHash)) {
			us.setContrasena(claveHash);
			return dao.login(us);
		}
		
		return new Usuario();
	}

	@Override
	public Usuario traerIdUsuario(String nombreUsuario) {		
		return dao.traerIdUsuario(nombreUsuario);
	}

}
