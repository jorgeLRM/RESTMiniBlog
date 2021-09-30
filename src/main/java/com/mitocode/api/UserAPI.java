package com.mitocode.api;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.mitocode.config.Secure;
import com.mitocode.model.Usuario;
import com.mitocode.service.IUsuarioService;
import com.mitocode.service.impl.MySessionStore;
import com.mitocode.util.SecurityUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Path("/usuarios")
public class UserAPI {
	
	@Context
	private UriInfo uriInfo;
	@Inject
	private MySessionStore sessionStore;
	@Inject
	private SecurityUtil securityUtil;
	@Inject
	private IUsuarioService usuarioService;


	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("email") String email, @FormParam("password") String password) {
		try {
			Usuario user = authenticateUser(email, password);
			String token = getToken(email, password);

			sessionStore.setNombreUsuario(user.getUsuario());
			sessionStore.setIdUsuario(user.getId());
			sessionStore.setClave(securityUtil.encodeText(password));
			
			return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
			// return Response.ok().entity(token).build();
		} catch (Exception e) {
			return Response.status(UNAUTHORIZED).build();
		}
	}

	private String getToken(String email, String password) {
		Key key = securityUtil.generateKey(securityUtil.encodeText(password));
		String token = Jwts.builder().setSubject(email).setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date()).setExpiration(toDate(LocalDateTime.now().plusSeconds(2000)))
				.signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString()).compact();
		// System.out.println(token);
		return token;
	}

	private Usuario authenticateUser(String usuario, String password) throws Exception {

		Usuario us = new Usuario();
		us.setUsuario(usuario);
		us.setContrasena(password);

		Usuario user = usuarioService.login(us);

		if (user == null) {
			System.out.println("***********Email o password incorrecto****************");
			user = new Usuario();
			throw new SecurityException("Email o password incorrecto");
		}
		
		return user;
	}

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	@GET
	@Path("/{nombre}")
	@Produces("application/json")
	@Secure
	public Response listar(@PathParam("nombre") String nombre) {
		Usuario user = new Usuario();
		try {
			user = this.usuarioService.traerIdUsuario(nombre);			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return Response.status(Response.Status.OK).entity(user).build();		
		
	}
}
