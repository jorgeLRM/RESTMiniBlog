package com.mitocode.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mitocode.config.Secure;
import com.mitocode.model.Persona;
import com.mitocode.service.IPersonaService;

@Path("/personas")
public class PersonaAPI {
	
	@Inject
	private IPersonaService service;
	
	@GET
	@Path("/saludar")
	@Produces("application/json")
	public Persona saludar() {
		Persona per = new Persona();
		//per.setId(1);
		//per.setNombre("MitoCode");

		return per;
	}
	
	@GET
	@Path("/foto/{id}")
	@Produces("application/octet-stream")
	@Secure
	public Response traerFoto(@PathParam("id") Integer id) {
		Persona per = new Persona();
		try {			
			
			per.setIdPersona(id);
			per = this.service.listarPorId(per);				
		}catch(Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_OCTET_STREAM_TYPE).entity(per.getFoto()).build();
	}


}
