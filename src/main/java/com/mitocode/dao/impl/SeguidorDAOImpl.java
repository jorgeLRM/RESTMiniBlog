package com.mitocode.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.mitocode.dao.ISeguidorDAO;
import com.mitocode.model.Persona;
import com.mitocode.model.PublicadorSeguidor;

@Stateless
public class SeguidorDAOImpl implements ISeguidorDAO, Serializable {

	@PersistenceContext(unitName = "blogPU")
	private EntityManager em;

	@Override
	public Integer registrarPublicadoresSeguidores(List<PublicadorSeguidor> publicadores_seguidores) {
		try {
			int[] iarr = { 0 };
			publicadores_seguidores.forEach(ps -> {
				em.persist(ps);
				if (iarr[0] % 100 == 0) {
					em.flush();
					em.clear();
				}
				iarr[0]++;
			});
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

	@Override
	public List<PublicadorSeguidor> listarSeguidores(Persona per) {
		List<PublicadorSeguidor> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM PublicadorSeguidor p where p.publicador.idPersona =?1");
			query.setParameter(1, per.getIdPersona());

			lista = (List<PublicadorSeguidor>) query.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}
	
	@Override
	public List<PublicadorSeguidor> listarSeguidos(Persona per) {
		List<PublicadorSeguidor> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM PublicadorSeguidor p where p.seguidor.idPersona =?1");
			query.setParameter(1, per.getIdPersona());

			lista = (List<PublicadorSeguidor>) query.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public Integer dejarSeguir(List<PublicadorSeguidor> publicadores_seguidores) {
		int rpta = 0;
		try {

			publicadores_seguidores.forEach(ps -> {
				Query query = em.createQuery(
						"DELETE FROM PublicadorSeguidor WHERE publicador.idPersona =?1 AND seguidor.idPersona = ?2");
				query.setParameter(1, ps.getPublicador().getIdPersona());
				query.setParameter(2, ps.getSeguidor().getIdPersona());

				query.executeUpdate();
			});
			rpta = 1;

		} catch (Exception e) {
			rpta = 0;
		}
		return rpta;
	}


}
