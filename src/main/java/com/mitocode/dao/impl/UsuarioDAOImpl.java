package com.mitocode.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.mitocode.dao.IUsuarioDAO;
import com.mitocode.model.Usuario;

@Stateless
public class UsuarioDAOImpl implements IUsuarioDAO, Serializable {

	@PersistenceContext(unitName = "blogPU")
	private EntityManager em;

	@Override
	public Integer registrar(Usuario us) throws Exception {
		em.persist(us);
		return us.getPersona().getIdPersona();
	}

	@Override
	public Integer modificar(Usuario us) throws Exception {
		em.merge(us);
		return us.getPersona().getIdPersona();
	}

	@Override
	public List<Usuario> listar() throws Exception {
		List<Usuario> lista = new ArrayList<Usuario>();

		try {
			Query query = em.createQuery("SELECT p FROM Usuario p");
			lista = (List<Usuario>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public Usuario listarPorId(Usuario Usuario) throws Exception {
		Usuario us = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario p where p.idUsuario =?1");
			query.setParameter(1, Usuario.getPersona().getIdPersona());

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				us = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return us;
	}

	@Override
	public Usuario login(Usuario us) {
		Usuario usuario = null;
		String consulta;
		try {
			consulta = "FROM Usuario u WHERE u.usuario = ?1 and u.contrasena = ?2";
			Query query = em.createQuery(consulta);
			query.setParameter(1, us.getUsuario());
			query.setParameter(2, us.getContrasena());

			List<Usuario> lista = query.getResultList();
			if (!lista.isEmpty()) {
				usuario = lista.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return usuario;
	}

	@Override
	public String traerPassHashed(String us) {
		Usuario usuario = null;
		String consulta;
		try {
			consulta = "FROM Usuario u WHERE u.usuario = ?1";
			Query query = em.createQuery(consulta);
			query.setParameter(1, us);

			List<Usuario> lista = query.getResultList();
			if (!lista.isEmpty()) {
				usuario = lista.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return usuario != null ? usuario.getContrasena() : "";
	}

	@Override
	public Usuario traerIdUsuario(String nombreUsuario) {
		Usuario usuario = null;
		String consulta;
		try {
			consulta = "FROM Usuario u WHERE u.usuario = ?1";
			Query query = em.createQuery(consulta);
			query.setParameter(1, nombreUsuario);

			List<Usuario> lista = query.getResultList();
			if (!lista.isEmpty()) {
				usuario = lista.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return usuario != null ? usuario : new Usuario();
	}
}
