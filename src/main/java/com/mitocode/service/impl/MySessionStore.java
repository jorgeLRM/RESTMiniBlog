package com.mitocode.service.impl;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class MySessionStore implements Serializable {

	private Integer idUsuario;
	private String nombreUsuario;
	private String clave;

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
