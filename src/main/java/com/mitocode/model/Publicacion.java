package com.mitocode.model;

import java.io.Serializable;
import java.util.List;

import javax.json.bind.annotation.JsonbNillable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "publicacion")
//https://developer.jboss.org/thread/278550
public class Publicacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_publicador", nullable = false)
	private Persona publicador;

	@Column(name = "cuerpo", nullable = false, length = 250)
	private String cuerpo;

	@JsonIgnore
	@OneToMany(mappedBy = "publicacion", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Tag> tags;

	@JsonIgnore
	@OneToMany(mappedBy = "publicacion", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Mencion> menciones;

	public Publicacion() {

	}

	public Publicacion(Integer id, Persona publicador, String cuerpo) {	
		this.id = id;
		this.publicador = publicador;
		this.cuerpo = cuerpo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persona getPublicador() {
		return publicador;
	}

	public void setPublicador(Persona publicador) {
		this.publicador = publicador;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Mencion> getMenciones() {
		return menciones;
	}

	public void setMenciones(List<Mencion> menciones) {
		this.menciones = menciones;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

}
