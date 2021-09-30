package com.mitocode.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import com.mitocode.dao.IPublicacionDAO;
import com.mitocode.model.Mencion;
import com.mitocode.model.Persona;
import com.mitocode.model.Publicacion;
import com.mitocode.model.Tag;
import com.mitocode.service.IPublicacionService;

@Named
public class PublicacionServiceImpl implements Serializable, IPublicacionService {

	@EJB
	private IPublicacionDAO dao;

	@Override
	public int registrar(Publicacion t) throws Exception {
		// t.getTags().forEach(e -> e.setPublicacion(t));
		// t.getMenciones().forEach(e -> e.setPublicacion(t));

		List<Tag> tags = new ArrayList<>();
		List<Mencion> menciones = new ArrayList<>();

		String texto = t.getCuerpo();
		texto = texto.replaceAll(",", " ");
		String[] arreglo = texto.split(" ");
		for (String x : arreglo) {
			if (x.startsWith("@")) {
				x = x.substring(1, x.length());
				menciones.add(new Mencion(t, x)); //new Publicacion() para probar
			}

			if (x.startsWith("#")) {
				x = x.substring(1, x.length());
				tags.add(new Tag(t, x));
			}
		}
		t.setTags(tags);		
		t.setMenciones(menciones);
		int rpta = dao.registrar(t);		
		return rpta > 0 ? 1 : 0;		
	}

	@Override
	public int modificar(Publicacion t) throws Exception {
		int rpta = dao.modificar(t);
		return rpta > 0 ? 1 : 0;
	}

	@Override
	public List<Publicacion> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Publicacion listarPorId(Publicacion t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publicacion> listarPublicacionesPorPublicador(Persona publicador) throws Exception {
		return dao.listarPublicacionesPorPublicador(publicador);
	}

	@Override
	public List<Publicacion> listarPublicacionesDeSeguidores(Persona per) {		
		return dao.listarPublicacionesDeSeguidores(per);
	}

}
