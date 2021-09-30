package com.mitocode.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.mitocode.dao.IRolDAO;
import com.mitocode.model.Rol;

@Stateless
public class RolDAOImpl implements IRolDAO, Serializable {

	@PersistenceContext(unitName = "blogPU")
	private EntityManager em;

	@Override
	public Integer registrar(Rol per) throws Exception {
		em.persist(per);
		return per.getId();
	}

	@Override
	public Integer modificar(Rol per) throws Exception {
		em.merge(per);
		return per.getId();
	}

	@Override
	public List<Rol> listar() throws Exception {
		List<Rol> lista = new ArrayList<Rol>();

		try {
			Query query = em.createQuery("SELECT p FROM Rol p");
			lista = (List<Rol>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public Rol listarPorId(Rol Rol) throws Exception {
		Rol per = new Rol();
		List<Rol> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Rol p where p.idRol =?1");
			query.setParameter(1, Rol.getId());

			lista = (List<Rol>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				per = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return per;
	}

}
