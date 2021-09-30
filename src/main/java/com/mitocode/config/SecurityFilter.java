package com.mitocode.config;

import java.io.IOException;
import java.security.Key;
import java.security.Principal;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.mitocode.service.impl.MySessionStore;
import com.mitocode.util.SecurityUtil;

import io.jsonwebtoken.Jwts;

@Provider
@Secure
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter{

	private static final String BEARER = "Bearer";
	
	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private MySessionStore sessionStore;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if (authHeader == null || !authHeader.startsWith(BEARER)) {
			throw new NotAuthorizedException("No estás autorizado");
		}
		
		String token = authHeader.substring(BEARER.length()).trim();
		
		
		//Decodificar el token
		try {
			Key key =  securityUtil.generateKey(sessionStore.getClave());

			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			SecurityContext securityContext = requestContext.getSecurityContext();
			requestContext.setSecurityContext(new SecurityContext() {

				@Override
				public Principal getUserPrincipal() {
					return () -> Jwts.parser().setSigningKey(key).parseClaimsJwt(token).getBody().getSubject();
				}

				@Override
				public boolean isUserInRole(String role) {
					return securityContext.isUserInRole(role);
				}

				@Override
				public boolean isSecure() {
					return securityContext.isSecure();
				}

				@Override
				public String getAuthenticationScheme() {
					return securityContext.getAuthenticationScheme();
				}

			});
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		
	}

}
