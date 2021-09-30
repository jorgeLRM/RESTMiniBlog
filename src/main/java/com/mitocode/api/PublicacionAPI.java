package com.mitocode.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mitocode.config.Secure;
import com.mitocode.model.Persona;
import com.mitocode.model.Publicacion;
import com.mitocode.service.IPublicacionService;

@Path("/publicaciones")
public class PublicacionAPI {
	
	@Inject
	private IPublicacionService service;	
	private List<Publicacion> publicaciones;
	
	@GET
	@Path("/listar/{id}")
	@Produces("application/json")
	@Secure
	public Response listar(@PathParam("id") Integer id){
		try {
			Persona per = new Persona();
			per.setIdPersona(id);
			this.publicaciones = this.service.listarPublicacionesPorPublicador(per);
		}catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).entity(publicaciones).build();
	}

	@POST
	@Path("/publicar")
	@Produces("application/json")
	@Consumes("application/json")
	@Secure
	public Response publicar(Publicacion p) {
		try {
			int rpta = this.service.registrar(p);
		}catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).entity(p).build();
	}
}
